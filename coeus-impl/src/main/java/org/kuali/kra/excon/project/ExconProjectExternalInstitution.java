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

import org.kuali.coeus.common.framework.rolodex.Rolodex;

/**
 * ExconProjectExternalInstitution business object
 * 
 */
public class ExconProjectExternalInstitution extends ExconProjectAssociate implements Comparable<ExconProjectExternalInstitution> {

    private static final long serialVersionUID = 123452333758069217L;

    private Long projectExternalInstitutionId;
    private Long projectId;
    private Integer rolodexId;
    private Rolodex rolodexEntry;

    public ExconProjectExternalInstitution() {
    }

	public Long getProjectExternalInstitutionId() {
		return projectExternalInstitutionId;
	}

	public void setProjectExternalInstitutionId(Long projectExternalInstitutionId) {
		this.projectExternalInstitutionId = projectExternalInstitutionId;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Integer getRolodexId() {
		return rolodexId;
	}

	public void setRolodexId(Integer rolodexId) {
		this.rolodexId = rolodexId;
	}
		
    public Rolodex getRolodexEntry() {
    	this.refreshReferenceObject("rolodexEntry");
		return rolodexEntry;
	}

	public void setRolodexEntry(Rolodex rolodexEntry) {
		this.rolodexEntry = rolodexEntry;
	}

	public void resetPersistenceState() {
        projectExternalInstitutionId = null;
        versionNumber = null;
    }

    public int compareTo(ExconProjectExternalInstitution exconProjectExternalInstitutionArg) { // fix to compare via description
        return exconProjectExternalInstitutionArg.getRolodexId().compareTo(this.getRolodexId());
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((projectExternalInstitutionId == null) ? 0 : projectExternalInstitutionId
						.hashCode());
		result = prime
				* result
				+ ((rolodexId == null) ? 0 : rolodexId
						.hashCode());
		result = prime * result
				+ ((projectId == null) ? 0 : projectId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof ExconProjectExternalInstitution && ((ExconProjectExternalInstitution)o).getProjectExternalInstitutionId()==this.getProjectExternalInstitutionId()) {
			return true;
		}
		return false;
	}
    
    


}
