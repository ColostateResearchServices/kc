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
import org.kuali.kra.excon.rules.ExconProjectCommentAddRuleImpl;
import org.kuali.rice.krad.service.BusinessObjectService;

import java.io.Serializable;
import java.util.List;

/**
 * This is the base class for handling ExconProjectComments
 */
public class ExconProjectCommentsBean implements Serializable {

	private static final long serialVersionUID = 234678678457L;
	protected ExconProjectComment newComment;
    protected ExconProjectForm exconProjectForm;
    
    private transient BusinessObjectService businessObjectService;

    
    public ExconProjectCommentsBean(ExconProjectForm exconProjectForm) {
        this.exconProjectForm = exconProjectForm;
        init();
    }
    
    public void addExconProjectComment() {
//    	boolean success = true;
    	boolean success = new ExconProjectCommentAddRuleImpl().processAddExconProjectCommentBusinessRules(getExconProject(),getExconProjectComment());
//        boolean success = new AwardUnitContactAddRuleImpl().processAddAwardUnitContactBusinessRules(getAward(), getUnitContact()); // add to implement add validation rules
        if(success){
            getExconProject().add(getExconProjectComment());
            init();
        }
    }
    
    /**
     * Delete an ExconProjectComment
     * @param lineToDelete
     */
    public void deleteExconProjectComment(int lineToDelete) {
        deleteExconProjectComment(getExconProjectComments(), lineToDelete);                
    }
  
    public ExconProjectComment getNewComment() {
        return newComment;
    }

    /**
     * @return
     */
    public ExconProjectComment getExconProjectComment() {
       return (ExconProjectComment) newComment; 
    }
    
    /**
     * This method returns the list of ExconProjectComments
     * @return The list; may be empty
     */
    public List<ExconProjectComment> getExconProjectComments() {
        return ((ExconProjectDocument)exconProjectForm.getDocument()).getExconProject().getExconProjectComments();
    }
    
    /**
     * This method finds the count of ExconProjectComments
     * @return The count; may be 0
     */
    public int getExconProjectCommentsCount() {
        return getExconProjectComments().size();
    }
    
    /**
     * Find an ExconProjectComment in a collection of ExconProjectComment types and remove it from 
     * the main ExconProject collection of ExconProjectComments
     * @param events
     * @param lineToDelete
     */
    protected void deleteExconProjectComment(List<ExconProjectComment> comments, int lineToDelete) {
        if(comments.size() > lineToDelete) {
            ExconProjectComment foundComment = comments.get(lineToDelete);
            getExconProject().getExconProjectComments().remove(foundComment);
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
        this.newComment = new ExconProjectComment();
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
