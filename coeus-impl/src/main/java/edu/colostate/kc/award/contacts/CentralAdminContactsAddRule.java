package edu.colostate.kc.award.contacts;

import org.kuali.rice.krad.rules.rule.BusinessRule;


public interface CentralAdminContactsAddRule extends BusinessRule {
	
	/**
     * Rule invoked upon adding a CentralAdminContact
     *
     * @return boolean
     */
    boolean processAddCentralAdminContactRules(CentralAdminContactsAddEvent event);

}
