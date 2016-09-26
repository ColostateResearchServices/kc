/*
 * Copyright 2005-2013 The Kuali Foundation
 * 
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/ecl1.php
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.iu.uits.kra.negotiations.lookup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.PersistenceBroker;
import org.apache.ojb.broker.accesslayer.LookupException;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.kra.award.home.Award;
import org.kuali.coeus.common.framework.version.VersionStatus;
import org.kuali.kra.institutionalproposal.home.InstitutionalProposal;
import org.kuali.kra.institutionalproposal.proposallog.ProposalLog;
import org.kuali.kra.negotiations.bo.Negotiation;
import org.kuali.kra.negotiations.bo.NegotiationAssociationType;
import org.kuali.kra.negotiations.bo.NegotiationUnassociatedDetail;
import org.kuali.kra.negotiations.lookup.NegotiationDao;
import org.kuali.kra.negotiations.service.NegotiationService;
import org.kuali.kra.subaward.bo.SubAward;
import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.kew.api.WorkflowRuntimeException;
import org.kuali.rice.kns.datadictionary.BusinessObjectEntry;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.dao.impl.LookupDaoOjb;
import org.kuali.rice.krad.lookup.CollectionIncomplete;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.springframework.dao.DataIntegrityViolationException;
import org.springmodules.orm.ojb.OjbFactoryUtils;
import org.springmodules.orm.ojb.OjbOperationException;

import edu.iu.uits.kra.negotiations.bo.IUNegotiationAssociationTypeUtil;

/**
 * Negotiation Dao to assist with lookups. This implements looking up associated document information
 * as well as just negotiation info.
 */
public class IUNegotiationDaoOjb extends LookupDaoOjb implements NegotiationDao {

    private static final String ASSOC_PREFIX = "associatedNegotiable";
    private static final String NEGOTIATION_TYPE_ATTR = "negotiationAssociationTypeId";
    private static final String ASSOCIATED_DOC_ID_ATTR = "associatedDocumentId";
    private static final String INVALID_COLUMN_NAME = "NaN";
    
    private static Map<String, String> awardTransform;
    private static Map<String, String> proposalTransform;
    private static Map<String, String> proposalLogTransform;
    private static Map<String, String> unassociatedTransform;
    private static Map<String, String> subAwardTransform;
    
    private static Integer maxSearchResults;
    
    private NegotiationService negotiationService;

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(IUNegotiationDaoOjb.class);
    
