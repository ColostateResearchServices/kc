package edu.colostate.kc.coi;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.kra.coi.CoiDisclosure;
import org.kuali.kra.coi.CoiDisclosureDocument;
import org.kuali.kra.coi.CoiDisclosureForm;
import org.kuali.kra.coi.CoiUserRole;
import org.kuali.kra.coi.actions.CoiDisclosureActionServiceImpl;
import org.kuali.kra.coi.actions.DisclosureActionHelper;
import org.kuali.kra.coi.notification.CoiNotification;
import org.kuali.kra.coi.notification.CoiNotificationContext;
import org.kuali.kra.coi.notification.CoiNotificationRenderer;
import org.kuali.kra.infrastructure.Constants;
import org.kuali.rice.core.api.datetime.DateTimeService;
import org.kuali.rice.krad.document.authorization.PessimisticLock;
import org.kuali.rice.krad.service.PessimisticLockService;
import org.kuali.rice.krad.util.GlobalVariables;

public class CsuCoiDisclosureActionServiceImpl extends
		CoiDisclosureActionServiceImpl implements
		CsuCoiDisclosureActionService {

    private transient PessimisticLockService pessimisticLockService;
    private transient  DateTimeService dateTimeService;

    public ActionForward sendReviewCompleteNotifications(CoiDisclosureForm coiDisclosureForm,  ActionMapping mapping) {
    	
    	CoiDisclosureDocument coiDisclosureDocument = coiDisclosureForm.getCoiDisclosureDocument();
    	DisclosureActionHelper actionHelper = coiDisclosureForm.getDisclosureActionHelper();
    	
    	DisclosureReviewCompletedNotificationRenderer renderer = new DisclosureReviewCompletedNotificationRenderer(coiDisclosureDocument.getCoiDisclosure(), CsuCoiDisclosureActionService.REVIEW_COMPLETED);
    	DisclosureReviewCompletedNotificationRequestBean disclosureBean = getDisclosureBean(coiDisclosureDocument.getCoiDisclosure(), getReviewers(actionHelper));
        
        CoiNotificationContext context = new CoiNotificationContext(coiDisclosureDocument.getCoiDisclosure(), 
                                                                    disclosureBean.getActionType(), 
                                                                    disclosureBean.getDescription(), renderer);
        return checkToSendNotification(mapping, mapping.findForward(Constants.MAPPING_BASIC), coiDisclosureForm, renderer, context, disclosureBean);
    }
    
    private DisclosureReviewCompletedNotificationRequestBean getDisclosureBean(CoiDisclosure coiDisclosure, List<CoiUserRole> userRoles) {
    	DisclosureReviewCompletedNotificationRequestBean newBean = new DisclosureReviewCompletedNotificationRequestBean(coiDisclosure, userRoles);
        return newBean;
    }

    private ActionForward checkToSendNotification(ActionMapping mapping, ActionForward forward, CoiDisclosureForm coiDisclosureForm, 
    		CoiNotificationRenderer renderer, CoiNotificationContext context, 
    		DisclosureReviewCompletedNotificationRequestBean notificationRequestBean) {
        
        if (coiDisclosureForm.getNotificationHelper().getPromptUserForNotificationEditor(context)) {
            coiDisclosureForm.getNotificationHelper().initializeDefaultValues(context);
            return mapping.findForward("protocolNotificationEditor");
        } else {
            getKcNotificationService().sendNotificationAndPersist(context, new CoiNotification(), coiDisclosureForm.getCoiDisclosureDocument().getCoiDisclosure());
            return null;
        }
    }

    
    protected List<CoiUserRole> getReviewers(DisclosureActionHelper actionHelper) {
        List<CoiUserRole>list = new ArrayList<CoiUserRole>();
        for (CoiUserRole userRole: actionHelper.getCoiUserRoles()) {
            if ("COI Reviewer".equals(userRole.getRoleName())) {
                list.add(userRole);
            }
        }        
        return list;
    }
    
    
    public void completeCoiReview(CoiDisclosureForm coiDisclosureForm,  ActionMapping mapping) {
    	CoiDisclosure disclosure =  coiDisclosureForm.getCoiDisclosureDocument().getCoiDisclosure();
    	super.completeCoiReview(disclosure);
    	sendReviewCompleteNotifications(coiDisclosureForm, mapping);
    	clearPessimisticLocks(coiDisclosureForm.getCoiDisclosureDocument());
    	
    }
    
    protected void clearPessimisticLocks(CoiDisclosureDocument document) {
         List<PessimisticLock> locks = getPessimisticLockService().getPessimisticLocksForDocument(document.getDocumentNumber());
         getPessimisticLockService().releaseAllLocksForUser(locks, GlobalVariables.getUserSession().getPerson());
    	
    }

	public PessimisticLockService getPessimisticLockService() {
		return pessimisticLockService;
	}

	public void setPessimisticLockService(
            PessimisticLockService pessimisticLockService) {
		this.pessimisticLockService = pessimisticLockService;
	}
    
    public void closeoutReviews(CoiDisclosureDocument coiDisclosureDocument) {
    	CoiDisclosure coiDisclosure = coiDisclosureDocument.getCoiDisclosure();
    	List<CoiUserRole> currentReviewers = coiDisclosure.getCoiUserRoles();

    	for (CoiUserRole reviewer : currentReviewers) {
    		reviewer.setDateCompleted(getDateTimeService().getCurrentSqlDate());
    		reviewer.setReviewCompleted(true);
    	}
       getBusinessObjectService().save(coiDisclosure);
    	
    }

	public DateTimeService getDateTimeService() {
		return dateTimeService;
	}

	public void setDateTimeService(DateTimeService dateTimeService) {
		this.dateTimeService = dateTimeService;
	}


}
