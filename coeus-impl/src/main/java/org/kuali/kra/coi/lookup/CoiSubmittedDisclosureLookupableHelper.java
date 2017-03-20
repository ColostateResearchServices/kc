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

import org.apache.commons.lang3.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryByCriteria;
import org.displaytag.util.CollectionUtil;
import org.kuali.kra.coi.CoiDisclosure;
import org.kuali.kra.coi.CoiReviewer;
import org.kuali.kra.coi.CoiUserRole;
import org.kuali.kra.coi.auth.CoiDisclosureTask;
import org.kuali.kra.coi.lookup.dao.ojb.CoiDisclosureDaoOjb;
import org.kuali.kra.infrastructure.TaskName;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.util.GlobalVariables;

import java.util.*;

@SuppressWarnings("unchecked")
public class CoiSubmittedDisclosureLookupableHelper extends CoiDisclosureLookupableHelperBase {
    

    private static final long serialVersionUID = 4999402404037030752L;
    //field name
    private static final String LEAD_UNIT = "leadUnitNumber";

    private transient CoiDisclosureDaoOjb coiDisclosureDao;



    @Override
    public List<? extends BusinessObject> getLookupSpecificSearchResults(Map<String, String> fieldValues) {

        // disclosureStatus 2 is Routed for Review
        fieldValues.remove("disclosureStatusCode");
        fieldValues.put("disclosureStatusCode", "2");

        List<String> allUnits = new ArrayList<String>();
        String allUnitsString=fieldValues.get("unitNumber");
        if (allUnitsString != null && allUnitsString.length()>0) {
            allUnits.add(allUnitsString);
        }

        List<String> viewerUnits = getUnitsForCoiRole("COI Viewer");
        if (viewerUnits!=null && !viewerUnits.isEmpty()) {
            allUnits.addAll(viewerUnits);
        }

        List<String> reviewerUnits = getUnitsForCoiRole("COI Reviewer");
        if (reviewerUnits!=null && !reviewerUnits.isEmpty()) {
            allUnits.addAll(reviewerUnits);
        }

        List<String> approverUnits = getUnitsForCoiRole("COI Approver");
        if (approverUnits!=null && !approverUnits.isEmpty()) {
            allUnits.addAll(approverUnits);
        }

        if ( allUnits !=null &&  !allUnits.isEmpty()) {
            fieldValues.put("unitNumber", String.join("|", allUnits));
        }

        List<CoiDisclosure> allDisclosures = (List<CoiDisclosure>) super.getResults(fieldValues);
        List<CoiDisclosure> submittedDisclosures = new ArrayList<CoiDisclosure>();

        for (CoiDisclosure disclosure : allDisclosures) {
            if (disclosure.isSubmitted() && this.disclosureCanBeDisplayed(disclosure, fieldValues)) {
                submittedDisclosures.add(disclosure);
            }
        }

        List<CoiDisclosure> assignedDisclosureReviews = (List<CoiDisclosure>) (getAssignedDisclosureReviews());

        for (CoiDisclosure disclosure : assignedDisclosureReviews) {
            if (disclosure.isSubmitted() && this.disclosureCanBeDisplayed(disclosure, fieldValues) && !submittedDisclosures.contains(disclosure)) {
                submittedDisclosures.add(disclosure);
            }
        }

        return submittedDisclosures;
    }
    
    /**
     * This method determines whether the disclosure can be viewed by the current user.
     * Researchers should only see their own disclosures.  COI Admin should have unrestricted access.
     * @param rawDisclosure
     * @param fieldValues
     * @return true when current user is allowed to view the disclosure; false otherwise
     */
    private boolean disclosureCanBeDisplayed(CoiDisclosure rawDisclosure, Map<String, String> fieldValues) {
        boolean displayDisclosure = false;
        String researcherLeadUnit = fieldValues.get(LEAD_UNIT);
        if (rawDisclosure.getCoiDisclosureDocument() != null) {
            CoiDisclosureTask task = new CoiDisclosureTask(TaskName.VIEW_COI_DISCLOSURE, rawDisclosure);
            if (getTaskAuthorizationService().isAuthorized(getUserIdentifier(), task) && 
                (StringUtils.isBlank(researcherLeadUnit) || researcherLeadUnit.equals(rawDisclosure.getLeadUnitNumber()))) {
                
                displayDisclosure = true;
            }
        }        
        return displayDisclosure;
    }

    
    @Override
    protected boolean isAuthorizedForCoiLookups() {
        return true;
    }


    /**
     * this is identical to code in CoiDisclosureReviewsLookupableHelper, but had issues Spring injecting that class into this one.
     * @return
     */
    protected List<CoiDisclosure> getAssignedDisclosureReviews() {

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



    public CoiDisclosureDaoOjb getCoiDisclosureDao() {
        return coiDisclosureDao;
    }

    public void setCoiDisclosureDao(CoiDisclosureDaoOjb coiDisclosureDao) {
        this.coiDisclosureDao = coiDisclosureDao;
    }

}
