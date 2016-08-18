package edu.colostate.kc.award.awardalternatenumber;

import org.kuali.coeus.sys.framework.rule.KcDocumentEventBase;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.rules.rule.BusinessRule;

public class AwardAlternateNumberRuleEvent extends KcDocumentEventBase {

	private AwardAlternateNumber newAwardAlternateNumber;
	
	protected AwardAlternateNumberRuleEvent(String description,
			String errorPathPrefix, 
			AwardAlternateNumber awardAlternateNumber, 
			Document document) {
		super(description, errorPathPrefix, document);
		this.newAwardAlternateNumber  = awardAlternateNumber;
	}

	@Override
	public Class<AwardAlternateNumberRule> getRuleInterfaceClass() {
		return AwardAlternateNumberRule.class;
	}

	@Override
	public boolean invokeRuleMethod(BusinessRule rule) {
        return this.getRuleInterfaceClass().cast(rule).processAwardAlternateNumberBusinessRules(this);
	}

	@Override
	protected void logEvent() {
		// TODO Auto-generated method stub

	}

	public AwardAlternateNumber getNewAwardAlternateNumber() {
		return newAwardAlternateNumber;
	}

	public void setNewAwardAlternateNumber(
			AwardAlternateNumber newAwardAlternateNumber) {
		this.newAwardAlternateNumber = newAwardAlternateNumber;
	}

}
