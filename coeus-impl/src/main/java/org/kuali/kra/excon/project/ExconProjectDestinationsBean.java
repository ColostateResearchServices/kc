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
import org.kuali.kra.excon.rules.ExconProjectDestinationAddRuleImpl;
import org.kuali.rice.krad.service.BusinessObjectService;

import java.io.Serializable;
import java.util.List;

/**
 * This is the base class for handling ExconProjectDestinations
 */
public class ExconProjectDestinationsBean implements Serializable {

	private static final long serialVersionUID = 234563457L;
	protected ExconProjectDestination newDestination;
    protected ExconProjectForm exconProjectForm;
    
    private transient BusinessObjectService businessObjectService;
  
    public ExconProjectDestinationsBean(ExconProjectForm exconProjectForm) {
        this.exconProjectForm = exconProjectForm;
        init();
    }
    
    public void addExconProjectDestination() {
        boolean success = new ExconProjectDestinationAddRuleImpl().processAddExconProjectDestinationBusinessRules(getExconProject(), getNewDestination());
        if(success){
            getExconProject().add(getExconProjectDestination());
            init();
        }
    }
    
    /**
     * Delete an ExconProjectDestination
     * @param lineToDelete
     */
    public void deleteExconProjectDestination(int lineToDelete) {
        deleteExconProjectDestination(getExconProjectDestinations(), lineToDelete);                
    }

    
    public ExconProjectDestination getNewDestination() {
        return newDestination;
    }    

    /**
     * @return
     */
    public ExconProjectDestination getExconProjectDestination() {
       return (ExconProjectDestination) newDestination; 
    }
    
    /**
     * This method returns the list of ExconProjectDestinations
     * @return The list; may be empty
     */
    public List<ExconProjectDestination> getExconProjectDestinations() {
        return ((ExconProjectDocument)exconProjectForm.getDocument()).getExconProject().getExconProjectDestinations();
    }
    
    /**
     * This method finds the count of ExconProjectDestinations
     * @return The count; may be 0
     */
    public int getExconProjectDestinationsCount() {
        return getExconProjectDestinations().size();
    }
    
    /**
     * Find an ExconProjectDestination in a collection of ExconProjectDestination types and remove it from 
     * the main ExconProject collection of ExconProjectDestinations
     * @param destinations
     * @param lineToDelete
     */
    protected void deleteExconProjectDestination(List<ExconProjectDestination> destinations, int lineToDelete) {
        if(destinations.size() > lineToDelete) {
            ExconProjectDestination foundDestination = destinations.get(lineToDelete);
            getExconProject().getExconProjectDestinations().remove(foundDestination);
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
        this.newDestination = new ExconProjectDestination();
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
