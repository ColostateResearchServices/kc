package edu.iu.uits.kra.negotiations.bo;

import org.apache.commons.lang.ArrayUtils;
import org.kuali.kra.negotiations.bo.NegotiationAssociationType;

/**
 * A simple static utility class for dealing with user created negotiation 
 * association types. 
 * 
 */
public class IUNegotiationAssociationTypeUtil {
	/* Begin UITSRA-3231 */
	public static final String[] LINKED_NEGOTIATION_ASSOCIATIONS = {
		NegotiationAssociationType.PROPOSAL_LOG_ASSOCIATION,
		NegotiationAssociationType.INSTITUATIONAL_PROPOSAL_ASSOCIATION,
		NegotiationAssociationType.AWARD_ASSOCIATION,
		NegotiationAssociationType.SUB_AWARD_ASSOCIATION};
    
    public static boolean isUnassociatedType(String negotiationAssociationType) {
    	return !ArrayUtils.contains(LINKED_NEGOTIATION_ASSOCIATIONS, negotiationAssociationType);
    }
    
    public static boolean isAssociatedType(String negotiationAssociationType) {
    	return ArrayUtils.contains(LINKED_NEGOTIATION_ASSOCIATIONS, negotiationAssociationType);
    }
    /* End UITSRA-3231 */
}
