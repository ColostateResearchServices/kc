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
package org.kuali.kra.excon.document;

import org.apache.commons.lang.StringUtils;
import org.kuali.kra.excon.project.ExconProject;
import org.kuali.kra.infrastructure.Constants;
import org.kuali.rice.krad.util.AuditCluster;
import org.kuali.rice.krad.util.AuditError;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.rules.rule.DocumentAuditRule;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * This class to check whether activity type has been changed for PD or Award, and budget is not sync'ed.  
 */
public class ExconProjectDestinationsAuditRule implements DocumentAuditRule{

    /**
     * @see org.kuali.rice.krad.rules.rule.DocumentAuditRule#processRunAuditBusinessRules(org.kuali.rice.krad.document.Document)
     */
    public boolean processRunAuditBusinessRules(Document document) {
        boolean valid = true;

        ExconProjectDocument exconProjectDocument = (ExconProjectDocument)document;
        
        valid &=projectHasDestination(exconProjectDocument);
        
        return valid;
    }
    
    private boolean projectHasDestination(ExconProjectDocument exconProjectDocument) { // move hardcoded values into a constants class
    	List<AuditError> auditErrors = new ArrayList<AuditError>();
    	ExconProject project=exconProjectDocument.getExconProject();
    	if (project!=null && StringUtils.equals("International Travel", project.getProjectType().getDescription())) {
    		if (project.getExconProjectDestinations().size()<1) {
    			auditErrors.add(new AuditError("exconProjectDestinationsBean.newDestination.destinationCountryCode", "error.exconProjectDestination.missing",
    					"destinations.destinations"));
    			GlobalVariables.getAuditErrorMap().put("destinationsAuditErrors", new AuditCluster("Destinations",auditErrors,Constants.AUDIT_ERRORS));
    			return false;
    		}
    	}
    	return true;
    }



}
