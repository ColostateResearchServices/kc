package edu.colostate.kc.coi;

import org.kuali.kra.coi.CoiDisclosure;
import org.kuali.kra.coi.notification.DisclosureCertifiedNotificationRenderer;

public class DisclosureReviewCompletedNotificationRenderer extends
		DisclosureCertifiedNotificationRenderer {

	public DisclosureReviewCompletedNotificationRenderer(CoiDisclosure coiDisclosure,
			String actionTaken) {
		super(coiDisclosure, actionTaken);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 4417052339311297681L;

}
