package edu.colostate.kc.award;

import edu.colostate.kc.award.awardalternatenumber.AwardAlternateNumberHelper;
import org.kuali.kra.award.AwardForm;

import edu.colostate.kc.award.contacts.CsuCentralAdminContactsHelper;

public class CsuAwardForm extends AwardForm {

	private AwardAlternateNumberHelper awardAlternateNumberHelper;
    private CsuCentralAdminContactsHelper csuCentralAdminContactsHelper;

    public CsuAwardForm() {
        super();
        init();
    }	

    public void init() {
    	awardAlternateNumberHelper = new AwardAlternateNumberHelper();
        csuCentralAdminContactsHelper = new CsuCentralAdminContactsHelper();
    }

	public AwardAlternateNumberHelper getAwardAlternateNumberHelper() {
		return awardAlternateNumberHelper;
	}

	public void setAwardAlternateNumberHelper(
			AwardAlternateNumberHelper awardAlternateNumberHelper) {
		this.awardAlternateNumberHelper = awardAlternateNumberHelper;
	}

    public CsuCentralAdminContactsHelper getCsuCentralAdminContactsHelper() {
        return csuCentralAdminContactsHelper;
    }

    public void setCsuCentralAdminContactsHelper(
            CsuCentralAdminContactsHelper csuCentralAdminContactsHelper) {
        this.csuCentralAdminContactsHelper = csuCentralAdminContactsHelper;
    }

}
