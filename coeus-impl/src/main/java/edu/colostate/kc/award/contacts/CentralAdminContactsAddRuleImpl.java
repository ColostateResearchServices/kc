package edu.colostate.kc.award.contacts;

import org.apache.commons.lang.StringUtils;
import org.kuali.coeus.sys.framework.rule.KcTransactionalDocumentRuleBase;

import edu.colostate.kc.infrastructure.CSUKeyConstants;

public class CentralAdminContactsAddRuleImpl extends KcTransactionalDocumentRuleBase
		implements CentralAdminContactsAddRule {
	
	
	private static final String NEW_CENT_ADMIN_CONTACT = "csuCentralAdminContactsHelper.newCentralAdminContact";
	private static final String NAME = ".fullName";
	
	

	@Override
	public boolean processAddCentralAdminContactRules(
			CentralAdminContactsAddEvent event) {
		return validateAddedContact(event.getNewContact());
	}
	
	
	private boolean validateAddedContact(CsuCentralAdminContact contact){
		
	    boolean valid = true;
	    
	    if (StringUtils.isEmpty(contact.getPersonId())) {
	        valid = false;
	        reportError(NEW_CENT_ADMIN_CONTACT+NAME, 
	                CSUKeyConstants.CSU_AWARD_CENTRAL_ADMIN_CONTACT_NAME);
	    }
	    return valid;
	}

}
