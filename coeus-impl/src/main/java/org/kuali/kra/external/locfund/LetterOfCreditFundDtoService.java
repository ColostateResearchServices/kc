package org.kuali.kra.external.locfund;

import org.kuali.kra.award.cgb.LetterOfCreditFund;
import org.kuali.kra.award.cgb.LetterOfCreditFundGroup;
import org.kuali.kra.external.cgbbillingfrequency.CgbBillingFrequency;
import org.kuali.kra.external.frequency.FrequencyDto;
import org.kuali.kra.external.service.KcDtoService;
import org.kuali.kra.external.service.KcDtoServiceBase;


public class LetterOfCreditFundDtoService  extends KcDtoServiceBase<LetterOfCreditFundDto, LetterOfCreditFund> {

	
	private KcDtoService<LetterOfCreditFundGroupDto, LetterOfCreditFundGroup> locFundGroupDtoService;

	@Override
	public LetterOfCreditFundDto buildDto(
			LetterOfCreditFund dataObject) {

		if (dataObject != null) {
			LetterOfCreditFundDto dto = new LetterOfCreditFundDto();
			dto.setFundCode(dataObject.getFundCode());
			dto.setExpirationDate(dataObject.getExpirationDate());
			dto.setStartDate(dataObject.getStartDate());
			dto.setAmount(dataObject.getAmount() != null ? dataObject.getAmount().bigDecimalValue() : null );
			dto.setGroupCode(dataObject.getGroupCode());
			dto.setDescription(dataObject.getDescription());
			dto.setActive(dataObject.isActive());
			dto.setFundGroup(locFundGroupDtoService.buildDto(dataObject.getFundGroup()));
			return dto;
		} else {
			return null;
		}

	}

	public KcDtoService<LetterOfCreditFundGroupDto, LetterOfCreditFundGroup> getLocFundGroupDtoService() {
		return locFundGroupDtoService;
	}

	public void setLocFundGroupDtoService(
			KcDtoService<LetterOfCreditFundGroupDto, LetterOfCreditFundGroup> locFundGroupDtoService) {
		this.locFundGroupDtoService = locFundGroupDtoService;
	}

}

