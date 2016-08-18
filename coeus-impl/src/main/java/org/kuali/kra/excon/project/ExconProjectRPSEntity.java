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

import org.apache.commons.lang.StringUtils;
import org.kuali.coeus.sys.framework.service.KcServiceLocator;
import org.kuali.rice.location.api.country.CountryService;

import java.net.URLEncoder;
import java.sql.Date;

/**
 * ExconProjectRPSEntity business object
 * 
 */
public class ExconProjectRPSEntity extends ExconProjectAssociate implements Comparable<ExconProjectRPSEntity> {

    private static final long serialVersionUID = 1652888857845217L;
    
	private Long rpsEntityId;
    private Long projectId;
    private String firstName;
    private String lastName;
    private String otherNames;
    private String concatNames;
    private String companyName;
    private String streetAddress;
    private String city;
    private String state;
    private String countryCode;
    private Date birthdate;
    private String otherInfo;
    private String rpsMatchCode;
    
    public ExconProjectRPSEntity() {
    	
    }

    public Long getRpsEntityId() {
		return rpsEntityId;
	}

	public void setRpsEntityId(Long rpsEntityId) {
		this.rpsEntityId = rpsEntityId;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getFirstName() {
		return firstName != null ? firstName : "";
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName != null ? lastName : "";
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getOtherNames() {
		return otherNames != null ? otherNames : "";
	}

	public void setOtherNames(String otherNames) {
		this.otherNames = otherNames;
	}

	public String getConcatNames() {
		return concatNames;
	}

	public void setConcatNames(String concatNames) {
		this.concatNames = concatNames;
	}
	
	public String getEncodedNames() {
		try {
			return URLEncoder.encode(concatNames,"US-ASCII");
		}
		catch (Exception e) {
			return "";
		}
	}

	public String getCompanyName() {
		return companyName != null ? companyName : "";
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getStreetAddress() {
		return streetAddress != null ? streetAddress : "";
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getCity() {
		return city != null ? city : "";
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state != null ? state : "";
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountryCode() {
		return countryCode != null ? countryCode : "";
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	
	public String getCountryName() {
		String countryName="";
		if (!StringUtils.isEmpty(countryCode)) {
			countryName=getCountryService().getCountryByAlternateCode(countryCode).getName();
		}
		return countryName;
	}
	
	public String getShortCountryCode() {
		String shortCountryCode="";
		if (!StringUtils.isEmpty(countryCode)) {
			shortCountryCode=getCountryService().getCountryByAlternateCode(countryCode).getCode();
		}
		return shortCountryCode;
	}
	
    protected CountryService getCountryService() {
    	return KcServiceLocator.getService(CountryService.class);
    }

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public String getOtherInfo() {
		return otherInfo;
	}

	public void setOtherInfo(String otherInfo) {
		this.otherInfo = otherInfo;
	}

	public String getRpsMatchCode() {
		return rpsMatchCode;
	}

	public void setRpsMatchCode(String rpsMatchCode) {
		this.rpsMatchCode = rpsMatchCode;
	}
	
	public String getRpsMatchDescription() {
		if (!StringUtils.isEmpty(rpsMatchCode)) {
			return getMatchCodeFinder().getKeyLabel(rpsMatchCode);
		}
		return null;
	}
	
	protected ExconProjectRPSMatchCodeFinder getMatchCodeFinder() {
		return new ExconProjectRPSMatchCodeFinder();
	}
    
    public void resetPersistenceState() {
        rpsEntityId = null;
        versionNumber = null;
    }

    public int compareTo(ExconProjectRPSEntity exconProjectRPSEntityArg) {
        return exconProjectRPSEntityArg.getUpdateTimestamp().compareTo(this.getUpdateTimestamp());
    }
    
}