package edu.colostate.kc.award.awardalternatenumber;

import java.io.Serializable;

import edu.colostate.kc.award.AwardExtension;
import edu.colostate.kc.award.CsuAwardForm;
import org.kuali.kra.award.AwardForm;

public class AwardAlternateNumberHelper implements Serializable {
	
	
    private AwardAlternateNumber newAwardAlternateNumber;

    public AwardAlternateNumberHelper() {
    	init();
    }
    
    private void init() {
    	newAwardAlternateNumber = new AwardAlternateNumber();
    }
    
    
    public boolean addAwardAlternateNumber(AwardForm form) {    	
    	AwardExtension extension = (AwardExtension) form.getAwardDocument().getAward().getExtension();
    	
    	AwardAlternateNumberRuleEvent event = new AwardAlternateNumberRuleEvent(
                "AddAwardAlternateNumber",
                "AddAwardAlternateNumber",
                getNewAwardAlternateNumber(),
                form.getAwardDocument());
    	
			boolean success = new AwardAlternateNumberRuleImpl().processAwardAlternateNumberBusinessRules(event);
			if(success){
				extension.addNewAwardAlternateNumber(getNewAwardAlternateNumber());
				newAwardAlternateNumber = new AwardAlternateNumber();
			}   
			return success;
    }

    public void deleteAwardAlternateNumber(AwardForm form, int numberToDelete) {
        CsuAwardForm csuAwardForm = (CsuAwardForm) form;
    	AwardExtension extension = (AwardExtension) csuAwardForm.getAwardDocument().getAward().getExtension();
    	extension.deleteAwardAlternateNumber(numberToDelete);
    	
    	
    }

	public AwardAlternateNumber getNewAwardAlternateNumber() {
		return newAwardAlternateNumber;
	}

	public void setNewAwardAlternateNumber(
			AwardAlternateNumber newAwardAlternateNumber) {
		this.newAwardAlternateNumber = newAwardAlternateNumber;
	}

}
