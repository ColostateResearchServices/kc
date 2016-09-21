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
package org.kuali.kra.excon.web.struts.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.drools.core.util.StringUtils;
import org.kuali.coeus.common.framework.version.VersionStatus;
import org.kuali.coeus.common.framework.version.history.VersionHistoryService;
import org.kuali.coeus.sys.framework.service.KcServiceLocator;
import org.kuali.coeus.common.notification.impl.service.KcNotificationService;
import org.kuali.kra.excon.customdata.ExconProjectCustomData;
import org.kuali.kra.excon.document.ExconProjectDocument;
import org.kuali.kra.excon.document.ExconProjectDocumentRule;
import org.kuali.kra.excon.project.ExconProject;
import org.kuali.kra.excon.project.ExconProjectForm;
import org.kuali.kra.excon.service.ExconProjectService;
import org.kuali.kra.infrastructure.Constants;
import org.kuali.kra.infrastructure.KeyConstants;

import org.kuali.coeus.sys.framework.validation.AuditHelper;
import org.kuali.coeus.sys.framework.validation.AuditHelper.ValidationState;

import org.kuali.coeus.sys.framework.controller.KcTransactionalDocumentActionBase;
import org.kuali.rice.kew.api.KewApiConstants;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.kuali.rice.kns.question.ConfirmationQuestion;
import org.kuali.rice.kns.web.struts.form.KualiDocumentFormBase;
import org.kuali.rice.kns.web.struts.form.KualiForm;
import org.kuali.rice.krad.bo.DocumentHeader;
import org.kuali.rice.krad.rules.rule.event.DocumentEvent;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.service.KualiRuleService;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class is ActionClass for ExconProject...
 */
public class ExconProjectAction extends KcTransactionalDocumentActionBase{

    private transient ExconProjectService exconProjectService;
    private static final Log LOG = LogFactory.getLog(ExconProjectAction.class);
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

        ExconProjectForm exconProjectForm = (ExconProjectForm) form;
        /*
        if (exconProjectForm.getExconProject() != null) {
            ExconProject exconProject = 
                KcServiceLocator.getService(ExconProjectService.class).getAmountInfo(exconProjectForm.getExconProject());
        }
        */
        
        ActionForward actionForward = super.execute(mapping, form, request, response);
        




