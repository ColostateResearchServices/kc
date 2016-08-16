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
import org.apache.struts.upload.FormFile;
import org.kuali.coeus.sys.framework.service.KcServiceLocator;
import org.kuali.coeus.common.framework.attachment.AttachmentFile;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.identity.PersonService;
import org.kuali.rice.krad.service.BusinessObjectService;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class...
 */
public class ExconProjectAttachment extends ExconProjectAssociate implements Comparable<ExconProjectAttachment> {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 502762957346287794L;

    private Long exconProjectAttachmentId;
    
    private Long fileId;

    private Long projectId;

    private AttachmentFile file;

    private transient FormFile newFile;

    private String typeCode;

    private ExconProjectAttachmentType type;

    private Integer documentId;

    private String description;

    /**
     * empty ctor to satisfy JavaBean convention.
     */
    public ExconProjectAttachment() {
        super();
    }

    /**
     * Convenience ctor to add the exconProject as an owner.
     * 
     * <p>
     * This ctor does not validate any of the properties.
     * </p>
     * 
     * @param exconProject the exconProject.
     */
    public ExconProjectAttachment(final ExconProject exconProject) {
        this.setExconProject(exconProject);
    }

    /**
     * Gets the projectId attribute. 
     * @return Returns the projectId.
     */
    public Long getExconProjectId() {
        return projectId;
    }

    /**
     * Sets the projectId attribute value.
     * @param projectId The projectId to set.
     */
    public void setExconProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getTypeCode() {
        return this.typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public Integer getDocumentId() {
        return this.documentId;
    }

    public void setDocumentId(Integer documentId) {
        this.documentId = documentId;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAttachmentDescription() {
        return "ExconProject Attachment";
    }

    /**
     * Gets the ExconProject Attachment File.
     */
    public AttachmentFile getFile() {
        return this.file;
    }

    /**
     * Sets the ExconProject Attachment File.
     */
    public void setFile(AttachmentFile file) {
        this.file = file;
    }

    /**
     * Gets the ExconProject Attachment New File.
     * @return the ExconProject Attachment New File
     */
    public FormFile getNewFile() {
        return this.newFile;
    }

    /**
     * Sets the ExconProject Attachment New File.
     * @param newFile the ExconProject Attachment New File
     */
    public void setNewFile(FormFile newFile) {
        this.newFile = newFile;
    }

    /**
     * Gets the file Id. 
     * @return the file Id.
     */
    public Long getFileId() {
        return this.fileId;
    }

    /**
     * Sets the file Id.
     * @param fileId the file Id.
     */
    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    /**
     * Gets the  id.
     * @return the  id
     */
    public Long getExconProjectAttachmentId() {
        return this.exconProjectAttachmentId;
    }

    /**
     * Sets the id.
     * @param id the id
     */
    public void setExconProjectAttachmentId(Long exconProjectAttachmentId) {
        this.exconProjectAttachmentId = exconProjectAttachmentId;
    }

    /**
     * Gets the type attribute. 
     * @return Returns the type.
     */
    public ExconProjectAttachmentType getType() {
        if (!StringUtils.isEmpty(typeCode)) {
            this.refreshReferenceObject("type");
        }
        return type;
    }

    /**
     * Sets the type attribute value.
     * @param type The type to set.
     */
    public void setType(ExconProjectAttachmentType type) {
        this.type = type;
    }

    /** 
     * {@inheritDoc}
     * also nulling the person id because when saving after versioning, the person id is reverting to the wrong BO.
     */
    public void resetPersistenceState() {
        this.setExconProjectAttachmentId(null);
    }

    /**
     * Checks if an attachment is new (not persisted yet).
     * @return true if new false if not
     */
    public boolean isNew() {
        return this.getExconProjectAttachmentId() == null;
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((documentId == null) ? 0 : documentId.hashCode());
        result = prime * result + ((typeCode == null) ? 0 : typeCode.hashCode());
        result = prime * result + ((this.file == null) ? 0 : this.file.hashCode());
        result = prime * result + ((this.fileId == null) ? 0 : this.fileId.hashCode());
        result = prime * result + ((this.exconProjectAttachmentId == null) ? 0 : this.exconProjectAttachmentId.hashCode());
        return result;
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ExconProjectAttachment other = (ExconProjectAttachment) obj;
        if (description == null) {
            if (other.description != null) {
                return false;
            }
        } else if (!description.equals(other.description)) {
            return false;
        }
        if (documentId == null) {
            if (other.documentId != null) {
                return false;
            }
        } else if (!documentId.equals(other.documentId)) {
            return false;
        }
        if (typeCode == null) {
            if (other.typeCode != null) {
                return false;
            }
        } else if (!typeCode.equals(other.typeCode)) {
            return false;
        }
        if (this.file == null) {
            if (other.file != null) {
                return false;
            }
        } else if (!this.file.equals(other.file)) {
            return false;
        }
        if (this.fileId == null) {
            if (other.fileId != null) {
                return false;
            }
        } else if (!this.fileId.equals(other.fileId)) {
            return false;
        }
        if (this.exconProjectAttachmentId == null) {
            if (other.exconProjectAttachmentId != null) {
                return false;
            }
        } else if (!this.exconProjectAttachmentId.equals(other.exconProjectAttachmentId)) {
            return false;
        }
        return true;
    }

    /**
     * 
     * @see org.kuali.coeus.sys.framework.model.KcPersistableBusinessObjectBase#beforeUpdate(org.apache.ojb.broker.PersistenceBroker)
     */
    @Override
    protected void preUpdate() {
        super.preUpdate();
        if (this.getVersionNumber() == null) {
            this.setVersionNumber(new Long(0));
        }
    }

    /**
     * 
     * This method returns the full name of the update user.
     * @return
     */
    public String getUpdateUserName() {
        Person updateUser = KcServiceLocator.getService(PersonService.class).getPersonByPrincipalName(this.getUpdateUser());
        return updateUser != null ? updateUser.getName() : this.getUpdateUser();
    }

    /**
     * This sets the update time stamp only if it hasn't already been set.
     * @see org.kuali.coeus.sys.framework.model.KcPersistableBusinessObjectBase#setUpdateTimestamp(java.sql.Timestamp)
     */
    @Override
    public void setUpdateTimestamp(Timestamp updateTimestamp) {
        if (getUpdateTimestamp() == null) {
            super.setUpdateTimestamp(updateTimestamp);
        }
    }

    /**
     * Implements comparable based on the exconProjectAttachmentId.
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(ExconProjectAttachment o) {
        return this.getExconProjectAttachmentId().compareTo(o.getExconProjectAttachmentId());
    }
    
    @Override
    protected void preRemove() {
        super.preRemove();
        //if we have a file and its linked to other versions make sure its not deleted with this version.
        if (getFileId() != null) {
            Map<String, Object> values = new HashMap<String, Object>();
            values.put("fileId", getFileId());
            List<ExconProjectAttachment> otherAttachmentVersions = 
                (List<ExconProjectAttachment>) KcServiceLocator.getService(BusinessObjectService.class).findMatching(ExconProjectAttachment.class, values);
            if (otherAttachmentVersions.size() > 1) {
                setFile(null);
                setFileId(null);
            }
        }
    }
}
