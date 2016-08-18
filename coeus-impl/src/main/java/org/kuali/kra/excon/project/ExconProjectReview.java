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

/**
 * ExconProjectReview business object
 * 
 */
public class ExconProjectReview extends ExconProjectAssociate implements Comparable<ExconProjectReview> {

    private static final long serialVersionUID = 123452333758069217L;

    private Long exconProjectReviewId;
    private Long projectId;
    private String projectReviewTypeCode;
    private String reviewComment;

    public ExconProjectReview() {
    }

	public Long getExconProjectReviewId() {
		return exconProjectReviewId;
	}

	public void setExconProjectReviewId(Long exconProjectReviewId) {
		this.exconProjectReviewId = exconProjectReviewId;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getProjectReviewTypeCode() {
		return projectReviewTypeCode;
	}

	public void setProjectReviewTypeCode(String projectReviewTypeCode) {
		this.projectReviewTypeCode = projectReviewTypeCode;
	}
	
	public String getProjectReviewTypeName() {
		String projectReviewTypeName="";
		if (!StringUtils.isEmpty(projectReviewTypeCode)) {
			projectReviewTypeName=getProjectReviewTypeCodeFinder().getKeyLabel(projectReviewTypeCode);
		}
		return projectReviewTypeName;
	}
	
	public ExconProjectReviewTypeCodeFinder getProjectReviewTypeCodeFinder() {
		return new ExconProjectReviewTypeCodeFinder();
	}
	
    public String getReviewComment() {
		return reviewComment;
	}

	public void setReviewComment(String reviewComment) {
		this.reviewComment = reviewComment;
	}

	public void resetPersistenceState() {
        exconProjectReviewId = null;
        versionNumber = null;
    }

    public int compareTo(ExconProjectReview exconProjectReviewArg) {
        return exconProjectReviewArg.getProjectReviewTypeName().compareTo(this.getProjectReviewTypeName());
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((exconProjectReviewId == null) ? 0 : exconProjectReviewId
						.hashCode());
		result = prime
				* result
				+ ((projectReviewTypeCode == null) ? 0 : projectReviewTypeCode
						.hashCode());
		result = prime * result
				+ ((projectId == null) ? 0 : projectId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof ExconProjectReview && ((ExconProjectReview)o).getExconProjectReviewId()==this.getExconProjectReviewId()) {
			return true;
		}
		return false;
	}
    
    


}
