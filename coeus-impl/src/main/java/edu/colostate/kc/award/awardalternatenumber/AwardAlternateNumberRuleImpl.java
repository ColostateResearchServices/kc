package edu.colostate.kc.award.awardalternatenumber;

import org.apache.commons.lang.StringUtils;
import org.kuali.coeus.sys.framework.rule.KcTransactionalDocumentRuleBase;

import edu.colostate.kc.infrastructure.CSUKeyConstants;

public class AwardAlternateNumberRuleImpl extends KcTransactionalDocumentRuleBase
		implements AwardAlternateNumberRule {

	private static final String NEW_AWARD_ALT_NBR = "awardAlternateNumberHelper.newAwardAlternateNumber";
	private static final String NUMBER = ".number";
	private static final String TYPECODE = ".awardAlternateNumberTypeCode";
	
	
	@Override
	public boolean processAwardAlternateNumberBusinessRules(
			AwardAlternateNumberRuleEvent event) {
	    boolean validType = validateAddedTypecode(event.getNewAwardAlternateNumber());
	    boolean validNumber = validateAddedNbr(event.getNewAwardAlternateNumber());
		
		return (validType && validNumber);
	}

		private boolean validateAddedTypecode(AwardAlternateNumber altNbr){
			
		    boolean valid = true;
		    
		    if (altNbr.getAwardAlternateNumberType() == null) {
		        valid = false;
		        reportError(NEW_AWARD_ALT_NBR+TYPECODE, 
		                CSUKeyConstants.CSU_AWARD_ALT_NBR_EMPTY_TYPE);
		    }
		    return valid;
		}
		
		private boolean validateAddedNbr(AwardAlternateNumber altNbr){
			
		    boolean valid = true;
		    
		    if (StringUtils.isEmpty(altNbr.getNumber())) {
		        valid = false;
		        reportError(NEW_AWARD_ALT_NBR+NUMBER, 
		                CSUKeyConstants.CSU_AWARD_ALT_NBR_EMPTY_NBR);
		    }
		    return valid;
		}
		
		
		}

