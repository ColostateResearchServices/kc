package edu.colostate.kc.coi;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.kra.coi.CoiDisclosureDocument;
import org.kuali.kra.coi.CoiDisclosureForm;
import org.kuali.kra.coi.actions.CoiDisclosureActionService;

public interface CsuCoiDisclosureActionService extends CoiDisclosureActionService {
	
	public static final String REVIEW_COMPLETED="reviewCompleted";
	
    /**
     * This method notifies when a COI review is completed. 
     * @param coiDisclosureDocument
     * @param coiDisclosureDocument
     * @param coiDisclosureForm
     * @param submitDisclosureAction
     * @param mapping
     */
	public ActionForward sendReviewCompleteNotifications(
            CoiDisclosureForm coiDisclosureForm, ActionMapping mapping);
	
     public void completeCoiReview(CoiDisclosureForm coiDisclosureForm, ActionMapping mapping);
     
     public void closeoutReviews(CoiDisclosureDocument coiDisclosureDocument);
     
     

}
