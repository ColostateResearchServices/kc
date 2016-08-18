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
import org.kuali.kra.excon.rules.ExconProjectUnitPersonAddRuleImpl;
import org.kuali.rice.krad.service.BusinessObjectService;

import java.io.Serializable;
import java.util.List;

/**
 * This is the base class for handling ExconProjectUnitPersons
 */
public class ExconProjectUnitPersonsBean implements Serializable {

	private static final long serialVersionUID = 23864523457L;
	protected ExconProjectUnitPerson newUnitPerson;
    protected ExconProjectForm exconProjectForm;
    
    private transient BusinessObjectService businessObjectService;
  
    public ExconProjectUnitPersonsBean(ExconProjectForm exconProjectForm) {
        this.exconProjectForm = exconProjectForm;
        init();
    }
    
    public void addExconProjectUnitPerson() {
        boolean success = new ExconProjectUnitPersonAddRuleImpl().processAddExconProjectUnitPersonBusinessRules(getExconProject(),getNewUnitPerson());
        if(success){
            getExconProject().add(getExconProjectUnitPerson());
            init();
        }
    }
    
    /**
     * Delete an ExconProjectUnitPerson
     * @param lineToDelete
     */
    public void deleteExconProjectUnitPerson(int lineToDelete) {
        deleteExconProjectUnitPerson(getExconProjectUnitPersons(), lineToDelete);                
    }
    
    public ExconProjectUnitPerson getNewUnitPerson() {
        return newUnitPerson;
    }

    /**
     * @return
     */
    public ExconProjectUnitPerson getExconProjectUnitPerson() {
       return (ExconProjectUnitPerson) newUnitPerson; 
    }
    
    /**
     * This method returns the list of ExconProjectUnitPersons
     * @return The list; may be empty
     */
    public List<ExconProjectUnitPerson> getExconProjectUnitPersons() {
        return ((ExconProjectDocument)exconProjectForm.getDocument()).getExconProject().getExconProjectUnitPersons();
    }
    
    /**
     * This method finds the count of ExconProjectUnitPersons
     * @return The count; may be 0
     */
    public int getExconProjectUnitPersonsCount() {
        return getExconProjectUnitPersons().size();
    }
    
    /**
     * Find an ExconProjectUnitPerson in a collection of ExconProjectUnitPerson types and remove it from 
     * the main ExconProject collection of ExconProjectUnitPersons
     * @param UnitPersons
     * @param lineToDelete
     */
    protected void deleteExconProjectUnitPerson(List<ExconProjectUnitPerson> UnitPersons, int lineToDelete) {
        if(UnitPersons.size() > lineToDelete) {
            ExconProjectUnitPerson foundUnitPerson = UnitPersons.get(lineToDelete);
            getExconProject().getExconProjectUnitPersons().remove(foundUnitPerson);
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
        this.newUnitPerson = new ExconProjectUnitPerson();
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
