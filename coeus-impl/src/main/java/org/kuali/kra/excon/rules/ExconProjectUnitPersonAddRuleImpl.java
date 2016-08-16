package org.kuali.kra.excon.rules;

import org.apache.commons.lang.StringUtils;
import org.kuali.coeus.sys.framework.service.KcServiceLocator;
import org.kuali.kra.excon.project.ExconProject;
import org.kuali.kra.excon.project.ExconProjectUnitPerson;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.util.GlobalVariables;

/**
 * This class implements the specified rule
 */
public class ExconProjectUnitPersonAddRuleImpl {
   
    private static final String EXCON_PROJECT_UNIT_PERSON_ERROR_KEY = "exconProjectUnitPersonsBean.newUnitPerson";
    private static final String ERROR_EXCON_PROJECT_UNIT_PERSON_ROLE_MISSING = "error.exconProjectUnitPerson.roleMissing";
    private static final String ERROR_EXCON_PROJECT_UNIT_PERSON_INVALID = "error.exconProjectUnitPerson.invalidPerson";
    /**
     * @param event
     * @return
     */
    public boolean processAddExconProjectUnitPersonBusinessRules(ExconProject exconProject, ExconProjectUnitPerson newPerson) {
        boolean valid = checkProjectUnitPersonIsValid(exconProject,newPerson);
        if (valid) {
        	valid &= checkProjectUnitPersonHasRole(exconProject,newPerson);
        }
        return  valid;
    }
    
    public boolean checkProjectUnitPersonIsValid(ExconProject exconProject, ExconProjectUnitPerson newPerson) {
    	if (newPerson.getPerson() == null) {
    		GlobalVariables.getMessageMap().putError(EXCON_PROJECT_UNIT_PERSON_ERROR_KEY, ERROR_EXCON_PROJECT_UNIT_PERSON_INVALID);
    		return false;
    	}
    	return true;
    }
    
    public boolean checkProjectUnitPersonHasRole(ExconProject exconProject, ExconProjectUnitPerson newPerson) {
    	if (StringUtils.isEmpty(newPerson.getRoleCode())) {
    		GlobalVariables.getMessageMap().putError(EXCON_PROJECT_UNIT_PERSON_ERROR_KEY, ERROR_EXCON_PROJECT_UNIT_PERSON_ROLE_MISSING);
    		return false;
    	}
    	return true;
    }

    private BusinessObjectService getBusinessObjectService() {
        return KcServiceLocator.getService(BusinessObjectService.class);
    }
}