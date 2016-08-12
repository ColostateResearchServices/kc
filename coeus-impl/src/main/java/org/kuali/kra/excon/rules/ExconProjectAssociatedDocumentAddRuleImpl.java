package org.kuali.kra.excon.rules;

import org.apache.commons.lang.StringUtils;
import org.kuali.kra.excon.project.ExconProject;
import org.kuali.kra.excon.project.ExconProjectAssociatedDocument;
import org.kuali.rice.krad.util.GlobalVariables;

/**
 * This class implements the specified rule
 */
public class ExconProjectAssociatedDocumentAddRuleImpl {
   
    private static final String EXCON_PROJECT_ASSOC_DOC_ERROR_KEY = "exconProjectAssociatedDocumentsBean.newAssociatedDocument";
    private static final String ERROR_EXCON_PROJECT_ASSOC_DOC_NUMBER_IS_REQUIRED = "error.exconProjectAssociatedDocument.assocDocNumberIsRequired";
    /**
     * @param event
     * @return
     */
    public boolean processAddExconProjectAssociatedDocumentBusinessRules(ExconProject exconProject, ExconProjectAssociatedDocument newAssociatedDocument) {
        boolean valid = checkAssociatedDocumentNumberIsValid(newAssociatedDocument);
        return  valid;
    }

    public boolean checkAssociatedDocumentNumberIsValid(ExconProjectAssociatedDocument newAssociatedDocument) { 

        if(StringUtils.isEmpty(newAssociatedDocument.getAssocDocNumber())) {
            GlobalVariables.getMessageMap().putError(EXCON_PROJECT_ASSOC_DOC_ERROR_KEY, ERROR_EXCON_PROJECT_ASSOC_DOC_NUMBER_IS_REQUIRED);
            return false;
        }

        return true;
    }

}