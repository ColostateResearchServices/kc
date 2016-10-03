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
package edu.colostate.kc.award.home.fundingproposal;

import java.util.ArrayList;
import java.util.List;

import org.kuali.coeus.sys.framework.gv.GlobalVariableService;
import org.kuali.coeus.sys.framework.service.KcServiceLocator;
import org.kuali.kra.award.awardhierarchy.AwardHierarchy;
import org.kuali.kra.award.awardhierarchy.AwardHierarchyService;
import org.kuali.kra.award.document.AwardDocument;
import org.kuali.kra.award.home.fundingproposal.AwardFundingProposal;
import org.kuali.kra.infrastructure.Constants;
import org.kuali.rice.kim.api.role.RoleService;
import org.kuali.rice.krad.util.AuditCluster;
import org.kuali.rice.krad.util.AuditError;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.rules.rule.DocumentAuditRule;

/**
 * This class processes audit rules (warnings) for the Report Information related
 * data of the AwardDocument.
 */
public class AwardFundingProposalAuditRule implements DocumentAuditRule {

    private static final String AWARD_FUNDING_PROPOSALS_WARNING_KEY = "fundingProposalBean.newFundingProposal.proposalNumber";
    private static final String WARNING_AWARD_NO_FUNDING_PROPOSAL = "warning.awardFundingProposal.none";
    private static final String FUNDING_PROPOSALS_AUDIT_WARNINGS = "fundingProposalsAuditWarnings";
    private List<AuditError> auditWarnings;

    
    /**
     * 
     * Constructs a AwardFundingProposalAuditRule.java. Added so unit test would not
     * need to call processRunAuditBusinessRules and therefore declare a document.
     */
    public AwardFundingProposalAuditRule() {
        auditWarnings = new ArrayList<AuditError>();
    }
    
    /**
     * @see org.kuali.core.rule.DocumentAuditRule#processRunAuditBusinessRules(org.kuali.core.document.Document)
     */
    public boolean processRunAuditBusinessRules(Document document) {
        boolean valid = true;
        AwardDocument awardDocument = (AwardDocument)document;
        auditWarnings = new ArrayList<AuditError>();
        
        valid &= checkForAtLeastOneFundingProposal(awardDocument);
         
        reportAndCreateAuditCluster();
        
        return valid;
    }
        
    /**
     * This method creates and adds the AuditCluster to the Global AuditErrorMap.
     */
    @SuppressWarnings("unchecked")
    protected void reportAndCreateAuditCluster() {
        if (auditWarnings.size() > 0) {
            AuditCluster existingWarnings = (AuditCluster) getGlobalVariableService().getAuditErrorMap().get(FUNDING_PROPOSALS_AUDIT_WARNINGS);
            if (existingWarnings == null) {
                getGlobalVariableService().getAuditErrorMap().put(FUNDING_PROPOSALS_AUDIT_WARNINGS, new AuditCluster("Funding Proposals",
                                                                                              auditWarnings, Constants.AUDIT_WARNINGS));
            } else {
                existingWarnings.getAuditErrorList().addAll(auditWarnings);
            }
        }
    }
    
    protected boolean checkForAtLeastOneFundingProposal(AwardDocument awardDocument) {
    	AwardHierarchyService ahService = KcServiceLocator.getService(AwardHierarchyService.class);
    	AwardHierarchy hierarchy = ahService.loadAwardHierarchy(awardDocument.getAward().getAwardNumber());
    	if (hierarchy==null || hierarchy.getParent()==null || hierarchy.getAwardNumber().equals(hierarchy.getParentAwardNumber())) {return true;}
    	List<AwardFundingProposal> fundingProposalList = awardDocument.getAward().getFundingProposals();
        if (fundingProposalList.isEmpty()) {
            auditWarnings.add(new AuditError(AWARD_FUNDING_PROPOSALS_WARNING_KEY, WARNING_AWARD_NO_FUNDING_PROPOSAL,                    
                    Constants.MAPPING_AWARD_HOME_PAGE + "." + "fundingProposals"));
            return false;
        } else {
            return true;
        }
    }
    private GlobalVariableService getGlobalVariableService() {
        return KcServiceLocator.getService(GlobalVariableService.class);
    }

}