        if(GlobalVariables.getAuditErrorMap().isEmpty()) {
            KcServiceLocator.getService(AuditHelper.class).auditConditionally((ExconProjectForm)form);
        }
/*        
        if(exconProjectForm.getExconProjectDocument().getExconProjectList() != null) {
            for(ExconProject exconProjectList:exconProjectForm.getExconProjectDocument().getExconProjectList()) {
                List<ExconProjectAttachment> exconProjectAttachmentsList = exconProjectList.getExconProjectAttachments();//new ArrayList<ExconProjectAttachments>();
                if(exconProjectAttachmentsList != null && !exconProjectAttachmentsList.isEmpty()) {
                     for(ExconProjectAttachment exconProjectAttachments:exconProjectAttachmentsList) {
                            if(exconProjectAttachments.getFileId() != null) {
                                String printAttachmentTypeInclusion=KcServiceLocator.getService(ParameterService.class).getParameterValueAsString(Constants.MODULE_NAMESPACE_SUBAWARD, ParameterConstants.DOCUMENT_COMPONENT, Constants.PARAMETER_PRINT_ATTACHMENT_TYPE_INCLUSION);
                                String[] attachmentTypeCode=printAttachmentTypeInclusion.split("\\,");
                                for(int typeCode=0;typeCode<attachmentTypeCode.length;typeCode++) {
                                    if(exconProjectAttachments.getExconProjectAttachmentType().equals(attachmentTypeCode[typeCode])) {
                                        String[] fileNameSplit=exconProjectAttachments.getFileId().toString().split("\\.pdf");
                                        ExconProjectPrintingService printService = KcServiceLocator.getService(ExconProjectPrintingService.class);
                                            if(printService.isPdf(exconProjectAttachments.getAttachmentContent())) {
                                            exconProjectAttachments.setFileNameSplit(fileNameSplit[0]);
                                            }
                                     }
                                 }
                             }
                            ExconProjectAttachmentType exconProjectAttachmentTypeValue =  KcServiceLocator.getService(BusinessObjectService.class).findBySinglePrimaryKey(ExconProjectAttachmentType.class, exconProjectAttachments.getExconProjectAttachmentType());
                            exconProjectAttachments.setTypeAttachment(exconProjectAttachmentTypeValue);
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
        ExconProjectForm exconProjectForm = (ExconProjectForm) form;
        ActionForward forward;
        forward = handleDocument(
        mapping, form, request, response, exconProjectForm);
        ExconProjectDocument exconProjectDocument = (ExconProjectDocument) exconProjectForm.getDocument();
        exconProjectForm.initializeFormOrDocumentBasedOnCommand();
        ExconProject exconProject = exconProjectForm.getExconProject();
        exconProjectForm.getExconProjectDocument().setExconProject(exconProject);
        return forward;
    }

    /**.
     * this method is for handleDocument
     * @param mapping the ActionMapping
     * @param form the ActionForm
     * @param request the Request
     * @param response the Response
     * @param exconProjectForm the ExconProjectForm
     * @return ActionForward
     * @throws Exception
     */
    ActionForward handleDocument(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                  HttpServletResponse response, ExconProjectForm exconProjectForm) throws Exception {

        ActionForward forward = null;

        String command = exconProjectForm.getCommand();
        if (KewApiConstants.ACTIONLIST_INLINE_COMMAND.equals(command)) {
            loadDocumentInForm(request, exconProjectForm);
//        } else if (Constants.MAPPING_SUBAWARD_ACTION_PAGE.equals(command)) {
        } else if ("projectActions".equals(command)) {
            loadDocumentInForm(request, exconProjectForm);
            forward = exconProjectActions(mapping, exconProjectForm, request, response);
        } else {
            forward = super.docHandler(mapping, form, request, response);
        }
        return forward;
    }
    /**.
    *
    * loadDocumentInForm
     * @param request the Request
     * @param exconProjectForm the ExconProjectForm
    * @throws WorkflowException
    *
    */
    protected void loadDocumentInForm(HttpServletRequest request,
    ExconProjectForm exconProjectForm)
    throws WorkflowException {
        String docIdRequestParameter =
        request.getParameter(KRADConstants.PARAMETER_DOC_ID);
        ExconProjectDocument retrievedDocument = (ExconProjectDocument)
        KRADServiceLocatorWeb.getDocumentService().
        getByDocumentHeaderId(docIdRequestParameter);
        exconProjectForm.setDocument(retrievedDocument);
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
        ExconProject exconProject = ((ExconProjectForm) kualiForm).getExconProjectDocument().getExconProject();
    }

    /**
     * This method sets an exconProjectNumber on an exconProject if
     * the exconProjectNumber hasn't been initialized yet.
     * @param exconProject
     */
    protected void checkExconProjectCode(ExconProject exconProject){
    	String exconProjectNumber=exconProject.getProjectNumber();
        if (exconProjectNumber == null || Integer.valueOf(exconProjectNumber)<1) {
            exconProjectNumber = getExconProjectService().getNextExconProjectNumber();
            exconProject.setProjectNumber(exconProjectNumber);
        }
        
        for (ExconProjectCustomData customData : exconProject.getExconProjectCustomDataList()) {
            customData.setExconProject(exconProject);
        }
    }
    
