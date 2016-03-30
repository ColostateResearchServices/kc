/*.
 * Copyright 2005-2014 The Kuali Foundation
 * 
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.colostate.kc.award.reservation.web.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.drools.core.util.StringUtils;
import org.kuali.kra.award.AwardForm;
import org.kuali.kra.award.home.Award;
import org.kuali.kra.bo.versioning.VersionStatus;
import org.kuali.kra.common.notification.service.KcNotificationService;
import org.kuali.kra.infrastructure.Constants;
import org.kuali.kra.infrastructure.KeyConstants;
import org.kuali.kra.infrastructure.KraServiceLocator;
import org.kuali.kra.service.VersionHistoryService;
import org.kuali.kra.web.struts.action.AuditActionHelper;
import org.kuali.kra.web.struts.action.AuditActionHelper.ValidationState;
import org.kuali.kra.web.struts.action.KraTransactionalDocumentActionBase;
import org.kuali.rice.kew.api.KewApiConstants;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.kuali.rice.kns.question.ConfirmationQuestion;
import org.kuali.rice.kns.util.KNSGlobalVariables;
import org.kuali.rice.kns.web.struts.form.KualiDocumentFormBase;
import org.kuali.rice.kns.web.struts.form.KualiForm;
import org.kuali.rice.krad.bo.DocumentHeader;
import org.kuali.rice.krad.rules.rule.event.KualiDocumentEvent;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.service.KualiRuleService;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;

import edu.colostate.kc.award.reservation.document.AwardAccountReservationDocument;
import edu.colostate.kc.award.reservation.AwardAccountReservation;
import edu.colostate.kc.award.reservation.AwardAccountReservationsBean;
import edu.colostate.kc.award.reservation.web.struts.form.AwardAccountReservationForm;


/**
 * This class is ActionClass for AwardAccountReservation...
 */
public class AwardAccountReservationAction extends KraTransactionalDocumentActionBase{

    private static final Log LOG = LogFactory.getLog(AwardAccountReservationAction.class);
    private static final String DOCUMENT_ROUTE_QUESTION="DocRoute";

