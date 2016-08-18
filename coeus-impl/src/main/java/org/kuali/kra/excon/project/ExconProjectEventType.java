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
 * ExconProjectEvent Type business object
 * 
 * @author Kuali Coeus development team (kc.dev@kuali.org)
 */
public class ExconProjectEventType extends KcPersistableBusinessObjectBase {

    private static final long serialVersionUID = 1654567833758069217L;

    private String exconProjectEventTypeCode;

    private String description;

    public ExconProjectEventType() {
    }

    public String getExconProjectEventTypeCode() {
        return exconProjectEventTypeCode;
    }

    public void setExconProjectEventTypeCode(String exconProjectEventTypeCode) {
        this.exconProjectEventTypeCode = exconProjectEventTypeCode;
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
        result = PRIME * result + ((exconProjectEventTypeCode == null) ? 0 : exconProjectEventTypeCode.hashCode());
        result = PRIME * result + ((description == null) ? 0 : description.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final ExconProjectEventType other = (ExconProjectEventType) obj;
        if (exconProjectEventTypeCode == null) {
            if (other.exconProjectEventTypeCode != null) return false;
        } else if (!exconProjectEventTypeCode.equals(other.exconProjectEventTypeCode)) return false;
        if (description == null) {
            if (other.description != null) return false;
        } else if (!description.equals(other.description)) return false;
        return true;
    }
}
