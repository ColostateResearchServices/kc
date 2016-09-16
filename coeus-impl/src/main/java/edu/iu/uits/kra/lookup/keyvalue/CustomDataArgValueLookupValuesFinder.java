package edu.iu.uits.kra.lookup.keyvalue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.coeus.common.framework.custom.arg.ArgValueLookup;
import org.kuali.coeus.sys.framework.service.KcServiceLocator;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;
import org.kuali.rice.krad.service.BusinessObjectService;

public class CustomDataArgValueLookupValuesFinder extends KeyValuesBase {

	private static final long serialVersionUID = 622778129205306665L;
	private String argName;
    private String savedArgValue;
	private boolean actvInd;
	private static final String INACTIVE_ARG_VALUE_FLAG = "(inactive)";

	/**
     * @see org.kuali.rice.krad.keyvalues.KeyValuesBase#getKeyValues()
     */
    public List<KeyValue> getKeyValues() {

        Map<String, String> fieldValues = new HashMap<String, String>();
        fieldValues.put("argumentName", argName);
        
        Collection<ArgValueLookup> argValueLookups = (Collection<ArgValueLookup>) KcServiceLocator.getService(BusinessObjectService.class).findMatching(ArgValueLookup.class, fieldValues);
        List<KeyValue> keyValues = new ArrayList<KeyValue>();
        List<KeyValue> inactiveKeyValues = new ArrayList<KeyValue>();
        
        // UITSRA-3298  Create Custom Data Field type of DropDown ArgValue
        for (ArgValueLookup argValueLookup : argValueLookups) {
        	// ArgValueLookup record has never been selected before
        	if(StringUtils.equalsIgnoreCase(savedArgValue, "false")) {
        		if (argValueLookup.isActvInd()) {
                	keyValues.add(new ConcreteKeyValue(argValueLookup.getId().toString(),
                		StringUtils.isNotBlank(argValueLookup.getValue()) ? argValueLookup.getValue() : argValueLookup.getDescription()));
        		}
        		//else - Do not display the inactive record
        	}
        	else {
        		if (argValueLookup.isActvInd()) {
                	keyValues.add(new ConcreteKeyValue(argValueLookup.getId().toString(),
                    	StringUtils.isNotBlank(argValueLookup.getValue()) ? argValueLookup.getValue() : argValueLookup.getDescription()));        			
        		}
        		else {
        			inactiveKeyValues.add(new ConcreteKeyValue(argValueLookup.getId().toString() + INACTIVE_ARG_VALUE_FLAG,
                        StringUtils.isNotBlank(argValueLookup.getValue()) ? argValueLookup.getValue() : argValueLookup.getDescription()));        			
        		}
        	}
        }
        // Added comparator below to alphabetize lists on value
        Collections.sort(keyValues, new KeyValueComparator());
        keyValues.addAll(inactiveKeyValues);
        return keyValues;
    }

    public String getArgName() {
        return argName;
    }

    public void setArgName(String argName) {
        this.argName = argName;
    }

    /**
	 * @return the savedArgValue
	 */
	public String getSavedArgValue() {
		return savedArgValue;
	}

	/**
	 * @param savedArgValue the savedArgValue to set
	 */
	public void setSavedArgValue(String savedArgValue) {
		this.savedArgValue = savedArgValue;
	}

    /**
	 * @return the actvInd
	 */
	public boolean isActvInd() {
		return actvInd;
	}

	/**
	 * @param actvInd the actvInd to set
	 */
	public void setActvInd(boolean actvInd) {
		this.actvInd = actvInd;
	}
	
    class KeyValueComparator implements Comparator<KeyValue> {
        public int compare(KeyValue o1, KeyValue o2) {
            return o1.getValue().compareToIgnoreCase(o2.getValue());
        }        
    }
}
