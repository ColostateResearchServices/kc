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

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("departmentPreApproverDerivedRoleTypeService")
public class DepartmentLevelApproverDerivedRoleTypeServiceImpl extends ProposalAllUnitAdministratorDerivedRoleTypeServiceImpl {

    private static final String UNIT_APPROVER_TYPE_CODE = "16";

    @Value(UNIT_APPROVER_TYPE_CODE)
    private String unitAdministratorTypeCode;

    @Override
    protected String getUnitAdministratorTypeCode(Map<String, String> qualifications, String roleName) {
        return getUnitAdministratorTypeCode();
    }

    public String getUnitAdministratorTypeCode() {
        return unitAdministratorTypeCode;
    }

    public void setUnitAdministratorTypeCode(String unitAdministratorTypeCode) {
        this.unitAdministratorTypeCode = unitAdministratorTypeCode;
    }
}
