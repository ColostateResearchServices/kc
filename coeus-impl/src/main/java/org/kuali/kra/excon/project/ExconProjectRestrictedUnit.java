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

import org.drools.core.util.StringUtils;
import org.kuali.coeus.common.framework.unit.UnitService;
import org.kuali.coeus.sys.framework.model.KcPersistableBusinessObjectBase;
import org.kuali.coeus.sys.framework.service.KcServiceLocator;
import org.kuali.coeus.common.framework.unit.Unit;

/**
 * ExconProjectRestrictedUnit business object
 * 
 * @author Kuali Coeus development team (kc.dev@kuali.org)
 */
public class ExconProjectRestrictedUnit extends KcPersistableBusinessObjectBase {

	private static final long serialVersionUID = 124857345897L;

	private String unitNumber;
	
	private Unit unit;
   
    public ExconProjectRestrictedUnit() {
    }

	public String getUnitNumber() {
		return unitNumber;
	}

	public void setUnitNumber(String unitNumber) {
		this.unitNumber = unitNumber;
	}
	
	public String getUnitName() {
		String unitName="";
		if (!StringUtils.isEmpty(unitNumber)) {
			unitName=getUnitService().getUnitName(unitNumber);
		}
		return unitName;
	}
	
	public Unit getUnit() {
		if (!StringUtils.isEmpty(unitNumber)) {
			unit=getUnitService().getUnit(unitNumber);
		}
		return unit;
	}
	
	public void setUnit(Unit unit) {
		this.unit = unit;
		return;
	}
	
    protected UnitService getUnitService() {
    	return KcServiceLocator.getService(UnitService.class);
    }
 
}
