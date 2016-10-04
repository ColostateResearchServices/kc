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
package edu.colostate.kc.award.customdata;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.kuali.coeus.sys.framework.gv.GlobalVariableService;
import org.kuali.coeus.sys.framework.service.KcServiceLocator;
import org.kuali.kra.award.customdata.AwardCustomData;
import org.kuali.kra.award.document.AwardDocument;
import org.kuali.kra.award.home.Award;
import org.kuali.kra.infrastructure.Constants;
import org.kuali.rice.krad.util.AuditCluster;
import org.kuali.rice.krad.util.AuditError;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.rules.rule.DocumentAuditRule;


/**
 * This class processes audit rules (warnings) for the Report Information related
 * data of the AwardDocument.
 */
public class AwardCustomDataAuditRule implements DocumentAuditRule {


    private static final String AWARD_EXP_DATE_ERROR_KEY = "customDataHelper.customDataList[24].value";
    private static final String ERROR_AWARD_EXP_DATE_BEFORE_EFFECTIVE = "error.award.expirationDate.beforeEffective";
    private static final String ERROR_AWARD_EXP_DATE_BEFORE_TODAY = "error.award.expirationDate.beforeToday";
    private static final String ERROR_AWARD_EXP_DATE_AFTER_TODAY = "error.award.expirationDate.afterToday";
    private static final String CUSTOM_DATA_GENERAL_ERRORS = "CustomDataGeneralErrors";
    private String CUSTOM_DATA_GROUP_NAME = "General";
    private List<AuditError> auditErrors;
    
    /**
     * 
     * Constructs a AwardSponsorContactAuditRule.java. Added so unit test would not
     * need to call processRunAuditBusinessRules and therefore declare a document.
     */
    public AwardCustomDataAuditRule() {
        auditErrors = new ArrayList<AuditError>();
    }
    
    /**
     * @see org.kuali.core.rule.DocumentAuditRule#processRunAuditBusinessRules(org.kuali.core.document.Document)
     */
    public boolean processRunAuditBusinessRules(Document document) {
        boolean valid = true;
        AwardDocument awardDocument = (AwardDocument)document;
        auditErrors = new ArrayList<AuditError>();
        valid &= checkForProperExpiration(awardDocument);
        reportAndCreateAuditCluster();
        
        return valid;
    }
        
    /**
     * This method creates and adds the AuditCluster to the Global AuditErrorMap.
     */
    @SuppressWarnings("unchecked")
    protected void reportAndCreateAuditCluster() {
        if (auditErrors.size() > 0) {
            AuditCluster existingErrors = (AuditCluster) getGlobalVariableService().getAuditErrorMap().get(CUSTOM_DATA_GENERAL_ERRORS);
            if (existingErrors == null) {
				getGlobalVariableService().getAuditErrorMap().put(CUSTOM_DATA_GENERAL_ERRORS, new AuditCluster(CUSTOM_DATA_GROUP_NAME,
                                                                                              auditErrors, Constants.AUDIT_ERRORS));
            } else {
                existingErrors.getAuditErrorList().addAll(auditErrors);
            }
        }
    }
    
    protected String getExpirationDate(Award award) {
    	List<AwardCustomData> acdList = award.getAwardCustomDataList();
    	for (AwardCustomData acd : acdList) {
    		if (acd != null) {
    			if (acd.getCustomAttributeId()==6 && acd.getValue() != null) {
    				return acd.getValue();
    			}
    		}
    	}
    	
    	return null;
    }
    
    protected boolean checkForProperExpiration(AwardDocument awardDocument) {
    	boolean isValid=true;
    	Award award = awardDocument.getAward();
    	Integer awardTransactionTypeCode=award.getAwardTransactionTypeCode();
    	
    	if (awardTransactionTypeCode.intValue()!=1 && awardTransactionTypeCode.intValue()!=3) {
    		return true;
    	}
    	
    	String expDateStr = getExpirationDate(award);
    	if (expDateStr==null) {
    		return false;
    	}
    	Date expDate;
    	try {
    		expDate=(new SimpleDateFormat("MM/dd/yyyy")).parse(expDateStr);
    	}
    	catch (Exception e) {
    		System.err.println(e.toString());
    		return false;
    	}
    	
    	expDateStr=(new SimpleDateFormat("yyyyMMdd")).format(expDate);
    	
    	
    	Date effectiveDate = award.getAwardEffectiveDate();
    	String todaysDateStr = (new SimpleDateFormat("yyyyMMdd")).format(new Date());
    	
    	if (awardTransactionTypeCode.intValue()==1) {
    		if (effectiveDate!=null) {
    			String effectiveDateStr = (new SimpleDateFormat("yyyyMMdd")).format(effectiveDate);
    			if (expDateStr.compareTo(effectiveDateStr)<0) {
    				auditErrors.add(new AuditError(AWARD_EXP_DATE_ERROR_KEY, ERROR_AWARD_EXP_DATE_BEFORE_EFFECTIVE,
						Constants.MAPPING_AWARD_CUSTOM_DATA_PAGE + ".General"));
    				isValid=false;
    			}
    		}	
    		
    		if (expDateStr.compareTo(todaysDateStr)<0) {
    			auditErrors.add(new AuditError(AWARD_EXP_DATE_ERROR_KEY, ERROR_AWARD_EXP_DATE_BEFORE_TODAY,
					Constants.MAPPING_AWARD_CUSTOM_DATA_PAGE + ".General"));
    			isValid=false;
    		}
    	}
    	else
    	{
    		if (expDateStr.compareTo(todaysDateStr)>0) {
    			auditErrors.add(new AuditError(AWARD_EXP_DATE_ERROR_KEY, ERROR_AWARD_EXP_DATE_AFTER_TODAY,
					Constants.MAPPING_AWARD_CUSTOM_DATA_PAGE + ".General"));
    			isValid=false;
    		}
    	}
    	
    	return isValid;
    }
	private GlobalVariableService getGlobalVariableService() {
		return KcServiceLocator.getService(GlobalVariableService.class);
	}

}
