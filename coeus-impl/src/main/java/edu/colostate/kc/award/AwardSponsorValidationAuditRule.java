/*
 * Copyright 2005-2013 The Kuali Foundation
 * 
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/ecl1.php
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.colostate.kc.award;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.kuali.coeus.sys.framework.gv.GlobalVariableService;
import org.kuali.coeus.sys.framework.service.KcServiceLocator;
import org.kuali.kra.award.customdata.AwardCustomData;
import org.kuali.kra.award.document.AwardDocument;
import org.kuali.kra.award.home.Award;
import org.kuali.kra.infrastructure.Constants;
import org.kuali.kra.infrastructure.KeyConstants;
import org.kuali.rice.core.api.CoreApiServiceLocator;
import org.kuali.rice.krad.util.AuditCluster;
import org.kuali.rice.krad.util.AuditError;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.rules.rule.DocumentAuditRule;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.coeus.common.api.sponsor.SponsorService;
import org.springframework.util.StringUtils;

/**
 * This class processes audit rules (warnings) for the Report Information related
 * data of the AwardDocument.
 */
public class AwardSponsorValidationAuditRule implements DocumentAuditRule {

    private static final String AWARD_SPONSOR_ERROR_KEY = "document.awardList[0].sponsorCode";
    private static final String AWARD_CFDA_ERROR_KEY = "document.awardList[0].cfdaNumber";
    private static final String AWARD_SCO_WARNING_KEY = "customDataHelper.customDataList[6].value";
    private static final String ERROR_AWARD_DISALLOWED_SPONSOR = "error.award.sponsor.disallowed";
    private static final String ERROR_AWARD_INVALID_SPONSOR = "error.award.sponsor.invalid";
    private static final String ERROR_AWARD_MISSING_CODE = "error.award.code.missing";
    private static final String WARNING_AWARD_MISSING_SCO = "warning.award.sco.missing";
    private static final String SPONSOR_AUDIT_ERRORS = "homePageAuditErrors";
    private static final String CUSTOM_DATA_AUDIT_ERRORS = "customDataAuditErrors";
    private List<AuditError> auditErrors;
    private List<AuditError> auditWarnings;
    
    /**
     * 
     * Constructs a AwardSponsorContactAuditRule.java. Added so unit test would not
     * need to call processRunAuditBusinessRules and therefore declare a document.
     */
    public AwardSponsorValidationAuditRule() {
        auditErrors = new ArrayList<AuditError>();
        auditWarnings = new ArrayList<AuditError>();
    }
    
    /**
     * @see org.kuali.core.rule.DocumentAuditRule#processRunAuditBusinessRules(org.kuali.core.document.Document)
     */
    public boolean processRunAuditBusinessRules(Document document) {
        boolean valid = true;
        AwardDocument awardDocument = (AwardDocument)document;
        auditErrors = new ArrayList<AuditError>();
        auditWarnings = new ArrayList<AuditError>();
        valid &= checkForAllowedSponsor(awardDocument);
        valid &= checkForValidSponsor(awardDocument);
        if (valid) {
        	valid &= checkForCorrectCode(awardDocument);
        }
        reportAndCreateAuditCluster();
        
        return valid;
    }
        
    /**
     * This method creates and adds the AuditCluster to the Global AuditErrorMap.
     */
    @SuppressWarnings("unchecked")
    protected void reportAndCreateAuditCluster() {
    	 if (auditWarnings.size() > 0) {
             AuditCluster existingWarnings = (AuditCluster) getGlobalVariableService().getAuditErrorMap().get(CUSTOM_DATA_AUDIT_ERRORS);
             if (existingWarnings == null) {
                 getGlobalVariableService().getAuditErrorMap().put(CUSTOM_DATA_AUDIT_ERRORS, new AuditCluster("General",
                                                                                               auditWarnings, Constants.AUDIT_WARNINGS));
             } else {
                 existingWarnings.getAuditErrorList().addAll(auditWarnings);
             }
        }
        if (auditErrors.size() > 0) {
            AuditCluster existingErrors = (AuditCluster) getGlobalVariableService().getAuditErrorMap().get(SPONSOR_AUDIT_ERRORS);
            if (existingErrors == null) {
                getGlobalVariableService().getAuditErrorMap().put(SPONSOR_AUDIT_ERRORS, new AuditCluster(Constants.MAPPING_AWARD_HOME_DETAILS_AND_DATES_PAGE_NAME,
                                                                                              auditErrors, Constants.AUDIT_ERRORS));
            } else {
                existingErrors.getAuditErrorList().addAll(auditErrors);
            }
        }
    }
    
