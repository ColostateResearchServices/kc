/*
 * Copyright 2005-2014 The Kuali Foundation
 * 
 * Licensed under the Educational CommexconProjecty License, Version 2.0 (the "License");
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
package org.kuali.kra.excon.service.impl;

import org.kuali.coeus.common.framework.version.VersionStatus;
import org.kuali.coeus.common.framework.version.history.VersionHistoryService;
import org.kuali.coeus.common.framework.custom.attr.CustomAttributeDocument;
import org.kuali.coeus.common.framework.version.history.VersionHistory;
import org.kuali.kra.excon.customdata.ExconProjectCustomData;
import org.kuali.kra.excon.document.ExconProjectDocument;
import org.kuali.kra.excon.project.ExconProject;
import org.kuali.kra.excon.project.ExconProjectAttachment;
import org.kuali.kra.excon.service.ExconProjectService;
import org.kuali.coeus.common.framework.version.VersionException;
import org.kuali.coeus.common.framework.version.VersioningService;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.service.DocumentService;
import org.kuali.rice.krad.service.SequenceAccessorService;

import java.text.DecimalFormat;
import java.util.*;

/** {@inheritDoc} */
public class ExconProjectServiceImpl implements ExconProjectService {
    
    private static final String EXCON_PROJECT_NUMBER = "projectNumber";
    private static final String EXCON_PROJECT_ID = "exconProjectId";
    private static final String SEQUENCE_NUMBER = "sequenceNumber";
    
    private BusinessObjectService businessObjectService;
    private VersioningService versioningService;
    private DocumentService documentService;
    private VersionHistoryService versionHistoryService;
    private SequenceAccessorService sequenceAccessorService;

    /**
     * Note ExconProjects are ordered by sequenceNumber
     * @see org.kuali.kra.exconProject.home.ExconProjectService#findExconProjectsForExconProjectNumber(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public List<ExconProject> findExconProjectsForExconProjectNumber(String exconProjectNumber) {
        List<ExconProject> results = new ArrayList<>(businessObjectService.findMatchingOrderBy(ExconProject.class,
                                                                Collections.singletonMap(EXCON_PROJECT_NUMBER, exconProjectNumber),
                                                                SEQUENCE_NUMBER,
                                                                true));
        return results;
    }

    /** {@inheritDoc} */
    public ExconProject getExconProject(Long exconProjectId) {
        return exconProjectId != null ? (ExconProject) businessObjectService.findByPrimaryKey(ExconProject.class,
                Collections.singletonMap(EXCON_PROJECT_ID, exconProjectId)) : null;
    }
    
    /** {@inheritDoc} */
    public ExconProject getExconProject(String exconProjectId) {
        return exconProjectId != null ? (ExconProject) businessObjectService.findByPrimaryKey(ExconProject.class,
                Collections.singletonMap(EXCON_PROJECT_ID, exconProjectId)) : null;
    }

    public void setBusinessObjectService(BusinessObjectService businessObjectService) {
        this.businessObjectService = businessObjectService;
    }

    public String getNextExconProjectNumber() {
    	Long nextExconProjectNumber = sequenceAccessorService.getNextAvailableSequenceNumber("EXCON_PROJECT_NUMBER_S");
    	DecimalFormat formatter = new DecimalFormat("000000");        
        String nextExconProjectNumberStr = formatter.format(nextExconProjectNumber);
        return nextExconProjectNumberStr;
    }
    
    /**
     * Set the Sequence Accessor Service.
     * @param sequenceAccessorService the Sequence Accessor Service
     */
    public void setSequenceAccessorService(SequenceAccessorService sequenceAccessorService) {
        this.sequenceAccessorService = sequenceAccessorService;
    }
    
    public ExconProjectDocument createNewExconProjectVersion(ExconProjectDocument exconProjectDocument) throws VersionException, WorkflowException {
        ExconProject newVersion = getVersioningService().createNewVersion(exconProjectDocument.getExconProject());
        
        for (ExconProjectAttachment attach : newVersion.getExconProjectAttachments()) {
            ExconProjectAttachment orignalAttachment = findMatchingExconProjectAttachment(exconProjectDocument.getExconProject().getExconProjectAttachments(), attach.getFileId());
            attach.setUpdateUser(orignalAttachment.getUpdateUser());
            attach.setUpdateTimestamp(orignalAttachment.getUpdateTimestamp());
            attach.setUpdateUserSet(true);
        }
        
        incrementVersionNumberIfCanceledVersionsExist(newVersion);//Canceled versions retain their own version number.
        ExconProjectDocument newExconProjectDocument = (ExconProjectDocument) getDocumentService().getNewDocument(ExconProjectDocument.class);
        newExconProjectDocument.getDocumentHeader().setDocumentDescription(exconProjectDocument.getDocumentHeader().getDocumentDescription());
        newExconProjectDocument.setExconProject(newVersion);
        newVersion.setExconProjectDocument(newExconProjectDocument);        
        synchNewCustomAttributes(newVersion, exconProjectDocument.getExconProject());
        
        return newExconProjectDocument;
    }   
    
