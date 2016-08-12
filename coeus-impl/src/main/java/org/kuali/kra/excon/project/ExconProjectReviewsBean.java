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
import org.kuali.kra.excon.rules.ExconProjectReviewAddRuleImpl;
import org.kuali.rice.krad.service.BusinessObjectService;

import java.io.Serializable;
import java.util.List;

//import org.kuali.kra.excon.rules.ExconProjectReviewAddRuleImpl;

/**
 * This is the base class for handling ExconProjectReviews
 */
public class ExconProjectReviewsBean implements Serializable {

	private static final long serialVersionUID = 23456757457L;
	protected ExconProjectReview newReview;
    protected ExconProjectForm exconProjectForm;
    
    private transient BusinessObjectService businessObjectService;
  
    public ExconProjectReviewsBean(ExconProjectForm exconProjectForm) {
        this.exconProjectForm = exconProjectForm;
        init();
    }
    
    public void addExconProjectReview() {
        boolean success = new ExconProjectReviewAddRuleImpl().processAddExconProjectReviewBusinessRules(getExconProject(), getExconProjectReview());
        if(success){
            getExconProject().add(getExconProjectReview());
            init();
        }
    }
    
    /**
     * Delete an ExconProjectReview
     * @param lineToDelete
     */
    public void deleteExconProjectReview(int lineToDelete) {
        deleteExconProjectReview(getExconProjectReviews(), lineToDelete);                
    }
    
    public ExconProjectReview getNewReview() {
        return newReview;
    }    

    /**
     * @return
     */
    public ExconProjectReview getExconProjectReview() {
       return (ExconProjectReview) newReview; 
    }
    
    /**
     * This method returns the list of ExconProjectReviews
     * @return The list; may be empty
     */
    public List<ExconProjectReview> getExconProjectReviews() {
        return ((ExconProjectDocument)exconProjectForm.getDocument()).getExconProject().getExconProjectReviews();
    }
    
    /**
     * This method finds the count of ExconProjectReviews
     * @return The count; may be 0
     */
    public int getExconProjectReviewsCount() {
        return getExconProjectReviews().size();
    }
    
    /**
     * Find an ExconProjectReview in a collection of ExconProjectReview types and remove it from 
     * the main ExconProject collection of ExconProjectReviews
     * @param events
     * @param lineToDelete
     */
    protected void deleteExconProjectReview(List<ExconProjectReview> events, int lineToDelete) {
        if(events.size() > lineToDelete) {
            ExconProjectReview foundReview = events.get(lineToDelete);
            getExconProject().getExconProjectReviews().remove(foundReview);
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
        this.newReview = new ExconProjectReview();
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
