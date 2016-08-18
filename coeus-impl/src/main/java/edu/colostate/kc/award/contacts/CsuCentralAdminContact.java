package edu.colostate.kc.award.contacts;

import org.kuali.kra.award.contacts.AwardUnitContact;
import org.kuali.kra.award.home.ContactRole;
import org.kuali.coeus.common.framework.person.KcPerson;
import org.kuali.coeus.common.framework.rolodex.NonOrganizationalRolodex;
import org.kuali.coeus.common.framework.unit.UnitContactType;

public class CsuCentralAdminContact extends AwardUnitContact {
	
	public CsuCentralAdminContact() {
		super();
	}
	
	public CsuCentralAdminContact(KcPerson person, ContactRole role, UnitContactType contactType) {
			super(person, role, contactType);
		} 
	
	public CsuCentralAdminContact(NonOrganizationalRolodex rolodex, ContactRole role, UnitContactType contactType) {
		super(rolodex, role, contactType);
	} 

}
