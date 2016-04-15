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
package edu.colostate.kc.award.reservation.document;


import org.apache.commons.lang.ObjectUtils;
import edu.colostate.kc.award.reservation.*;
import org.kuali.coeus.common.framework.unit.Unit ;
import org.kuali.kra.infrastructure.KeyConstants;
import org.kuali.coeus.sys.framework.service.KcServiceLocator;
import org.kuali.coeus.sys.framework.rule.KcBusinessRule;
import org.kuali.coeus.sys.framework.rule.KcDocumentEventBaseExtension;
import org.kuali.coeus.sys.framework.rule.KcTransactionalDocumentRuleBase ;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.rules.rule.DocumentAuditRule;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.MessageMap;
import org.apache.commons.lang.StringUtils;

import java.util.Collections;

/**
 * This class is for rule validation while
 * exconProjectDocumentRule is used...
 */
public class AwardAccountReservationDocumentRule extends
        KcTransactionalDocumentRuleBase implements AwardAccountReservationRule,
        KcBusinessRule,
        DocumentAuditRule {


    private static final String NEW_EXCON_PROJECT = "document.exconProjectList[0]";

    private static final org.apache.commons.logging.Log LOG = org.apache.commons.logging.LogFactory.getLog(AwardAccountReservationDocumentRule.class);
    /**.
     * This method is for AddAwardAccountReservationBusinessRules
     * @param exconProject
     * @return rulePassed boolean...
     */
    public boolean processAddAwardAccountReservationBusinessRules(AwardAccountReservation exconProject) {

        boolean rulePassed = true;
        rulePassed &= processSaveAwardAccountReservationBusinessRules(exconProject, NEW_EXCON_PROJECT);
        return rulePassed;
}
    /**.
     * This method is for SaveAwardAccountReservationBusinessRules
     * @param exconProject
     * @param propertyPrefix
     * @return  boolean...
     */
    protected boolean  processSaveAwardAccountReservationBusinessRules(AwardAccountReservation exconProject, String propertyPrefix){
     
        boolean rulePassed = true;


        return rulePassed;
    }


    @Override
    public boolean processRunAuditBusinessRules(Document document){
        boolean retval = true; //super.processRunAuditBusinessRules(document);
        return retval;
    }

    
    
    @Override
    protected boolean processCustomSaveDocumentBusinessRules(Document document) {
        if (!(document instanceof AwardAccountReservationDocument)) {
            return false;
        }

        MessageMap errorMap = GlobalVariables.getMessageMap();

        return true;
    }
    
    /**
     * @see org.kuali.coeus.sys.framework.rule.KcBusinessRule#processRules(org.kuali.coeus.sys.framework.rule.KcDocumentEventBaseExtension)
     */
    public boolean processRules(KcDocumentEventBaseExtension event) {
        boolean retVal = false;
        retVal = event.getRule().processRules(event);
        return retVal;
    }
 
}