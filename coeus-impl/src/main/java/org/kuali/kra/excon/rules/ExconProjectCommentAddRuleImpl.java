package org.kuali.kra.excon.rules;

import org.kuali.kra.excon.project.ExconProject;
import org.kuali.kra.excon.project.ExconProjectComment;
import org.kuali.rice.krad.util.GlobalVariables;

/**
 * This class implements the specified rule
 */
public class ExconProjectCommentAddRuleImpl {
   
    private static final String EXCON_PROJECT_COMMENT_ERROR_KEY = "exconProjectCommentsBean.newComment";
    private static final String ERROR_EXCON_PROJECT_COMMENT_TYPE_IS_REQUIRED = "error.exconProjectComment.commentTypeIsRequired";
    /**
     * @param event
     * @return
     */
    public boolean processAddExconProjectCommentBusinessRules(ExconProject exconProject, ExconProjectComment newComment) {
        boolean valid = checkCommentTypeIsValid(newComment);
//        valid &= checkForDuplicatePerson(award, newUnitContact);
        return  valid;
    }

    public boolean checkCommentTypeIsValid(ExconProjectComment newComment) { 

        if(newComment.getCommentTypeCode() == null) {
            GlobalVariables.getMessageMap().putError(EXCON_PROJECT_COMMENT_ERROR_KEY, ERROR_EXCON_PROJECT_COMMENT_TYPE_IS_REQUIRED);
            return false;
        }

        return true;
    }

}