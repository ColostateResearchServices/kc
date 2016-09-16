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
package org.kuali.coeus.propdev.impl.auth;

/**
 * Created by tjwilson on 8/8/2016.
 */

import edu.colostate.kc.infrastructure.CSUKeyConstants;
import org.apache.commons.lang3.StringUtils;
import org.kuali.coeus.common.framework.unit.UnitService;
import org.kuali.coeus.common.framework.unit.admin.AbstractUnitAdministrator;
import org.kuali.coeus.common.framework.unit.admin.UnitAdministrator;
import org.kuali.coeus.propdev.impl.core.DevelopmentProposal;
import org.kuali.kra.kim.bo.KcKimAttributes;
import org.kuali.rice.coreservice.framework.parameter.ParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.kuali.kra.infrastructure.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component("departmentLevelApproverDerivedRoleTypeService")
public class DepartmentLevelApproverDerivedRoleTypeServiceImpl extends ProposalAllUnitAdministratorDerivedRoleTypeServiceImpl {

    @Autowired
    @Qualifier("parameterService")
    private ParameterService parameterService;

    @Autowired
    @Qualifier("unitService")
    private UnitService unitService;

//    @Override
//    public List<? extends AbstractUnitAdministrator> getUnitAdministrators(Map<String, String> qualifiers) {
//        String unitNumber = qualifiers.get(KcKimAttributes.UNIT_NUMBER);
//        List<UnitAdministrator> result = new ArrayList<UnitAdministrator>();
//       if (unitNumber != null) {
//            result.addAll(unitService.retrieveUnitAdministratorsByUnitNumber(getUnitNumberForPersonUnit(unitService.getUnit(unitNumber))));
//        }
//        return result;
//    }

    @Override
    protected String getUnitAdministratorTypeCode(Map<String, String> qualifications, String roleName) {
        return getUnitAdministratorTypeCode();
    }

    public String getUnitAdministratorTypeCode() {
        return parameterService.getParameterValueAsString(Constants.MODULE_NAMESPACE_PROPOSAL_DEVELOPMENT, Constants.PARAMETER_COMPONENT_DOCUMENT, CSUKeyConstants.UNIT_APPROVER_TYPE_CODE);
    }

}