    /**
     * @see org.kuali.kra.web.struts.action.KraTransactionalDocumentActionBase
     * #save(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ExconProjectForm exconProjectForm = (ExconProjectForm) form;

        ExconProject exconProject = exconProjectForm.getExconProjectDocument().getExconProject();
        String userId = GlobalVariables.getUserSession().getPrincipalName();
        if (exconProject.getProjectId() == null) {
            getVersionHistoryService().updateVersionHistory(exconProject, VersionStatus.PENDING, userId);
        }
        checkExconProjectCode(exconProject);

       if (new ExconProjectDocumentRule().processAddExconProjectBusinessRules(exconProject)) {
    	   DocumentHeader docHeader=exconProjectForm.getExconProjectDocument().getDocumentHeader();
    	   if (StringUtils.isEmpty(docHeader.getDocumentDescription()) || !docHeader.getDocumentDescription().equals(exconProject.getTitle())) {
    		   docHeader.setDocumentDescription(exconProject.getTitle());
    	   }
            
            getExconProjectService().updateExconProjectSequenceStatus(exconProject, VersionStatus.PENDING);
            ActionForward forward = super.save(mapping, form, request, response);
            return forward;
        } else {
        
            return mapping.findForward(Constants.MAPPING_BASIC);
         
         }
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
   *
   * This method gets called upon navigation to Events tab.
   * @param mapping the ActionMapping
    * @param form the ActionForm
    * @param request the Request
    * @param response the Response
   * @return ActionForward
   */
  public ActionForward events(ActionMapping mapping,
		  ActionForm form, HttpServletRequest request, HttpServletResponse response) {
	   return mapping.findForward("events");
  }
  
  /**
  *
  * This method gets called upon navigation to Contacts tab.
   * @param mapping the ActionMapping
  * @param form the ActionForm
  * @param request the Request
  * @param response the Response
   * @return ActionForward
  */
 public ActionForward contacts(ActionMapping mapping, ActionForm form,
	HttpServletRequest request, HttpServletResponse response) {

     return mapping.findForward(Constants.MAPPING_CONTACTS_PAGE);
 }
 
 public ActionForward agreements(ActionMapping mapping, ActionForm form,
	HttpServletRequest request, HttpServletResponse response) {

     return mapping.findForward("agreements");
 }

 /**
 *
 * This method gets called upon navigation to Destinations tab.
  * @param mapping the ActionMapping
 * @param form the ActionForm
 * @param request the Request
 * @param response the Response
  * @return ActionForward
 */
public ActionForward destinations(ActionMapping mapping, ActionForm form,
	HttpServletRequest request, HttpServletResponse response) {

    return mapping.findForward("destinations");
}
 
/**
*
* This method gets called upon navigation to commentsAndAttachments tab.
  * @param mapping the ActionMapping
  * @param form the ActionForm
  * @param request the Request
  * @param response the Response
   * @return ActionForward
*/

public ActionForward commentsAndAttachments(ActionMapping mapping, ActionForm form
        , HttpServletRequest request, HttpServletResponse response) {
    return mapping.findForward("commentsAndAttachments");
}

public ActionForward backgroundCheck(ActionMapping mapping, ActionForm form
        , HttpServletRequest request, HttpServletResponse response) {
    return mapping.findForward("backgroundCheck");
}