    static {
        awardTransform = new HashMap<String, String>();
        awardTransform.put("sponsorName", "sponsor.sponsorName");
        awardTransform.put("piName", "projectPersons.fullName");
        //proposal type code doesn't exist on the award so make sure we don't find awards when
        //search for proposal type
        awardTransform.put("negotiableProposalTypeCode", INVALID_COLUMN_NAME);
        awardTransform.put("leadUnitNumber", "unitNumber");
        awardTransform.put("leadUnitName", "leadUnit.unitName");
        awardTransform.put("subAwardRequisitionerId", INVALID_COLUMN_NAME);
                
        proposalTransform = new HashMap<String, String>();
        proposalTransform.put("sponsorName", "sponsor.sponsorName");
        proposalTransform.put("piName", "projectPersons.fullName");
        proposalTransform.put("leadUnitNumber", "unitNumber");
        proposalTransform.put("leadUnitName", "leadUnit.unitName");
        proposalTransform.put("negotiableProposalTypeCode", "proposalTypeCode");
        proposalTransform.put("subAwardRequisitionerId", INVALID_COLUMN_NAME);
        
        proposalLogTransform = new HashMap<String, String>();
        proposalLogTransform.put("sponsorName", "sponsor.sponsorName");
        proposalLogTransform.put("leadUnitNumber", "leadUnit");
        proposalLogTransform.put("leadUnitName", "unit.unitName");
        proposalLogTransform.put("negotiableProposalTypeCode", "proposalTypeCode");
        proposalLogTransform.put("subAwardRequisitionerId", INVALID_COLUMN_NAME);

        unassociatedTransform = new HashMap<String, String>();
        unassociatedTransform.put("sponsorName", "sponsor.sponsorName");
        unassociatedTransform.put("piName", "piName");
        //proposal type code doesn't exist here either so make sure we don't find then when
        //searching for proposal type
        unassociatedTransform.put("negotiableProposalTypeCode", INVALID_COLUMN_NAME);
        unassociatedTransform.put("leadUnitName", "leadUnit.unitName");
        unassociatedTransform.put("subAwardRequisitionerId", INVALID_COLUMN_NAME);
        
        subAwardTransform = new HashMap<String, String>();
        subAwardTransform.put("sponsorName", INVALID_COLUMN_NAME);
        subAwardTransform.put("sponsorCode", INVALID_COLUMN_NAME);
        subAwardTransform.put("piName", INVALID_COLUMN_NAME);
        subAwardTransform.put("negotiableProposalTypeCode", INVALID_COLUMN_NAME);
        subAwardTransform.put("leadUnitName", "leadUnit.unitName");
        subAwardTransform.put("subAwardRequisitionerId", "requisitionerId");
        
        /* Begin IU Customization */ 
        // UITSRA-3711
        subAwardTransform.put("leadUnitNumber", "requisitionerUnit");
        /* End IU Customization */
        
        /* Begin IU Customization */    
        // UITSRA-2893, UITSRA-2894
        //Per Core meeting on 11/12/2013, we are not going to display subAwardOrganizationName for awards or IPs (for now)
        awardTransform.put("subAwardOrganizationName", INVALID_COLUMN_NAME);
        proposalTransform.put("subAwardOrganizationName", INVALID_COLUMN_NAME);
        proposalLogTransform.put("subAwardOrganizationName", INVALID_COLUMN_NAME);
        unassociatedTransform.put("subAwardOrganizationName", "subAwardOrganization.organizationName");
        subAwardTransform.put("subAwardOrganizationName", "organization.organizationName");
        /* End IU Customization */
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public Collection<Negotiation> getNegotiationResults(Map<String, String> fieldValues) {
        Map<String, String> associationDetails = new HashMap<String, String>();
        Iterator<Map.Entry<String, String>> iter = fieldValues.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, String> entry = iter.next();
            if (StringUtils.startsWith(entry.getKey(), ASSOC_PREFIX)) {
                iter.remove();
                if (!StringUtils.isEmpty(entry.getValue())) {
                    associationDetails.put(entry.getKey().replaceFirst(ASSOC_PREFIX + ".", ""), entry.getValue());
                }
            }
        }
        
        Collection<Negotiation> result = new ArrayList<Negotiation>();
        if (!associationDetails.isEmpty()) {
        	/* Begin IU Customization UITSRA-4033 */ 
        	long totalMatchingResultsCount = 0L;
        	
        	if (StringUtils.isEmpty(fieldValues.get("negotiationAssociationTypeId")) || 
        			StringUtils.equals(getNegotiationService().getNegotiationAssociationType(NegotiationAssociationType.AWARD_ASSOCIATION).getId().toString(), fieldValues.get("negotiationAssociationTypeId").trim())) {
            	Collection<Negotiation> resultLinkedToAward = getNegotiationsLinkedToAward(fieldValues, associationDetails);
                totalMatchingResultsCount += ((CollectionIncomplete<Negotiation>) resultLinkedToAward).getActualSizeIfTruncated().longValue(); 
                addListToList(result, resultLinkedToAward);        		
        	}
        	if (StringUtils.isEmpty(fieldValues.get("negotiationAssociationTypeId")) || 
        			StringUtils.equals(getNegotiationService().getNegotiationAssociationType(NegotiationAssociationType.INSTITUATIONAL_PROPOSAL_ASSOCIATION).getId().toString(), fieldValues.get("negotiationAssociationTypeId").trim())) {
        		Collection<Negotiation> resultLinkedToProposal = getNegotiationsLinkedToProposal(fieldValues, associationDetails);
        		totalMatchingResultsCount += ((CollectionIncomplete<Negotiation>) resultLinkedToProposal).getActualSizeIfTruncated().longValue(); 
        		addListToList(result, resultLinkedToProposal);
        	}
        	if (StringUtils.isEmpty(fieldValues.get("negotiationAssociationTypeId")) || 
        			StringUtils.equals(getNegotiationService().getNegotiationAssociationType(NegotiationAssociationType.PROPOSAL_LOG_ASSOCIATION).getId().toString(), fieldValues.get("negotiationAssociationTypeId").trim())) {
        		Collection<Negotiation> resultLinkedToProposalLog = getNegotiationsLinkedToProposalLog(fieldValues, associationDetails);
            	totalMatchingResultsCount += ((CollectionIncomplete<Negotiation>) resultLinkedToProposalLog).getActualSizeIfTruncated().longValue(); 
            	addListToList(result, resultLinkedToProposalLog);
        	}
        	if (StringUtils.isEmpty(fieldValues.get("negotiationAssociationTypeId")) || 
        			StringUtils.equals(getNegotiationService().getNegotiationAssociationType(NegotiationAssociationType.NONE_ASSOCIATION).getId().toString(), fieldValues.get("negotiationAssociationTypeId").trim())) {
        		Collection<Negotiation> resultLinkedToUnassociated = getNegotiationsUnassociated(fieldValues, associationDetails);
            	totalMatchingResultsCount += ((CollectionIncomplete<Negotiation>) resultLinkedToUnassociated).getActualSizeIfTruncated().longValue(); 
            	addListToList(result, resultLinkedToUnassociated);
        	}
        	if (StringUtils.isEmpty(fieldValues.get("negotiationAssociationTypeId")) || 
        			StringUtils.equals(getNegotiationService().getNegotiationAssociationType(NegotiationAssociationType.SUB_AWARD_ASSOCIATION).getId().toString(), fieldValues.get("negotiationAssociationTypeId").trim())) {
        		Collection<Negotiation> resultLinkedToSubAward = getNegotiationsLinkedToSubAward(fieldValues, associationDetails);
            	CollectionIncomplete<Negotiation> subAwardCollectionIncomplete = new CollectionIncomplete<Negotiation>(resultLinkedToSubAward, (long)resultLinkedToSubAward.size());
            	totalMatchingResultsCount += subAwardCollectionIncomplete.getActualSizeIfTruncated().longValue();
            	addListToList(result, resultLinkedToSubAward);
        	}
            result = new CollectionIncomplete<Negotiation>(result, totalMatchingResultsCount);
            
            /* End IU Customization */
        } else {
            result = findCollectionBySearchHelper(Negotiation.class, fieldValues, false, false, null);
        }
        if (result != null && !result.isEmpty() && StringUtils.isNotBlank(fieldValues.get("negotiationAge"))) {
            try {
                result = filterByNegotiationAge(fieldValues.get("negotiationAge"), result);
            } catch (NumberFormatException e) {
                GlobalVariables.getMessageMap().putError(KRADConstants.DOCUMENT_ERRORS, RiceKeyConstants.ERROR_CUSTOM, new String[] { "Invalid Numeric Input: " + fieldValues.get("negotiationAge")});
                result = new ArrayList<Negotiation>();
            }
        }
        return result;
    }
    
