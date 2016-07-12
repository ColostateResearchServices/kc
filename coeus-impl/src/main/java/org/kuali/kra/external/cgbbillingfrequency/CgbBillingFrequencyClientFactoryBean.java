package org.kuali.kra.external.cgbbillingfrequency;

import org.kuali.rice.coreservice.framework.parameter.ParameterService;
import org.springframework.beans.factory.FactoryBean;

public class CgbBillingFrequencyClientFactoryBean implements FactoryBean<CgbBillingFrequencyClient> {

	private boolean sharedRice;
    private ParameterService parameterService;
    
	@Override
	public CgbBillingFrequencyClient getObject() throws Exception {
		CgbBillingFrequencyClient object = null; 
		if(sharedRice)
		    object = null;//(CgbBillingFrequencyClient) (CgbBillingFrequencyClientKSBClientImpl.getInstance());
		else
		    object = (CgbBillingFrequencyClient) (CgbBillingFrequencyClientImpl.getInstance());

        object.setParameterService(parameterService);

		return object;
	}

	@Override
	public Class<?> getObjectType() {
		return CgbBillingFrequencyClient.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	public boolean isSharedRice() {
		return sharedRice;
	}

	public void setSharedRice(boolean sharedRice) {
		this.sharedRice = sharedRice;
	}

	public ParameterService getParameterService() {
		return parameterService;
	}

	public void setParameterService(ParameterService parameterService) {
		this.parameterService = parameterService;
	}
	

}
