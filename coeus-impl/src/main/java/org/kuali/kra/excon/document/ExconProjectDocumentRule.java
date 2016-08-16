/*
 * 
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
package org.kuali.kra.excon.document;


import org.kuali.coeus.sys.framework.rule.KcDocumentEventBaseExtension;
import org.kuali.coeus.sys.framework.rule.KcTransactionalDocumentRuleBase;
import org.kuali.kra.excon.project.ExconProject;
import org.kuali.coeus.sys.framework.rule.KcBusinessRule;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.MessageMap;

/**
 * This class is for rule validation while
 * exconProjectDocumentRule is used...
 */
public class ExconProjectDocumentRule extends
        KcTransactionalDocumentRuleBase implements ExconProjectRule {


    private static final String NEW_EXCON_PROJECT = "document.exconProjectList[0]";

    private static final org.apache.commons.logging.Log LOG = org.apache.commons.logging.LogFactory.getLog(ExconProjectDocumentRule.class);
    /**.
     * This method is for AddExconProjectBusinessRules
     * @param exconProject
     * @return rulePassed boolean...
     */
    public boolean processAddExconProjectBusinessRules(ExconProject exconProject) {

        boolean rulePassed = true;
        rulePassed &= processSaveExconProjectBusinessRules(exconProject, NEW_EXCON_PROJECT);
        return rulePassed;
}
    /**.
     * This method is for SaveExconProjectBusinessRules
     * @param exconProject
     * @param propertyPrefix
     * @return  boolean...
     */
    protected boolean  processSaveExconProjectBusinessRules(ExconProject exconProject, String propertyPrefix){
     
        boolean rulePassed = true;


        return rulePassed;
    }


    public boolean processRunAuditBusinessRules(Document document){

//        boolean retval = super.processRunAuditBusinessRules(document);
        boolean retval = new ExconProjectDestinationsAuditRule().processRunAuditBusinessRules(document);
        retval &= new ExconProjectPersonsAuditRule().processRunAuditBusinessRules(document);
        return retval;
    }
    
    
    
    @Override
    protected boolean processCustomSaveDocumentBusinessRules(Document document) {
        if (!(document instanceof ExconProjectDocument)) {
            return false;
        }

        MessageMap errorMap = GlobalVariables.getMessageMap();

        return true;
    }
    
    /**
     * @see org.kuali.kra.rule.BusinessRuleInterface#processRules(org.kuali.kra.rule.event.KraDocumentEventBaseExtension)
     */
    public boolean processRules(KcDocumentEventBaseExtension event) {
        boolean retVal = false;
        retVal = event.getRule().processRules(event);
        return retVal;
    }
 
}