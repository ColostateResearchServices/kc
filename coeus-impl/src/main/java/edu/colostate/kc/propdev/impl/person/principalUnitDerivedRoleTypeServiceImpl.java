package edu.colostate.kc.propdev.impl.person;

import org.apache.commons.lang3.StringUtils;
import org.kuali.kra.kim.bo.KcKimAttributes;
import org.kuali.rice.core.api.exception.RiceIllegalArgumentException;
import org.kuali.rice.core.api.membership.MemberType;
import org.kuali.rice.core.api.uif.RemotableAttributeError;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.role.RoleMembership;
import org.kuali.rice.kim.impl.role.RoleServiceImpl;
import org.kuali.rice.kns.kim.role.PrincipalDerivedRoleTypeServiceImpl;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by tjwilson on 10/19/2016.
 */
@Component("principalUnitDerivedRoleTypeService")
public class principalUnitDerivedRoleTypeServiceImpl extends PrincipalDerivedRoleTypeServiceImpl{

    @Override
    public boolean hasDerivedRole(String principalId, List<String> groupIds, String namespaceCode, String roleName, Map<String, String> qualification) {
        return super.hasDerivedRole(principalId, groupIds, namespaceCode, roleName, qualification) &&
                this.getIdentityService().getEntityByPrincipalId(principalId).getPrimaryEmployment().getPrimaryDepartmentCode() != null;

    }

}