    /**
     * @see org.kuali.kra.web.struts.action.
     * KraTransactionalDocumentActionBase#execute(
     * org.apache.struts.action.ActionMapping,
     * org.apache.struts.action.ActionForm,
     * javax.servlet.http.HttpServletRequest,
     *  javax.servlet.http.HttpServletResponse)
     */
    @Override
    public ActionForward execute(ActionMapping mapping,
    ActionForm form, HttpServletRequest request,
    	HttpServletResponse response) throws Exception {

        AwardAccountReservationForm awardAccountReservationForm = (AwardAccountReservationForm) form;
        /*
        if (awardAccountReservationForm.getAwardAccountReservation() != null) {
            AwardAccountReservation awardAccountReservation = 
                KraServiceLocator.getService(AwardAccountReservationService.class).getAmountInfo(awardAccountReservationForm.getAwardAccountReservation());
        }
        */
        
        ActionForward actionForward = super.execute(mapping, form, request, response);
        
        if (KNSGlobalVariables.getAuditErrorMap().isEmpty()) {
            new AuditActionHelper().auditConditionally((AwardAccountReservationForm) form);
        }
/*        
        if(awardAccountReservationForm.getAwardAccountReservationDocument().getAwardAccountReservationList() != null) {
            for(AwardAccountReservation awardAccountReservationList:awardAccountReservationForm.getAwardAccountReservationDocument().getAwardAccountReservationList()) {
                List<AwardAccountReservationAttachment> awardAccountReservationAttachmentsList = awardAccountReservationList.getAwardAccountReservationAttachments();//new ArrayList<AwardAccountReservationAttachments>();
                if(awardAccountReservationAttachmentsList != null && !awardAccountReservationAttachmentsList.isEmpty()) {
                     for(AwardAccountReservationAttachment awardAccountReservationAttachments:awardAccountReservationAttachmentsList) {
                            if(awardAccountReservationAttachments.getFileId() != null) {
                                String printAttachmentTypeInclusion=KraServiceLocator.getService(ParameterService.class).getParameterValueAsString(Constants.MODULE_NAMESPACE_SUBAWARD, ParameterConstants.DOCUMENT_COMPONENT, Constants.PARAMETER_PRINT_ATTACHMENT_TYPE_INCLUSION);
                                String[] attachmentTypeCode=printAttachmentTypeInclusion.split("\\,");
                                for(int typeCode=0;typeCode<attachmentTypeCode.length;typeCode++) {
                                    if(awardAccountReservationAttachments.getAwardAccountReservationAttachmentType().equals(attachmentTypeCode[typeCode])) {
                                        String[] fileNameSplit=awardAccountReservationAttachments.getFileId().toString().split("\\.pdf");
                                        AwardAccountReservationPrintingService printService = KraServiceLocator.getService(AwardAccountReservationPrintingService.class);
                                            if(printService.isPdf(awardAccountReservationAttachments.getAttachmentContent())) {
                                            awardAccountReservationAttachments.setFileNameSplit(fileNameSplit[0]);
                                            }
                                     }
                                 }
                             }
                            AwardAccountReservationAttachmentType awardAccountReservationAttachmentTypeValue =  KraServiceLocator.getService(BusinessObjectService.class).findBySinglePrimaryKey(AwardAccountReservationAttachmentType.class, awardAccountReservationAttachments.getAwardAccountReservationAttachmentType());
                            awardAccountReservationAttachments.setTypeAttachment(awardAccountReservationAttachmentTypeValue);
                     }
                }
            }
        }
        */

        return actionForward;
    }
    /**
     * @see org.kuali.core.web.struts.action.KualiDocumentActionBase#docHandler(
     * org.apache.struts.action.ActionMapping,
     *  org.apache.struts.action.ActionForm,
     * javax.servlet.http.HttpServletRequest,
     *  javax.servlet.http.HttpServletResponse)
     */
    @Override
    public ActionForward docHandler(ActionMapping mapping, ActionForm form
            , HttpServletRequest request, HttpServletResponse response) throws Exception {
        AwardAccountReservationForm awardAccountReservationForm = (AwardAccountReservationForm) form;
        ActionForward forward;
        forward = handleDocument(
        mapping, form, request, response, awardAccountReservationForm);
        AwardAccountReservationDocument awardAccountReservationDocument = (AwardAccountReservationDocument) awardAccountReservationForm.getDocument();
        awardAccountReservationForm.initializeFormOrDocumentBasedOnCommand();
        AwardAccountReservation awardAccountReservation = awardAccountReservationForm.getAwardAccountReservation();
        awardAccountReservationForm.getAwardAccountReservationDocument().setAwardAccountReservation(awardAccountReservation);
        return forward;
    }

    /**.
     * this method is for handleDocument
     * @param mapping the ActionMapping
     * @param form the ActionForm
     * @param request the Request
     * @param response the Response
     * @param awardAccountReservationForm the AwardAccountReservationForm
     * @return ActionForward
     * @throws Exception
     */
    ActionForward handleDocument(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                  HttpServletResponse response, AwardAccountReservationForm awardAccountReservationForm) throws Exception {

        ActionForward forward = null;

        String command = awardAccountReservationForm.getCommand();
        if (KewApiConstants.ACTIONLIST_INLINE_COMMAND.equals(command)) {
            loadDocumentInForm(request, awardAccountReservationForm);
        } else {
            forward = super.docHandler(mapping, form, request, response);
        }
        return forward;
    }
    /**.
    *
    * loadDocumentInForm
     * @param request the Request
     * @param awardAccountReservationForm the AwardAccountReservationForm
    * @throws WorkflowException
    *
    */
    protected void loadDocumentInForm(HttpServletRequest request,
    AwardAccountReservationForm awardAccountReservationForm)
    throws WorkflowException {
        String docIdRequestParameter =
        request.getParameter(KRADConstants.PARAMETER_DOC_ID);
        AwardAccountReservationDocument retrievedDocument = (AwardAccountReservationDocument)
        KRADServiceLocatorWeb.getDocumentService().
        getByDocumentHeaderId(docIdRequestParameter);
        awardAccountReservationForm.setDocument(retrievedDocument);
        request.setAttribute(KRADConstants.PARAMETER_DOC_ID,
        		docIdRequestParameter);
    }

