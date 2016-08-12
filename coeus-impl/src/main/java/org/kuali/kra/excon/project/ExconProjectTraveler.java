/*
 * Copyright 2005-2014 The Kuali Foundation
 * 
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/ecl1.php
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
import org.springframework.util.AutoPopulatingList;

import java.util.ArrayList;
import java.util.List;

public class ExconProjectTraveler extends KcPersistableBusinessObjectBase{
    
    private static final long serialVersionUID = 967856545234587L;
    private String personId;
	private List<ExconProjectTravelerCommunication> travelerCommunications;
   
    
    /**
     * Constructs a ExconProjectTraveler
     */
    public ExconProjectTraveler() {
    	travelerCommunications = new AutoPopulatingList<ExconProjectTravelerCommunication>(ExconProjectTravelerCommunication.class);
    }

	public String getPersonId() {
		return personId;
	}


	public void setPersonId(String personId) {
		this.personId = personId;
	}
	
	public void setTravelerCommunications(ArrayList<ExconProjectTravelerCommunication> travelerCommunications){
		this.travelerCommunications = travelerCommunications;
	}
	
	public List<ExconProjectTravelerCommunication> getTravelerCommunications() {
		if ((travelerCommunications==null || travelerCommunications.size()<1) && !StringUtils.isEmpty(personId)) {
			this.refreshReferenceObject("travelerCommunications");
		}
		return travelerCommunications;
	}
	
    public void resetPersistenceState() {
        personId = null;
        versionNumber = null;
    }
}
