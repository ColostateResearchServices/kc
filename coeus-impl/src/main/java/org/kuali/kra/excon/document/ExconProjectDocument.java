/*
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
package org.kuali.kra.excon.document;
 
import org.apache.struts.action.ActionForm;
import org.kuali.coeus.common.impl.krms.KcKrmsFactBuilderServiceHelper;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.kuali.coeus.common.framework.custom.DocumentCustomData;
import org.kuali.coeus.common.framework.krms.KrmsRulesContext;
import org.kuali.coeus.common.framework.version.VersionStatus;
import org.kuali.coeus.common.framework.version.history.VersionHistoryService;
import org.kuali.coeus.sys.framework.controller.DocHandlerService;
import org.kuali.coeus.sys.framework.model.KcTransactionalDocumentBase;
import org.kuali.coeus.sys.framework.service.KcServiceLocator;
import org.kuali.coeus.sys.framework.workflow.KcWorkflowService;
import org.kuali.kra.excon.project.ExconProject;
import org.kuali.kra.excon.project.ExconProjectReview;
import org.kuali.kra.excon.service.ExconProjectService;
import org.kuali.kra.krms.KcKrmsConstants;
import org.kuali.rice.ken.util.NotificationConstants;
import org.kuali.rice.kew.api.KewApiConstants;
import org.kuali.rice.kew.api.WorkflowDocument;
import org.kuali.rice.kew.framework.postprocessor.DocumentRouteStatusChange;
import org.kuali.rice.kim.api.permission.PermissionService;
import org.kuali.rice.krad.document.Copyable;
import org.kuali.rice.krad.document.SessionDocument;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krms.api.engine.Facts.Builder;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is for exconProjectDocument...
 */
