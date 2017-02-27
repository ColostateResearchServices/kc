package org.kuali.kra.excon.rules;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.upload.FormFile;
import org.kuali.coeus.common.framework.attachment.AttachmentFile;
import org.kuali.kra.excon.project.ExconProject;
import org.kuali.kra.excon.project.ExconProjectAttachment;
import org.kuali.rice.krad.datadictionary.validation.constraint.AnyCharacterPatternConstraint;
import org.kuali.rice.krad.util.GlobalVariables;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * This class implements the specified rule
 */
public class ExconProjectAttachmentAddRuleImpl {
   
    private static final String EXCON_PROJECT_ATTACHMENT_ERROR_KEY = "exconProjectAttachmentsBean.newAttachment";
    private static final String ERROR_EXCON_PROJECT_ATTACHMENT_TYPE_IS_REQUIRED = "error.exconProjectAttachment.attachmentTypeIsRequired";
    private static final String ERROR_EXCON_PROJECT_ATTACHMENT_FILE_IS_REQUIRED = "error.exconProjectAttachment.attachmentFileIsRequired";
    private static final String ERROR_ATTACHMENT_FILE_INVALID_FORMAT = "error.format.org.kuali.rice.kns.datadictionary.validation.charlevel.AnyCharacterValidationPattern.allowWhitespace";

    /**
     * @param event
     * @return
     */
    public boolean processAddExconProjectAttachmentBusinessRules(ExconProject exconProject, ExconProjectAttachment newAttachment) {
        boolean valid = checkAttachmentTypeIsValid(newAttachment);
        valid &= checkAttachmentFileIsValid(newAttachment);
        return  valid;
    }

    public boolean checkAttachmentTypeIsValid(ExconProjectAttachment newAttachment) { 

        if(newAttachment.getTypeCode() == null) {
            GlobalVariables.getMessageMap().putError(EXCON_PROJECT_ATTACHMENT_ERROR_KEY, ERROR_EXCON_PROJECT_ATTACHMENT_TYPE_IS_REQUIRED);
            return false;
        }

        return true;
    }
    
    public boolean checkAttachmentFileIsValid(ExconProjectAttachment newAttachment) {
        FormFile newFile=newAttachment.getNewFile();
        String filename=AttachmentFile.createFromFormFile(newFile).getName();
    	if (newFile == null || StringUtils.isEmpty(filename)) {
    		GlobalVariables.getMessageMap().putError(EXCON_PROJECT_ATTACHMENT_ERROR_KEY, ERROR_EXCON_PROJECT_ATTACHMENT_FILE_IS_REQUIRED);
            return false;
    	}

        AnyCharacterPatternConstraint constraint = new AnyCharacterPatternConstraint();
        constraint.setAllowWhitespace(true);

        if (!filename.matches(constraint.getValue())) {
            GlobalVariables.getMessageMap().putError(EXCON_PROJECT_ATTACHMENT_ERROR_KEY, ERROR_ATTACHMENT_FILE_INVALID_FORMAT, new String[]{"File Name ("+filename+")"});
            return false;
        }
    	return true;
    }

}