package edu.colostate.kc.coi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.kra.coi.CoiDisclProject;
import org.kuali.kra.coi.CoiDisclosure;
import org.kuali.kra.coi.CoiDisclosureEventType;
import org.kuali.rice.krad.service.BusinessObjectService;

public class CsuWorkDisclosureProjectLookupServiceImpl implements
		CsuWorkDisclosureProjectLookupService {


    private BusinessObjectService businessObjectService;

	@Override
	public boolean hasCsuWorkProject(String personId) {		
	        String disclosureNumber = getUserDisclosureNumber(personId);
	        List<CoiDisclProject> disclProjectsCsuWork = getUserCsuWorkProject(disclosureNumber); 
		
		return !disclProjectsCsuWork.isEmpty();
	}
	
	private String getUserDisclosureNumber(String personId) {
        Map<String, Object> fieldValues = new HashMap<String, Object>();
        String disclosureNumber = null;
        fieldValues.put("personId", personId);
        List<CoiDisclosure> usersDisclosures = (List<CoiDisclosure>) businessObjectService.findMatching(CoiDisclosure.class,
                        fieldValues);
        if (usersDisclosures != null && !usersDisclosures.isEmpty())  {
        	CoiDisclosure disclosure = (CoiDisclosure) usersDisclosures.get(0);
        	disclosureNumber = disclosure.getCoiDisclosureNumber();
        }
	    return disclosureNumber;
	}
	
	private List<CoiDisclProject> getUserCsuWorkProject(String disclosureNumber)
	{
	
	    HashMap<String, Object> fieldValues = new HashMap<String, Object>();
	    fieldValues.put("disclosureEventType", CoiDisclosureEventType.CSU_WORK_ACTIVITY);
        fieldValues.put("coiDisclosureNumber", disclosureNumber);

	    List<CoiDisclProject> disclProjects = (List<CoiDisclProject>) businessObjectService.findMatching(CoiDisclProject.class, fieldValues);
		return disclProjects;
	}

	
	public BusinessObjectService getBusinessObjectService() {
		return businessObjectService;
	}

	public void setBusinessObjectService(BusinessObjectService businessObjectService) {
		this.businessObjectService = businessObjectService;
	}

}
