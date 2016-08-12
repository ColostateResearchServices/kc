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

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.drools.core.util.StringUtils;
import org.kuali.coeus.common.framework.version.VersionStatus;
import org.kuali.coeus.common.framework.version.history.VersionHistory;
import org.kuali.kra.excon.document.ExconProjectDocument;
import org.kuali.kra.excon.project.ExconProject;
import org.kuali.kra.excon.project.ExconProjectAssociatedDocumentsBean;
import org.kuali.kra.excon.project.ExconProjectForm;
import org.kuali.kra.infrastructure.Constants;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.kuali.rice.kns.question.ConfirmationQuestion;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ExconProjectHomeAction extends ExconProjectAction {
	private static final String DOC_HANDLER_URL_PATTERN = "%s/DocHandler.do?command=displayDocSearchView&docId=%s";
	private static final String EXCON_PROJECT_VERSION_EDITPENDING_PROMPT_KEY = "message.subaward.version.editpending.prompt";
	
    @Override
    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ExconProject exconProject = ((ExconProjectForm)form).getExconProject();
    	if (StringUtils.isEmpty(exconProject.getTitle())) {
    		exconProject.setTitle(exconProject.getProjectTypeCode());
    	}
    	ActionForward forward=super.save(mapping, form, request, response);
    	return forward;
    }
    
    /**
     * This method is used to handle the edit button
     * action on an ACTIVE ExconProject.
     *  If no Pending version exists for the same
     * exconprojectCode, a new ExconProject version is created.
     * If a Pending version exists,
     * the user is prompted as to whether she would
     * like to edit the Pending version. Answering Yes results in that Pending
     * version ExconProjectDocument to be opened. Answering No
     * simply returns the user to the ACTIVE document screen
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward editOrVersion(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                        HttpServletResponse response) throws Exception {
        ExconProjectForm exconProjectForm = ((ExconProjectForm)form);
        ExconProjectDocument exconProjectDocument = exconProjectForm.getExconProjectDocument();
        ExconProject exconproject = exconProjectDocument.getExconProject();
        ActionForward forward = null;

        VersionHistory foundPending = findPendingVersion(exconproject);
        if (foundPending != null) {
            Object question = request.getParameter(KRADConstants.QUESTION_CLICKED_BUTTON);
            if (question == null) {
                forward = showPromptForEditingPendingVersion(mapping, form, request, response);
            } else {
                forward = processPromptForEditingPendingVersionResponse(mapping, request, response, exconProjectForm, foundPending);
            }
        } else {
            forward = createAndSaveNewExconProjectVersion(response, exconProjectForm, exconProjectDocument, exconproject);
        }

        return forward;
    }
    
    /**
     * This method find pending exconproject versions.
     * @param exconproject
     * @return VersionHistory
     */
    private VersionHistory findPendingVersion(ExconProject exconproject) {
        List<VersionHistory> histories = getVersionHistoryService().loadVersionHistory(ExconProject.class, exconproject.getProjectNumber());
        VersionHistory foundPending = null;
        for (VersionHistory history: histories) {
            if (history.getStatus() == VersionStatus.PENDING && exconproject.getSequenceNumber() < history.getSequenceOwnerSequenceNumber()) {
                foundPending = history;
                break;
            }
        }
        return foundPending;
    }
    
    /**
     * This method shows prompt for editing pending exconproject version.
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    private ActionForward showPromptForEditingPendingVersion(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return this.performQuestionWithoutInput(mapping, form, request, response, "EDIT_OR_VERSION_QUESTION_ID",
                    getResources(request).getMessage(EXCON_PROJECT_VERSION_EDITPENDING_PROMPT_KEY),
                    KRADConstants.CONFIRMATION_QUESTION,
                    KRADConstants.MAPPING_CANCEL, "");
    }
    
    /**
     * This method process the edit pending version prompt.
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws WorkflowException,IOException
     */
    private ActionForward processPromptForEditingPendingVersionResponse(ActionMapping mapping, HttpServletRequest request,
            HttpServletResponse response, ExconProjectForm exconProjectForm, 
            VersionHistory foundPending) throws WorkflowException, 
                                                IOException {
        ActionForward forward;
        Object buttonClicked = request.getParameter(KRADConstants.QUESTION_CLICKED_BUTTON);
        if (ConfirmationQuestion.NO.equals(buttonClicked)) {
            forward = mapping.findForward(Constants.MAPPING_BASIC);            
        } else {
            initializeFormWithExconProject(exconProjectForm, (ExconProject) foundPending.getSequenceOwner());
            response.sendRedirect(makeDocumentOpenUrl(exconProjectForm.getExconProjectDocument()));
            forward = null;
        }
        return forward;
    }
    
    private void initializeFormWithExconProject(ExconProjectForm exconProjectForm, ExconProject exconProject) throws WorkflowException {
        reinitializeExconProjectForm(exconProjectForm, findDocumentForExconProject(exconProject));
    }

    private ExconProjectDocument findDocumentForExconProject(ExconProject exconProject) throws WorkflowException {
        ExconProjectDocument document = (ExconProjectDocument) getDocumentService().getByDocumentHeaderId(exconProject.getExconProjectDocument().getDocumentNumber());
        document.setExconProject(exconProject);
        return document;
    }
    
    /**
     * This method prepares the ExconProjectForm with the document found via the ExconProject lookup
     * Because the helper beans may have preserved a different ExconProjectForm, we need to reset these too
     * @param exconProjectForm
     * @param document
     */
    private void reinitializeExconProjectForm(ExconProjectForm exconProjectForm, ExconProjectDocument document) throws WorkflowException {
        exconProjectForm.populateHeaderFields(document.getDocumentHeader().getWorkflowDocument());
        exconProjectForm.setDocument(document);
        document.setDocumentSaveAfterExconProjectLookupEditOrVersion(true);
        exconProjectForm.initialize();
    }
    
    private String makeDocumentOpenUrl(ExconProjectDocument newExconProjectDocument) {
        String workflowUrl = getKualiConfigurationService().getPropertyValueAsString(KRADConstants.WORKFLOW_URL_KEY);
        return String.format(DOC_HANDLER_URL_PATTERN, workflowUrl, newExconProjectDocument.getDocumentNumber());
    }
    
    private ActionForward createAndSaveNewExconProjectVersion(HttpServletResponse response, ExconProjectForm exconProjectForm,
    		ExconProjectDocument exconProjectDocument, ExconProject exconProject) throws Exception {
//    	exconProjectForm.getExconProjectDocument().getExconProject().setNewVersion(true); // TODO: not sure this is needed...but if so, it comes from exconProject
    	ExconProjectDocument newExconProjectDocument = getExconProjectService().createNewExconProjectVersion(exconProjectForm.getExconProjectDocument());
    	getDocumentService().saveDocument(newExconProjectDocument);
    	getExconProjectService().updateExconProjectSequenceStatus(newExconProjectDocument.getExconProject(), VersionStatus.PENDING);
    	getVersionHistoryService().updateVersionHistory(newExconProjectDocument.getExconProject(), VersionStatus.PENDING,
    			GlobalVariables.getUserSession().getPrincipalName());
    	reinitializeExconProjectForm(exconProjectForm, newExconProjectDocument);
    	return new ActionForward(makeDocumentOpenUrl(newExconProjectDocument), true);
    }
    
    public ActionForward addAssociatedDocumentStub(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) 
            throws Exception {
    	getExconProjectAssociatedDocumentsBean(form).reInitDocument();
    	return mapping.findForward(Constants.MAPPING_BASIC);
    }
    
    public ActionForward addAssociatedDocument(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) 
            throws Exception {
    	getExconProjectAssociatedDocumentsBean(form).addExconProjectAssociatedDocument();
    	return mapping.findForward(Constants.MAPPING_BASIC);
    }
    
    public ActionForward deleteAssociatedDocument(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) 
            throws Exception {
    	getExconProjectAssociatedDocumentsBean(form).deleteExconProjectAssociatedDocument(getLineToDelete(request));
    	return mapping.findForward(Constants.MAPPING_BASIC);
    }
    
    private ExconProjectAssociatedDocumentsBean getExconProjectAssociatedDocumentsBean(ActionForm form) {
        return ((ExconProjectForm) form).getExconProjectAssociatedDocumentsBean();
    }
	
}