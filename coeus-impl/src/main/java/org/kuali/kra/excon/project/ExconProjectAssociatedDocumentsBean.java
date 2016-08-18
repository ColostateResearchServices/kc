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
package org.kuali.kra.excon.project;


import org.kuali.coeus.sys.framework.service.KcServiceLocator;
import org.kuali.kra.excon.document.ExconProjectDocument;
import org.kuali.kra.excon.rules.ExconProjectAssociatedDocumentAddRuleImpl;
import org.kuali.rice.krad.service.BusinessObjectService;

import java.io.Serializable;
import java.util.List;

/**
 * This is the base class for handling ExconProjectAssociatedDocuments
 */
public class ExconProjectAssociatedDocumentsBean implements Serializable {

	private static final long serialVersionUID = 789757457L;
	protected ExconProjectAssociatedDocument newAssociatedDocument;
    protected ExconProjectForm exconProjectForm;
    
    private transient BusinessObjectService businessObjectService;
  
    public ExconProjectAssociatedDocumentsBean(ExconProjectForm exconProjectForm) {
        this.exconProjectForm = exconProjectForm;
        init();
    }
    
    public void addExconProjectAssociatedDocument() {
        boolean success = new ExconProjectAssociatedDocumentAddRuleImpl().processAddExconProjectAssociatedDocumentBusinessRules(getExconProject(), getExconProjectAssociatedDocument());
    	if(success){
            getExconProject().add(getExconProjectAssociatedDocument());
            init();
        }
    }
    
    public void reInitDocument() {
    	newAssociatedDocument.setAssocDocNumber(null);
    	newAssociatedDocument.setAssocDocTitle(null);
    }
    
    public String getAssocDocLookupURL() {
    	return getNewAssociatedDocument().getAssocDocLookupURL(exconProjectForm.getFormKey());
    }
    
    /**
     * Delete an ExconProjectAssociatedDocument
     * @param lineToDelete
     */
    public void deleteExconProjectAssociatedDocument(int lineToDelete) {
        deleteExconProjectAssociatedDocument(getExconProjectAssociatedDocuments(), lineToDelete);                
    }
    
    public ExconProjectAssociatedDocument getNewAssociatedDocument() {
        return newAssociatedDocument;
    }    

    /**
     * @return
     */
    public ExconProjectAssociatedDocument getExconProjectAssociatedDocument() {
       return (ExconProjectAssociatedDocument) newAssociatedDocument; 
    }
    
    /**
     * This method returns the list of ExconProjectAssociatedDocuments
     * @return The list; may be empty
     */
    public List<ExconProjectAssociatedDocument> getExconProjectAssociatedDocuments() {
        return ((ExconProjectDocument)exconProjectForm.getDocument()).getExconProject().getExconProjectAssociatedDocuments();
    }
    
    /**
     * This method finds the count of ExconProjectAssociatedDocuments
     * @return The count; may be 0
     */
    public int getExconProjectAssociatedDocumentsCount() {
        return getExconProjectAssociatedDocuments().size();
    }
    
    /**
     * Find an ExconProjectAssociatedDocument in a collection of ExconProjectAssociatedDocument types and remove it from 
     * the main ExconProject collection of ExconProjectAssociatedDocuments
     * @param events
     * @param lineToDelete
     */
    protected void deleteExconProjectAssociatedDocument(List<ExconProjectAssociatedDocument> events, int lineToDelete) {
        if(events.size() > lineToDelete) {
            ExconProjectAssociatedDocument foundAssociatedDocument = events.get(lineToDelete);
            getExconProject().getExconProjectAssociatedDocuments().remove(foundAssociatedDocument);
        }
    }

    /**
     * @return
     */
    protected BusinessObjectService getBusinessObjectService() {
        if(businessObjectService == null) {
            businessObjectService = (BusinessObjectService) KcServiceLocator.getService(BusinessObjectService.class);
        }
        return businessObjectService;
    }
    
    
    protected void init() {
        this.newAssociatedDocument = new ExconProjectAssociatedDocument();
    }
    

    protected ExconProject getExconProject() {
        return getDocument().getExconProject();
    }
    
    protected ExconProjectDocument getDocument() {
        return exconProjectForm.getExconProjectDocument();
    }

    void setBusinessObjectService(BusinessObjectService bos) {
        businessObjectService = bos;
    }
}