    /**
     * @see org.kuali.kra.exconProject.home.ExconProjectService#synchNewCustomAttributes(org.kuali.kra.exconProject.home.ExconProject, org.kuali.kra.exconProject.home.ExconProject)
     */
    public void synchNewCustomAttributes(ExconProject newExconProject, ExconProject oldExconProject) {
        Set<Integer> availableCustomAttributes = new HashSet<Integer>();
        for (ExconProjectCustomData exconProjectCustomData : newExconProject.getExconProjectCustomDataList()) {
            availableCustomAttributes.add(exconProjectCustomData.getCustomAttributeId().intValue());
        }
        
        if(oldExconProject.getExconProjectDocument() != null) {
            Map<String, CustomAttributeDocument> customAttributeDocuments = oldExconProject.getExconProjectDocument().getCustomAttributeDocuments();
            for (Map.Entry<String, CustomAttributeDocument> entry : customAttributeDocuments.entrySet()) {
                CustomAttributeDocument customAttributeDocument = entry.getValue();
                if(!availableCustomAttributes.contains(customAttributeDocument.getId())) {
                    ExconProjectCustomData exconProjectCustomData = new ExconProjectCustomData();
                    exconProjectCustomData.setCustomAttributeId((long) customAttributeDocument.getId());
                    exconProjectCustomData.setCustomAttribute(customAttributeDocument.getCustomAttribute());
                    exconProjectCustomData.setValue(customAttributeDocument.getCustomAttribute().getDefaultValue());
                    exconProjectCustomData.setExconProject(newExconProject);
                    newExconProject.getExconProjectCustomDataList().add(exconProjectCustomData);
                }
            }
            newExconProject.getExconProjectCustomDataList().removeAll(getInactiveCustomDataList(newExconProject.getExconProjectCustomDataList(), customAttributeDocuments));
        }
    }
    
    private List<ExconProjectCustomData> getInactiveCustomDataList(List<ExconProjectCustomData> exconProjectCustomDataList, Map<String, CustomAttributeDocument> customAttributeDocuments) {
        List<ExconProjectCustomData> inactiveCustomDataList = new ArrayList<ExconProjectCustomData>();
        for(ExconProjectCustomData exconProjectCustomData : exconProjectCustomDataList) {
            CustomAttributeDocument customAttributeDocument = customAttributeDocuments.get(exconProjectCustomData.getCustomAttributeId().toString());
            if(customAttributeDocument == null || !customAttributeDocument.isActive()) {
                inactiveCustomDataList.add(exconProjectCustomData);
            }
        }
        return inactiveCustomDataList;
    }
    
    private ExconProjectAttachment findMatchingExconProjectAttachment(List<ExconProjectAttachment> originalExconProjectList, Long currentFileId) throws VersionException {
        for (ExconProjectAttachment attach : originalExconProjectList) {
            if (attach.getFileId().equals(currentFileId)) {
                return attach;
            }
        }
        throw new VersionException("Unable to find matching attachment.");
    }
    
    protected void incrementVersionNumberIfCanceledVersionsExist(ExconProject exconProject) {
        List<VersionHistory> versionHistory = (List<VersionHistory>) businessObjectService.findMatching(VersionHistory.class, getHashMap(exconProject.getProjectNumber()));
        exconProject.setSequenceNumber(versionHistory.size() + 1);
    }
    
    protected Map<String, String> getHashMap(String exconProjectNumber) {
        Map<String, String> map = new HashMap<String,String>();
        map.put("sequenceOwnerVersionNameValue", exconProjectNumber);
        return map;
    }
    
    protected VersioningService getVersioningService() {
        return versioningService;
    }

    public void setVersioningService(VersioningService versioningService) {
        this.versioningService = versioningService;
    }

    protected DocumentService getDocumentService() {
        return documentService;
    }

    public void setDocumentService(DocumentService documentService) {
        this.documentService = documentService;
    }

    protected VersionHistoryService getVersionHistoryService() {
        return versionHistoryService;
    }

    public void setVersionHistoryService(VersionHistoryService versionHistoryService) {
        this.versionHistoryService = versionHistoryService;
    }

    @Override
    public void updateExconProjectSequenceStatus(ExconProject exconProject, VersionStatus status) {
        if (status.equals(VersionStatus.ACTIVE)) {
            archiveCurrentActiveExconProject(exconProject.getProjectNumber());
        }
        exconProject.setExconProjectSequenceStatus(status.toString());
        if (exconProject.getExconProjectDocument() != null) {
            businessObjectService.save(exconProject);
        }
    }
    
    protected void archiveCurrentActiveExconProject(String exconProjectNumber) {
        Map<String, Object> values = new HashMap<String, Object>();
        values.put("projectNumber", exconProjectNumber);
        values.put("exconProjectSequenceStatus", VersionStatus.ACTIVE.name());
        Collection<ExconProject> exconProjects = businessObjectService.findMatching(ExconProject.class, values);
        for (ExconProject exconProject : exconProjects) {
            exconProject.setExconProjectSequenceStatus(VersionStatus.ARCHIVED.name());
            exconProject.setAllowUpdateTimestampToBeReset(false);
            businessObjectService.save(exconProject);
        }
    }

    /**
     * 
     * @see org.kuali.kra.exconProject.home.ExconProjectService#getActiveOrNewestExconProject(java.lang.String)
     */
    public ExconProject getActiveOrNewestExconProject(String exconProjectNumber) {
        List<VersionHistory> versions = getVersionHistoryService().loadVersionHistory(ExconProject.class, exconProjectNumber);
        VersionHistory newest = null;
        for (VersionHistory version: versions) {
            if (version.getStatus() == VersionStatus.ACTIVE) {
                newest = version;
//                break;
            } else if (newest == null || (version.getStatus() != VersionStatus.CANCELED && version.getSequenceOwnerSequenceNumber() > newest.getSequenceOwnerSequenceNumber())) {
                newest = version;
            }  
        }
        if (newest != null) {
            return (ExconProject) newest.getSequenceOwner();
        } else {
            return null;
        }
        
    }
    
    public ExconProject getExconProjectAssociatedWithDocument(String docNumber) {
        Map<String, Object> values = new HashMap<String, Object>();
        values.put("documentNumber", docNumber);
        List<ExconProject> exconProjects = (List<ExconProject>) businessObjectService.findMatching(ExconProject.class, values);
        return exconProjects.get(0);
    }
}