    private void addListToList(Collection<Negotiation> fullResultList, Collection<Negotiation> listToAdd) {
        if (fullResultList != null && listToAdd != null) {
            Integer max = getNegotiatonSearchResultsLimit();
            if (max == null) {
                max = 500;
            }
            if (fullResultList.size() < max) {
                int fullResultListPlusListToAddSize = fullResultList.size() + listToAdd.size();
                if (fullResultListPlusListToAddSize <= max) {
                    fullResultList.addAll(listToAdd);
                } else {
                    int numberOfNewEntriesToAdd = max - fullResultList.size();
                    int counter = 0;
                    for (Negotiation neg : listToAdd) {
                        if (counter < numberOfNewEntriesToAdd) {
                            fullResultList.add(neg);
                        }
                        counter++;
                    }
                }
            }
        }
    }
    
    public Collection findCollectionBySearchHelper(Class businessObjectClass, Map formProps, boolean unbounded, boolean usePrimaryKeyValuesOnly, Object additionalCriteria ) {
        BusinessObject businessObject = checkBusinessObjectClass(businessObjectClass);
        if (usePrimaryKeyValuesOnly) {
            return executeSearch(businessObjectClass, getCollectionCriteriaFromMapUsingPrimaryKeysOnly(businessObjectClass, formProps), unbounded);
        }
        
        Criteria crit = getCollectionCriteriaFromMap(businessObject, formProps);
        if (additionalCriteria != null && additionalCriteria instanceof Criteria) {
            crit.addAndCriteria((Criteria) additionalCriteria);
        }

        return executeSearch(businessObjectClass, crit, unbounded);
    }
    
