package org.kuali.kra.external.locfund;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebParam;

import org.apache.commons.lang.StringUtils;
import org.kuali.kra.award.cgb.LetterOfCreditFund;
import org.kuali.kra.award.cgb.LetterOfCreditFundGroup;
import org.kuali.kra.external.cgbbillingfrequency.CgbBillingFrequency;
import org.kuali.kra.external.service.KcDtoService;
import org.kuali.rice.krad.service.BusinessObjectService;

public class LetterOfCreditFundWebServiceImpl implements
		LetterOfCreditFundWebService {
	
	private BusinessObjectService businessObjectService;
	private KcDtoService<LetterOfCreditFundDto, LetterOfCreditFund> locFundDtoService;
	private KcDtoService<LetterOfCreditFundGroupDto, LetterOfCreditFundGroup> locFundGroupDtoService;



	@Override
	public List<LetterOfCreditFundDto> findMatchingFund(String fundCode,
			String description) {
		Map<String, String> values = new HashMap<String, String>();
		if (!StringUtils.isEmpty(fundCode)) {
			values.put("fundCode", fundCode);
		}
		if (!StringUtils.isEmpty(description)) {
			values.put("description", description);
		}
		
		return locFundDtoService.buildDtoList(getBusinessObjectService()
				.findMatching(LetterOfCreditFund.class, values));
	}

	@Override
	public List<LetterOfCreditFundDto> allLocFunds() {
		return locFundDtoService.buildDtoList(getBusinessObjectService().findAll(LetterOfCreditFund.class));
	}


	@Override
	public List<LetterOfCreditFundGroupDto> findMatchingFundGroup(
			@WebParam(name = "groupCode") String groupCode,
			@WebParam(name = "description") String description) {
		Map<String, String> values = new HashMap<String, String>();
		if (!StringUtils.isEmpty(groupCode)) {
			values.put("groupCode", groupCode);
		}
		if (!StringUtils.isEmpty(description)) {
			values.put("description", description);
		}
		return locFundGroupDtoService.buildDtoList(getBusinessObjectService()
				.findMatching(LetterOfCreditFundGroup.class, values));

	}

	@Override
	public List<LetterOfCreditFundGroupDto> allLocFundGroups() {

		return locFundGroupDtoService.buildDtoList(getBusinessObjectService().findAll(LetterOfCreditFundGroup.class));
	}

	public BusinessObjectService getBusinessObjectService() {
		return businessObjectService;
	}

	public void setBusinessObjectService(BusinessObjectService businessObjectService) {
		this.businessObjectService = businessObjectService;
	}

	public KcDtoService<LetterOfCreditFundDto, LetterOfCreditFund> getLocFundDtoService() {
		return locFundDtoService;
	}

	public void setLocFundDtoService(
			KcDtoService<LetterOfCreditFundDto, LetterOfCreditFund> locFundDtoService) {
		this.locFundDtoService = locFundDtoService;
	}

	public KcDtoService<LetterOfCreditFundGroupDto, LetterOfCreditFundGroup> getLocFundGroupDtoService() {
		return locFundGroupDtoService;
	}

	public void setLocFundGroupDtoService(
			KcDtoService<LetterOfCreditFundGroupDto, LetterOfCreditFundGroup> locFundGroupDtoService) {
		this.locFundGroupDtoService = locFundGroupDtoService;
	}

}
