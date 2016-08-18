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

import org.kuali.coeus.sys.framework.service.KcServiceLocator;
import org.kuali.coeus.sys.framework.keyvalue.KeyValueComparator;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.kim.api.permission.PermissionService;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.util.GlobalVariables;

import java.util.*;

public class ExconProjectTypeCodeValuesFinder extends UifKeyValuesFinderBase {
    
	private static final long serialVersionUID = 82345259246485395L;
	private BusinessObjectService businessObjectService;
	private PermissionService permissionService;
    
    public ExconProjectTypeCodeValuesFinder() {
        businessObjectService = KcServiceLocator.getService(BusinessObjectService.class);
        permissionService = KcServiceLocator.getService(PermissionService.class);
    }

    @Override
    public List<KeyValue> getKeyValues() {
        List<KeyValue> result = new ArrayList<KeyValue>();
        Collection<ExconProjectType> types = getBusinessObjectService().findAll(ExconProjectType.class);
        for (ExconProjectType type : types) {
        	String projectTypeCode=type.getExconProjectTypeCode();
        	HashMap<String,String> roleQualifiers=new HashMap<String,String>();
        	roleQualifiers.put("projectTypeCode", projectTypeCode);
        	if (getPermissionService().isAuthorized( GlobalVariables.getUserSession().getPerson().getPrincipalId(),"KC-EXCON", "Create Project Document", roleQualifiers)) {
        		ConcreteKeyValue pair = new ConcreteKeyValue();
        		pair.setKey(projectTypeCode);
        		pair.setValue(type.getDescription());
        		result.add(pair);
        	}
        }
        Collections.sort(result,new KeyValueComparator());
        return result;
    }
    
    protected BusinessObjectService getBusinessObjectService() {
        return businessObjectService;
    }
    
    protected PermissionService getPermissionService() {
    	return permissionService;
    }
}