    private BusinessObject checkBusinessObjectClass(Class businessObjectClass) {
        if (businessObjectClass == null) {
            throw new IllegalArgumentException("BusinessObject class passed to LookupDaoOjb findCollectionBySearchHelper... method was null");
        }
        BusinessObject businessObject = null;
        try {
            businessObject = (BusinessObject) businessObjectClass.newInstance();
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException("LookupDaoOjb could not get instance of " + businessObjectClass.getName(), e);
        }
        catch (InstantiationException e) {
            throw new RuntimeException("LookupDaoOjb could not get instance of " + businessObjectClass.getName(), e);
        }
        return businessObject;
    }
    
    private Collection executeSearch(Class businessObjectClass, Criteria criteria, boolean unbounded) {
        Collection searchResults = new ArrayList();
        Long matchingResultsCount = null;
        try {
            Integer searchResultsLimit = getNegotiatonSearchResultsLimit();
            if (!unbounded && (searchResultsLimit != null)) {
                matchingResultsCount = new Long(getPersistenceBrokerTemplate().getCount(QueryFactory.newQuery(businessObjectClass, criteria)));
                getDbPlatform().applyLimitSql(searchResultsLimit);
            }
            if ((matchingResultsCount == null) || (matchingResultsCount.intValue() <= searchResultsLimit.intValue())) {
                matchingResultsCount = new Long(0);
            }
            searchResults = getPersistenceBrokerTemplate().getCollectionByQuery(QueryFactory.newQuery(businessObjectClass, criteria));
            // populate Person objects in business objects
            List bos = new ArrayList();
            bos.addAll(searchResults);
            searchResults = bos;
        }
        catch (OjbOperationException e) {
            throw new RuntimeException("NegotiationDaoOjb encountered exception during executeSearch", e);
        }
        catch (DataIntegrityViolationException e) {
            throw new RuntimeException("NegotiationDaoOjb encountered exception during executeSearch", e);
        }
        return new CollectionIncomplete(searchResults, matchingResultsCount);
    }
    
    private Integer getNegotiatonSearchResultsLimit(){
        if (maxSearchResults == null) {
            BusinessObjectEntry businessObjectEntry = (BusinessObjectEntry) KNSServiceLocator.getDataDictionaryService().getDataDictionary().getBusinessObjectEntry("Negotiation");
            maxSearchResults =  businessObjectEntry.getLookupDefinition().getResultSetLimit();
        }
        return maxSearchResults;
    }
    
    /**
     * Search for awards linked to negotiation using both award and negotiation values.
     * @param negotiationValues
     * @param associatedValues
     * @return
     */
    @SuppressWarnings("unchecked")
    protected CollectionIncomplete<Negotiation> getNegotiationsLinkedToAward(Map<String, String> negotiationValues, Map<String, String> associatedValues) {
        Map<String, String> values = transformMap(associatedValues, awardTransform);
        if (values == null) {
        	return new CollectionIncomplete<Negotiation>(new ArrayList<Negotiation>(), 0L);
        }
        values.put("awardSequenceStatus", VersionStatus.ACTIVE.name());

        Criteria criteria = getCollectionCriteriaFromMap(new Award(), values);
        Criteria negotiationCrit = new Criteria();
        ReportQueryByCriteria subQuery = QueryFactory.newReportQuery(Award.class, criteria);
        subQuery.setAttributes(new String[] {"awardNumber"});
        negotiationCrit.addIn(ASSOCIATED_DOC_ID_ATTR, subQuery);
        negotiationCrit.addEqualTo(NEGOTIATION_TYPE_ATTR, 
                getNegotiationService().getNegotiationAssociationType(NegotiationAssociationType.AWARD_ASSOCIATION).getId());
        Collection<Negotiation> result = this.findCollectionBySearchHelper(Negotiation.class, negotiationValues, false, false, negotiationCrit);
        Long matchingResultsCount = new Long(getPersistenceBrokerTemplate().getCount(QueryFactory.newQuery(Negotiation.class, negotiationCrit)));
        return new CollectionIncomplete<Negotiation>(result, matchingResultsCount);
    }
    
