package org.kuali.kra.external.locfund;

import java.util.Date;
import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.kra.external.frequency.FrequencyDto;
import org.kuali.kra.infrastructure.Constants;



@WebService(name = "letterOfCreditFundWebService", targetNamespace = Constants.FINANCIAL_INTEGRATION_KC_SERVICE_NAMESPACE)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface LetterOfCreditFundWebService {
	
	
	public List<LetterOfCreditFundDto> findMatchingFund(@WebParam(name = "fundCode") String fundCode, 
			                                            @WebParam(name = "description") String description);
	
	public List<LetterOfCreditFundDto> allLocFunds();	

	
	public List<LetterOfCreditFundGroupDto> findMatchingFundGroup(@WebParam(name = "groupCode") String groupCode, @WebParam(name = "description") String description);
	
	public List<LetterOfCreditFundGroupDto> allLocFundGroups();		
	
	
}