    /**.
    *
    * This method builds the string for the ActionForward
    * @param forwardPath
    * @param docIdRequestParameter
    * @return String
    */
   public String buildForwardStringForActionListCommand(String forwardPath,
		 String docIdRequestParameter) {
       StringBuilder sb = new StringBuilder();
       sb.append(forwardPath);
       sb.append("?");
       sb.append(KRADConstants.PARAMETER_DOC_ID);
       sb.append("=");
       sb.append(docIdRequestParameter);
       return sb.toString();
   }

    /**
     * @see org.kuali.kra.web.struts.action.KraTransactionalDocumentActionBase#loadDocument(
     * org.kuali.rice.kns.web.struts.form.KualiDocumentFormBase)
     */
    protected void loadDocument(KualiDocumentFormBase kualiForm)
    throws WorkflowException {
        super.loadDocument(kualiForm);
        AwardAccountReservation awardAccountReservation = ((AwardAccountReservationForm) kualiForm).getAwardAccountReservationDocument().getAwardAccountReservation();
    }

    /**
     * This method sets an awardAccountReservationNumber on an awardAccountReservation if
     * the awardAccountReservationNumber hasn't been initialized yet.
     * @param awardAccountReservation
     */
    protected void checkAwardAccountReservationCode(AwardAccountReservation awardAccountReservation){
    	/*
    	String awardAccountReservationNumber=awardAccountReservation.getProjectNumber();
        if (awardAccountReservationNumber == null || Integer.valueOf(awardAccountReservationNumber)<1) {
            awardAccountReservationNumber = getAwardAccountReservationService().getNextAwardAccountReservationNumber();
            awardAccountReservation.setProjectNumber(awardAccountReservationNumber);
        }
        
        for (AwardAccountReservationCustomData customData : awardAccountReservation.getAwardAccountReservationCustomDataList()) {
            customData.setAwardAccountReservation(awardAccountReservation);
        }
        */
    }
    
    /**
     * @see org.kuali.kra.web.struts.action.KraTransactionalDocumentActionBase
     * #save(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        AwardAccountReservationForm awardAccountReservationForm = (AwardAccountReservationForm) form;

        AwardAccountReservation awardAccountReservation = awardAccountReservationForm.getAwardAccountReservationDocument().getAwardAccountReservation();
        String userId = GlobalVariables.getUserSession().getPrincipalName();
        if (awardAccountReservation.getReservationUser() == null) {
            awardAccountReservation.setReservationUser(userId);
        }
//        checkAwardAccountReservationCode(awardAccountReservation);
/*
       if (new AwardAccountReservationDocumentRule().processAddAwardAccountReservationBusinessRules(awardAccountReservation)) {
    	   DocumentHeader docHeader=awardAccountReservationForm.getAwardAccountReservationDocument().getDocumentHeader();
    	   if (StringUtils.isEmpty(docHeader.getDocumentDescription()) || !docHeader.getDocumentDescription().equals(awardAccountReservation.getProjectTitle())) {
    		   docHeader.setDocumentDescription(awardAccountReservation.getProjectTitle());
    	   }
            
            getAwardAccountReservationService().updateAwardAccountReservationSequenceStatus(awardAccountReservation, VersionStatus.PENDING);
*/
        	DocumentHeader docHeader=awardAccountReservationForm.getAwardAccountReservationDocument().getDocumentHeader();
        	if (StringUtils.isEmpty(docHeader.getDocumentDescription())) {
        		docHeader.setDocumentDescription("Account Reservations for: "+awardAccountReservation.getReservationUser());
        	}
            ActionForward forward = super.save(mapping, form, request, response);
            return forward;
            /*
        } else {
        
            return mapping.findForward(Constants.MAPPING_BASIC);
         
         }
         */
    }

    /**
    *
    * This method gets called upon navigation to project tab.
   * @param mapping the ActionMapping
     * @param form the ActionForm
     * @param request the Request
     * @param response the Response
    * @return ActionForward
    */
   public ActionForward home(ActionMapping mapping,
		   ActionForm form, HttpServletRequest request, HttpServletResponse response) {

//       return mapping.findForward(Constants.MAPPING_SUBAWARD_PAGE);
	   return mapping.findForward("home");
   }

   /**
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws Exception
    */
   public ActionForward addReservation(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) 
                                                                                                                       throws Exception {
       getAwardAccountReservationsBean(form).addReservedAccount();
       return mapping.findForward(Constants.MAPPING_BASIC);
   }

   /**
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws Exception
    */
   public ActionForward deleteSelectedReservedAccount(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) 
                                                                                                                       throws Exception {
//       getAwardAccountReservationsBean(form).deleteReservedAccount(getLineToDelete(request));
	   ((AwardAccountReservationForm)form).getAwardAccountReservation().deleteAccounts((AwardAccountReservationForm)form);
       return mapping.findForward(Constants.MAPPING_BASIC);
   }
   
   private AwardAccountReservationsBean getAwardAccountReservationsBean(ActionForm form) {
       return ((AwardAccountReservationForm) form).getAwardAccountReservationsBean();
   }