    /**
     * Search for institutional proposals linked to negotiations using both criteria.
     * @param negotiationValues
     * @param associatedValues
     * @return
     */
    @SuppressWarnings("unchecked")
    protected CollectionIncomplete<Negotiation> getNegotiationsLinkedToProposal(Map<String, String> negotiationValues, Map<String, String> associatedValues) {
        Map<String, String> values = transformMap(associatedValues, proposalTransform);
        if (values == null) {
            return new CollectionIncomplete<Negotiation>(new ArrayList<Negotiation>(), 0L);
        }
        values.put("proposalSequenceStatus", VersionStatus.ACTIVE.name());
        Criteria criteria = getCollectionCriteriaFromMap(new InstitutionalProposal(), values);
        Criteria negotiationCrit = new Criteria();
        ReportQueryByCriteria subQuery = QueryFactory.newReportQuery(InstitutionalProposal.class, criteria);
        subQuery.setAttributes(new String[] {"proposalNumber"});
        negotiationCrit.addIn(ASSOCIATED_DOC_ID_ATTR, subQuery);
        negotiationCrit.addEqualTo(NEGOTIATION_TYPE_ATTR, 
                getNegotiationService().getNegotiationAssociationType(NegotiationAssociationType.INSTITUATIONAL_PROPOSAL_ASSOCIATION).getId());
        Collection<Negotiation> result = this.findCollectionBySearchHelper(Negotiation.class, negotiationValues, false, false, negotiationCrit);
        // UITSRA-4033
        Long matchingResultsCount = new Long(getPersistenceBrokerTemplate().getCount(QueryFactory.newQuery(Negotiation.class, negotiationCrit)));
        return new CollectionIncomplete<Negotiation>(result, matchingResultsCount);
    }
    
    /**
     * Search for proposal logs linked to negotiations using both criteria.
     * @param negotiationValues
     * @param associatedValues
     * @return
     */
    @SuppressWarnings("unchecked")
    protected CollectionIncomplete<Negotiation> getNegotiationsLinkedToProposalLog(Map<String, String> negotiationValues, Map<String, String> associatedValues) {
        Map<String, String> values = transformMap(associatedValues, proposalLogTransform);
        if (values == null) {
        	return new CollectionIncomplete<Negotiation>(new ArrayList<Negotiation>(), 0L);
        }
        Criteria criteria = getCollectionCriteriaFromMap(new ProposalLog(), values);
        Criteria negotiationCrit = new Criteria();
        ReportQueryByCriteria subQuery = QueryFactory.newReportQuery(ProposalLog.class, criteria);
        subQuery.setAttributes(new String[] {"proposalNumber"});
        negotiationCrit.addIn(ASSOCIATED_DOC_ID_ATTR, subQuery);
        negotiationCrit.addEqualTo(NEGOTIATION_TYPE_ATTR, 
                getNegotiationService().getNegotiationAssociationType(NegotiationAssociationType.PROPOSAL_LOG_ASSOCIATION).getId());
        Collection<Negotiation> result = this.findCollectionBySearchHelper(Negotiation.class, negotiationValues, false, false, negotiationCrit);
        Long matchingResultsCount = new Long(getPersistenceBrokerTemplate().getCount(QueryFactory.newQuery(Negotiation.class, negotiationCrit)));
        return new CollectionIncomplete<Negotiation>(result, matchingResultsCount);
    } 
    
