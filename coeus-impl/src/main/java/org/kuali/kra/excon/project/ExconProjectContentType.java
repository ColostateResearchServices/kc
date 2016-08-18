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
public class ExconProjectContentType extends KcPersistableBusinessObjectBase {

    private static final long serialVersionUID = 1652576724548069217L;

    private String contentTypeCode;

    private String description;

    public ExconProjectContentType() {
    }

    public String getContentTypeCode() {
        return contentTypeCode;
    }

    public void setContentTypeCode(String contentTypeCode) {
        this.contentTypeCode = contentTypeCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