    protected boolean checkForAllowedSponsor(AwardDocument awardDocument) {
    	String sponsorCode = awardDocument.getAward().getSponsorCode();
    	String invalidSponsorList = CoreApiServiceLocator.getKualiConfigurationService().getPropertyValueAsString("csu.kc.disallowed.sponsors");
    	if (invalidSponsorList==null || invalidSponsorList.isEmpty()) {
    		return true;
    	}
    	StringTokenizer sponsorTokens = new StringTokenizer(invalidSponsorList,",");
    	while (sponsorTokens.hasMoreTokens()) {
    		if (sponsorCode.equalsIgnoreCase(sponsorTokens.nextToken())) {
    			auditErrors.add(new AuditError(AWARD_SPONSOR_ERROR_KEY, ERROR_AWARD_DISALLOWED_SPONSOR,                    
                        Constants.MAPPING_AWARD_HOME_PAGE + "." + Constants.MAPPING_AWARD_HOME_DETAILS_AND_DATES_PAGE_ANCHOR, new String[]{sponsorCode}));
    			return false;
    		}
    	}
        return true;
    }

    protected boolean checkForValidSponsor(AwardDocument awardDocument) {
    	SponsorService ss = KcServiceLocator.getService(SponsorService.class);
        if (!ss.isValidSponsor(awardDocument.getAward().getSponsor())) {
        	auditErrors.add(new AuditError(AWARD_SPONSOR_ERROR_KEY, ERROR_AWARD_INVALID_SPONSOR,                    
        			Constants.MAPPING_AWARD_HOME_PAGE + "." + Constants.MAPPING_AWARD_HOME_DETAILS_AND_DATES_PAGE_ANCHOR, new String[]{awardDocument.getAward().getSponsorCode()}));
			return false;
        }
    	return true;
    }
    
    protected boolean hasSCOCode(Award award) {
    	List<AwardCustomData> acdList = award.getAwardCustomDataList();
    	for (AwardCustomData acd : acdList) {
    		if (acd != null) {
    			if (acd.getCustomAttributeId()==13 && acd.getValue() != null && !acd.getValue().isEmpty()) {
    				return true;
    			}
    		}
    	}
    	
    	return false;
    }
    
    protected boolean checkForCorrectCode(AwardDocument awardDocument) {
    	Award award = awardDocument.getAward();
    	if (award.getAwardTypeCode()!=5 && award.getSponsor().getSponsorTypeCode().equals("A") || (award.getPrimeSponsor() !=null && award.getPrimeSponsor().getSponsorTypeCode().equals("A"))) {
    		if (award.getAwardTypeCode()==1) {
    			if (!hasSCOCode(award)) {
    				// make warning for not having SCO for Contract
    				auditWarnings.add(new AuditError(AWARD_SCO_WARNING_KEY, WARNING_AWARD_MISSING_SCO,
    						Constants.MAPPING_AWARD_CUSTOM_DATA_PAGE + ".General"));
    				return false;
    			}
    		}
    		else
    		{
    			if (!((StringUtils.hasText(award.getCfdaNumber()))^(hasSCOCode(award)))) {
    				// make error for non-contract Federal award needing EITHER SCO or CFDA
    				auditErrors.add(new AuditError(AWARD_CFDA_ERROR_KEY, ERROR_AWARD_MISSING_CODE,
    						Constants.MAPPING_AWARD_HOME_PAGE + "." + Constants.MAPPING_AWARD_HOME_DETAILS_AND_DATES_PAGE_ANCHOR));
    				return false;
    			}
    		}
    	} 	
    	return true;
    }
    private GlobalVariableService getGlobalVariableService() {
        return KcServiceLocator.getService(GlobalVariableService.class);
    }

}