    /**
     * 
     * This method returns Negotiations linked to subawards based on search.
     * @param negotiationValues
     * @param associatedValues
     * @return
     */
    @SuppressWarnings("unchecked")
    protected CollectionIncomplete<Negotiation> getNegotiationsLinkedToSubAward(Map<String, String> negotiationValues, Map<String, String> associatedValues) {        
        Map<String, String> values = transformMap(associatedValues, subAwardTransform);
        if (values == null) {
        	return new CollectionIncomplete<Negotiation>(new ArrayList<Negotiation>(), 0L);
        }
        values.put("statusCode", "1|6");
        Criteria criteria = getCollectionCriteriaFromMap(new SubAward(), values);
        Criteria negotiationCrit = new Criteria();
        ReportQueryByCriteria subQuery = QueryFactory.newReportQuery(SubAward.class, criteria);

        /* IU Customization Starts */
        // UITSRA-4197 Negotiation Title Search is returning inaccurate results
        subQuery.setAttributes(new String[] {"subAwardCode"});
        /* IU Customization Ends */
        
        negotiationCrit.addIn(ASSOCIATED_DOC_ID_ATTR, subQuery);
        negotiationCrit.addEqualTo(NEGOTIATION_TYPE_ATTR, 
                getNegotiationService().getNegotiationAssociationType(NegotiationAssociationType.SUB_AWARD_ASSOCIATION).getId());
        Collection<Negotiation> result = this.findCollectionBySearchHelper(Negotiation.class, negotiationValues, false, false, negotiationCrit);
        Long matchingResultsCount = new Long(getPersistenceBrokerTemplate().getCount(QueryFactory.newQuery(Negotiation.class, negotiationCrit)));
        return new CollectionIncomplete<Negotiation>(result, matchingResultsCount);
    }  
    
    /**
     * Search for unassociated negotiations using criteria from the unassociated detail.
     * @param negotiationValues
     * @param associatedValues
     * @return
     */
    @SuppressWarnings("unchecked")
    protected CollectionIncomplete<Negotiation> getNegotiationsUnassociated(Map<String, String> negotiationValues, Map<String, String> associatedValues) {
        Map<String, String> values = transformMap(associatedValues, unassociatedTransform);
        if (values == null) {
        	return new CollectionIncomplete<Negotiation>(new ArrayList<Negotiation>(), 0L);
        }
        Criteria criteria = getCollectionCriteriaFromMap(new NegotiationUnassociatedDetail(), values);
        Criteria negotiationCrit = new Criteria();
        ReportQueryByCriteria subQuery = QueryFactory.newReportQuery(NegotiationUnassociatedDetail.class, criteria);
        subQuery.setAttributes(new String[] {"negotiationUnassociatedDetailId"});
        negotiationCrit.addIn(ASSOCIATED_DOC_ID_ATTR, subQuery);
        /* Begin UITSRA-3231 */
        //negotiationCrit.addEqualTo(NEGOTIATION_TYPE_ATTR, 
        //        getNegotiationService().getNegotiationAssociationType(NegotiationAssociationType.NONE_ASSOCIATION).getId());
        for(String associationType : IUNegotiationAssociationTypeUtil.LINKED_NEGOTIATION_ASSOCIATIONS) {
            negotiationCrit.addNotEqualTo(NEGOTIATION_TYPE_ATTR, 
                    getNegotiationService().getNegotiationAssociationType(associationType).getId());        	
        }        
        /* End UITSRA-3231 */
        Collection<Negotiation> result = this.findCollectionBySearchHelper(Negotiation.class, negotiationValues, false, false, negotiationCrit);
        Long matchingResultsCount = new Long(getPersistenceBrokerTemplate().getCount(QueryFactory.newQuery(Negotiation.class, negotiationCrit)));
        return new CollectionIncomplete<Negotiation>(result, matchingResultsCount);
    }    
    
    /**
     * Take the associated field values and convert them to document specific values using the provided
     * transform key.
     * @param values
     * @param transformKey
     * @return
     */
    protected Map<String, String> transformMap(Map<String, String> values, Map<String, String> transformKey) {
        Map<String, String> result = new HashMap<String, String>();
        for (Map.Entry<String, String> entry : values.entrySet()) {
            if (transformKey.get(entry.getKey()) != null) {
                result.put(transformKey.get(entry.getKey()), entry.getValue());
            } else {
                result.put(entry.getKey(), entry.getValue());
            }
        }
        if (result.containsKey(INVALID_COLUMN_NAME)) {
            return null;
        } else {
            return result;
        }
    }
    
