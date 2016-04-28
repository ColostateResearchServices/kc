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
package edu.colostate.kc.award.contacts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.kuali.kra.award.document.AwardDocument;
import org.kuali.kra.award.home.Award;
import org.kuali.kra.award.lookup.keyvalue.AwardUnitContactTypeValuesFinder;
import org.kuali.kra.infrastructure.Constants;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.coeus.sys.framework.service.KcServiceLocator;
import org.kuali.rice.kim.api.permission.PermissionService;
import org.kuali.rice.kim.api.role.RoleService;
// import org.kuali.rice.kim.api.identity.IdentityService;
import org.kuali.rice.krad.util.AuditCluster;
import org.kuali.rice.krad.util.AuditError;
import org.kuali.rice.kns.util.KNSGlobalVariables;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.rules.rule.DocumentAuditRule;
import org.kuali.kra.award.contacts.AwardPerson;
import org.kuali.kra.award.contacts.AwardUnitContact;


/**
 * This class processes audit rules (warnings) for the Report Information related
 * data of the AwardDocument.
 */
public class AwardUnitContactsAuditRule implements DocumentAuditRule {

    private static final String AWARD_UNIT_CONTACT_LIST_ERROR_KEY = "document.awardList[0].awardUnitContacts.auditErrors";
    private static final String ERROR_AWARD_UNIT_CONTACT_MISSING = "error.awardUnitContact.missing";
    private static final String ERROR_AWARD_PI_IS_MANAGER = "error.awardUnitContacts.piAsManager";
    private static final String ERROR_AWARD_UNIT_ADMIN_NO_KFS_ROLE = "error.awardUnitContact.noKFSRole";
    private static final String ERROR_AWARD_UNIT_ADMIN_PI_INVALID = "error.awardUnitContact.piNoKFSRole";
    private static final String CONTACTS_AUDIT_ERRORS = "contactsAuditErrors";
    
    private List<AuditError> auditErrors;

    
    /**
     * 
     * Constructs a AwardSponsorContactAuditRule.java. Added so unit test would not
     * need to call processRunAuditBusinessRules and therefore declare a document.
     */
    public AwardUnitContactsAuditRule() {
        auditErrors = new ArrayList<AuditError>();
    }
    
    public boolean processRunAuditBusinessRules(Document document) {
        boolean valid = true;
        AwardDocument awardDocument = (AwardDocument)document;
        auditErrors = new ArrayList<AuditError>();
        
        valid = checkUnitContacts(awardDocument);
         
        reportAndCreateAuditCluster();
        
        return valid;
    }
        
    /**
     * This method creates and adds the AuditCluster to the Global AuditErrorMap.
     */
    @SuppressWarnings("unchecked")
    protected void reportAndCreateAuditCluster() {
        if (auditErrors.size() > 0) {
            AuditCluster existingErrors = (AuditCluster) GlobalVariables.getAuditErrorMap().get(CONTACTS_AUDIT_ERRORS);
            if (existingErrors == null) {
                GlobalVariables.getAuditErrorMap().put(CONTACTS_AUDIT_ERRORS, new AuditCluster(Constants.CONTACTS_PANEL_NAME,
                                                                                              auditErrors, Constants.AUDIT_ERRORS));
            } else {
                existingErrors.getAuditErrorList().addAll(auditErrors);
            }
        }
    }
    
