/*
 * Kuali Coeus, a comprehensive research administration system for higher education.
 * 
 * Copyright 2005-2016 Kuali, Inc.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.kuali.coeus.common.budget.impl.core;

import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.kuali.coeus.common.budget.framework.personnel.BudgetPersonnelDetails;
import org.kuali.coeus.common.budget.framework.personnel.ValidCeJobCode;
import org.kuali.kra.infrastructure.Constants;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.coreservice.framework.parameter.ParameterConstants;
import org.kuali.rice.coreservice.framework.parameter.ParameterService;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.coeus.propdev.impl.budget.core.ProposalBudgetForm;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("budgetPersonnelCostElementValuesFinder")
public class BudgetPersonnelCostElementValuesFinder extends CostElementValuesFinder {
    
	@Autowired
    @Qualifier("parameterService")
    private ParameterService parameterService;


    @Autowired
    @Qualifier("businessObjectService")
    private BusinessObjectService businessObjectService;

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {
        String jobCode="";
        BudgetPersonnelDetails details = ((ProposalBudgetForm)model).getAddProjectPersonnelHelper().getBudgetPersonnelDetail();
        if (details != null && details.getBudgetPerson() != null) {
            jobCode = ((ProposalBudgetForm) model).getAddProjectPersonnelHelper().getBudgetPersonnelDetail().getBudgetPerson().getJobCode();
        }

        return getValidCostElementForJobCode(jobCode);
    }
    
    private String getPersonnelBudgetCategoryTypeCode() {
        return this.getParameterService().getParameterValueAsString(Constants.MODULE_NAMESPACE_BUDGET, ParameterConstants.DOCUMENT_COMPONENT,Constants.BUDGET_CATEGORY_TYPE_PERSONNEL);
    }


    protected List<KeyValue> getValidCostElementForJobCode(String jobCode) {
        List<KeyValue> allKeyValues  = super.getKeyValues(getPersonnelBudgetCategoryTypeCode(), true, null);
        ArrayList<KeyValue> jobCodeLimitedKeyValues = new ArrayList<KeyValue>();

        List<ValidCeJobCode> validCesFromJobCode=null;

        Map<String, Object> fieldValues = new HashMap<String, Object>();
        if(StringUtils.isNotEmpty(jobCode)) {
            fieldValues.put("jobCode", jobCode.toUpperCase());
            validCesFromJobCode = (List<ValidCeJobCode>) getBusinessObjectService().findMatching(ValidCeJobCode.class, fieldValues);

            ArrayList<String> validCeCodes = new ArrayList<>();

            if (validCesFromJobCode != null) {
                for (ValidCeJobCode validCe : validCesFromJobCode) {
                    validCeCodes.add(validCe.getCostElement());
                }
                Iterator<KeyValue> iter = allKeyValues.iterator();
                while (iter.hasNext()) {
                    KeyValue keyValue = iter.next();
                    if (StringUtils.isEmpty(keyValue.getKey()) || validCeCodes.contains(keyValue.getKey())) {
                        jobCodeLimitedKeyValues.add(keyValue);
                    }
                }
            }
        }

        if (jobCodeLimitedKeyValues.size() > 1)  { allKeyValues= jobCodeLimitedKeyValues; }

        return allKeyValues;

    }

	public ParameterService getParameterService() {
		return parameterService;
	}

	public void setParameterService(ParameterService parameterService) {
		this.parameterService = parameterService;
	}


    public BusinessObjectService getBusinessObjectService() {
        return businessObjectService;
    }

    public void setBusinessObjectService(BusinessObjectService businessObjectService) {
        this.businessObjectService = businessObjectService;
    }
}
