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
package org.kuali.kra.excon.document.authorization;

import org.drools.core.util.StringUtils;
import org.kuali.coeus.common.framework.auth.KcTransactionalDocumentAuthorizerBase;
import org.kuali.kra.excon.document.ExconProjectDocument;
import org.kuali.rice.kew.api.KewApiConstants;
import org.kuali.rice.kew.api.action.ActionRequestType;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kns.authorization.AuthorizationConstants;
import org.kuali.rice.krad.document.Document;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
/**
 * This class is ExconProjectDocumentAuthorizer...
 */
public class ExconProjectDocumentAuthorizer extends KcTransactionalDocumentAuthorizerBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8150615104002799843L;

	/**.
	 * This method is for getting edit modes
	 * @param document the Document
	 * @param user the Person
	 * @param currentEditModes
	 *  the currentEditmodes ...
	 */
    public Set<String> getEditModes(Document document, Person user, Set<String> currentEditModes) {
        Set<String> editModes = new HashSet<String>();
        String userId = user.getPrincipalId();

        ExconProjectDocument exconProjectDocument = (ExconProjectDocument) document;

        if (exconProjectDocument.getExconProject().getProjectId() == null) {
            if (canCreateExconProject(userId)) {
//        	if (hasPermission(exconProjectDocument,user,"Create")) {
            	editModes.add(AuthorizationConstants.EditMode.FULL_ENTRY);
            } else {
                editModes.add(AuthorizationConstants.EditMode.UNVIEWABLE);
            }
        } else {
        	/* Change to actually check authorizations
            if (canExecuteExconProjectTask(userId,exconProjectDocument, TaskName.MODIFY_EXCON_PROJECT)) {
                editModes.add(AuthorizationConstants.EditMode.FULL_ENTRY);
            } else if (canExecuteExconProjectTask(userId, exconProjectDocument, TaskName.VIEW_EXCON_PROJECT)) {
                editModes.add(AuthorizationConstants.EditMode.VIEW_ONLY);
            } else {
                editModes.add(AuthorizationConstants.EditMode.UNVIEWABLE);
            }
            if (canExecuteExconProjectTask(userId,exconProjectDocument, TaskName.CREATE_EXCON_PROJECT)) {
                editModes.add("createExconProject");
            }
            */
        	if (hasPermission(exconProjectDocument,user,"Modify") && exconProjectDocument.isEditable()) {
        		editModes.add(AuthorizationConstants.EditMode.FULL_ENTRY);
        	} else if (hasPermission(exconProjectDocument,user,"View")) {
        		editModes.add(AuthorizationConstants.EditMode.VIEW_ONLY);
        	} else {
        		editModes.add(AuthorizationConstants.EditMode.UNVIEWABLE);
        	}
        	if (canCreateExconProject(userId)) {
        		editModes.add("createExconProject");
        	}
        	// TODO: this is a hack
        	/*
        	if (((ExconProjectDocument)document).isEditable()) {
        		editModes.add(AuthorizationConstants.EditMode.FULL_ENTRY);
        	}
        	else
        	{
        		editModes.add(AuthorizationConstants.EditMode.VIEW_ONLY);
        	}
        	editModes.add("createExconProject");
        	*/
        	
        }

        return editModes;
    }



    /**.
     * This method is for checking whether user
     * can execute exconProjecttask ExconProjectDocument
     * @param taskName the taskName
     * @param userId the userId
     * @return boolean
     */
    private boolean canExecuteExconProjectTask(String userId,ExconProjectDocument exconProjectDocument, String taskName) {
//        ExconProjectTask task = new ExconProjectTask(taskName, exconProjectDocument);
//        return getTaskAuthorizationService().isAuthorized(userId, task);
    	return true;
    }

    /**
     * Does the user have permission to create an ExconProject?
     * @param userId the userId
     * @return true if the user can create an ExconProject; otherwise false
     */
    private boolean canCreateExconProject(String userId) {
//        ApplicationTask task = new ApplicationTask(TaskName.CREATE_EXCON_PROJECT);
//        return getTaskAuthorizationService().isAuthorized(userId, task);
    	return getPermissionService().isAuthorized(userId, "KC-EXCON", "Create Project Document", new HashMap<String,String>());
    }

    /**
     * This method is for checking whether user can open
     * @param document the Document
     * @param user the Person
     * @return boolean
     */
    public boolean canOpen(Document document, Person user) {
        ExconProjectDocument exconProjectDocument = (ExconProjectDocument) document;
        if (exconProjectDocument.getExconProject().getProjectId() == null) {
            return hasPermission(exconProjectDocument,user,"Create");
        }
 //       return canExecuteExconProjectTask(user.getPrincipalId(),(ExconProjectDocument) document, TaskName.VIEW_EXCON_PROJECT);
        return hasPermission(exconProjectDocument,user,"Open");
 //       return true;
    }

    @Override
    public boolean canRoute(Document document, Person user) {
        boolean canRoute = false;
        ExconProjectDocument exconProjectDocument = (ExconProjectDocument) document;
//        canRoute = (!(isFinal(document) || isProcessed (document)) && hasPermission(exconProjectDocument, user, PermissionConstants.SUBMIT_EXCON_PROJECT));
        canRoute = (!(isFinal(document) || isProcessed(document) || isWaitingComplete(document)) && hasPermission(exconProjectDocument,user,"Submit"));
        return canRoute;
//        return true;
    }
    
    protected boolean isFinal(Document document) {
        return KewApiConstants.ROUTE_HEADER_FINAL_CD.equals(
                document.getDocumentHeader().getWorkflowDocument().getStatus().getCode());
    }
    
    protected boolean isProcessed (Document document){
       boolean isProcessed = false;
       String status = document.getDocumentHeader().getWorkflowDocument().getStatus().getCode();
       // if document is in processed state
       if (status.equalsIgnoreCase(KewApiConstants.ROUTE_HEADER_PROCESSED_CD))
               isProcessed = true;
       return isProcessed;   
   }
    
    protected boolean isWaitingComplete (Document document) {
    	return KewApiConstants.ROUTE_HEADER_ENROUTE_CD.equals(document.getDocumentHeader().getWorkflowDocument().getStatus().getCode()) &&
    			document.getDocumentHeader().getWorkflowDocument().getRequestedActions().contains(ActionRequestType.COMPLETE);
    }
    
    private boolean hasPermission(ExconProjectDocument exconProjectDocument, Person user, String permissionName) {
//        return isAuthorized(exconProjectDocument, Constants.MODULE_NAMESPACE_EXCON_PROJECT, permissionName, user.getPrincipalId());
//    	return isAuthorized(exconProjectDocument, "KC-EXCON", permissionName+" Project Document", user.getPrincipalId());
    	HashMap<String,String> roleQualifiers=new HashMap<String,String>();
    	if (!StringUtils.isEmpty(exconProjectDocument.getExconProject().getProjectTypeCode())) {
    		roleQualifiers.put("projectTypeCode", exconProjectDocument.getExconProject().getProjectTypeCode());
    	}
    	return isAuthorized(exconProjectDocument, "KC-EXCON", permissionName+" Project Document", user.getPrincipalId(), null, roleQualifiers);
//    	return true;
    }
    
    /**
     * @see org.kuali.rice.krad.document.DocumentAuthorizer#canInitiate(java.lang.String, org.kuali.rice.kim.api.identity.Person)
     */
    public boolean canInitiate(String documentTypeName, Person user) {
        return canCreateExconProject(user.getPrincipalId());
//    	return getPermissionService().hasPermission(user.getPrincipalId(), "KC-EXCON", "Create Project Document");
    	
    }
    public boolean canEdit(Document document, Person user) {
//        return canExecuteExconProjectTask(user.getPrincipalId(), (ExconProjectDocument) document, TaskName.MODIFY_EXCON_PROJECT);
    	return hasPermission((ExconProjectDocument)document,user,"Modify");
//    	return true;
    }

    @Override
    public boolean canSendNoteFyi(Document document, Person user) {
        return false;
    }

    @Override
    public boolean canFyi(Document document, Person user) {
        return false;
    }



}
