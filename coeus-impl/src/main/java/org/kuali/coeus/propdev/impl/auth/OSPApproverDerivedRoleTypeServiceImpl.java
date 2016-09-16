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

import edu.colostate.kc.infrastructure.CSUKeyConstants;
import org.apache.commons.lang3.StringUtils;
import org.kuali.coeus.common.framework.unit.UnitService;
import org.kuali.coeus.common.framework.unit.admin.AbstractUnitAdministrator;
import org.kuali.coeus.common.framework.unit.admin.UnitAdministrator;
import org.kuali.coeus.propdev.impl.core.DevelopmentProposal;
import org.kuali.kra.infrastructure.Constants;
import org.kuali.kra.kim.bo.KcKimAttributes;
import org.kuali.rice.core.api.membership.MemberType;
import org.kuali.rice.coreservice.framework.parameter.ParameterService;
import org.kuali.rice.kim.api.role.RoleMembership;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component("ospApproverDerivedRoleTypeService")
public class OSPApproverDerivedRoleTypeServiceImpl extends ProposalAllUnitAdministratorDerivedRoleTypeServiceImpl {

    @Autowired
    @Qualifier("parameterService")
    private ParameterService parameterService;

    @Autowired
    @Qualifier("unitService")
    private UnitService unitService;

    @Override
    public List<? extends AbstractUnitAdministrator> getUnitAdministrators(Map<String, String> qualifiers) {
        String proposalNumber = qualifiers.get(KcKimAttributes.PROPOSAL);
        String unitNumber = qualifiers.get(KcKimAttributes.UNIT_NUMBER);
        List<UnitAdministrator> result = new ArrayList<UnitAdministrator>();
        if (proposalNumber != null) {
            DevelopmentProposal proposal = getDataObjectService().find(DevelopmentProposal.class, proposalNumber);
            if (StringUtils.isNotBlank(proposal.getOwnedByUnitNumber())) {
                result.addAll(unitService.retrieveUnitAdministratorsByUnitNumber(proposal.getOwnedByUnitNumber()));
            }
        } else if (unitNumber != null) {
            result.addAll(unitService.retrieveUnitAdministratorsByUnitNumber(getUnitNumberForPersonUnit(unitService.getUnit(unitNumber))));
        }
        return result;
    }

    @Override
    public List<RoleMembership> getRoleMembersFromDerivedRole(String namespaceCode, String roleName, Map<String, String> qualification) {
        List<RoleMembership> members = new ArrayList<RoleMembership>();

        List<String> unitAdminTypeCodes = getUnitAdministratorTypeCodes(qualification, roleName);
        for (java.lang.String unitAdminTypeCode : unitAdminTypeCodes) {
            members.addAll(getUnitAdministrators(qualification).stream().filter(unitAdministrator -> {
                return StringUtils.isNotBlank(unitAdministrator.getPersonId()) &&
                        (StringUtils.isBlank(unitAdminTypeCode) || StringUtils.equals(unitAdministrator.getUnitAdministratorTypeCode(), unitAdminTypeCode));
            }).map(unitAdmin -> {
                return unitAdmin.getPersonId();
            })
                    .distinct()
                    .map(adminId -> {
                        return RoleMembership.Builder.create(null, null, adminId, MemberType.PRINCIPAL, null).build();
                    }).collect(Collectors.toList()));
        }
        return members;
    }


    protected List<String> getUnitAdministratorTypeCodes(Map<String, String> qualifications, String roleName) {
        return getUnitAdministratorTypeCodes();
    }

    public List<String> getUnitAdministratorTypeCodes() {
        String unitAdministratorTypeCodes = parameterService.getParameterValueAsString(Constants.MODULE_NAMESPACE_PROPOSAL_DEVELOPMENT, Constants.PARAMETER_COMPONENT_DOCUMENT, CSUKeyConstants.UNIT_OSPAPPROVER_TYPE_CODE);
        List<String> unitAdministratorTypeCodesList = Arrays.asList(unitAdministratorTypeCodes.split(","));
        return unitAdministratorTypeCodesList;
    }

}
