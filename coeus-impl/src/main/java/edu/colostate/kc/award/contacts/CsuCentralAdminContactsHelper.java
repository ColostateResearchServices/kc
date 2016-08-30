package edu.colostate.kc.award.contacts;

import java.io.Serializable;

import org.kuali.kra.award.AwardForm;
import org.kuali.coeus.common.framework.unit.UnitContactType;

import edu.colostate.kc.award.AwardExtension;
import edu.colostate.kc.award.CsuAwardForm;

public class CsuCentralAdminContactsHelper implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	CsuCentralAdminContact newCentralAdminContact;
	
	
    public CsuCentralAdminContactsHelper() {
    	initNewContact();
    }
    
    private void initNewContact() {
    	newCentralAdminContact = new CsuCentralAdminContact();
    	newCentralAdminContact.setUnitContactType(UnitContactType.CONTACT);
    	newCentralAdminContact.setUnitAdministratorTypeCode("C"); // <-- this is wrong TODO - fix
    }

    public void deleteCentralAdminContact(AwardForm form, int numberToDelete) {
        CsuAwardForm csuAwardForm = (CsuAwardForm) form;
    	AwardExtension extension = (AwardExtension) csuAwardForm.getAwardDocument().getAward().getExtension();
    	extension.deleteCentralAdminContact(numberToDelete);
    	
    	
    }

    

    public void addCentralAdminContact(AwardForm form) {
        CsuAwardForm csuAwardForm = (CsuAwardForm) form;
        
        CentralAdminContactsAddEvent event = new CentralAdminContactsAddEvent(
                "AddCentralAdminContact",
                "AddCentralAdminContact",
                getNewCentralAdminContact(),
                form.getAwardDocument());
    	
			boolean success = new CentralAdminContactsAddRuleImpl().processAddCentralAdminContactRules(event);
			if(success){
		    	AwardExtension extension = (AwardExtension) csuAwardForm.getAwardDocument().getAward().getExtension();
		    	extension.addCentralAdminContact(getNewCentralAdminContact());
		    	initNewContact();
			}
			
    }
    	
    

	public CsuCentralAdminContact getNewCentralAdminContact() {
		return newCentralAdminContact;
	}

	public void setNewCentralAdminContact(
			CsuCentralAdminContact newCentralAdminContact) {
		this.newCentralAdminContact = newCentralAdminContact;
	}
}
