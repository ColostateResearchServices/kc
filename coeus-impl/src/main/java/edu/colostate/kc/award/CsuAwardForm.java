package edu.colostate.kc.award;

import org.kuali.kra.award.AwardForm;

import edu.colostate.kc.award.contacts.CsuCentralAdminContactsHelper;

public class CsuAwardForm extends AwardForm {

    private CsuCentralAdminContactsHelper csuCentralAdminContactsHelper;

    public CsuAwardForm() {
        super();
        init();
    }	

    public void init() {
        csuCentralAdminContactsHelper = new CsuCentralAdminContactsHelper();
    }

    public CsuCentralAdminContactsHelper getCsuCentralAdminContactsHelper() {
        return csuCentralAdminContactsHelper;
    }

    public void setCsuCentralAdminContactsHelper(
            CsuCentralAdminContactsHelper csuCentralAdminContactsHelper) {
        this.csuCentralAdminContactsHelper = csuCentralAdminContactsHelper;
    }

}
