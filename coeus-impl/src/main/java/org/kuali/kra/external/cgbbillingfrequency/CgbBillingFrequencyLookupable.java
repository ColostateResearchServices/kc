package org.kuali.kra.external.cgbbillingfrequency;

import java.util.List;
import java.util.Map;

import org.kuali.kra.external.dunningcampaign.DunningCampaignClient;
import org.kuali.rice.kns.lookup.AbstractLookupableHelperServiceImpl;
import org.kuali.rice.krad.bo.BusinessObject;

public class CgbBillingFrequencyLookupable extends
		AbstractLookupableHelperServiceImpl {
	
	
	private CgbBillingFrequencyClient cgbBillingFrequencyClient;	

	@Override
	public List<? extends BusinessObject> getSearchResults(
			Map<String, String> fieldValues) {
	
		return getCgbBillingFrequencyClient().getMatching(fieldValues);		
	}

	public CgbBillingFrequencyClient getCgbBillingFrequencyClient() {
		return cgbBillingFrequencyClient;
	}

	public void setCgbBillingFrequencyClient(
			CgbBillingFrequencyClient cgbBillingFrequencyClient) {
		this.cgbBillingFrequencyClient = cgbBillingFrequencyClient;
	}

}
