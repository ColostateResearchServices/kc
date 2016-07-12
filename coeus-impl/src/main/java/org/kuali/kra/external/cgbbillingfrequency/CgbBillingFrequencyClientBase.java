package org.kuali.kra.external.cgbbillingfrequency;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.kuali.kfs.module.external.kc.dto.BillingFrequencyDTO;
import org.kuali.kfs.module.external.kc.service.BillingFrequencyService;
import org.kuali.kra.infrastructure.Constants;
import org.kuali.rice.coreservice.framework.parameter.ParameterService;

public abstract class CgbBillingFrequencyClientBase implements CgbBillingFrequencyClient {
	


		
		protected static final String SOAP_SERVICE_NAME = "billingFrequencyServiceSOAP";
		protected static final QName SERVICE_NAME = new QName(Constants.FINANCIAL_SYSTEM_SERVICE_NAMESPACE, SOAP_SERVICE_NAME);
	    private static final String ERROR_MESSAGE = "Cannot connect to the service. The service may be down, please try again later.";
	    private static final Log LOG = LogFactory.getLog(CgbBillingFrequencyClientBase.class);
	    private ParameterService parameterService;
	    
	    protected abstract BillingFrequencyService getServiceHandle();
		
		@Override
		public CgbBillingFrequency getCgbBillingFrequency(String billingFrequencyCode) {
			try {
	            BillingFrequencyService service = getServiceHandle();
	            LOG.info("Connecting to financial system...");
	            CgbBillingFrequency freq = getCgbBillingFrequency(service.getBillingFrequency(billingFrequencyCode));
				return freq;
			} catch (Exception e) {
				LOG.error(ERROR_MESSAGE + e.getMessage(), e);
				return null;
			}
		}
		
		protected CgbBillingFrequency getCgbBillingFrequency(BillingFrequencyDTO dto) {
			if (dto != null) {
				CgbBillingFrequency billingFrequency = new CgbBillingFrequency();
				billingFrequency.setActive(dto.isActive());
				billingFrequency.setCode(dto.getFrequency());
				billingFrequency.setDescription(dto.getFrequencyDescription());
				billingFrequency.setGracePeriodDays(dto.getGracePeriodDays());
				return billingFrequency;
			} else {
				return null;
			}
		}
		
		public List<CgbBillingFrequency> getActive() {
			try {
	            BillingFrequencyService service = getServiceHandle();
	            List<BillingFrequencyDTO> dtos = service.getActive();

			
			if (dtos != null) {
				List<CgbBillingFrequency> result = new ArrayList<CgbBillingFrequency>();
				for (BillingFrequencyDTO dto : dtos) {
					result.add(getCgbBillingFrequency(dto));
				}
				return result;
			} else {
				return null;
			}
			} catch (Exception e) {
				LOG.error(ERROR_MESSAGE + e.getMessage(), e);
				return null;
			}
			
		}	

		public List<CgbBillingFrequency> getMatching(Map<String, String> fieldValues) {
			return getActive();
		}

		protected ParameterService getParameterService() {
			return parameterService;
		}

		public void setParameterService(ParameterService parameterService) {
			this.parameterService = parameterService;
		}

	}
	
	

