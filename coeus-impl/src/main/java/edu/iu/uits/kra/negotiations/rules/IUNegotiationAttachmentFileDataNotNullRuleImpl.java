package edu.iu.uits.kra.negotiations.rules;

import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.kuali.coeus.sys.framework.service.KcServiceLocator;
import org.kuali.kra.negotiations.bo.Negotiation;
import org.kuali.kra.negotiations.bo.NegotiationActivityAttachment;
import org.kuali.coeus.sys.framework.validation.ErrorReporter;

import edu.colostate.kc.infrastructure.CSUKeyConstants;
import edu.iu.uits.kra.dao.ProtocolAttachmentDao;

public class IUNegotiationAttachmentFileDataNotNullRuleImpl {

	private static final Log LOG = LogFactory.getLog(IUNegotiationAttachmentFileDataNotNullRuleImpl.class);
	
	protected static final String PROPERTY_NAME = "activities[%d].newAttachment.newFile"; 
	protected static final String ERROR_MSG_KEY = CSUKeyConstants.ERROR_PROTOCOL_ATTACHMENT_FILE_NULL;
	
	private ProtocolAttachmentDao attachmentDao;
	private ErrorReporter errorReporter;
	
	public IUNegotiationAttachmentFileDataNotNullRuleImpl(ErrorReporter errorReporter) {
		this.attachmentDao = KcServiceLocator.getService(ProtocolAttachmentDao.class);
		this.errorReporter = errorReporter;
	}
	
	public boolean checkAttachmentFileDataNotNull(Negotiation negotiation) {
		boolean isValid = true;
		ArrayList<Long> nullFileIds = attachmentDao.checkNegotiationForNullFileData(negotiation.getNegotiationId());
		if(nullFileIds != null && nullFileIds.size() > 0) {
			for(Long nullFileId : nullFileIds) {
				for (int i=0; i < negotiation.getActivities().size(); i++) {
					for(int j=0; j < negotiation.getActivities().get(i).getAttachments().size(); j++) {
						NegotiationActivityAttachment attachment = negotiation.getActivities().get(i).getAttachments().get(j);
						if(attachment.getFileId() != null && attachment.getFileId().equals(nullFileId)) {
							LOG.fatal("VALIDATION: Attachment ID " + nullFileId + " is null!");
							reportNullFileDataError(attachment, i, j, attachment.getFile().getName());
							isValid = false;
						}
					}
				}
			}
		}
		return isValid;
	}
	
	protected void reportNullFileDataError(NegotiationActivityAttachment attachment, int activityIndex, int attachmentIndex, String filename) {
        this.errorReporter.reportError(String.format(PROPERTY_NAME, activityIndex), ERROR_MSG_KEY, new String[] { String.valueOf(attachmentIndex + 1), filename });
	}
}
