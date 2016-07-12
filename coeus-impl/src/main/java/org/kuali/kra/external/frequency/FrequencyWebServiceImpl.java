/*
 * Kuali Coeus, a comprehensive research administration system for higher education.
 * 
 * Copyright 2005-2015 Kuali, Inc.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.kuali.kra.external.frequency;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebParam;

import org.kuali.kra.award.paymentreports.Frequency;
import org.kuali.kra.external.cgbbillingfrequency.CgbBillingFrequency;
import org.kuali.kra.external.service.KcDtoService;
import org.kuali.rice.krad.service.BusinessObjectService;

public class FrequencyWebServiceImpl implements FrequencyWebService {
	
	private BusinessObjectService businessObjectService;
	private KcDtoService<FrequencyDto, CgbBillingFrequency> frequencyDtoService;

	@Override
	public List<FrequencyDto> findActive() {
		Map<String,String> fieldValues = new HashMap<String,String>();
		fieldValues.put("active", "Y");
		return frequencyDtoService.buildDtoList(getBusinessObjectService().findMatching(CgbBillingFrequency.class, fieldValues));
	}

	@Override
	public List<FrequencyDto> findAll() {
		return frequencyDtoService.buildDtoList(getBusinessObjectService().findAll(CgbBillingFrequency.class));
	}

	public BusinessObjectService getBusinessObjectService() {
		return businessObjectService;
	}

	public void setBusinessObjectService(BusinessObjectService businessObjectService) {
		this.businessObjectService = businessObjectService;
	}

	public KcDtoService<FrequencyDto, CgbBillingFrequency> getFrequencyDtoService() {
		return frequencyDtoService;
	}

	public void setFrequencyDtoService(
			KcDtoService<FrequencyDto, CgbBillingFrequency> frequencyDtoService) {
		this.frequencyDtoService = frequencyDtoService;
	}

}
