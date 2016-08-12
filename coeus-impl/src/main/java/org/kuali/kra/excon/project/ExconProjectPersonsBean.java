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
import org.kuali.kra.excon.rules.ExconProjectPersonAddRuleImpl;
import org.kuali.rice.krad.service.BusinessObjectService;

import java.io.Serializable;
import java.util.List;

/**
 * This is the base class for handling ExconProjectPersons
 */
public class ExconProjectPersonsBean implements Serializable {

	private static final long serialVersionUID = 27764523457L;
	protected ExconProjectPerson newPerson;
    protected ExconProjectForm exconProjectForm;
    protected String userName;
    
    private transient BusinessObjectService businessObjectService;
  
    public ExconProjectPersonsBean(ExconProjectForm exconProjectForm) {
        this.exconProjectForm = exconProjectForm;
        init();
    }
    
    public void addExconProjectPerson() {
        boolean success = new ExconProjectPersonAddRuleImpl().processAddExconProjectPersonBusinessRules(getExconProject(), getExconProjectPerson());
        if(success){
            getExconProject().add(getExconProjectPerson());
            init();
        }
    }
    
    public String getUserName() {
    	return userName;
    }
    
    public void setUserName(String userName) {
    	this.userName=userName;
    }
    
    /**
     * Delete an ExconProjectPerson
     * @param lineToDelete
     */
    public void deleteExconProjectPerson(int lineToDelete) {
        deleteExconProjectPerson(getExconProjectPersons(), lineToDelete);                
    }
    
    public ExconProjectPerson getNewPerson() {
        if ("International Travel".equals(getExconProject().getProjectType().getDescription())) {
        	newPerson.setRoleTypeCode("TVR");
        }
        return newPerson;
    }

    /**
     * @return
     */
    public ExconProjectPerson getExconProjectPerson() {
       return (ExconProjectPerson) newPerson; 
    }
    
    /**
     * This method returns the list of ExconProjectPersons
     * @return The list; may be empty
     */
    public List<ExconProjectPerson> getExconProjectPersons() {
        return ((ExconProjectDocument)exconProjectForm.getDocument()).getExconProject().getExconProjectPersons();
    }
    
    /**
     * This method finds the count of ExconProjectPersons
     * @return The count; may be 0
     */
    public int getExconProjectPersonsCount() {
        return getExconProjectPersons().size();
    }
    
    /**
     * Find an ExconProjectPerson in a collection of ExconProjectPerson types and remove it from 
     * the main ExconProject collection of ExconProjectPersons
     * @param Persons
     * @param lineToDelete
     */
    protected void deleteExconProjectPerson(List<ExconProjectPerson> Persons, int lineToDelete) {
        if(Persons.size() > lineToDelete) {
            ExconProjectPerson foundPerson = Persons.get(lineToDelete);
            getExconProject().getExconProjectPersons().remove(foundPerson);
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
        newPerson = new ExconProjectPerson();
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
