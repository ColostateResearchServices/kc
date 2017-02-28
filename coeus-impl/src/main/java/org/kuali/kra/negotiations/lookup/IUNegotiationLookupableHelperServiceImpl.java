package org.kuali.kra.negotiations.lookup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.coeus.common.framework.custom.attr.CustomAttribute;
import org.kuali.coeus.common.framework.person.KcPerson;
import org.kuali.kra.negotiations.bo.Negotiation;
import org.kuali.kra.negotiations.customdata.NegotiationCustomData;
import org.kuali.kra.negotiations.customdata.LookupableNegotiationCustomData;
import org.kuali.rice.kns.lookup.HtmlData.AnchorHtmlData;
import org.kuali.rice.kns.web.struts.form.LookupForm;
import org.kuali.rice.kns.web.ui.Column;
import org.kuali.rice.kns.web.ui.Field;
import org.kuali.rice.kns.web.ui.ResultRow;
import org.kuali.rice.kns.web.ui.Row;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.lookup.CollectionIncomplete;
import org.kuali.rice.krad.util.BeanPropertyComparator;
import org.kuali.coeus.common.framework.person.KcPersonService;
import org.kuali.coeus.sys.framework.service.KcServiceLocator;

import edu.iu.uits.kra.negotiations.lookup.IUNegotiationDaoOjb;


/**
 * Negotiation Lookup Helper Service
 */
public class IUNegotiationLookupableHelperServiceImpl extends NegotiationLookupableHelperServiceImpl {

	private static final long serialVersionUID = -7144337780492481726L;
    private static final String USER_ID = "userId";
    private boolean customDataSearchParamsExist =false;

    private NegotiationDao negotiationDao;
    private KcPersonService kcPersonService;


