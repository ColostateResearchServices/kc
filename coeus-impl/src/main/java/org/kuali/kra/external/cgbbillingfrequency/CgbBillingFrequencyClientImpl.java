package org.kuali.kra.external.cgbbillingfrequency;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.ws.WebServiceClient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.kuali.kfs.module.external.kc.service.BillingFrequencyService;
import org.kuali.kfs.module.external.kc.service.BillingFrequencyServiceSOAP;
import org.kuali.kra.infrastructure.Constants;
import org.kuali.rice.core.api.config.property.ConfigContext;

public class CgbBillingFrequencyClientImpl extends
		CgbBillingFrequencyClientBase {

	
    public final static URL WSDL_LOCATION;
    
    private static CgbBillingFrequencyClientImpl client;
    
    private static final Log LOG = LogFactory.getLog(CgbBillingFrequencyClientImpl.class);
    
    private CgbBillingFrequencyClientImpl() {
    }

    public static CgbBillingFrequencyClientImpl getInstance() {
        if (client == null) {
            client = new CgbBillingFrequencyClientImpl();
        }
        return client;
    }
      
    static
    {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        if (null == cl) {
            cl = CgbBillingFrequencyClientImpl .class.getClassLoader();
        }
        String wsdlPath =  ((WebServiceClient) (BillingFrequencyServiceSOAP.class.getAnnotation(WebServiceClient.class))).wsdlLocation();
        WSDL_LOCATION = cl.getResource(wsdlPath); 
    }

    


    @Override
    protected BillingFrequencyService getServiceHandle() {
        URL wsdlURL = null;
        
        boolean getFinSystemURLFromWSDL = getParameterService().getParameterValueAsBoolean("KC-AWARD", "Document", Constants.GET_FIN_SYSTEM_URL_FROM_WSDL);
        
        if (getFinSystemURLFromWSDL) {
            wsdlURL = WSDL_LOCATION;
        } else {
            String serviceEndPointUrl = ConfigContext.getCurrentContextConfig().getProperty(Constants.FIN_SYSTEM_INTEGRATION_SERVICE_URL);
            try {
                wsdlURL = new URL(serviceEndPointUrl + SOAP_SERVICE_NAME + "?wsdl");
            } catch (MalformedURLException mue) {
                LOG.error("Could not construct financial system URL from config file: " + mue.getMessage());
            }
        }
        
        BillingFrequencyServiceSOAP ss = new BillingFrequencyServiceSOAP(wsdlURL, SERVICE_NAME);
        return ss.getBillingFrequencyServicePort();
    }	
	
	
}
