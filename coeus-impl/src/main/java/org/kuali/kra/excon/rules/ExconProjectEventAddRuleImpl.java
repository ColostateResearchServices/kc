package org.kuali.kra.excon.rules;

import org.kuali.kra.excon.project.ExconProject;
import org.kuali.kra.excon.project.ExconProjectEvent;
import org.kuali.rice.krad.util.GlobalVariables;

/**
 * This class implements the specified rule
 */
public class ExconProjectEventAddRuleImpl {
   
    private static final String EXCON_PROJECT_EVENT_ERROR_KEY = "exconProjectEventsBean.newEvent";
    private static final String ERROR_EXCON_PROJECT_EVENT_TYPE_IS_REQUIRED = "error.exconProjectEvent.eventTypeIsRequired";
    private static final String ERROR_EXCON_PROJECT_EVENT_DATE_IS_REQUIRED = "error.exconProjectEvent.eventDateIsRequired";
    /**
     * @param event
     * @return
     */
    public boolean processAddExconProjectEventBusinessRules(ExconProject exconProject, ExconProjectEvent newEvent) {
        boolean valid = checkEventTypeIsValid(newEvent);
        valid &= checkEventDateIsPresent(newEvent);
        return  valid;
    }

    public boolean checkEventTypeIsValid(ExconProjectEvent newEvent) { 

        if(newEvent.getProjectEventTypeCode() == null) {
            GlobalVariables.getMessageMap().putError(EXCON_PROJECT_EVENT_ERROR_KEY, ERROR_EXCON_PROJECT_EVENT_TYPE_IS_REQUIRED);
            return false;
        }

        return true;
    }

    public boolean checkEventDateIsPresent(ExconProjectEvent newEvent) {
        if (newEvent.getEventDate() == null) {
            GlobalVariables.getMessageMap().putError(EXCON_PROJECT_EVENT_ERROR_KEY, ERROR_EXCON_PROJECT_EVENT_DATE_IS_REQUIRED);
            return false;
        }
        return true;
    }

}