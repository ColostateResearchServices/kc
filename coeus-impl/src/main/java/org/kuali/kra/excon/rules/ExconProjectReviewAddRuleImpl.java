package org.kuali.kra.excon.rules;

import org.kuali.kra.excon.project.ExconProject;
import org.kuali.kra.excon.project.ExconProjectReview;
import org.kuali.rice.krad.util.GlobalVariables;

/**
 * This class implements the specified rule
 */
public class ExconProjectReviewAddRuleImpl {
   
    private static final String EXCON_PROJECT_REVIEW_ERROR_KEY = "exconProjectReviewsBean.newReview";
    private static final String ERROR_EXCON_PROJECT_REVIEW_TYPE_IS_REQUIRED = "error.exconProjectReview.projectReviewTypeIsRequired";
    /**
     * @param event
     * @return
     */
    public boolean processAddExconProjectReviewBusinessRules(ExconProject exconProject, ExconProjectReview newReview) {
        boolean valid = checkReviewTypeIsValid(newReview);
        return  valid;
    }

    public boolean checkReviewTypeIsValid(ExconProjectReview newReview) { 

        if(newReview.getProjectReviewTypeCode() == null) {
            GlobalVariables.getMessageMap().putError(EXCON_PROJECT_REVIEW_ERROR_KEY, ERROR_EXCON_PROJECT_REVIEW_TYPE_IS_REQUIRED);
            return false;
        }

        return true;
    }

}