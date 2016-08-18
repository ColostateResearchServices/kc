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


import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.kuali.coeus.sys.framework.service.KcServiceLocator;
import org.kuali.coeus.common.framework.attachment.AttachmentFile;
import org.kuali.kra.excon.document.ExconProjectDocument;
import org.kuali.kra.excon.rules.ExconProjectAttachmentAddRuleImpl;
import org.kuali.rice.krad.service.BusinessObjectService;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is the base class for handling ExconProjectAttachments
 */
public class ExconProjectAttachmentsBean implements Serializable {

	private static final long serialVersionUID = 234523457L;
    private final ExconProjectForm form;
    
    private ExconProjectAttachment newAttachment;
    
    public ExconProjectAttachmentsBean(final ExconProjectForm form) {
        this.form = form;
    }
    
    
    
    
    
    
    /**
     * Gets the new attachment. This method will not return null.
     * Also, The ExconProjectAttachment should have a valid exconProject Id at this point.
     * @return the new attachment
     */
    public ExconProjectAttachment getNewAttachment() {
        if (this.newAttachment == null) {
            this.initAttachment();
        }
        
        return this.newAttachment;
    }
    
    /**
     * initializes a new attachment setting the exconProject id.
     */
    private void initAttachment() {
        this.setNewAttachment(new ExconProjectAttachment(this.getExconProject()));
    }

    /**
     * Sets the newAttachment attribute value.
     * @param newAttachment The newAttachment to set.
     */
    public void setNewAttachment(ExconProjectAttachment newAttachment) {
        this.newAttachment = newAttachment;
    }

    /**
     * Gets the form attribute. 
     * @return Returns the form.
     */
    public ExconProjectForm getForm() {
        return form;
    }
    
    /**
     * Get the ExconProject.
     * @return the ExconProject
     * @throws IllegalArgumentException if the {@link ExconProjectDocument ExconProjectDocument}
     * or {@link ExconProject ExconProject} is {@code null}.
     */
    public ExconProject getExconProject() {

        if (this.form.getExconProjectDocument() == null) {
            throw new IllegalArgumentException("the document is null");
        }
        
        if (this.form.getExconProjectDocument().getExconProject() == null) {
            throw new IllegalArgumentException("the exconProject is null");
        }

        return this.form.getExconProjectDocument().getExconProject();
    }
    
    /**
     * Adds the "new" ExconProjectAttachment to the ExconProject.  Before
     * adding this method executes validation.  If the validation fails the attachment is not added.
     */
    public void addExconProjectAttachment() {
    	
    	boolean success = new ExconProjectAttachmentAddRuleImpl().processAddExconProjectAttachmentBusinessRules(getExconProject(),getNewAttachment());
    	if (success) {
    		this.refreshAttachmentReferences(Collections.singletonList(this.getNewAttachment()));
    		this.syncNewFiles(Collections.singletonList(this.getNewAttachment()));
        
    		this.assignDocumentId(Collections.singletonList(this.getNewAttachment()), 
                this.createTypeToMaxDocNumber(this.getExconProject().getExconProjectAttachments()));
        
    		this.newAttachment.setExconProjectId(this.getExconProject().getProjectId()); //OJB Hack.  Could not get the exconProjectId to persist with anonymous access in repository file.
    		this.getExconProject().add(this.newAttachment);
    		getBusinessObjectService().save(this.newAttachment);

    		this.initNewAttachment();
    	}
    }
    
    /**
     * retrieves an attachment from a protocol based on a type.
     * 
     * @param <T> the type parameter
     * @param attachmentNumber the attachment number
     * @param type the type token
     * @return the attachment or null if not found
     * @throws IllegalArgumentException if the type is null or not recognized
     */
    public ExconProjectAttachment retrieveExistingAttachment(int attachmentNumber) {
        if (!validIndexForList(attachmentNumber, this.getExconProject().getExconProjectAttachments())) {
            return null;
        }
        
        return this.getExconProject().getExconProjectAttachments().get(attachmentNumber);
    }
    
