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

import java.sql.Date;

/**
 * ExconProjectDestination business object
 * 
 */
public class ExconProjectDestination extends ExconProjectAssociate implements Comparable<ExconProjectDestination> {

    private static final long serialVersionUID = 1652823456758069217L;

    private Long exconProjectDestId;
    private Long projectId;
    private String destinationCountryCode;
    private Date arrivalDate;
    private Date departureDate;
    private String destinationComment;
    private String sanctionList;

    public ExconProjectDestination() {
    }

	public Long getExconProjectDestId() {
		return exconProjectDestId;
	}

	public void setExconProjectDestId(Long exconProjectDestId) {
		this.exconProjectDestId = exconProjectDestId;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getDestinationCountryCode() {
		return destinationCountryCode;
	}

	public void setDestinationCountryCode(String destinationCountryCode) {
		this.destinationCountryCode = destinationCountryCode;
	}
	
	public String getDestinationCountryName() {
		String destinationCountryName="";
		if (!StringUtils.isEmpty(destinationCountryCode)) {
			destinationCountryName=getCountryService().getCountryByAlternateCode(destinationCountryCode).getName();
		}
		return destinationCountryName;
	}

	public Date getArrivalDate() {
		return arrivalDate;
	}
	
	public String getArrivalDateStr() {
		return ExconProject.formattedDate(arrivalDate);
	}

	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public Date getDepartureDate() {
		return departureDate;
	}
	
	public String getDepartureDateStr() {
		return ExconProject.formattedDate(departureDate);
	}

	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}

	public String getDestinationComment() {
		return destinationComment;
	}

	public void setDestinationComment(String destinationComment) {
		this.destinationComment = destinationComment;
	}
	
    public String getSanctionList() {
		return sanctionList;
	}

	public void setSanctionList(String sanctionList) {
		this.sanctionList = sanctionList;
	}
	
	public String getSanctionListName() {
		String sanctionListName="";
		if (!StringUtils.isEmpty(sanctionList)) {
			sanctionListName=getSanctionListFinder().getKeyLabel(sanctionList);
		}
		return sanctionListName;
	}
	
	public ExconProjectSanctionListFinder getSanctionListFinder() {
		return new ExconProjectSanctionListFinder();
	}

	public void resetPersistenceState() {
        exconProjectDestId = null;
        versionNumber = null;
    }

    public int compareTo(ExconProjectDestination exconProjectDestinationArg) {
        return exconProjectDestinationArg.getUpdateTimestamp().compareTo(this.getUpdateTimestamp());
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((destinationComment == null) ? 0 : destinationComment.hashCode());
		result = prime * result
				+ ((arrivalDate == null) ? 0 : arrivalDate.hashCode());
		result = prime
				* result
				+ ((exconProjectDestId == null) ? 0 : exconProjectDestId
						.hashCode());
		result = prime
				* result
				+ ((destinationCountryCode == null) ? 0 : destinationCountryCode
						.hashCode());
		result = prime * result
				+ ((projectId == null) ? 0 : projectId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExconProjectDestination other = (ExconProjectDestination) obj;
		if (arrivalDate == null) {
			if (other.arrivalDate != null)
				return false;
		} else if (!arrivalDate.equals(other.arrivalDate))
			return false;
		if (exconProjectDestId == null) {
			if (other.exconProjectDestId != null)
				return false;
		} else if (!exconProjectDestId.equals(other.exconProjectDestId))
			return false;
		if (destinationCountryCode == null) {
			if (other.destinationCountryCode != null)
				return false;
		} else if (!destinationCountryCode.equals(other.destinationCountryCode))
			return false;
		if (projectId == null) {
			if (other.projectId != null)
				return false;
		} else if (!projectId.equals(other.projectId))
			return false;
		return true;
	}
    
    protected CountryService getCountryService() {
    	return KcServiceLocator.getService(CountryService.class);
    }

}