    /**
     * Since the negotiation age is not persisted filter negotiations based on age.
     * @param value
     * @param negotiations
     * @return
     */
    protected Collection<Negotiation> filterByNegotiationAge(String value, Collection<Negotiation> negotiations) {
        int lowValue = 0;
        int highValue = 0;
        boolean greaterThan = false;
        boolean lessThan = false;
        boolean between = false;
        if (value.contains(">")) {
            greaterThan = true;
            lowValue = Integer.parseInt(value.replace(">", ""));
        } else if (value.contains("<")) {
            lessThan = true;
            highValue = Integer.parseInt(value.replace("<", ""));
        } else if (value.contains("..")) {
            between = true;
            String[] values = value.split("\\.\\.");
            lowValue = Integer.parseInt(values[0]);
            highValue = Integer.parseInt(values[1]);
        } else {
            lowValue = Integer.parseInt(value);
        }
        Iterator<Negotiation> iter = negotiations.iterator();
        while (iter.hasNext()) {
            Negotiation negotiation = iter.next();
            if (greaterThan) {
                if (negotiation.getNegotiationAge() <= lowValue) {
                    iter.remove();
                }
            } else if (lessThan) {
                if (negotiation.getNegotiationAge() >= highValue) {
                    iter.remove();
                }
            } else if (between) {
                if (negotiation.getNegotiationAge() < lowValue
                        || negotiation.getNegotiationAge() > highValue) {
                    iter.remove();
                }
            } else {
                if (negotiation.getNegotiationAge() != lowValue) {
                    iter.remove();
                }
            }
        }
        
        return negotiations;
    }

    public NegotiationService getNegotiationService() {
        return negotiationService;
    }

    public void setNegotiationService(NegotiationService negotiationService) {
        this.negotiationService = negotiationService;
    }
    
    
    /* IU Customization Starts   UITSRA-3138 */
    /**
     * @param searchString  NegotiationId wild card search string
     * @return  A list of NegotiationIds
     */
    public Collection<Long> getNegotiationIdsWithWildcard(String searchString) {
		Collection<Long> negotiationIds = new ArrayList<Long>();
		
		PersistenceBroker broker = null;
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        try {
            broker = getPersistenceBroker(false);
            conn = broker.serviceConnectionManager().getConnection();

            String query = "SELECT negotiation_id FROM negotiation WHERE negotiation_id LIKE ?";
            
            stmt = conn.prepareStatement(query);
            stmt.setString(1, StringUtils.replace(searchString, "*", "%"));
            rs = stmt.executeQuery();
            
            try {
	            while (rs.next()) {
	            	negotiationIds.add(rs.getLong(1));
	            }
            } finally {
            	try { rs.close(); } catch (Exception ignore) { }
            }
        } catch (SQLException sqle) {
            LOG.error("SQLException: " + sqle.getMessage(), sqle);
            throw new WorkflowRuntimeException(sqle);
        } catch (LookupException le) {
            LOG.error("LookupException: " + le.getMessage(), le);
            throw new WorkflowRuntimeException(le);
        } finally {
            try {
            	if(stmt != null) {
            		stmt.close();
            	}
                if (broker != null) {
                    OjbFactoryUtils.releasePersistenceBroker(broker, this.getPersistenceBrokerTemplate().getPbKey());
                }
            } catch (Exception e) {
                LOG.error("Failed closing connection: " + e.getMessage(), e);
            }
        }
        return negotiationIds;
	}
    