    /**
     * Checks if a given index is valid for a given list. This method returns null if the list is null.
     * 
     * @param index the index
     * @param forList the list
     * @return true if a valid index
     */
    private static boolean validIndexForList(int index, final List<?> forList) {      
        return forList != null && index >= 0 && index <= forList.size() - 1;
    }
    
    /**
     * initializes a new attachment personnel setting the protocol id.
     */
    private void initNewAttachment() {
        this.setNewAttachment(new ExconProjectAttachment(this.getExconProject()));
    }
    
    /**
     * Creates a map containing the highest doc number from a collection of attachments for each type code.
     * @param <T> the type
     * @param attachments the collection of attachments
     * @return the map
     */
    private Map<String, Integer> createTypeToMaxDocNumber(List<ExconProjectAttachment> attachments) {
        
        final Map<String, Integer> typeToDocNumber = new HashMap<String, Integer>();
        
        for (ExconProjectAttachment attachment : attachments) {
            final Integer curMax = typeToDocNumber.get(attachment.getTypeCode());
            if (curMax == null || curMax.intValue() < attachment.getDocumentId().intValue()) {
                typeToDocNumber.put(attachment.getTypeCode(), attachment.getDocumentId());
            }
        }
        
        return typeToDocNumber;
    }
    
    /** 
     * assigns a document id to all attachments in the passed in collection based on the passed in type to doc number map. 
     * 
     * @param <T> the type of attachments
     * @param attachments the collection of attachments that require doc number assignment
     * @param typeToDocNumber the map that contains all the highest doc numbers of existing attachments based on type code
     */
    private void assignDocumentId(List<ExconProjectAttachment> attachments,
        final Map<String, Integer> typeToDocNumber) {
        for (ExconProjectAttachment attachment : attachments) {
            if (attachment.isNew()) {
                final Integer nextDocNumber = ExconProjectAttachmentsBean.createNextDocNumber(typeToDocNumber.get(attachment.getTypeCode()));
                attachment.setDocumentId(nextDocNumber);
            }
        }
    }
    
    /**
     * Creates the next doc number from a passed in doc number.  If null 1 is returned.
     * @param docNumber the doc number to base the new number off of.
     * @return the new doc number.
     */
    private static Integer createNextDocNumber(final Integer docNumber) {
        return docNumber == null ? NumberUtils.INTEGER_ONE : Integer.valueOf(docNumber.intValue() + 1);
    }
    
    
    
    
    
    
    /** 
     * refreshes a given Collection of attachment's references that can change.
     * @param attachments the attachments.
     */
    private void refreshAttachmentReferences(List<ExconProjectAttachment> attachments) {
        assert attachments != null : "the attachments was null";
        
        for (final ExconProjectAttachment attachment : attachments) {   
                attachment.refreshReferenceObject("type");
        }
    }
    
    /** 
     * Syncs all new files for a given Collection of attachments on the exconProject.
     * @param attachments the attachments.
     */
    private void syncNewFiles(List<ExconProjectAttachment> attachments) {
        assert attachments != null : "the attachments was null";
        
        for (ExconProjectAttachment attachment : attachments) {
            if (ExconProjectAttachmentsBean.doesNewFileExist(attachment)) {
                final AttachmentFile newFile = AttachmentFile.createFromFormFile(attachment.getNewFile());
                //setting the sequence number to the old file sequence number
                if (attachment.getFile() != null) {
                    newFile.setSequenceNumber(attachment.getFile().getSequenceNumber());
                }
                attachment.setFile(newFile);
            }
        }
    }
    
    /**
     * Checks if a new file exists on an attachment
     * 
     * @param attachment the attachment
     * @return true if new false if not
     */
    private static boolean doesNewFileExist(ExconProjectAttachment attachment) {
        return attachment.getNewFile() != null && StringUtils.isNotBlank(attachment.getNewFile().getFileName());
    }

    


    private BusinessObjectService getBusinessObjectService() {
        return KcServiceLocator.getService(BusinessObjectService.class);
    }
}