    @SuppressWarnings("unchecked")
    @Override
    public List<? extends BusinessObject> getSearchResults(Map<String, String> fieldValues) {
        super.setBackLocationDocFormKey(fieldValues);

        String regex = "[0-9]+";

        if (this.getParameters().containsKey(USER_ID)) {
            fieldValues.put("associatedNegotiable.piId", ((String[]) this.getParameters().get(USER_ID))[0]);
            fieldValues.put("negotiatorPersonId", ((String[]) this.getParameters().get(USER_ID))[0]);
        }

        List<Long> customDataNegotiationIds = getNegotiationIdFromCustomDataParams(fieldValues);

        // if we specified customdata search parameters, but found no negotiations with those params, search is done, no results
        if (customDataSearchParamsExist() && (customDataNegotiationIds==null || customDataNegotiationIds.isEmpty()) ) {
            return new ArrayList<Negotiation>();
        }


        // UITSRA-3190 -Add Person Lookup capability to Search screens
        List<Long> piNegotiationIds = null;
        if (fieldValues.containsKey("associatedNegotiable.principalInvestigatorUserName")) {
        	String piUserName = fieldValues.get("associatedNegotiable.principalInvestigatorUserName");
            if (StringUtils.isNotBlank(piUserName)) {
            	// UITSRA-3477
            	if (StringUtils.contains(piUserName, "*")) {
            		piUserName = StringUtils.remove(piUserName, '*');
            	}
            	// End of UITSRA-3477
            	
        		KcPerson person = getKcPersonService().getKcPersonByUserName(piUserName);
        		if (person != null && person.getPersonId() != null) {
        			piNegotiationIds = new ArrayList<Long>(((IUNegotiationDaoOjb) getNegotiationDao()).getNegotiationIdsByPI(person.getPersonId()));
        			
        			if (piNegotiationIds.size() > 0) {
        				if (fieldValues.containsKey("negotiationId") && StringUtils.isNotBlank(fieldValues.get("negotiationId"))) {
        					String negotiationId = fieldValues.get("negotiationId");
        					if (negotiationId.matches(regex)) {
	        					if (!piNegotiationIds.contains(new Long(negotiationId))) {
	        						return new ArrayList<Negotiation>();
	        					}
        					}
        				}
        			}
        			else {
        				fieldValues.put("associatedDocumentId", "Invalid PI Id");
        			}
        		}
        		else {
        			fieldValues.put("associatedDocumentId", "Invalid PI Id");
        		}
            }
            fieldValues.remove("associatedNegotiable.principalInvestigatorUserName");
        }
        // End of UITSRA-3190
        
        // UITSRA-3191 - Change Negotiator Lookup field in Negotiation search options 
        KcPerson negotiator = null;
        if (fieldValues.containsKey("negotiatorUserName") && StringUtils.isNotBlank(fieldValues.get("negotiatorUserName")) ) {
        	negotiator = getKcPersonService().getKcPersonByUserName(fieldValues.get("negotiatorUserName"));
	        if (negotiator != null && StringUtils.isNotBlank(negotiator.getPersonId())) {
	        	fieldValues.put("negotiatorPersonId", negotiator.getPersonId());
	        }
	        else {
	        	fieldValues.put("negotiatorPersonId", "Invalid Negotiator Person Id");
	        }
	        fieldValues.remove("negotiatorUserName");
        }
        // End of UITSRA-3191

        
        // UITSRA-3761 - Update Negotiation Search Options and Results
        List<Long> subAwardNegotiationIds = null;
        if (fieldValues.containsKey("associatedNegotiable.requisitionerUserName")) {
        	String requisitionerUserName = fieldValues.get("associatedNegotiable.requisitionerUserName");
            if (StringUtils.isNotBlank(requisitionerUserName)) {
            	if (StringUtils.contains(requisitionerUserName, "*")) {
            		requisitionerUserName = StringUtils.remove(requisitionerUserName, '*');
            	}
            	
        		KcPerson person = getKcPersonService().getKcPersonByUserName(requisitionerUserName);
        		if (person != null && person.getPersonId() != null) {
        			subAwardNegotiationIds = new ArrayList<Long>(((IUNegotiationDaoOjb) getNegotiationDao()).getNegotiationIdsByRequisitioner(person.getPersonId()));
        			
        			if (subAwardNegotiationIds.size() > 0) {
        				if (fieldValues.containsKey("negotiationId") && StringUtils.isNotBlank(fieldValues.get("negotiationId"))) {
        					String negotiationId = fieldValues.get("negotiationId");
        					if (negotiationId.matches(regex)) {
	        					if (!subAwardNegotiationIds.contains(new Long(negotiationId))) {
	        						return new ArrayList<Negotiation>();
	        					}
        					}
        				}
        			}
        			else {
        				fieldValues.put("associatedDocumentId", "Invalid PI Id");
        			}
        		}
        		else {
        			fieldValues.put("associatedDocumentId", "Invalid PI Id");
        		}
            }
            fieldValues.remove("associatedNegotiable.requisitionerUserName");
            fieldValues.remove("associatedNegotiable.subAwardRequisitionerId");
        }


        /* End IU Customization */
        
        // UITSRA-3138
        // In class LookupDaoOjb.java (method addCriteria()), a String data type is required in order to create the 
        // search criteria for a Negotiation Id wild card search. Currently negotiationId is Long rather than String, 
        // which is not consistent with other KC modules like Award, IP etc. The ideal fix is to change the Negotiation Id's
        // data type from Long to String, but it requires a major design change on the foundation side.
        List<Long> wildcardNegotiationIds = null;
        if (fieldValues.containsKey("negotiationId") && fieldValues.get("negotiationId").contains("*")) { 
        	wildcardNegotiationIds = new ArrayList<Long>(((IUNegotiationDaoOjb) getNegotiationDao()).getNegotiationIdsWithWildcard(fieldValues.get("negotiationId")));
        }

        List<Long> userSpecifiedIds = null;
        if (!fieldValues.get("negotiationId").isEmpty()) {
            userSpecifiedIds = new ArrayList<>();
            List<String> list = Arrays.asList(fieldValues.get("negotiationId").split("|"));
            for (String value : list) {
                if (value.matches(regex)) { userSpecifiedIds.add(new Long(value)); }
            }
        }

        List<Long> allCommonNegotiationIds = null;
        if (wildcardNegotiationIds != null) { allCommonNegotiationIds = wildcardNegotiationIds;}
        if (piNegotiationIds != null && allCommonNegotiationIds == null) {
            allCommonNegotiationIds = piNegotiationIds;
        } else if (piNegotiationIds != null && allCommonNegotiationIds != null) {
            allCommonNegotiationIds.retainAll(piNegotiationIds);
        }
        if (customDataNegotiationIds != null && allCommonNegotiationIds == null) {
            allCommonNegotiationIds = customDataNegotiationIds;
        } else if (customDataNegotiationIds != null && allCommonNegotiationIds != null) {
            allCommonNegotiationIds.retainAll(customDataNegotiationIds);
        }
        if (subAwardNegotiationIds != null && allCommonNegotiationIds == null) {
            allCommonNegotiationIds = subAwardNegotiationIds;
        } else if (subAwardNegotiationIds != null && allCommonNegotiationIds != null) {
            allCommonNegotiationIds.retainAll(subAwardNegotiationIds);
        }
        if (userSpecifiedIds != null && allCommonNegotiationIds != null) {
            allCommonNegotiationIds.retainAll(userSpecifiedIds);
        }

        if (allCommonNegotiationIds != null && allCommonNegotiationIds.isEmpty()) {
            return new ArrayList<Negotiation>();
        }  else if (allCommonNegotiationIds != null) {
            fieldValues.put("negotiationId", StringUtils.join(allCommonNegotiationIds, '|'));
        }


                    
        List<Negotiation> searchResults = new ArrayList<Negotiation>();        
        CollectionIncomplete<Negotiation> limitedSearchResults;

        limitedSearchResults = (CollectionIncomplete<Negotiation>) getNegotiationDao().getNegotiationResults(fieldValues);
        searchResults.addAll(limitedSearchResults);

	    List defaultSortColumns = getDefaultSortColumns();
	    if (defaultSortColumns.size() > 0) {
                org.kuali.coeus.sys.framework.util.CollectionUtils.sort(searchResults, new BeanPropertyComparator(defaultSortColumns, true)); //UITSRA-4320
	            return new CollectionIncomplete<Negotiation>(searchResults, limitedSearchResults.getActualSizeIfTruncated());
	    }
	        return limitedSearchResults;
    }

