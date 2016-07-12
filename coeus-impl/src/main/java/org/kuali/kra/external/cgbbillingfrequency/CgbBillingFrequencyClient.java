package org.kuali.kra.external.cgbbillingfrequency;

import java.util.List;
import java.util.Map;

import org.kuali.rice.coreservice.framework.parameter.ParameterService;

public interface CgbBillingFrequencyClient {

    public CgbBillingFrequency getCgbBillingFrequency(String billingFrequencyID);

	public List<CgbBillingFrequency> getActive();	
	
	public List<CgbBillingFrequency> getMatching(Map<String, String> fieldValues);	
	
	public void setParameterService(ParameterService parameterService);

}