    protected boolean checkUnitContacts(AwardDocument awardDocument) {
    	Award award = awardDocument.getAward();
    	List<AwardUnitContact> unitContacts = award.getAwardUnitContacts();
    	AwardPerson awardPI = award.getPrincipalInvestigator();
    	String FiscalOfficer="Fiscal Officer";
    	String AccountManager="Account Manager";
    	String AccountSupervisor="Account Supervisor";
    	boolean isValid = true;
    	boolean hasAcctSupervisor = false;
    	Vector<String> requiredContacts=new Vector<String>();
    	requiredContacts.add(FiscalOfficer);
    	requiredContacts.add(AccountManager);

        PermissionService permService = KcServiceLocator.getService(PermissionService.class);
        String unitAdministratorTypeCode="";
        AwardUnitContactTypeValuesFinder unitContactTypeValuesFinder = new AwardUnitContactTypeValuesFinder();
        String unitAdminRole = "";
        HashMap<String,String> qualification=new HashMap<String,String>();
    	for (AwardUnitContact thisContact : unitContacts) {
    		unitAdministratorTypeCode=thisContact.getUnitAdministratorTypeCode();
    		unitAdminRole = unitContactTypeValuesFinder.getKeyLabel(unitAdministratorTypeCode);
    		if (FiscalOfficer.equals(unitAdminRole) || AccountSupervisor.equals(unitAdminRole)) {
    			String unitAdminPrincipalId = thisContact.getPersonId();
    			if (!permService.isAuthorized(unitAdminPrincipalId, "KFS-COA", "Serve As "+unitAdminRole, qualification)) {
//    			if (!permService.hasPermission(unitAdminPrincipalId, "KFS-COA", "Serve As "+unitAdminRole)) {
    				auditErrors.add(new AuditError(AWARD_UNIT_CONTACT_LIST_ERROR_KEY, ERROR_AWARD_UNIT_ADMIN_NO_KFS_ROLE,                    
    	                    Constants.MAPPING_AWARD_CONTACTS_PAGE + "." + Constants.CONTACTS_PANEL_ANCHOR, new String[]{thisContact.getFullName(),unitAdminRole}));
    				isValid = false;
    			}
    			else
    			{
    				if (AccountSupervisor.equals(unitAdminRole)) {hasAcctSupervisor=true;}
    			}
    		}
    		if (AccountManager.equals(unitAdminRole)) {
    			if (awardPI!=null && thisContact.getPersonId().equals(awardPI.getPersonId())) {
    				auditErrors.add(new AuditError(AWARD_UNIT_CONTACT_LIST_ERROR_KEY, ERROR_AWARD_PI_IS_MANAGER,
    						Constants.MAPPING_AWARD_CONTACTS_PAGE + "." +Constants.CONTACTS_PANEL_ANCHOR, null));
    				isValid = false;
    			}
    				
    		}
    		if (requiredContacts.contains(unitAdminRole)) {
    			requiredContacts.remove(unitAdminRole);
    		}	
    	}
    	
    	if (!hasAcctSupervisor) {
    		String awardPIPrincipalId = null;
    		if (awardPI!=null) {
    			awardPIPrincipalId=awardPI.getPersonId();
    		}
    		if (awardPIPrincipalId==null || !permService.isAuthorized(awardPIPrincipalId, "KFS-COA", "Serve As "+AccountSupervisor,qualification)) {
    			auditErrors.add(new AuditError(AWARD_UNIT_CONTACT_LIST_ERROR_KEY, ERROR_AWARD_UNIT_ADMIN_PI_INVALID,                    
	                    Constants.MAPPING_AWARD_CONTACTS_PAGE + "." + Constants.CONTACTS_PANEL_ANCHOR, null));
				isValid = false;   			
    		}
    	}
    	
 //   	String missingContactTypeLabel="";
    	for (String missingContactTypeLabel : requiredContacts) {
//    		missingContactTypeLabel = unitContactTypeValuesFinder.getKeyLabel(missingContactTypeCode);
    		auditErrors.add(new AuditError(AWARD_UNIT_CONTACT_LIST_ERROR_KEY, ERROR_AWARD_UNIT_CONTACT_MISSING,                    
                    Constants.MAPPING_AWARD_CONTACTS_PAGE + "." + Constants.CONTACTS_PANEL_ANCHOR, new String[]{missingContactTypeLabel}));
			isValid = false;
    	}
    	
    	return isValid;
    }
}