    /**
     * @param piPersonId  PI Person Id
     * @return  A list of NegotiationIds searched by PI
     */
    public Collection<Long> getNegotiationIdsByPI(String piPersonId) {
		Collection<Long> negotiationIds = new ArrayList<Long>();
		
		PersistenceBroker broker = null;
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        try {
            broker = getPersistenceBroker(false);
            conn = broker.serviceConnectionManager().getConnection();

            // UITSRA-3389
            String query = "select NEGOTIATION_ID from NEGOTIATION where NEGOTIATION_ID in \n" +
            					"(select NEGOTIATION_ID from NEGOTIATION_UNASSOC_DETAIL where PI_PERSON_ID=?) \n" +
            				" UNION \n" +
            				"select NEGOTIATION_ID from  NEGOTIATION where \n" + 
            					"ASSOCIATED_DOCUMENT_ID in (select DISTINCT PROPOSAL_NUMBER from PROPOSAL_PERSONS where CONTACT_ROLE_CODE='PI' and PERSON_ID=? \n" +   
            						"and PROPOSAL_NUMBER = NEGOTIATION.ASSOCIATED_DOCUMENT_ID)  and " +
            					"NEGOTIATION_ASSC_TYPE_ID = (select NEGOTIATION_ASSC_TYPE_ID from NEGOTIATION_ASSOCIATION_TYPE where NEGOTIATION_ASSC_TYPE_CODE='IP') \n" +
                			" UNION \n" +
                			"select NEGOTIATION_ID from  NEGOTIATION where \n" +
                				"ASSOCIATED_DOCUMENT_ID in (select DISTINCT AWARD_NUMBER from AWARD_PERSONS where CONTACT_ROLE_CODE='PI' and PERSON_ID=? \n" + 
                					"and AWARD_NUMBER = NEGOTIATION.ASSOCIATED_DOCUMENT_ID)  and \n" +
                				"NEGOTIATION_ASSC_TYPE_ID = (select NEGOTIATION_ASSC_TYPE_ID from NEGOTIATION_ASSOCIATION_TYPE where NEGOTIATION_ASSC_TYPE_CODE='AWD')";
            // End of UITSRA-3389
            
            stmt = conn.prepareStatement(query);
            stmt.setString(1, piPersonId);
            stmt.setString(2, piPersonId);
            stmt.setString(3, piPersonId);
            rs = stmt.executeQuery();
            
            try {
	            while (rs.next()) {
	            	negotiationIds.add(rs.getLong(1));
	            }
            } finally {
            	try { rs.close(); } catch (Exception ignore) { }
            }
        } catch (SQLException sqle) {
            LOG.error("SQLException: " + sqle.getMessage(), sqle);
            throw new WorkflowRuntimeException(sqle);
        } catch (LookupException le) {
            LOG.error("LookupException: " + le.getMessage(), le);
            throw new WorkflowRuntimeException(le);
        } finally {
            try {
            	if(stmt != null) {
            		stmt.close();
            	}
                if (broker != null) {
                    OjbFactoryUtils.releasePersistenceBroker(broker, this.getPersistenceBrokerTemplate().getPbKey());
                }
            } catch (Exception e) {
                LOG.error("Failed closing connection: " + e.getMessage(), e);
            }
        }
        return negotiationIds;
	}
	/* IU Customization Ends */

    /**
     * @param subAwardRequisitionerId  subAward Requisitioner ID
     * @return  A list of NegotiationIds searched by subAwardRequisitionerId
     */
    public Collection<Long> getNegotiationIdsByRequisitioner(String subAwardRequisitionerId) {
		Collection<Long> negotiationIds = new ArrayList<Long>();
		
		PersistenceBroker broker = null;
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        try {
            broker = getPersistenceBroker(false);
            conn = broker.serviceConnectionManager().getConnection();

            // UITSRA-3761
            String query = "select NEGOTIATION_ID from NEGOTIATION where \n" +
            					"ASSOCIATED_DOCUMENT_ID in (select SUBAWARD_CODE from SUBAWARD where REQUISITIONER_ID=?) \n" + 
            						"and NEGOTIATION_ASSC_TYPE_ID = \n" + 
            							"(select NEGOTIATION_ASSC_TYPE_ID from NEGOTIATION_ASSOCIATION_TYPE where NEGOTIATION_ASSC_TYPE_CODE = 'SWD')";            
            // End of UITSRA-3761
            
            stmt = conn.prepareStatement(query);
            stmt.setString(1, subAwardRequisitionerId);
            rs = stmt.executeQuery();
            
            try {
	            while (rs.next()) {
	            	negotiationIds.add(rs.getLong(1));
	            }
            } finally {
            	try { rs.close(); } catch (Exception ignore) { }
            }
        } catch (SQLException sqle) {
            LOG.error("SQLException: " + sqle.getMessage(), sqle);
            throw new WorkflowRuntimeException(sqle);
        } catch (LookupException le) {
            LOG.error("LookupException: " + le.getMessage(), le);
            throw new WorkflowRuntimeException(le);
        } finally {
            try {
            	if(stmt != null) {
            		stmt.close();
            	}
                if (broker != null) {
                    OjbFactoryUtils.releasePersistenceBroker(broker, this.getPersistenceBrokerTemplate().getPbKey());
                }
            } catch (Exception e) {
                LOG.error("Failed closing connection: " + e.getMessage(), e);
            }
        }
        return negotiationIds;
	}
	/* IU Customization Ends */

}
