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
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;

import java.util.*;

public class ExconProjectEmailContentValuesFinder extends UifKeyValuesFinderBase {
    
	private static final long serialVersionUID = 8530320729246485395L;
	private BusinessObjectService businessObjectService;
    private String emailContentType="BODY";
    
    public ExconProjectEmailContentValuesFinder() {
        businessObjectService = KcServiceLocator.getService(BusinessObjectService.class);
    }

    public void setEmailContentType (String emailContentType) {
    	this.emailContentType=emailContentType;
    }
    @Override
    public List<KeyValue> getKeyValues() {
        Map<String, Object> values = new HashMap<String, Object>();
        values.put("contentTypeCode", emailContentType);
        List<KeyValue> result = new ArrayList<KeyValue>();
        Collection<ExconProjectEmailContent> types = getBusinessObjectService().findMatching(ExconProjectEmailContent.class, values);
        for (ExconProjectEmailContent type : types) {
            ConcreteKeyValue pair = new ConcreteKeyValue();
            pair.setKey(type.getContentTypeCode());
            pair.setValue(type.getContentTypeObj().getDescription());
            result.add(pair);
        }
        return result;
    }
    
    protected BusinessObjectService getBusinessObjectService() {
        return businessObjectService;
    }
}