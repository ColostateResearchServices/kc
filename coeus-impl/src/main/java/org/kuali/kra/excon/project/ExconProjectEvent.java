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

import java.sql.Date;

/**
 * ExconProjectEvent business object
 * 
 */
public class ExconProjectEvent extends ExconProjectAssociate implements Comparable<ExconProjectEvent> {

    private static final long serialVersionUID = 1652888833758069217L;

    private Long exconProjectEventId;
    private Long projectId;
    private String projectEventTypeCode;
    private ExconProjectEventType projectEventType;
    private Date eventDate;
    private String eventComment;

    public ExconProjectEvent() {
    }

	public Long getExconProjectEventId() {
		return exconProjectEventId;
	}

	public void setExconProjectEventId(Long exconProjectEventId) {
		this.exconProjectEventId = exconProjectEventId;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getProjectEventTypeCode() {
		return projectEventTypeCode;
	}

	public void setProjectEventTypeCode(String projectEventTypeCode) {
		this.projectEventTypeCode = projectEventTypeCode;
	}
	
	public ExconProjectEventType getProjectEventType() {
    	if (!StringUtils.isEmpty(projectEventTypeCode)) {
            this.refreshReferenceObject("projectEventType");
        }
		return projectEventType;
	}
	
	public void setProjectEventType(ExconProjectEventType projectEventType) {
		this.projectEventType = projectEventType;
	}

	public Date getEventDate() {
		return eventDate;
	}
	
	public String getEventDateStr() {
		return ExconProject.formattedDate(eventDate);
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	public String getEventComment() {
		return eventComment;
	}

	public void setEventComment(String eventComment) {
		this.eventComment = eventComment;
	}
	
    public void resetPersistenceState() {
        exconProjectEventId = null;
        versionNumber = null;
    }

    public int compareTo(ExconProjectEvent exconProjectEventArg) {
        return exconProjectEventArg.getUpdateTimestamp().compareTo(this.getUpdateTimestamp());
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((eventComment == null) ? 0 : eventComment.hashCode());
		result = prime * result
				+ ((eventDate == null) ? 0 : eventDate.hashCode());
		result = prime
				* result
				+ ((exconProjectEventId == null) ? 0 : exconProjectEventId
						.hashCode());
		result = prime
				* result
				+ ((projectEventTypeCode == null) ? 0 : projectEventTypeCode
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
		ExconProjectEvent other = (ExconProjectEvent) obj;
		if (eventComment == null) {
			if (other.eventComment != null)
				return false;
		} else if (!eventComment.equals(other.eventComment))
			return false;
		if (eventDate == null) {
			if (other.eventDate != null)
				return false;
		} else if (!eventDate.equals(other.eventDate))
			return false;
		if (exconProjectEventId == null) {
			if (other.exconProjectEventId != null)
				return false;
		} else if (!exconProjectEventId.equals(other.exconProjectEventId))
			return false;
		if (projectEventTypeCode == null) {
			if (other.projectEventTypeCode != null)
				return false;
		} else if (!projectEventTypeCode.equals(other.projectEventTypeCode))
			return false;
		if (projectId == null) {
			if (other.projectId != null)
				return false;
		} else if (!projectId.equals(other.projectId))
			return false;
		return true;
	}
    
    


}