    /**
     * @see org.kuali.rice.kns.lookup.AbstractLookupableHelperServiceImpl#getRows()
     */
    @Override
    public List<Row> getRows() {
        List<Row> rows =  super.getRows();
        for (Row row : rows) {
            for (Field field : row.getFields()) {
                if (field.getPropertyName().equals("negotiatorUserName")) {
                    field.setFieldConversions("principalName:negotiatorUserName,principalId:negotiatorPersonId");
                }
                if (field.getPropertyName().equals("associatedNegotiable.principalInvestigatorUserName")) {
                    field.setFieldConversions("principalName:associatedNegotiable.principalInvestigatorUserName,principalId:associatedNegotiable.principalInvestigatorPersonId");
                }
                if (field.getPropertyName().equals("associatedNegotiable.requisitionerUserName")) {
                    field.setFieldConversions("principalName:associatedNegotiable.requisitionerUserName,principalId:associatedNegotiable.subAwardRequisitionerId");
                }                
            }
        }
        return rows;
    }
    
    public KcPersonService getKcPersonService() {
	    if (this.kcPersonService == null) {
	        this.kcPersonService = KcServiceLocator.getService(KcPersonService.class);
	    }
	    return this.kcPersonService;
    }
    
    /* Begin IU Customization */    
    public String getCustomAttributeId(String groupName, String attributeName) {
        Map<String, String> fieldValues = new HashMap<String, String>();
        fieldValues.put("groupName", groupName);
        fieldValues.put("name", attributeName);
        List<CustomAttribute> customAttributes = (List<CustomAttribute>) getBusinessObjectService().findMatching(CustomAttribute.class, fieldValues);
        if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(customAttributes)) {
            return customAttributes.get(0).getId().toString();
        }
        else {
            return null;
        }
    }
    
    /**
     * Call's the super class's performLookup function and edits the URLs for the unit name, unit number, sponsor name, subAwardOrganization name, and pi name.
     * @see org.kuali.kra.lookup.KraLookupableHelperServiceImpl#performLookup(LookupForm, Collection, boolean)
     */
    @Override
    public Collection performLookup(LookupForm lookupForm, Collection resultTable, boolean bounded) {
        final String leadUnitName = "associatedNegotiable.leadUnitName";
        final String leadUnitNumber = "associatedNegotiable.leadUnitNumber";
        final String sponsorName = "associatedNegotiable.sponsorName";
        final String piName = "associatedNegotiable.piName";
        final String subAwardOrganizationName = "associatedNegotiable.subAwardOrganizationName";
                
        Collection lookupStuff = super.performLookup(lookupForm, resultTable, bounded);
        Iterator i = resultTable.iterator();
        while (i.hasNext()) {
            ResultRow row = (ResultRow) i.next();
            for (Column column : row.getColumns()) {
                //the Subaward Organization name, unit name, pi Name and sponsor name don't need to generate links.
                if (StringUtils.equalsIgnoreCase(column.getPropertyName(), leadUnitName) 
                        || StringUtils.equalsIgnoreCase(column.getPropertyName(), sponsorName)
                        || StringUtils.equalsIgnoreCase(column.getPropertyName(), subAwardOrganizationName)
                        || StringUtils.equalsIgnoreCase(column.getPropertyName(), piName)) {
                    column.setPropertyURL("");
                    for (AnchorHtmlData data : column.getColumnAnchors()) {
                        if (data != null) {
                            data.setHref("");
                        }
                    }
                }
                if (StringUtils.equalsIgnoreCase(column.getPropertyName(), leadUnitNumber)){
                    String unitNumber = column.getPropertyValue();
                    //String newUrl = "http://127.0.0.1:8080/kc-dev/kr/inquiry.do?businessObjectClassName=org.kuali.kra.bo.Unit&unitNumber=" + unitNumber + "&methodToCall=start";
                    String newUrl = "inquiry.do?businessObjectClassName=org.kuali.kra.bo.Unit&unitNumber=" + unitNumber + "&methodToCall=start";
                    column.setPropertyURL(newUrl);
                    for (AnchorHtmlData data : column.getColumnAnchors()) {
                        if (data != null) {
                            data.setHref(newUrl);
                        }
                    }
                }
            }
        }
        return lookupStuff;
    }
    /* End IU Customization */
    
    
    protected List<Long> getCustomDataIdsOld(Map<String, String> formProps, List<Long> commonIds) {
        	List<Long> ids = null;
            
            // UITSRA-3138
            Collection<NegotiationCustomData> customDatas = getLookupService().findCollectionBySearchUnbounded(NegotiationCustomData.class, formProps);
            if (!customDatas.isEmpty()) {
            	ids = new ArrayList<Long>();
                for (NegotiationCustomData customData : customDatas) {
                    ids.add(customData.getNegotiationCustomDataId());
                }            
            }
            
            if (commonIds != null && ids !=null ) {
               ids.retainAll(commonIds);
            }
            return ids;        
    }

    protected List<Long> getNegotiationIdFromCustDataItem(String item, String groupName, String attributeName, Map<String, String> fieldValues, List<Long> commonIds) {
        Map<String, String> formProps = new HashMap<String, String>();
        List<Long> ids = null;

        if (!StringUtils.isEmpty(fieldValues.get(item)) && !StringUtils.equals("*", fieldValues.get(item).trim())) {
            formProps.put("value", fieldValues.get(item));
            formProps.put("customAttributeId", getCustomAttributeId(groupName, attributeName));
            ids = getCustomDataIds(formProps, commonIds);

            if (commonIds != null && ids !=null && !commonIds.isEmpty() ) {
                ids.retainAll(commonIds);
            }
            setCustomDataSearchParamsExist(true);
        } else {
            ids = commonIds;
        }
        fieldValues.remove(item);
        return ids;
    }

    protected List<Long> getCustomDataIds(Map<String, String> formProps,  List<Long> commonIds) {
        List<Long> ids = null;

        Collection<LookupableNegotiationCustomData> customDatas = getLookupService().findCollectionBySearchUnbounded(LookupableNegotiationCustomData.class, formProps);
        if (!customDatas.isEmpty()) {
            ids = new ArrayList<Long>();
            for (LookupableNegotiationCustomData customData : customDatas) {
                ids.add(customData.getNegotiationId());
            }
        }

        return ids;
    }

    protected List<Long> getNegotiationIdFromCustomDataParams(Map<String, String> fieldValues)
    {
        List<Long> ids = null;
        Map<String, String> formProps = new HashMap<String, String>();

        ids = getNegotiationIdFromCustDataItem("sponsorAwardNumber", "All Negotiations", "SPON_AWD_ID", fieldValues, ids);
        ids = getNegotiationIdFromCustDataItem("gsTeam", "All Negotiations", "SP_TEAM", fieldValues, ids);
        ids = getNegotiationIdFromCustDataItem("csuRefNum", "All Negotiations", "CSU_REF_NUM", fieldValues, ids);
        ids = getNegotiationIdFromCustDataItem("recordResidesWith", "All Negotiations", "RECORD_RESIDES_WITH", fieldValues, ids);
        ids = getNegotiationIdFromCustDataItem("accountId", "All Negotiations", "accountId", fieldValues, ids);
        ids = getNegotiationIdFromCustDataItem("contractDate", "All Negotiations", "contractDate", fieldValues, ids);
        ids = getNegotiationIdFromCustDataItem("modification_id", "All Negotiations", "MOD_NUM", fieldValues, ids);
        ids = getNegotiationIdFromCustDataItem("proposalDocID", "All Negotiations", "proposalDocID", fieldValues, ids);
        ids = getNegotiationIdFromCustDataItem("ipid", "All Negotiations", "CSU_REF_NUM", fieldValues, ids);


        ids = getNegotiationIdFromCustDataItem("ricroCleared", "SP Office Negotiations", "RICRO_CLEARED", fieldValues, ids);
        ids = getNegotiationIdFromCustDataItem("coiCleared", "SP Office Negotiations", "COI_CLEARED", fieldValues, ids);
        ids = getNegotiationIdFromCustDataItem("proposalActionType", "SP Office Negotiations", "PROP_ACTION_TYPE", fieldValues, ids);


        return ids;
    }

    protected boolean customDataSearchParamsExist() {return this.customDataSearchParamsExist;       }
    protected void setCustomDataSearchParamsExist(boolean customDataSearchParamsExist) { this.customDataSearchParamsExist = customDataSearchParamsExist; }




}
