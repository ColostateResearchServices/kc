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
package org.kuali.kra.excon.service;

import org.kuali.coeus.common.framework.version.VersionStatus;
import org.kuali.kra.excon.document.ExconProjectDocument;
import org.kuali.kra.excon.project.ExconProject;
import org.kuali.coeus.common.framework.version.VersionException;
import org.kuali.rice.kew.api.exception.WorkflowException;

import java.util.List;

/**
 * 
 * This class intends to provide basic business service behavior 
 * and accessors for ExconProjects. 

 */
public interface ExconProjectService {
    /**
     * Get the ExconProject based upon its unique id number.
     * 
     * @param exconProjectId the ExconProject's unique id number
     * @return the ExconProject or null if not found.
     * 
     * @deprecated The identifier for ExconProject is a Long, but this method expects a String
     */
    public ExconProject getExconProject(String exconProjectId);

    /**
     * Get the ExconProject based upon its unique id number.
     * 
     * @param exconProjectId
     * @return
     */
    public ExconProject getExconProject(Long exconProjectId);
    
    /**
     * This method finds all ExconProjects for the specified exconProjectNumber
     * @param exconProjectId
     * @return The list of ExconProjects
     */
    public List<ExconProject> findExconProjectsForExconProjectNumber(String exconProjectNumber);
    
    /**
     * Create new version of the exconProject document
     * @param exconProjectDocument
     * @return
     * @throws VersionException
     */
    public ExconProjectDocument createNewExconProjectVersion(ExconProjectDocument exconProjectDocument) throws VersionException, WorkflowException;
    
    /**
     * Update the exconProject to use the new VersionStatus. If the version status is ACTIVE, any other active version of this
     * exconProject will be set to ARCHIVED.
     * @param exconProject
     * @param status
     */
    void updateExconProjectSequenceStatus(ExconProject exconProject, VersionStatus status);
    
    /**
     * Returns the active exconProject or if none exist, the newest non-cancelled exconProject.
     * @param exconProjectNumber
     * @return
     */
    ExconProject getActiveOrNewestExconProject(String exconProjectNumber);
    
    
    /**
     * This method is to synch custom attributes. During copy process only existing custom attributes
     * available in the old document is copied. We need to make sure we have all the latest custom attributes
     * tied to the new document.
     * @param newExconProject
     * @param oldExconProject
     */
    public void synchNewCustomAttributes(ExconProject newExconProject, ExconProject oldExconProject);
    
    public ExconProject getExconProjectAssociatedWithDocument(String docNumber);
    
    public String getNextExconProjectNumber();
    
    
}
