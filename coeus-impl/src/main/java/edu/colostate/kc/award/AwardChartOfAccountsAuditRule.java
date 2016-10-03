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
import org.kuali.kra.award.document.AwardDocument;
import org.kuali.kra.infrastructure.Constants;
import org.kuali.rice.core.api.CoreApiServiceLocator;
import org.kuali.rice.krad.util.AuditCluster;
import org.kuali.rice.krad.util.AuditError;
import org.kuali.rice.kns.util.KNSGlobalVariables;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.rules.rule.DocumentAuditRule;
import org.kuali.rice.krad.service.KRADServiceLocator;

/**
 * This class processes audit rules (warnings) for the Report Information related
 * data of the AwardDocument.
 */
public class AwardChartOfAccountsAuditRule implements DocumentAuditRule {

    private static final String AWARD_CHART_OF_ACCOUNTS_ERROR_KEY = "document.awardList[0].financialChartOfAccountsCode";
    private static final String ERROR_AWARD_INVALID_CHART_OF_ACCOUNTS = "error.awardFinancialChartOfAccounts.invalid";
    private static final String AWARD_CHART_OF_ACCOUNTS_AUDIT_ERRORS = "homePageAuditErrors";
    private List<AuditError> auditErrors;

    
    /**
     * 
     * Constructs a AwardFundingProposalAuditRule.java. Added so unit test would not
     * need to call processRunAuditBusinessRules and therefore declare a document.
     */
    public AwardChartOfAccountsAuditRule() {
        auditErrors = new ArrayList<AuditError>();
    }
    
    /**
     * @see org.kuali.core.rule.DocumentAuditRule#processRunAuditBusinessRules(org.kuali.core.document.Document)
     */
    public boolean processRunAuditBusinessRules(Document document) {
        boolean valid = true;
        AwardDocument awardDocument = (AwardDocument)document;
        auditErrors = new ArrayList<AuditError>();
        
        valid &= checkValidChartOfAccounts(awardDocument);
         
        reportAndCreateAuditCluster();
        
        return valid;
    }
        
    /**
     * This method creates and adds the AuditCluster to the Global AuditErrorMap.
     */
    @SuppressWarnings("unchecked")
    protected void reportAndCreateAuditCluster() {
        if (auditErrors.size() > 0) {
            AuditCluster existingErrors = (AuditCluster) getGlobalVariableService().getAuditErrorMap().get(AWARD_CHART_OF_ACCOUNTS_AUDIT_ERRORS);
            if (existingErrors == null) {
                getGlobalVariableService().getAuditErrorMap().put(AWARD_CHART_OF_ACCOUNTS_AUDIT_ERRORS, new AuditCluster("Institution",
                                                                                              auditErrors, Constants.AUDIT_ERRORS));
            } else {
                existingErrors.getAuditErrorList().addAll(auditErrors);
            }
        }
    }
    
    protected boolean checkValidChartOfAccounts(AwardDocument awardDocument) {
    	if (awardDocument.getAward().getAccountNumber() == null) {
    		return true;
    	}
    	String coaCode = awardDocument.getAward().getFinancialChartOfAccountsCode();
    	if (coaCode != null) {	
    		String validCoaList = CoreApiServiceLocator.getKualiConfigurationService().getPropertyValueAsString("csu.kc.valid.coa");
    		if (validCoaList==null || validCoaList.isEmpty()) {
    			return false;
    		}
    		StringTokenizer coaTokens = new StringTokenizer(validCoaList,",");
    		while (coaTokens.hasMoreTokens()) {
    			if (coaCode.equals(coaTokens.nextToken())) {
    				return true;
    			}
    		}
    	}
    	auditErrors.add(new AuditError(AWARD_CHART_OF_ACCOUNTS_ERROR_KEY, ERROR_AWARD_INVALID_CHART_OF_ACCOUNTS,                    
    		Constants.MAPPING_AWARD_HOME_PAGE + "." + Constants.MAPPING_AWARD_HOME_DETAILS_AND_DATES_PAGE_ANCHOR, new String[]{coaCode}));
    	return false;
    }
    private GlobalVariableService getGlobalVariableService() {
        return KcServiceLocator.getService(GlobalVariableService.class);
    }


}