package edu.colostate.kc.award.contacts;

import org.kuali.coeus.sys.framework.rule.KcDocumentEventBase;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.rules.rule.BusinessRule;

public class CentralAdminContactsAddEvent extends KcDocumentEventBase {
	
	
	private CsuCentralAdminContact newContact;
	
	protected CentralAdminContactsAddEvent(String description,
			String errorPathPrefix, 
			CsuCentralAdminContact newCentralAdminContact, 
			Document document) {
		super(description, errorPathPrefix, document);
		this.newContact  = newCentralAdminContact;
	}

	@Override
	public Class<CentralAdminContactsAddRule> getRuleInterfaceClass() {
		return CentralAdminContactsAddRule.class;
	}

	@Override
	public boolean invokeRuleMethod(BusinessRule rule) {
        return this.getRuleInterfaceClass().cast(rule).processAddCentralAdminContactRules(this);
	}

	@Override
	protected void logEvent() {
		// TODO Auto-generated method stub

	}

	public CsuCentralAdminContact getNewContact() {
		return newContact;
	}

	public void setNewContact(CsuCentralAdminContact newContact) {
		this.newContact = newContact;
	}

}
