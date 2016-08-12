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
import org.kuali.kra.excon.rules.ExconProjectEventAddRuleImpl;
import org.kuali.rice.krad.service.BusinessObjectService;

import java.io.Serializable;
import java.util.List;

/**
 * This is the base class for handling ExconProjectEvents
 */
public class ExconProjectEventsBean implements Serializable {

	private static final long serialVersionUID = 23456757457L;
	protected ExconProjectEvent newEvent;
    protected ExconProjectForm exconProjectForm;
    
    private transient BusinessObjectService businessObjectService;
  
    public ExconProjectEventsBean(ExconProjectForm exconProjectForm) {
        this.exconProjectForm = exconProjectForm;
        init();
    }
    
    public void addExconProjectEvent() {
        boolean success = new ExconProjectEventAddRuleImpl().processAddExconProjectEventBusinessRules(getExconProject(), getExconProjectEvent());
        if(success){
            getExconProject().add(getExconProjectEvent());
            init();
        }
    }
    
    /**
     * Delete an ExconProjectEvent
     * @param lineToDelete
     */
    public void deleteExconProjectEvent(int lineToDelete) {
        deleteExconProjectEvent(getExconProjectEvents(), lineToDelete);                
    }
    
    public ExconProjectEvent getNewEvent() {
        return newEvent;
    }    

    /**
     * @return
     */
    public ExconProjectEvent getExconProjectEvent() {
       return (ExconProjectEvent) newEvent; 
    }
    
    /**
     * This method returns the list of ExconProjectEvents
     * @return The list; may be empty
     */
    public List<ExconProjectEvent> getExconProjectEvents() {
        return ((ExconProjectDocument)exconProjectForm.getDocument()).getExconProject().getExconProjectEvents();
    }
    
    /**
     * This method finds the count of ExconProjectEvents
     * @return The count; may be 0
     */
    public int getExconProjectEventsCount() {
        return getExconProjectEvents().size();
    }
    
    /**
     * Find an ExconProjectEvent in a collection of ExconProjectEvent types and remove it from 
     * the main ExconProject collection of ExconProjectEvents
     * @param events
     * @param lineToDelete
     */
    protected void deleteExconProjectEvent(List<ExconProjectEvent> events, int lineToDelete) {
        if(events.size() > lineToDelete) {
            ExconProjectEvent foundEvent = events.get(lineToDelete);
            getExconProject().getExconProjectEvents().remove(foundEvent);
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
        this.newEvent = new ExconProjectEvent();
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