    /**
*
* This method gets called upon navigation to custom data tab.
  * @param mapping the ActionMapping
  * @param form the ActionForm
  * @param request the Request
  * @param response the Response
   * @return ActionForward
*/

public ActionForward customData(ActionMapping mapping, ActionForm form
        , HttpServletRequest request, HttpServletResponse response) {
    ExconProjectForm exconProjectForm = (ExconProjectForm) form;
    exconProjectForm.getCustomDataHelper().prepareCustomData();
    return mapping.findForward(Constants.MAPPING_CUSTOM_DATA);
}

/**
*
* This method gets called upon navigation to exconProject action tab.
  * @param mapping the ActionMapping
  * @param form the ActionForm
  * @param request the Request
  * @param response the Response
   * @return ActionForward
*/

public ActionForward exconProjectActions(ActionMapping mapping,
ActionForm form, HttpServletRequest request, HttpServletResponse response) {

//    return mapping.findForward(Constants.MAPPING_SUBAWARD_ACTION_PAGE);
    return mapping.findForward("projectActions");
}

/**
 * @return
 */
protected VersionHistoryService getVersionHistoryService() {
    return KcServiceLocator.getService(VersionHistoryService.class);
}

/**.
*
* This method gets called upon getting ExconProjectService
  * @param
  * @return exconProjectService
*/
public ExconProjectService getExconProjectService() {
    if (exconProjectService == null) {
        exconProjectService = KcServiceLocator.getService(ExconProjectService.class);
    }
    return exconProjectService;
}
/**.
 * This method sets the exconProjectService
 * @return exconProjectService
 * @param exconProject
 */
public void setExconProjectService(ExconProjectService exconProjectService) {
    this.exconProjectService = exconProjectService;
}
/**
 * This method sets an exconProjectNumber on an exconProject if
 * the exconProjectCode hasn't been initialized yet.
 * @param exconProject
 */

@Override
public ActionForward route(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

    ExconProjectForm exconProjectForm = (ExconProjectForm) form;
    exconProjectForm.setAuditActivated(false);
    ValidationState status = KcServiceLocator.getService(AuditHelper.class).isValidSubmission(exconProjectForm, true);
    Object question = request.getParameter(KRADConstants.QUESTION_INST_ATTRIBUTE_NAME);
    Object buttonClicked = request.getParameter(KRADConstants.QUESTION_CLICKED_BUTTON);
    String methodToCall = ((KualiForm) form).getMethodToCall();
    
    if (status == ValidationState.OK) {
        super.route(mapping, form, request, response);
        return sendNotification(mapping, exconProjectForm, ExconProject.NOTIFICATION_TYPE_SUBMIT, "Submit ExconProject");
    } else {
        if (status == ValidationState.WARNING) {
            if(question == null){
                return this.performQuestionWithoutInput(mapping, form, request, response, DOCUMENT_ROUTE_QUESTION, "Validation Warning Exists. Are you sure want to submit to workflow routing.", KRADConstants.CONFIRMATION_QUESTION, methodToCall, "");
            } else if(DOCUMENT_ROUTE_QUESTION.equals(question) && ConfirmationQuestion.YES.equals(buttonClicked)) {
                super.route(mapping, form, request, response);
                return sendNotification(mapping, exconProjectForm, ExconProject.NOTIFICATION_TYPE_SUBMIT, "Submit ExconProject");
            } else {
                return mapping.findForward(Constants.MAPPING_BASIC);
            }    
        } else {
            GlobalVariables.getMessageMap().clearErrorMessages();
            GlobalVariables.getMessageMap().
            putError("datavalidation", KeyConstants.ERROR_WORKFLOW_SUBMISSION, new String[] {});
            exconProjectForm.setAuditActivated(true);   
            return mapping.findForward(Constants.MAPPING_BASIC);
        }
    }
}

@Override
public ActionForward blanketApprove(ActionMapping mapping,
		ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws Exception {
    ExconProjectForm exconProjectForm = (ExconProjectForm) form;

    exconProjectForm.setAuditActivated(false);
    ValidationState status = KcServiceLocator.getService(AuditHelper.class).
    isValidSubmission(exconProjectForm, true);
    if ((status == ValidationState.OK) || (status == ValidationState.WARNING)) {
        super.blanketApprove(mapping, form, request, response);
        return sendNotification(mapping, exconProjectForm, ExconProject.NOTIFICATION_TYPE_SUBMIT, "Submit ExconProject");
    } else {
        GlobalVariables.getMessageMap().clearErrorMessages();
        GlobalVariables.getMessageMap().
        putError("datavalidation", KeyConstants.ERROR_WORKFLOW_SUBMISSION,  new String[] {});
        exconProjectForm.setAuditActivated(true);
        return mapping.findForward(Constants.MAPPING_BASIC);

    }
}

  @Override
  public ActionForward approve(ActionMapping mapping, ActionForm form,
   HttpServletRequest request,
   HttpServletResponse response) throws Exception {
      ExconProjectForm exconProjectForm = (ExconProjectForm) form;
      ActionForward forward = mapping.findForward(Constants.MAPPING_BASIC);
      ValidationState status = KcServiceLocator.getService(AuditHelper.class).
      isValidSubmission(exconProjectForm, true);

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



  /**
   * Use the Kuali Rule Service to apply the rules for the given event.
   * @param event the event to process
   * @return true if success; false if there was a validation error
   */
  protected final boolean applyRules(DocumentEvent event) {
      return KcServiceLocator.getService(KualiRuleService.class).applyRules(event);
  }


  public ActionForward sendNotification(ActionMapping mapping, ExconProjectForm exconProjectForm, 
                                        String notificationType, String notificationString) {
      ExconProject exconProject = exconProjectForm.getExconProjectDocument().getExconProject();
      /*
      ExconProjectNotificationContext context = new ExconProjectNotificationContext(exconProject, notificationType, notificationString, Constants.MAPPING_SUBAWARD_PAGE);
      if (exconProjectForm.getNotificationHelper().getPromptUserForNotificationEditor(context)) {
          exconProjectForm.getNotificationHelper().initializeDefaultValues(context);
          return mapping.findForward("notificationEditor");
      } else {
          getNotificationService().sendNotification(context);
      */
          return null;  // to get holding page
      // }
  }

  
  protected KcNotificationService getNotificationService() {
      return KcServiceLocator.getService(KcNotificationService.class);
  }
   
  /**
   * 
   * This method is called to print forms
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return
   * @throws Exception
   
 public ActionForward printForms(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
         Map<String, Object> reportParameters = new HashMap<String, Object>();          
         ExconProjectForm exconProjectForm = (ExconProjectForm) form;
         List<ExconProjectForms> printFormTemplates = new ArrayList<ExconProjectForms>();
         List<ExconProjectForms> exconProjectFormList = exconProjectForm.getExconProjectDocument().getExconProjectList().get(0).getExconProjectForms();
         ExconProjectPrintingService printService = KcServiceLocator.getService(ExconProjectPrintingService.class);
         printFormTemplates = printService.getSponsorFormTemplates(exconProjectForm.getExconProjectPrintAgreement(),exconProjectFormList);
              Collection<ExconProjectFundingSource> fundingSource = (Collection<ExconProjectFundingSource>) KcServiceLocator
                      .getService(BusinessObjectService.class).findAll(ExconProjectFundingSource.class);
              if(exconProjectForm.getExconProjectPrintAgreement().getFundingSource() != null){
                  for (ExconProjectFundingSource exconProjectFunding : fundingSource) {
                      if(exconProjectForm.getExconProjectPrintAgreement().getFundingSource().equals(exconProjectFunding.getExconProjectFundingSourceId().toString())){
                          reportParameters.put("awardNumber",exconProjectFunding.getAward().getAwardNumber());
                          reportParameters.put("awardTitle",exconProjectFunding.getAward().getParentTitle());
                          reportParameters.put("sponsorAwardNumber",exconProjectFunding.getAward().getSponsorAwardNumber());
                          reportParameters.put("sponsorName",exconProjectFunding.getAward().getSponsor().getSponsorName());
                          reportParameters.put("cfdaNumber",exconProjectFunding.getAward().getCfdaNumber());
                          reportParameters.put("awardID",exconProjectFunding.getAward().getAwardId());
                      }
                  }
              }
              ExconProjectPrintingService exconProjectPrintingService = KcServiceLocator.getService(ExconProjectPrintingService.class);
              AttachmentDataSource dataStream ;
              reportParameters.put(ExconProjectPrintingService.SELECTED_TEMPLATES, printFormTemplates);
              reportParameters.put("fdpType",exconProjectForm.getExconProjectPrintAgreement().getFdpType());
              if(exconProjectForm.getExconProjectPrintAgreement().getFdpType().equals(SUBAWARD_AGREEMENT)){
                  dataStream = exconProjectPrintingService.printExconProjectFDPReport(exconProjectForm.getExconProjectDocument().getExconProject(), ExconProjectPrintType.SUB_AWARD_FDP_TEMPLATE, reportParameters);
              } else{
                  dataStream = exconProjectPrintingService.printExconProjectFDPReport(exconProjectForm.getExconProjectDocument().getExconProject(), ExconProjectPrintType.SUB_AWARD_FDP_MODIFICATION, reportParameters);
              }                                           
              streamToResponse(dataStream,response);
      
      return  mapping.findForward(Constants.MAPPING_BASIC);
  }
  */

}
