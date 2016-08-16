package org.kuali.kra.excon.rules;

import org.apache.commons.lang.StringUtils;
import org.kuali.coeus.sys.framework.service.KcServiceLocator;
import org.kuali.coeus.common.framework.unit.Unit;
import org.kuali.kra.excon.project.ExconProject;
import org.kuali.kra.excon.project.ExconProjectPerson;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.util.GlobalVariables;

/**
 * This class implements the specified rule
 */
public class ExconProjectPersonAddRuleImpl {
   
    private static final String EXCON_PROJECT_PERSON_WARNING_KEY = "exconProjectPersonsBean.newPerson";
    private static final String WARNING_EXCON_PROJECT_PERSON_UNIT_IS_RESTRICTED = "warning.exconProjectPersonUnit.isRestricted";
    private static final String ERROR_EXCON_PROJECT_PERSON_ROLE_MISSING = "error.exconProjectPerson.roleMissing";
    private static final String ERROR_EXCON_PROJECT_PERSON_INVALID = "error.exconProjectPerson.invalidPerson";
    /**
     * @param event
     * @return
     */
    public boolean processAddExconProjectPersonBusinessRules(ExconProject exconProject, ExconProjectPerson newPerson) {
        boolean valid = checkProjectPersonIsValid(exconProject,newPerson);
        		
        if (valid) {
        	valid &= checkProjectPersonHasRole(exconProject,newPerson);
        	valid &= checkUnitIsRestricted(exconProject,newPerson);
        }
        return  valid;
    }
    
    public boolean checkProjectPersonIsValid(ExconProject exconProject, ExconProjectPerson newPerson) {
    	if (newPerson.getPerson() == null) {
    		GlobalVariables.getMessageMap().putError(EXCON_PROJECT_PERSON_WARNING_KEY, ERROR_EXCON_PROJECT_PERSON_INVALID);
    		return false;
    	}
    	
    	return true;
    }

    public boolean checkUnitIsRestricted(ExconProject exconProject, ExconProjectPerson newPerson) {
    	if (!exconProject.getProjectType().getDescription().equals("International Travel") || newPerson.getRoleType()==null || !newPerson.getRoleType().getDescription().equals("Traveler")) {
    		return true;
    	}
    	
    	if (newPerson.getPerson().getUnit()==null) {
    		return true;
    	}
    	Unit newUnit = newPerson.getPerson().getUnit();
    	String unitNumber = newUnit.getUnitNumber();

        if(exconProject.isUnitRestricted(unitNumber)) {
            GlobalVariables.getMessageMap().putWarning(EXCON_PROJECT_PERSON_WARNING_KEY, WARNING_EXCON_PROJECT_PERSON_UNIT_IS_RESTRICTED, new String[]{newUnit.getUnitName()});
        }

        return true;
    }
    
    public boolean checkProjectPersonHasRole(ExconProject exconProject, ExconProjectPerson newPerson) {
    	if (StringUtils.isEmpty(newPerson.getRoleTypeCode())) {
    		GlobalVariables.getMessageMap().putError(EXCON_PROJECT_PERSON_WARNING_KEY, ERROR_EXCON_PROJECT_PERSON_ROLE_MISSING);
    		return false;
    	}
    	return true;
    }

    private BusinessObjectService getBusinessObjectService() {
        return KcServiceLocator.getService(BusinessObjectService.class);
    }
}