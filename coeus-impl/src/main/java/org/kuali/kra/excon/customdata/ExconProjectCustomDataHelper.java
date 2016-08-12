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
package org.kuali.kra.excon.customdata;

import org.kuali.coeus.common.framework.custom.attr.CustomAttributeDocument;
import org.kuali.coeus.common.framework.custom.CustomDataHelperBase;
import org.kuali.kra.excon.document.ExconProjectDocument;
import org.kuali.kra.excon.project.ExconProject;
import org.kuali.kra.excon.project.ExconProjectForm;
import org.kuali.rice.kew.api.WorkflowDocument;

import java.util.List;
import java.util.Map;

/**
 * The CustomDataHelper is used to manage the Custom Data tab web page.
 * It contains the data, forms, and methods needed to render the page.
 */
/**
 * This class...
 */
public class ExconProjectCustomDataHelper extends CustomDataHelperBase<ExconProjectCustomData> {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -2395737022153534376L;   
    
    /**
     * Each Helper must contain a reference to its document form
     * so that it can access the document.
     */
    private ExconProjectForm exconProjectForm;
    
    /**
     * Constructs a CustomDataHelper.
     * @param from the exconProjectForm
     */
    public ExconProjectCustomDataHelper(ExconProjectForm exconProjectForm) {
        this.exconProjectForm = exconProjectForm;
    }
    
    /**
     * Get the Award.
     */
    private ExconProject getExconProject() {
        ExconProjectDocument document = exconProjectForm.getExconProjectDocument();
        if (document == null || document.getExconProject() == null) {
            throw new IllegalArgumentException("invalid (null) ExconProjectDocument in ExconProjectForm");
        }
        return document.getExconProject();
    }

    @Override
    protected ExconProjectCustomData getNewCustomData() {
        return new ExconProjectCustomData();
    }

    @Override
    public List<ExconProjectCustomData> getCustomDataList() {
        return getExconProject().getExconProjectCustomDataList();
    }

    @Override
    public Map<String, CustomAttributeDocument> getCustomAttributeDocuments() {
        return exconProjectForm.getExconProjectDocument().getCustomAttributeDocuments();
    }

    @Override
    public boolean documentNotRouted() {
        WorkflowDocument doc = exconProjectForm.getExconProjectDocument().getDocumentHeader().getWorkflowDocument();
        return doc.isSaved() || doc.isInitiated();
    }

}
