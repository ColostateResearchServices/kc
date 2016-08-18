package org.kuali.kra.excon.rules;

import org.apache.commons.lang.StringUtils;
import org.kuali.coeus.common.framework.attachment.AttachmentFile;
import org.kuali.kra.excon.project.ExconProject;
import org.kuali.kra.excon.project.ExconProjectAttachment;
import org.kuali.rice.krad.util.GlobalVariables;

/**
 * This class implements the specified rule
 */
public class ExconProjectAttachmentAddRuleImpl {
   
    private static final String EXCON_PROJECT_ATTACHMENT_ERROR_KEY = "exconProjectAttachmentsBean.newAttachment";
    private static final String ERROR_EXCON_PROJECT_ATTACHMENT_TYPE_IS_REQUIRED = "error.exconProjectAttachment.attachmentTypeIsRequired";
    private static final String ERROR_EXCON_PROJECT_ATTACHMENT_FILE_IS_REQUIRED = "error.exconProjectAttachment.attachmentFileIsRequired";

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
        
    	if (newAttachment.getNewFile() == null || StringUtils.isEmpty(AttachmentFile.createFromFormFile(newAttachment.getNewFile()).getName())) {
    		GlobalVariables.getMessageMap().putError(EXCON_PROJECT_ATTACHMENT_ERROR_KEY, ERROR_EXCON_PROJECT_ATTACHMENT_FILE_IS_REQUIRED);
            return false;
    	}
    	return true;
    }

}