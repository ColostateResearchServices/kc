package org.kuali.kra.excon.rules;

import org.kuali.coeus.sys.framework.service.KcServiceLocator;
import org.kuali.kra.excon.project.ExconProject;
import org.kuali.kra.excon.project.ExconProjectDestination;
import org.kuali.kra.excon.project.ExconProjectRestrictedCountry;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.util.GlobalVariables;

/**
 * This class implements the specified rule
 */
public class ExconProjectDestinationAddRuleImpl {
   
    private static final String EXCON_PROJECT_DESTINATION_WARNING_KEY = "exconProjectDestinationsBean.newDestination";
    private static final String WARNING_EXCON_PROJECT_DESTINATION_IS_SANCTIONED = "warning.exconProjectDestination.isSanctioned";
    private static final String ERROR_EXCON_PROJECT_DESTINATION_IS_INVALID = "error.exconProjectDestination.isInvalid";
    /**
     * @param event
     * @return
     */
    public boolean processAddExconProjectDestinationBusinessRules(ExconProject exconProject, ExconProjectDestination newDestination) {
        boolean valid = checkLocationIsValid(newDestination);
        if (valid) {
        	valid &= checkLocationIsSanctioned(newDestination);
        }
        return  valid;
    }

    public boolean checkLocationIsValid(ExconProjectDestination newDestination) {
    	if (newDestination.getDestinationCountryCode() == null) {
    		GlobalVariables.getMessageMap().putError(EXCON_PROJECT_DESTINATION_WARNING_KEY, ERROR_EXCON_PROJECT_DESTINATION_IS_INVALID);
    		return false;
    	}
    	return true;
    }
    
    public boolean checkLocationIsSanctioned(ExconProjectDestination newDestination) { 
    	ExconProjectRestrictedCountry restrictedCountry = getRestrictedCountry(newDestination);

        if(restrictedCountry != null) {
        	String sanctionListCode = restrictedCountry.getSanctionListCode();
        	String sanctionListName = restrictedCountry.getSanctionListName();
            GlobalVariables.getMessageMap().putWarning(EXCON_PROJECT_DESTINATION_WARNING_KEY, WARNING_EXCON_PROJECT_DESTINATION_IS_SANCTIONED, new String[]{sanctionListName});
            newDestination.setSanctionList(sanctionListCode);
        }

        return true;
    }

    private ExconProjectRestrictedCountry getRestrictedCountry(ExconProjectDestination newDestination) {
        ExconProjectRestrictedCountry restrictedCountry = getBusinessObjectService().findBySinglePrimaryKey(ExconProjectRestrictedCountry.class, newDestination.getDestinationCountryCode());
        return restrictedCountry;
    }

    private BusinessObjectService getBusinessObjectService() {
        return KcServiceLocator.getService(BusinessObjectService.class);
    }
}