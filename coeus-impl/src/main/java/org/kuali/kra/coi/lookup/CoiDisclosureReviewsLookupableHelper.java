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
package org.kuali.kra.coi.lookup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryByCriteria;
import org.kuali.kra.coi.CoiDisclProject;
import org.kuali.kra.coi.CoiDisclosure;
import org.kuali.kra.coi.CoiDisclosureEventType;
import org.kuali.kra.coi.CoiReviewer;
import org.kuali.kra.coi.CoiUserRole;
import org.kuali.kra.coi.lookup.dao.CoiDisclosureDao;
import org.kuali.kra.coi.lookup.dao.ojb.CoiDisclosureDaoOjb;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.util.GlobalVariables;

@SuppressWarnings("unchecked")
public class CoiDisclosureReviewsLookupableHelper extends CoiDisclosureLookupableHelperBase {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 5482769028074271782L;
    
    private CoiDisclosureDaoOjb coiDisclosureDao;

    @Override
    public List<? extends BusinessObject> getLookupSpecificSearchResults(Map<String, String> fieldValues) {

        String currentUser = GlobalVariables.getUserSession().getPrincipalName();
        List<CoiDisclosure> disclosuresToReview = new ArrayList<CoiDisclosure>();

        
        HashMap<String, Object> roleFieldValues = new HashMap<String, Object>();
        roleFieldValues.put("userId", currentUser);
        roleFieldValues.put("reviewerCode", CoiReviewer.ASSIGNED_REVIEWER);
        roleFieldValues.put("reviewCompleted", false);

        List<CoiUserRole> coiUserRoles = (List<CoiUserRole>) businessObjectService.findMatching(CoiUserRole.class, roleFieldValues);
        
        List<Long> disclosureList =   new ArrayList<Long>();
        
        for (CoiUserRole role : coiUserRoles) {
            disclosureList.add(role.getCoiDisclosureId());
        }
        
        
        Criteria crit = new Criteria();
        crit.addIn("coiDisclosureId", disclosureList);
        QueryByCriteria query = new QueryByCriteria(CoiDisclosure.class, crit);
        
        if (!disclosureList.isEmpty()) {
           disclosuresToReview =  new ArrayList<CoiDisclosure>((Collection<CoiDisclosure>)getCoiDisclosureDao().getPersistenceBrokerTemplate().getCollectionByQuery(query));       
        }
        
        return disclosuresToReview;
        
    }
    
    @Override
    protected boolean isAuthorizedForCoiLookups() {
        return true;
    }

	public CoiDisclosureDaoOjb getCoiDisclosureDao() {
		return coiDisclosureDao;
	}

	public void setCoiDisclosureDao(CoiDisclosureDaoOjb coiDisclosureDao) {
		this.coiDisclosureDao = coiDisclosureDao;
	}
}