public class ExconProjectDocument extends KcTransactionalDocumentBase
implements  Copyable, SessionDocument, KrmsRulesContext {

    private static final long serialVersionUID = 5454824570787613256L;
    private transient boolean documentSaveAfterVersioning;
    private List<ExconProject> exconProjectList;
    public static final String DOCUMENT_TYPE_CODE = "ECPJ";
    private static final String DEFAULT_TAB = "Versions";
    private static final String ALTERNATE_OPEN_TAB = "Parameters";
    private static final Log LOG = LogFactory.getLog(ExconProjectDocument.class);
    
    @Override
    public String getDocumentTypeCode() {
        return DOCUMENT_TYPE_CODE;
    }
    /**.
     * Constructs a exconProjectDocument object
     */
    public ExconProjectDocument() {
        super();
        init();
    }
    public ExconProject getExconProject() {
        return getExconProjectList().size() > 0 ? getExconProjectList().get(0) : new ExconProject();
    }
    public void setExconProject(ExconProject exconProject){
        exconProjectList.set(0, exconProject);
    }
    public void setExconProjectList(List<ExconProject> exconProjectList) {
        this.exconProjectList = exconProjectList;
    }
    public List<ExconProject> getExconProjectList() {
        return exconProjectList;
    }

    /**.
     * The method is for doRouteStatusChange
     */
    @Override
    public void doRouteStatusChange(DocumentRouteStatusChange statusChangeEvent) {
        super.doRouteStatusChange(statusChangeEvent);
        String newStatus = statusChangeEvent.getNewRouteStatus();
        String currentUser=GlobalVariables.getUserSession().getPrincipalName();

        if (KewApiConstants.ROUTE_HEADER_FINAL_CD.equalsIgnoreCase(newStatus)) {
        	getExconProjectService().updateExconProjectSequenceStatus(getExconProject(), VersionStatus.ACTIVE);
            getVersionHistoryService().updateVersionHistory(getExconProject(), VersionStatus.ACTIVE, GlobalVariables.getUserSession().getPrincipalName());
        }
        if (newStatus.equalsIgnoreCase(KewApiConstants.ROUTE_HEADER_CANCEL_CD)
        || newStatus.equalsIgnoreCase(
        KewApiConstants.ROUTE_HEADER_DISAPPROVED_CD)) {
        	getExconProjectService().updateExconProjectSequenceStatus(getExconProject(), VersionStatus.CANCELED);
            getVersionHistoryService().updateVersionHistory(getExconProject(), VersionStatus.CANCELED, GlobalVariables. getUserSession().getPrincipalName());
        }

        for (ExconProject exconProject : exconProjectList) {
            exconProject.setExconProjectDocument(this);
        }
    }
    
    /**
     * This method specifies if this document may be
     * edited; i.e. it's only initiated or saved
     * @return boolean
     */
    public boolean isEditable() {
        WorkflowDocument workflowDoc =
        getDocumentHeader().getWorkflowDocument();
        return workflowDoc.isInitiated() || workflowDoc.isSaved() || workflowDoc.isCompletionRequested();
    }
    
    public boolean isCanceled() {
        WorkflowDocument workflow = getDocumentHeader().getWorkflowDocument();
        return workflow.isCanceled();
    }
    
    public boolean getCanModify() {
        Map<String,String> permissionDetails =new HashMap<String,String>();
        permissionDetails.put("sectionName", "exconProject");
        permissionDetails.put("documentTypeName", "exconProjectDocument");
        Map<String,String> qualifications =new HashMap<String,String>();
        qualifications.put("projectTypeCode", this.getExconProject().getProjectTypeCode());
        return getPermissionService().isAuthorized(GlobalVariables.getUserSession().getPrincipalId(),
        		"KC-EXCON",
        		"Modify Project Document",
        		qualifications); // TODO: change from hardcoded
        /*
        Map<String,String> qualifications =new HashMap<String,String>();
        qualifications.put(KraAuthorizationConstants.QUALIFICATION_UNIT_NUMBER, this.getLeadUnitNumber());
        return getPermissionService().isAuthorized(
                GlobalVariables.getUserSession().getPrincipalId(), 
                KraAuthorizationConstants.KC_AWARD_NAMESPACE, 
                KraAuthorizationConstants.PERMISSION_MODIFY_AWARD, 
                qualifications);
         */
//    	return true; // TODO: hack
    }
    
    protected PermissionService getPermissionService() {
        return KcServiceLocator.getService(PermissionService.class);
    }
    
    protected void init() {
        exconProjectList = new ArrayList<ExconProject>();
        exconProjectList.add(new ExconProject());
    }
    /**
     * @return
     */
    public boolean isDocumentSaveAfterVersioning() {
        return documentSaveAfterVersioning;
    }
    
    /**
     * @param documentSaveAfterVersioning
     */
    public void setDocumentSaveAfterExconProjectLookupEditOrVersion(boolean documentSaveAfterVersioning) {
        this.documentSaveAfterVersioning = documentSaveAfterVersioning;
    }
    /**
     * @return
     */
    protected VersionHistoryService getVersionHistoryService() {
        return KcServiceLocator.getService(VersionHistoryService.class);
    }
    
    protected ExconProjectService getExconProjectService() {
    	return KcServiceLocator.getService(ExconProjectService.class);
    }
    /**
     * This method is to check whether rice
     * async routing is ok now.
     * Close to hack.  called by holdingpageaction
     * Different document type may have different
     * routing set up, so each document type
     * can implement its own
     *  isProcessComplete
     * @return
     */
    public boolean isProcessComplete() {

        boolean isComplete = false;

        if (getDocumentHeader().hasWorkflowDocument()) {
            /**
             * per KRACOEUS-5394 changing from getDocumentHeader().getWorkflowDocument().isFinal().  This way
             * we route back to the award document more appropriately from holding page.
             */
            if (getDocumentHeader().getWorkflowDocument().isFinal() 
                    || getDocumentHeader().getWorkflowDocument().isProcessed()
                    || KcServiceLocator.getService(KcWorkflowService.class).hasPendingApprovalRequests(getDocumentHeader().getWorkflowDocument())) {
                isComplete = true;
            }
        }
           
        return isComplete;
    }
    

    @Override
    public List<? extends DocumentCustomData> getDocumentCustomData() {
        return getExconProject().getExconProjectCustomDataList();
    }

    
    /**
     * 
     * @see org.kuali.core.bo.PersistableBusinessObjectBase#buildListOfDeletionAwareLists()
     */
    @SuppressWarnings("unchecked")
    @Override
    public List buildListOfDeletionAwareLists() {
        List managedLists = super.buildListOfDeletionAwareLists();
        ExconProject exconProject = getExconProject();
        managedLists.add(exconProject.getExconProjectPersons());
        managedLists.add(exconProject.getExconProjectUnitPersons());
        managedLists.add(exconProject.getExconProjectRPSEntities());
        managedLists.add(exconProject.getExconProjectEvents());
        managedLists.add(exconProject.getExconProjectDestinations());
        managedLists.add(exconProject.getExconProjectComments());
        managedLists.add(exconProject.getExconProjectAttachments());
        managedLists.add(exconProject.getExconProjectExternalInstitutions());
        managedLists.add(exconProject.getExconProjectReviews());
        managedLists.add(exconProject.getExconProjectAssociatedDocuments());
        return managedLists;
    }
    
    public String getDocumentBoNumber() {
        return getExconProject().getDocumentKey();
    }
    @Override
    public void populateContextQualifiers(Map<String, String> qualifiers) {
        qualifiers.put("namespaceCode", "KC-EXCON");
        qualifiers.put("name", "KC Export Control Context");
    }
    
    @Override
    public void addFacts(Builder factsBuilder) {
        KcKrmsFactBuilderServiceHelper fbService = KcServiceLocator.getService("exconProjectFactBuilderService");
        fbService.addFacts(factsBuilder, this);
    }
    @Override
    public void populateAgendaQualifiers(Map<String, String> qualifiers) {
        qualifiers.put(KcKrmsConstants.UNIT_NUMBER, getLeadUnitNumber());
    }

    public String getLeadUnitNumber() {
        return getExconProject().getLeadUnitNumber();
    }
    
    public String buildForwardUrl() {
        DocHandlerService DocHandlerService = KcServiceLocator.getService(DocHandlerService.class);
        String forward = DocHandlerService.getDocHandlerUrl(getDocumentNumber());
        forward = forward.replaceFirst(DEFAULT_TAB, ALTERNATE_OPEN_TAB);
        if (forward.indexOf("?") == -1) {
            forward += "?";
        }
        else {
            forward += "&";
        }
        forward += KewApiConstants.DOCUMENT_ID_PARAMETER + "=" + documentNumber;
        forward += "&" + KewApiConstants.COMMAND_PARAMETER + "=" + NotificationConstants.NOTIFICATION_DETAIL_VIEWS.DOC_SEARCH_VIEW;
        if (GlobalVariables.getUserSession().isBackdoorInUse()) {
            forward += "&" + KewApiConstants.BACKDOOR_ID_PARAMETER + "=" + GlobalVariables.getUserSession().getPrincipalName();
        }
        
        String returnVal = "<a href=\"" + forward + "\"target=\"_blank\">" + documentNumber + "</a>";
        return returnVal;
    }
    
    /**
     * @see org.kuali.coeus.sys.framework.model.KcTransactionalDocumentBase#answerSplitNodeQuestion(java.lang.String)
     */
    @Override
    public boolean answerSplitNodeQuestion( String routeNodeName ) {
    	LOG.debug("Processing answerSplitNodeQuestion:"+routeNodeName );
    	ExconProject exconProject=getExconProject();
        if( StringUtils.equals("isActualMatch", routeNodeName )) {
        	if (!StringUtils.equals("RPS", exconProject.getProjectTypeCode())) {return false;}
        	if (exconProject.getRpsEntity() != null && StringUtils.equals(exconProject.getRpsEntity().getRpsMatchCode(),"ACT")) {
        		return true;
        	}
        	return false;
        }
        String reviewPrefix="requiresReviewBy";
        if (StringUtils.startsWith(routeNodeName, reviewPrefix)) {
        	String reviewTypeCode=routeNodeName.substring(reviewPrefix.length());
        	for (ExconProjectReview review : exconProject.getExconProjectReviews()) {
        		if (StringUtils.equals(reviewTypeCode,review.getProjectReviewTypeCode())) {
        			return true;
        		}
        	}        	
        	return false;
        }
        //defer to the super class. KcTransactionalDocumentBase will throw the UnsupportedOperationException
        //if no super class answers the question.
        return super.answerSplitNodeQuestion(routeNodeName);
    }

}