/**
 * @return
 */
protected VersionHistoryService getVersionHistoryService() {
    return KraServiceLocator.getService(VersionHistoryService.class);
}

/**.
*
* This method gets called upon getting AwardAccountReservationService
  * @param
  * @return awardAccountReservationService
*/
/*
public AwardAccountReservationService getAwardAccountReservationService() {
    if (awardAccountReservationService == null) {
        awardAccountReservationService = KraServiceLocator.getService(AwardAccountReservationService.class);
    }
    return awardAccountReservationService;
}
*/

/**.
 * This method sets the awardAccountReservationService
 * @return awardAccountReservationService
 * @param awardAccountReservation
 */
/*
public void setAwardAccountReservationService(AwardAccountReservationService awardAccountReservationService) {
    this.awardAccountReservationService = awardAccountReservationService;
}
*/
/**
 * This method sets an awardAccountReservationNumber on an awardAccountReservation if
 * the awardAccountReservationCode hasn't been initialized yet.
 * @param awardAccountReservation
 */

@Override
public ActionForward route(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

    AwardAccountReservationForm awardAccountReservationForm = (AwardAccountReservationForm) form;
    awardAccountReservationForm.setAuditActivated(false);
    ValidationState status = new AuditActionHelper().isValidSubmission(awardAccountReservationForm, true);
    Object question = request.getParameter(KRADConstants.QUESTION_INST_ATTRIBUTE_NAME);
    Object buttonClicked = request.getParameter(KRADConstants.QUESTION_CLICKED_BUTTON);
    String methodToCall = ((KualiForm) form).getMethodToCall();
    
    if (status == ValidationState.OK) {
        super.route(mapping, form, request, response);
        return sendNotification(mapping, awardAccountReservationForm, AwardAccountReservation.NOTIFICATION_TYPE_SUBMIT, "Submit AwardAccountReservation");
    } else {
        if (status == ValidationState.WARNING) {
            if(question == null){
                return this.performQuestionWithoutInput(mapping, form, request, response, DOCUMENT_ROUTE_QUESTION, "Validation Warning Exists. Are you sure want to submit to workflow routing.", KRADConstants.CONFIRMATION_QUESTION, methodToCall, "");
            } else if(DOCUMENT_ROUTE_QUESTION.equals(question) && ConfirmationQuestion.YES.equals(buttonClicked)) {
                super.route(mapping, form, request, response);
                return sendNotification(mapping, awardAccountReservationForm, AwardAccountReservation.NOTIFICATION_TYPE_SUBMIT, "Submit AwardAccountReservation");
            } else {
                return mapping.findForward(Constants.MAPPING_BASIC);
            }    
        } else {
            GlobalVariables.getMessageMap().clearErrorMessages();
            GlobalVariables.getMessageMap().
            putError("datavalidation", KeyConstants.ERROR_WORKFLOW_SUBMISSION, new String[] {});
            awardAccountReservationForm.setAuditActivated(true);   
            return mapping.findForward(Constants.MAPPING_BASIC);
        }
    }
}

