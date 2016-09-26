package org.kuali.kra.negotiations.lookup;

import java.util.ArrayList;
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
import  org.kuali.coeus.sys.framework.util.CollectionUtils;
import org.kuali.coeus.sys.framework.service.KcServiceLocator;

import edu.iu.uits.kra.negotiations.lookup.IUNegotiationDaoOjb;


/**
 * Negotiation Lookup Helper Service
 */
public class IUNegotiationLookupableHelperServiceImpl extends NegotiationLookupableHelperServiceImpl {

	private static final long serialVersionUID = -7144337780492481726L;
    private static final String USER_ID = "userId";
   
    private NegotiationDao negotiationDao;
    private KcPersonService kcPersonService;


    @SuppressWarnings("unchecked")
    @Override
    public List<? extends BusinessObject> getSearchResults(Map<String, String> fieldValues) {
        super.setBackLocationDocFormKey(fieldValues);
        if (this.getParameters().containsKey(USER_ID)) {
            fieldValues.put("associatedNegotiable.piId", ((String[]) this.getParameters().get(USER_ID))[0]);
            fieldValues.put("negotiatorPersonId", ((String[]) this.getParameters().get(USER_ID))[0]);
        }
        
    	List<Long> ids = null;

        
        /* Begin IU Customization */
        //UITSRA-2543
        Map<String, String> formProps = new HashMap<String, String>();
        if (!StringUtils.isEmpty(fieldValues.get("sponsorAwardNumber"))
                && !StringUtils.equals("*", fieldValues.get("sponsorAwardNumber").trim())) {
            formProps.put("value", fieldValues.get("sponsorAwardNumber"));
            formProps.put("customAttributeId", getCustomAttributeId("All Negotiations", "SPON_AWD_ID"));
            ids = getCustomDataIds(formProps, null);
        }
        fieldValues.remove("sponsorAwardNumber");
        
        //UITSRA-2893, UITSRA-2894
        if (!StringUtils.isEmpty(fieldValues.get("gsTeam")) && !StringUtils.equals("*", fieldValues.get("gsTeam").trim())) {
            formProps.put("value", fieldValues.get("gsTeam"));
            formProps.put("customAttributeId", getCustomAttributeId("All Negotiations", "gsTeam"));
            ids = getCustomDataIds(formProps, ids);
        }
        
        if (!StringUtils.isEmpty(fieldValues.get("recordResidesWith")) && !StringUtils.equals("*", fieldValues.get("recordResidesWith").trim())) {
            formProps.put("value", fieldValues.get("recordResidesWith"));
            formProps.put("customAttributeId", getCustomAttributeId("All Negotiations", "recordLocation"));
            ids = getCustomDataIds(formProps, ids);
        }

        if (!StringUtils.isEmpty(fieldValues.get("accountId")) && !StringUtils.equals("*", fieldValues.get("accountId").trim())) {
            formProps.put("value", fieldValues.get("accountId"));
            formProps.put("customAttributeId", getCustomAttributeId("All Negotiations", "accountID"));
            ids = getCustomDataIds(formProps, ids);
        }
        fieldValues.remove("recordResidesWith");
        fieldValues.remove("gsTeam");
        fieldValues.remove("accountId");
        //End of UITSRA-2893, UITSRA-2894
        
        // UITSRA-4218
        if (!StringUtils.isEmpty(fieldValues.get("contractDate")) && !StringUtils.equals("*", fieldValues.get("contractDate").trim())) {
            formProps.put("value", fieldValues.get("contractDate"));
            formProps.put("customAttributeId", getCustomAttributeId("All Negotiations", "contractDate"));
            ids = getCustomDataIds(formProps, ids);
        }
        fieldValues.remove("contractDate");
        // End of UITSRA-4218
        
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
        					String regex = "[0-9]+";
        					String negotiationId = fieldValues.get("negotiationId");
        					if (negotiationId.matches(regex)) {
	        					if (!piNegotiationIds.contains(new Long(negotiationId))) {
	        						return new ArrayList<Negotiation>();
	        					}
        					}
        				}
        				else {
        					fieldValues.put("negotiationId", StringUtils.join(piNegotiationIds, '|'));
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
        					String regex = "[0-9]+";
        					String negotiationId = fieldValues.get("negotiationId");
        					if (negotiationId.matches(regex)) {
	        					if (!subAwardNegotiationIds.contains(new Long(negotiationId))) {
	        						return new ArrayList<Negotiation>();
	        					}
        					}
        				}
        				else {
        					fieldValues.put("negotiationId", StringUtils.join(subAwardNegotiationIds, '|'));
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
        
        if (!StringUtils.isEmpty(fieldValues.get("modification_id")) && !StringUtils.equals("*", fieldValues.get("modification_id").trim())) {
            formProps.put("value", fieldValues.get("modification_id"));
            formProps.put("customAttributeId", getCustomAttributeId("All Negotiations", "MOD_NUM"));
            ids = getCustomDataIds(formProps, ids);
        }
        if (!StringUtils.isEmpty(fieldValues.get("proposalDocID")) && !StringUtils.equals("*", fieldValues.get("proposalDocID").trim())) {
            formProps.put("value", fieldValues.get("proposalDocID"));
            formProps.put("customAttributeId", getCustomAttributeId("All Negotiations", "proposalDocID"));
            ids = getCustomDataIds(formProps, ids);
        }
        if (!StringUtils.isEmpty(fieldValues.get("ipid")) && !StringUtils.equals("*", fieldValues.get("ipid").trim())) {
            formProps.put("value", fieldValues.get("ipid"));
            formProps.put("customAttributeId", getCustomAttributeId("All Negotiations", "CSU_REF_NUM"));
            ids = getCustomDataIds(formProps, ids);
        }
        if (!StringUtils.isEmpty(fieldValues.get("proposalType")) && !StringUtils.equals("*", fieldValues.get("proposalType").trim())) {
            formProps.put("value", fieldValues.get("proposalType"));
            formProps.put("customAttributeId", getCustomAttributeId("Grant Services Negotiations", "proposalType"));
            ids = getCustomDataIds(formProps, ids);
        }
        fieldValues.remove("modification_id");
        fieldValues.remove("proposalDocID");
        fieldValues.remove("ipid");
        fieldValues.remove("proposalType");        
        // End of UITSRA-3761

        if (!StringUtils.isEmpty(fieldValues.get("ricroCleared")) ) {
            formProps.put("value", fieldValues.get("ricroCleared"));
            formProps.put("customAttributeId", getCustomAttributeId("SP Office Negotiations", "RICRO_CLEARED"));
            ids = getCustomDataIds(formProps, ids);
        }
        fieldValues.remove("ricroCleared");        
        
        if (!StringUtils.isEmpty(fieldValues.get("coiCleared")) ) {
            formProps.put("value", fieldValues.get("coiCleared"));
            formProps.put("customAttributeId", getCustomAttributeId("SP Office Negotiations", "COI_CLEARED"));
            ids = getCustomDataIds(formProps, ids);
        }
        fieldValues.remove("coiCleared");          

        if (!StringUtils.isEmpty(fieldValues.get("proposalActionType")) ) {
            formProps.put("value", fieldValues.get("proposalActionType"));
            formProps.put("customAttributeId", getCustomAttributeId("SP Office Negotiations", "PROP_ACTION_TYPE"));
            ids = getCustomDataIds(formProps, ids);
        }
        fieldValues.remove("proposalActionType");          

        if (!StringUtils.isEmpty(fieldValues.get("csuRefNum")) && !StringUtils.equals("*", fieldValues.get("csuRefNum").trim())) {
            formProps.put("value", fieldValues.get("csuRefNum"));
            formProps.put("customAttributeId", getCustomAttributeId("All Negotiations", "CSU_REF_NUM"));
            ids = getCustomDataIds(formProps, ids);
        }
        fieldValues.remove("csuRefNum");        
        
        
        /* CSU customization custom data arg search fix */
        fieldValues.put("negotiationCustomDataList.negotiationCustomDataId", StringUtils.join(ids, '|'));

        /* End IU Customization */
        
        // UITSRA-3138
        // In class LookupDaoOjb.java (method addCriteria()), a String data type is required in order to create the 
        // search criteria for a Negotiation Id wild card search. Currently negotiationId is Long rather than String, 
        // which is not consistent with other KC modules like Award, IP etc. The ideal fix is to change the Negotiation Id's
        // data type from Long to String, but it requires a major design change on the foundation side.
        List<Long> wildcardNegotiationIds = null;
        if (fieldValues.containsKey("negotiationId") && fieldValues.get("negotiationId").contains("*")) { 
        	wildcardNegotiationIds = new ArrayList<Long>(((IUNegotiationDaoOjb) getNegotiationDao()).getNegotiationIdsWithWildcard(fieldValues.get("negotiationId")));
        	fieldValues.put("negotiationId", StringUtils.join(wildcardNegotiationIds, '|'));
        }
                    
        List<Negotiation> searchResults = new ArrayList<Negotiation>();        
        CollectionIncomplete<Negotiation> limitedSearchResults;
        
        // UITSRA-3138
        if (wildcardNegotiationIds == null || wildcardNegotiationIds.size() != 0 ||
        		  piNegotiationIds == null || piNegotiationIds.size() != 0) {
        	// UITSRA-4033
        	limitedSearchResults = (CollectionIncomplete<Negotiation>) getNegotiationDao().getNegotiationResults(fieldValues);
        	searchResults.addAll(limitedSearchResults);

	        List defaultSortColumns = getDefaultSortColumns();
	        if (defaultSortColumns.size() > 0) {
                org.kuali.coeus.sys.framework.util.CollectionUtils.sort(searchResults, new BeanPropertyComparator(defaultSortColumns, true)); //UITSRA-4320
	            return new CollectionIncomplete<Negotiation>(searchResults, limitedSearchResults.getActualSizeIfTruncated());
	        }
	        return limitedSearchResults;
        }
        return searchResults;
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
    
    
    protected List<Long> getCustomDataIds(Map<String, String> formProps, List<Long> commonIds) {
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
}
