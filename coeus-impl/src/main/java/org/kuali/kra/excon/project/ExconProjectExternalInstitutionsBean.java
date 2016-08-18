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
import org.kuali.kra.excon.rules.ExconProjectExternalInstitutionAddRuleImpl;
import org.kuali.rice.krad.service.BusinessObjectService;

import java.io.Serializable;
import java.util.List;

//import org.kuali.kra.excon.rules.ExconProjectExternalInstitutionAddRuleImpl;

/**
 * This is the base class for handling ExconProjectExternalInstitutions
 */
public class ExconProjectExternalInstitutionsBean implements Serializable {

	private static final long serialVersionUID = 2345345345457L;
	protected ExconProjectExternalInstitution newExternalInstitution;
    protected ExconProjectForm exconProjectForm;
    
    private transient BusinessObjectService businessObjectService;
  
    public ExconProjectExternalInstitutionsBean(ExconProjectForm exconProjectForm) {
        this.exconProjectForm = exconProjectForm;
        init();
    }
    
    public void addExconProjectExternalInstitution() {
        boolean success = new ExconProjectExternalInstitutionAddRuleImpl().processAddExconProjectExternalInstitutionBusinessRules(getExconProject(), getExconProjectExternalInstitution());
        if(success){
            getExconProject().add(getExconProjectExternalInstitution());
            init();
        }
    }
    
    /**
     * Delete an ExconProjectExternalInstitution
     * @param lineToDelete
     */
    public void deleteExconProjectExternalInstitution(int lineToDelete) {
        deleteExconProjectExternalInstitution(getExconProjectExternalInstitutions(), lineToDelete);                
    }
    
    public ExconProjectExternalInstitution getNewExternalInstitution() {
        return newExternalInstitution;
    }    

    /**
     * @return
     */
    public ExconProjectExternalInstitution getExconProjectExternalInstitution() {
       return (ExconProjectExternalInstitution) newExternalInstitution; 
    }
    
    /**
     * This method returns the list of ExconProjectExternalInstitutions
     * @return The list; may be empty
     */
    public List<ExconProjectExternalInstitution> getExconProjectExternalInstitutions() {
        return ((ExconProjectDocument)exconProjectForm.getDocument()).getExconProject().getExconProjectExternalInstitutions();
    }
    
    /**
     * This method finds the count of ExconProjectExternalInstitutions
     * @return The count; may be 0
     */
    public int getExconProjectExternalInstitutionsCount() {
        return getExconProjectExternalInstitutions().size();
    }
    
    /**
     * Find an ExconProjectExternalInstitution in a collection of ExconProjectExternalInstitution types and remove it from 
     * the main ExconProject collection of ExconProjectExternalInstitutions
     * @param events
     * @param lineToDelete
     */
    protected void deleteExconProjectExternalInstitution(List<ExconProjectExternalInstitution> events, int lineToDelete) {
        if(events.size() > lineToDelete) {
            ExconProjectExternalInstitution foundExternalInstitution = events.get(lineToDelete);
            getExconProject().getExconProjectExternalInstitutions().remove(foundExternalInstitution);
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
        this.newExternalInstitution = new ExconProjectExternalInstitution();
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
