package org.kuali.kra.excon.rules;

import org.kuali.kra.excon.project.ExconProject;
import org.kuali.kra.excon.project.ExconProjectExternalInstitution;
import org.kuali.rice.krad.util.GlobalVariables;

/**
 * This class implements the specified rule
 */
public class ExconProjectExternalInstitutionAddRuleImpl {
   
    private static final String EXCON_PROJECT_EXTERNAL_INSTITUTION_ERROR_KEY = "exconProjectExternalInstitutionsBean.newExternalInstitution";
    private static final String ERROR_EXCON_PROJECT_EXTERNAL_INSTITUTION_IS_REQUIRED = "error.exconProjectExternalInstitution.externalInstitutionIsRequired";
    /**
     * @param event
     * @return
     */
    public boolean processAddExconProjectExternalInstitutionBusinessRules(ExconProject exconProject, ExconProjectExternalInstitution newExternalInstitution) {
        boolean valid = checkExternalInstitutionIsValid(newExternalInstitution);
        return  valid;
    }

    public boolean checkExternalInstitutionIsValid(ExconProjectExternalInstitution newExternalInstitution) { 

        if(newExternalInstitution.getRolodexId() == null || newExternalInstitution.getRolodexEntry() == null) {
            GlobalVariables.getMessageMap().putError(EXCON_PROJECT_EXTERNAL_INSTITUTION_ERROR_KEY, ERROR_EXCON_PROJECT_EXTERNAL_INSTITUTION_IS_REQUIRED);
            return false;
        }

        return true;
    }

}