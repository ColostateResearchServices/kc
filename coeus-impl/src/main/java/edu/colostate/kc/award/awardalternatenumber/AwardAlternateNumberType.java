package edu.colostate.kc.award.awardalternatenumber;

import org.kuali.coeus.sys.framework.model.KcPersistableBusinessObjectBase;

public class AwardAlternateNumberType extends KcPersistableBusinessObjectBase {
	
	private String description;
	private String awardAlternateNumberTypeCode;
	
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAwardAlternateNumberTypeCode() {
		return awardAlternateNumberTypeCode;
	}
	public void setAwardAlternateNumberTypeCode(String awardAlternateNumberTypeCode) {
		this.awardAlternateNumberTypeCode = awardAlternateNumberTypeCode;
	}

	
	
}
