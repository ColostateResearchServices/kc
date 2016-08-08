package edu.colostate.kc.award.awardalternatenumber;

import org.kuali.rice.krad.rules.rule.BusinessRule;

public interface AwardAlternateNumberRule extends BusinessRule {
    /**
     * Rule invoked upon adding a Award Alternate Number
     *
     * @return boolean
     */
    boolean processAwardAlternateNumberBusinessRules(AwardAlternateNumberRuleEvent event);
	
}
