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
package edu.colostate.kc.award.reservation.document.authorization;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.drools.core.util.StringUtils;
import org.kuali.kra.authorization.ApplicationTask;
import org.kuali.kra.authorization.KcTransactionalDocumentAuthorizerBase;
import org.kuali.kra.infrastructure.Constants;
import org.kuali.kra.infrastructure.PermissionConstants;
import org.kuali.kra.infrastructure.TaskName;

import edu.colostate.kc.award.reservation.document.AwardAccountReservationDocument;
import edu.colostate.kc.award.reservation.AwardAccountReservation;

import org.kuali.rice.kew.api.KewApiConstants;
import org.kuali.rice.kew.api.action.ActionRequestType;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.permission.PermissionService;
import org.kuali.rice.kns.authorization.AuthorizationConstants;
import org.kuali.rice.krad.document.Document;
/**
 * This class is AwardAccountReservationDocumentAuthorizer...
 */
public class AwardAccountReservationDocumentAuthorizer extends KcTransactionalDocumentAuthorizerBase {

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

        AwardAccountReservationDocument awardAccountReservationDocument = (AwardAccountReservationDocument) document;

        if (awardAccountReservationDocument.getAwardAccountReservation().getReservationUser() == null) {
            if (canCreateAwardAccountReservation(userId)) {
//        	if (hasPermission(awardAccountReservationDocument,user,"Create")) {
            	editModes.add(AuthorizationConstants.EditMode.FULL_ENTRY);
            } else {
                editModes.add(AuthorizationConstants.EditMode.UNVIEWABLE);
            }
        } else {
        	if (hasPermission(awardAccountReservationDocument,user,"Modify") && awardAccountReservationDocument.isEditable()) {
        		editModes.add(AuthorizationConstants.EditMode.FULL_ENTRY);
        	} else if (hasPermission(awardAccountReservationDocument,user,"View")) {
        		editModes.add(AuthorizationConstants.EditMode.VIEW_ONLY);
        	} else {
        		editModes.add(AuthorizationConstants.EditMode.UNVIEWABLE);
        	}
        	if (canCreateAwardAccountReservation(userId)) {
        		editModes.add("createAwardAccountReservation");
        	}
        	// TODO: this is a hack
        	/*
        	if (((AwardAccountReservationDocument)document).isEditable()) {
        		editModes.add(AuthorizationConstants.EditMode.FULL_ENTRY);
        	}
        	else
        	{
        		editModes.add(AuthorizationConstants.EditMode.VIEW_ONLY);
        	}
        	editModes.add("createAwardAccountReservation");
        	*/
        	
        }

        return editModes;
    }



    /**.
     * This method is for checking whether user
     * can execute awardAccountReservationtask AwardAccountReservationDocument
     * @param taskName the taskName
     * @param userId the userId
     * @return boolean
     */
    private boolean canExecuteAwardAccountReservationTask(String userId,AwardAccountReservationDocument awardAccountReservationDocument, String taskName) {
//        AwardAccountReservationTask task = new AwardAccountReservationTask(taskName, awardAccountReservationDocument);
//        return getTaskAuthorizationService().isAuthorized(userId, task);
    	return true;
    }

    /**
     * Does the user have permission to create an AwardAccountReservation?
     * @param userId the userId
     * @return true if the user can create an AwardAccountReservation; otherwise false
     */
    private boolean canCreateAwardAccountReservation(String userId) {
    	return getPermissionService().isAuthorized(userId, "KC-AWARD", "Create Award Account Reservation Document", new HashMap<String,String>());
    }

    /**
     * This method is for checking whether user can open
     * @param document the Document
     * @param user the Person
     * @return boolean
     */
    public boolean canOpen(Document document, Person user) {
        AwardAccountReservationDocument awardAccountReservationDocument = (AwardAccountReservationDocument) document;
        if (awardAccountReservationDocument.getAwardAccountReservation().getReservationUser() == null) {
            return hasPermission(awardAccountReservationDocument,user,"Create");
        }
        return hasPermission(awardAccountReservationDocument,user,"Open");
    }

    @Override
    public boolean canRoute(Document document, Person user) {
        boolean canRoute = false;
        AwardAccountReservationDocument awardAccountReservationDocument = (AwardAccountReservationDocument) document;
        canRoute = (!(isFinal(document) || isProcessed(document) || isWaitingComplete(document)) && hasPermission(awardAccountReservationDocument,user,"Submit"));
        return canRoute;
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
    
    private boolean hasPermission(AwardAccountReservationDocument awardAccountReservationDocument, Person user, String permissionName) {
    	HashMap<String,String> roleQualifiers=new HashMap<String,String>();
    	return isAuthorized(awardAccountReservationDocument, "KC-AWARD", permissionName+" Award Account Reservation Document", user.getPrincipalId(), null, roleQualifiers);
    }
    
    /**
     * @see org.kuali.rice.krad.document.DocumentAuthorizer#canInitiate(java.lang.String, org.kuali.rice.kim.api.identity.Person)
     */
    public boolean canInitiate(String documentTypeName, Person user) {
        return canCreateAwardAccountReservation(user.getPrincipalId());
//    	return getPermissionService().hasPermission(user.getPrincipalId(), "KC-AWARD", "Create Award Account Reservation Document");
    	
    }
    public boolean canEdit(Document document, Person user) {
    	return hasPermission((AwardAccountReservationDocument)document,user,"Modify");
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
