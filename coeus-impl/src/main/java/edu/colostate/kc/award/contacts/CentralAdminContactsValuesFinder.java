package edu.colostate.kc.award.contacts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.coeus.common.framework.unit.admin.UnitAdministratorType;
import org.kuali.coeus.sys.framework.service.KcServiceLocator;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;
import org.kuali.rice.krad.service.BusinessObjectService;


public class CentralAdminContactsValuesFinder  extends KeyValuesBase {
	    
	    private BusinessObjectService businessObjectService;
	    
	    public CentralAdminContactsValuesFinder() {
	        businessObjectService = KcServiceLocator.getService(BusinessObjectService.class);
	    }

	    /**
	     * @see org.kuali.rice.krad.keyvalues.KeyValuesFinder#getKeyValues()
	     */
	    @SuppressWarnings("unchecked")
	    public List<KeyValue> getKeyValues() {
	        Map<String, Object> values = new HashMap<String, Object>();
	        values.put("defaultGroupFlag", "C");
	        List<KeyValue> result = new ArrayList<KeyValue>();
	        Collection<UnitAdministratorType> types = getBusinessObjectService().findMatching(UnitAdministratorType.class, values);
	        for (UnitAdministratorType type : types) {
	            ConcreteKeyValue pair = new ConcreteKeyValue();
	            pair.setKey(type.getCode());
	            pair.setValue(type.getDescription());
	            result.add(pair);
	        }
	        return result;
	    }
	    
	    protected BusinessObjectService getBusinessObjectService() {
	        return businessObjectService;
	    }
	}

