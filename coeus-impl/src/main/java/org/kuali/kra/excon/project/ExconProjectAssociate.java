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
import org.kuali.coeus.common.framework.version.sequence.associate.SequenceAssociate;
import org.kuali.coeus.sys.framework.model.KcPersistableBusinessObjectBase;

import java.io.Serializable;


public abstract class ExconProjectAssociate extends KcPersistableBusinessObjectBase implements SequenceAssociate<ExconProject>, Serializable {

    private static final long serialVersionUID = -1966175324490120727L;

    private String projectNumber;

    private Integer sequenceNumber;

    private ExconProject exconProject;

    /**
     * @return
     */
    public String getProjectNumber() {
        return projectNumber;
    }

    /**
     * @return
     */
    public Integer getSequenceNumber() {
        return sequenceNumber;
    }

    /**
     * @return
     */
    public ExconProject getExconProject() {
        return exconProject;
    }

    /**
     * @param projectNumber
     */
    public void setProjectNumber(String projectNumber) {
        this.projectNumber = projectNumber;
    }

    /**
     * @param sequenceNumber
     */
    public void setSequenceNumber(Integer sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    /**
     * @param exconProject
     */
    public void setExconProject(ExconProject exconProject) {
        this.exconProject = exconProject;
        if (exconProject != null) {
            setSequenceNumber(exconProject.getSequenceNumber());
            setProjectNumber(exconProject.getProjectNumber());
        } else {
            setSequenceNumber(0);
            setProjectNumber("");
        }
    }
    
    /**
     * If the exconProject's exconProject number is not equal to the exconProject number we will persist, 
     * then update it based on the exconProject.
     * @see org.kuali.coeus.sys.framework.model.KcPersistableBusinessObjectBase#prePersist()
     */
    @Override
    protected void prePersist() {
        super.prePersist();
        if (exconProject != null && !StringUtils.equals(exconProject.getProjectNumber(), getProjectNumber())) {
            setSequenceNumber(exconProject.getSequenceNumber());
            setProjectNumber(exconProject.getProjectNumber());            
        }
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((projectNumber == null) ? 0 : projectNumber.hashCode());
        result = PRIME * result + ((sequenceNumber == null) ? 0 : sequenceNumber.hashCode());
        return result;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof ExconProjectAssociate)) {
            return false;
        }
        ExconProjectAssociate other = (ExconProjectAssociate) obj;
        if (projectNumber == null) {
            if (other.projectNumber != null) {
                return false;
            }
        } else if (!projectNumber.equals(other.projectNumber)) {
            return false;
        }
        if (sequenceNumber == null) {
            if (other.sequenceNumber != null) {
                return false;
            }
        } else if (!sequenceNumber.equals(other.sequenceNumber)) {
            return false;
        }
        return true;
    }

    /**
     * @see org.kuali.coeus.common.framework.version.sequence.associate.SequenceAssociate#getSequenceOwner()
     */
    public ExconProject getSequenceOwner() {
        return getExconProject();
    }

    /**
     * @see org.kuali.coeus.common.framework.version.sequence.associate.SequenceAssociate#setSequenceOwner(org.kuali.coeus.common.framework.version.sequence.owner.SequenceOwner)
     */
    public void setSequenceOwner(ExconProject newlyVersionedOwner) {
        setExconProject(newlyVersionedOwner);
    }
}
