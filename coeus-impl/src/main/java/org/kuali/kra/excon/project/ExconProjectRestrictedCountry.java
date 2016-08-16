/*
 * Copyright 2005-2014 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.kra.excon.project;

import org.drools.core.util.StringUtils;
import org.kuali.coeus.sys.framework.model.KcPersistableBusinessObjectBase;
import org.kuali.coeus.sys.framework.service.KcServiceLocator;
import org.kuali.rice.location.api.country.CountryService;

/**
 * ExconProjectRestrictedCountry business object
 * 
 * @author Kuali Coeus development team (kc.dev@kuali.org)
 */
public class ExconProjectRestrictedCountry extends KcPersistableBusinessObjectBase {

	private static final long serialVersionUID = 1235971345897L;

	private String postalCountryCode;

    private String sanctionListCode;
    
    public ExconProjectRestrictedCountry() {
    }

	public String getPostalCountryCode() {
		return postalCountryCode;
	}

	public void setPostalCountryCode(String postalCountryCode) {
		this.postalCountryCode = postalCountryCode;
	}
	
	public String getCountryName() {
		String countryName="";
		if (!StringUtils.isEmpty(postalCountryCode)) {
			countryName=getCountryService().getCountryByAlternateCode(postalCountryCode).getName();
		}
		return countryName;
	}

	public String getSanctionListCode() {
		return sanctionListCode;
	}

	public void setSanctionListCode(String sanctionListCode) {
		this.sanctionListCode = sanctionListCode;
	}
	
	public String getSanctionListName() {
		if (!StringUtils.isEmpty(sanctionListCode)) {
			return getSanctionListFinder().getKeyLabel(sanctionListCode);
		}
		return null;
	}
	
	protected ExconProjectSanctionListFinder getSanctionListFinder() {
		return new ExconProjectSanctionListFinder();
	}
	
    protected CountryService getCountryService() {
    	return KcServiceLocator.getService(CountryService.class);
    }
 
}
