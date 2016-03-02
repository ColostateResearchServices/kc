package org.kuali.kra.external.locfund;

import org.kuali.kra.award.cgb.LetterOfCreditFundGroup;
import org.kuali.kra.external.frequency.FrequencyDto;
import org.kuali.kra.external.service.KcDtoServiceBase;



public class LetterOfCreditFundGroupDtoService extends KcDtoServiceBase<LetterOfCreditFundGroupDto, LetterOfCreditFundGroup> {

	@Override
	public LetterOfCreditFundGroupDto buildDto(
			LetterOfCreditFundGroup dataObject) {

		if (dataObject != null) {
			LetterOfCreditFundGroupDto dto = new LetterOfCreditFundGroupDto();
			dto.setGroupCode(dataObject.getGroupCode());
			dto.setDescription(dataObject.getDescription());
			dto.setActive(dataObject.isActive());
			return dto;
		} else {
			return null;
		}
	}

}
