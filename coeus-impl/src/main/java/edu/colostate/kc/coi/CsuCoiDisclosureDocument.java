package edu.colostate.kc.coi;

import java.util.Date;

import org.kuali.kra.coi.CoiDisclosure;
import org.kuali.kra.coi.CoiDisclosureDocument;

public class CsuCoiDisclosureDocument extends CoiDisclosureDocument {
	
    public void defaultDocumentDescription() {
    	Date date = new Date();
    	CoiDisclosure disclosure = getCoiDisclosure();
        String desc = String.format("COI Disclosure; Type: %s; Reporter: %s; Initiated: %s",
                disclosure.getCoiDisclosureEventType().getDescription(),
                disclosure.getDisclosureReporter().getReporter().getFullName() ,
                date.toString()); 
        getDocumentHeader().setDocumentDescription(desc);
    }

}
