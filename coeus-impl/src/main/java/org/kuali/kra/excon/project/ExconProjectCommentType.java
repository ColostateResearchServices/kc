/*
 * Copyright 2005-2014 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.kra.excon.project;

import org.kuali.coeus.sys.framework.model.KcPersistableBusinessObjectBase;

/**
 * ExconProjectComment Type business object
 * 
 * @author Kuali Coeus development team (kc.dev@kuali.org)
 */
public class ExconProjectCommentType extends KcPersistableBusinessObjectBase {

    private static final long serialVersionUID = 1652234233758069217L;

    private String exconProjectCommentTypeCode;

    private String description;

    public ExconProjectCommentType() {
    }

    public String getExconProjectCommentTypeCode() {
        return exconProjectCommentTypeCode;
    }

    public void setExconProjectCommentTypeCode(String exconProjectCommentTypeCode) {
        this.exconProjectCommentTypeCode = exconProjectCommentTypeCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((exconProjectCommentTypeCode == null) ? 0 : exconProjectCommentTypeCode.hashCode());
        result = PRIME * result + ((description == null) ? 0 : description.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final ExconProjectCommentType other = (ExconProjectCommentType) obj;
        if (exconProjectCommentTypeCode == null) {
            if (other.exconProjectCommentTypeCode != null) return false;
        } else if (!exconProjectCommentTypeCode.equals(other.exconProjectCommentTypeCode)) return false;
        if (description == null) {
            if (other.description != null) return false;
        } else if (!description.equals(other.description)) return false;
        return true;
    }
}
