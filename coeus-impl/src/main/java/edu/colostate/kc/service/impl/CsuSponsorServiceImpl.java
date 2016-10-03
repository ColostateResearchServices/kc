package edu.colostate.kc.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.kuali.coeus.common.framework.sponsor.hierarchy.SponsorHierarchy;
import org.kuali.coeus.common.impl.sponsor.SponsorServiceImpl;

import edu.colostate.kc.service.CsuSponsorService;
import org.kuali.coeus.sys.framework.service.KcServiceLocator;
import org.kuali.rice.krad.service.BusinessObjectService;

public class CsuSponsorServiceImpl extends SponsorServiceImpl implements CsuSponsorService {
    
    
    public  CsuSponsorServiceImpl() {
        System.out.println("test default ctor");
    }

    public boolean isSponsorInHierarchy(String sponsorCode, String sponsorHierarchy) {
        Map<String, String> valueMap = new HashMap<String, String>();
        valueMap.put("sponsorCode", sponsorCode);
        valueMap.put("hierarchyName", sponsorHierarchy);
        int matchingHierarchies = getBusinessObjectService().countMatching(SponsorHierarchy.class, valueMap);
        
        return matchingHierarchies > 0;
    }

    private BusinessObjectService getBusinessObjectService() {
        return KcServiceLocator.getService(BusinessObjectService.class);
    }
}