@Override
public ActionForward blanketApprove(ActionMapping mapping,
		ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws Exception {
    AwardAccountReservationForm awardAccountReservationForm = (AwardAccountReservationForm) form;

    awardAccountReservationForm.setAuditActivated(false);
    ValidationState status = new AuditActionHelper().
    isValidSubmission(awardAccountReservationForm, true);
    if ((status == ValidationState.OK) || (status == ValidationState.WARNING)) {
        super.blanketApprove(mapping, form, request, response);
        return sendNotification(mapping, awardAccountReservationForm, AwardAccountReservation.NOTIFICATION_TYPE_SUBMIT, "Submit AwardAccountReservation");
    } else {
        GlobalVariables.getMessageMap().clearErrorMessages();
        GlobalVariables.getMessageMap().
        putError("datavalidation", KeyConstants.ERROR_WORKFLOW_SUBMISSION,  new String[] {});
        awardAccountReservationForm.setAuditActivated(true);
        return mapping.findForward(Constants.MAPPING_BASIC);

    }
}

  @Override
  public ActionForward approve(ActionMapping mapping, ActionForm form,
   HttpServletRequest request,
   HttpServletResponse response) throws Exception {
      AwardAccountReservationForm awardAccountReservationForm = (AwardAccountReservationForm) form;
      ActionForward forward = mapping.findForward(Constants.MAPPING_BASIC);
      ValidationState status = new AuditActionHelper().
      isValidSubmission(awardAccountReservationForm, true);

      if ((status == ValidationState.OK) || (status == ValidationState.WARNING)) {
          return forward = super.approve(mapping, form, request, response);
      } else {
          GlobalVariables.getMessageMap().clearErrorMessages();
          GlobalVariables.getMessageMap().
          putError("datavalidation", KeyConstants.
          ERROR_WORKFLOW_SUBMISSION,  new String[] {});

          return forward;
      }
  }

  @SuppressWarnings("unchecked")
  @Override
  public ActionForward refresh(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
          throws Exception {
      super.refresh(mapping, form, request, response);
//      GlobalVariables.getUserSession().removeObject(Constants.LINKED_FUNDING_PROPOSALS_KEY);
      AwardAccountReservationForm reservationMultiLookupForm = (AwardAccountReservationForm) form;
      String lookupResultsBOClassName = request.getParameter(KRADConstants.LOOKUP_RESULTS_BO_CLASS_NAME);
      String lookupResultsSequenceNumber = request.getParameter(KRADConstants.LOOKUP_RESULTS_SEQUENCE_NUMBER);
      reservationMultiLookupForm.setLookupResultsBOClassName(lookupResultsBOClassName);
      reservationMultiLookupForm.setLookupResultsSequenceNumber(lookupResultsSequenceNumber);
      AwardAccountReservation awardAccountReservation = reservationMultiLookupForm.getAwardAccountReservationDocument().getAwardAccountReservation();
//      getKeywordService().addKeywords(awardDocument, awardMultiLookupForm);
      
//      awardAccountReservation.refreshReferenceObject("sponsor");
      awardAccountReservation.addAccounts(reservationMultiLookupForm);
      
      return mapping.findForward(Constants.MAPPING_BASIC);
  }


  /**
   * Use the Kuali Rule Service to apply the rules for the given event.
   * @param event the event to process
   * @return true if success; false if there was a validation error
   */
  protected final boolean applyRules(KualiDocumentEvent event) {
      return KraServiceLocator.getService(KualiRuleService.class).applyRules(event);
  }


  public ActionForward sendNotification(ActionMapping mapping, AwardAccountReservationForm awardAccountReservationForm, 
                                        String notificationType, String notificationString) {
      AwardAccountReservation awardAccountReservation = awardAccountReservationForm.getAwardAccountReservationDocument().getAwardAccountReservation();
      /*
      AwardAccountReservationNotificationContext context = new AwardAccountReservationNotificationContext(awardAccountReservation, notificationType, notificationString, Constants.MAPPING_SUBAWARD_PAGE);
      if (awardAccountReservationForm.getNotificationHelper().getPromptUserForNotificationEditor(context)) {
          awardAccountReservationForm.getNotificationHelper().initializeDefaultValues(context);
          return mapping.findForward("notificationEditor");
      } else {
          getNotificationService().sendNotification(context);
      */
          return null;  // to get holding page
      // }
  }

  
  protected KcNotificationService getNotificationService() {
      return KraServiceLocator.getService(KcNotificationService.class);
  }
   
}
