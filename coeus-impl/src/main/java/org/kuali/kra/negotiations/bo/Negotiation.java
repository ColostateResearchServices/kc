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
package org.kuali.kra.negotiations.bo;

import org.apache.commons.lang3.StringUtils;
import org.kuali.coeus.common.framework.person.KcPerson;
import org.kuali.coeus.common.framework.person.KcPersonService;
import org.kuali.coeus.common.permissions.impl.PermissionableKeys;
import org.kuali.coeus.common.framework.auth.perm.Permissionable;
import org.kuali.coeus.sys.framework.model.KcPersistableBusinessObjectBase;
import org.kuali.coeus.sys.framework.service.KcServiceLocator;
import org.kuali.kra.infrastructure.Constants;
import org.kuali.kra.infrastructure.RoleConstants;
import org.kuali.kra.negotiations.customdata.NegotiationCustomData;
import org.kuali.kra.negotiations.document.NegotiationDocument;
import org.kuali.kra.negotiations.notifications.NegotiationNotification;
import org.kuali.kra.negotiations.service.NegotiationService;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.coeus.common.framework.custom.attr.CustomAttribute;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * 
 * This class handles the negotiation BO.
 */
public class Negotiation extends KcPersistableBusinessObjectBase implements Permissionable {

    private static final long MILLISECS_PER_DAY = 24 * 60 * 60 * 1000;


    private transient BusinessObjectService businessObjectService;
    private transient KcPersonService kcPersonService;


    private static final long serialVersionUID = 2529772854773433195L;

    private Long negotiationId;
    private String documentNumber;
    private Long negotiationStatusId;
    private Long negotiationAgreementTypeId;
    private Long negotiationAssociationTypeId;
    private String negotiatorPersonId;
    private String negotiatorName;
    private Date negotiationStartDate;
    private Date negotiationEndDate;
    private Date anticipatedAwardDate;
    private String documentFolder;
    private String allAttachments;

    // transient
    private String negotiatorUserName;

    private NegotiationUnassociatedDetail unAssociatedDetail;
    private List<NegotiationCustomData> negotiationCustomDataList;
    private Negotiable associatedDocument;

    private NegotiationDocument negotiationDocument;

    /**
     * Long awardId - award String proposalNumber -developmentProposal Long proposalId - institutionalProposal
     */
    private String associatedDocumentId;

    private NegotiationStatus negotiationStatus;
    private NegotiationAgreementType negotiationAgreementType;
    private NegotiationAssociationType negotiationAssociationType;

    private List<NegotiationActivity> activities;

    private int printindex;
    private boolean printAll = true;
    private Long oldNegotiationAssociationTypeId;

    /* IU Customization Starts */
    // UITSRA-2543
    private String sponsorAwardNumber;
    private static final String NEGOTIATION_SPONSOR_AWARD_NUMBER_CUSTOM_ATTR_LABEL="Sponsor Award ID";
    private Long SPONSOR_AWARD_NUMBER_CUSTOM_ATTRIBUTE_ID = 110L;

    // UITSRA-2893, UITSRA-2894
    private transient String recordResidesWith;
    private transient String gsTeam;
    private transient String accountId;
    private static final String NEGOTIATION_GS_TEAM_CUSTOM_ATTR_LABEL="Grant Services Team";
    private Long GS_TEAM_CUSTOM_ATTRIBUTE_ID = 107L;
    private static final String NEGOTIATION_RECORD_RESIDES_WITH_CUSTOM_ATTR_LABEL="Record Resides With";
    private Long RECORD_RESIDES_WITH_CUSTOM_ATTRIBUTE_ID = 106L;
    /* IU Customization Ends */

    private List<NegotiationNotification> negotiationNotifications;

    // UITSRA-3761
    private transient String modification_id;
    private transient String proposalDocID;
    private transient String proposalType;
    private static final String NEGOTIATION_MODIFICATION_ID_CUSTOM_ATTR_LABEL="Modification ID";
    private Long MODIFICATION_ID_CUSTOM_ATTRIBUTE_ID = 105L;

    // UITSRA-4133
    private static final String NEGOTIATION_ACCOUNT_ID_CUSTOM_ATTR_LABEL="Account ID(s)";
    private Long ACCOUNT_ID_CUSTOM_ATTRIBUTE_ID = 112L;

    // UITSRA-4218
    private transient String agreementDate;
    private static final String NEGOTIATION_AGREEMENT_DATE_CUSTOM_ATTR_LABEL="Fully Executed Agreement Date (Format MM/DD/YYYY)";
    private Long AGREEMENT_DATE_CUSTOM_ATTRIBUTE_ID = 125L;



    // CSU enhancements
    private transient String csuRefNum;
    private transient boolean ricroCleared;
    private transient boolean coiCleared;
    private transient String proposalActionType;

    public int getPrintindex() {
        return printindex;
    }

    public void setPrintindex(int printindex) {
        this.printindex = printindex;
    }

    public Negotiation() {
        super();
        activities = new ArrayList<NegotiationActivity>();
        negotiationCustomDataList = new ArrayList<NegotiationCustomData>();
        negotiationNotifications = new ArrayList<NegotiationNotification>();
    }

    public Integer getNegotiationAge() {
        if (getNegotiationStartDate() == null) {
            return null;
        }
        else {
            long start = getNegotiationStartDate().getTime();
            long end = 0L;
            if (getNegotiationEndDate() == null) {
                end = Calendar.getInstance().getTimeInMillis();
            }
            else {
                end = getNegotiationEndDate().getTime();
            }

            return new Long((end - start) / MILLISECS_PER_DAY).intValue();
        }
    }

    public String getAllAttachments() {
        return allAttachments;
    }

    public void setAllAttachments(String allAttachments) {
        this.allAttachments = allAttachments;
    }

    public Long getNegotiationId() {
        return negotiationId;
    }


    public void setNegotiationId(Long negotiationId) {
        this.negotiationId = negotiationId;
    }


    public String getDocumentNumber() {
        return documentNumber;
    }


    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }


    public Long getNegotiationStatusId() {
        return negotiationStatusId;
    }


    public void setNegotiationStatusId(Long negotiationStatusId) {
        this.negotiationStatusId = negotiationStatusId;
    }


    public Long getNegotiationAgreementTypeId() {
        return negotiationAgreementTypeId;
    }


    public void setNegotiationAgreementTypeId(Long negotiationAgreementTypeId) {
        this.negotiationAgreementTypeId = negotiationAgreementTypeId;
    }


    public Long getNegotiationAssociationTypeId() {
        return negotiationAssociationTypeId;
    }


    public void setNegotiationAssociationTypeId(Long negotiationAssociationTypeId) {
        this.negotiationAssociationTypeId = negotiationAssociationTypeId;
    }


    public String getNegotiatorPersonId() {
        return negotiatorPersonId;
    }


    public void setNegotiatorPersonId(String negotiatorPersonId) {
        this.negotiatorPersonId = negotiatorPersonId;
        if (getNegotiator() != null) {
            setNegotiatorName(getNegotiator().getFullName());
        }
    }

    public KcPerson getNegotiator() {
        if (this.getNegotiatorPersonId() == null) {
            return null;
        }
        else {
            try {
                return getKcPersonService().getKcPersonByPersonId(this.getNegotiatorPersonId());
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
    }

    public String getNegotiatorUserName() {
        KcPerson negotiator = getNegotiator();
        if (negotiator == null) {
            return negotiatorUserName;
        }
        else {
            return negotiator.getUserName();
        }
    }

    public void setNegotiatorUserName(String negotiatorUserName) {
        this.negotiatorUserName = negotiatorUserName;
        KcPerson negotiator = null;
        try {
            negotiator = getKcPersonService().getKcPersonByUserName(negotiatorUserName);
        }
        catch (IllegalArgumentException e) {
            // invalid username, will be caught by validation routines
        }
        if (negotiator != null) {
            setNegotiatorPersonId(negotiator.getPersonId());
        }
        else {
            setNegotiatorPersonId(null);
        }
    }

    public Date getNegotiationStartDate() {
        if (negotiationStartDate == null || negotiationStartDate.equals(""))
        {
            Calendar now = Calendar.getInstance();
            setNegotiationStartDate (new java.sql.Date (now.get(Calendar.YEAR)-1900, now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH)));
        }
        return negotiationStartDate;
    }


    public void setNegotiationStartDate(Date negotiationStartDate) {
        this.negotiationStartDate = negotiationStartDate;
    }


    public Date getNegotiationEndDate() {
        return negotiationEndDate;
    }


    public void setNegotiationEndDate(Date negotiationEndDate) {
        this.negotiationEndDate = negotiationEndDate;
    }


    public Date getAnticipatedAwardDate() {
        return anticipatedAwardDate;
    }


    public void setAnticipatedAwardDate(Date anticipatedAwardDate) {
        this.anticipatedAwardDate = anticipatedAwardDate;
    }


    public String getDocumentFolder() {
        return documentFolder;
    }


    public void setDocumentFolder(String documentFolder) {
        this.documentFolder = documentFolder;
    }


    public String getAssociatedDocumentId() {
        return associatedDocumentId;
    }


    public void setAssociatedDocumentId(String associatedDocumentId) {
        this.associatedDocumentId = associatedDocumentId;
    }


    public NegotiationStatus getNegotiationStatus() {
        return negotiationStatus;
    }


    public void setNegotiationStatus(NegotiationStatus negotiationStatus) {
        this.negotiationStatus = negotiationStatus;
    }


    public NegotiationAgreementType getNegotiationAgreementType() {
        return negotiationAgreementType;
    }


    public void setNegotiationAgreementType(NegotiationAgreementType negotiationAgreementType) {
        this.negotiationAgreementType = negotiationAgreementType;
    }


    public NegotiationAssociationType getNegotiationAssociationType() {
        return negotiationAssociationType;
    }


    public void setNegotiationAssociationType(NegotiationAssociationType negotiationAssociationType) {
        this.negotiationAssociationType = negotiationAssociationType;
    }

    public NegotiationUnassociatedDetail getUnAssociatedDetail() {
        if (unAssociatedDetail == null) {
            unAssociatedDetail = getNegotiationService().findAndLoadNegotiationUnassociatedDetail(this);
        }
        return unAssociatedDetail;
    }

    public void setUnAssociatedDetail(NegotiationUnassociatedDetail unAssociatedDetail) {
        this.unAssociatedDetail = unAssociatedDetail;
    }

    public List<NegotiationActivity> getActivities() {
        return activities;
    }

    public void setActivities(List<NegotiationActivity> activities) {
        this.activities = activities;
    }

    @Override
    public String getDocumentNumberForPermission() {
        return this.negotiationId != null ? this.negotiationId.toString() : null;
    }

    @Override
    public String getDocumentKey() {
        return PermissionableKeys.NEGOTIATION_KEY;
    }

    @Override
    public List<String> getRoleNames() {
        List<String> roleNames = new ArrayList<String>();
        roleNames.add(RoleConstants.NEGOTIATION_COI);
        roleNames.add(RoleConstants.NEGOTIATION_KP);
        roleNames.add(RoleConstants.NEGOTIATION_NEGOTIATION_ADMINISTRATOR);
        roleNames.add(RoleConstants.NEGOTIATION_NEGOTIATOR);
        roleNames.add(RoleConstants.NEGOTIATION_PI);
        return roleNames;
    }

    @Override
    public String getNamespace() {
        return Constants.MODULE_NAMESPACE_NEGOTIATION;
    }

    @Override
    public String getLeadUnitNumber() {
        Negotiable bo = getAssociatedDocument();
        if (bo != null) {
            ((BusinessObject) bo).refresh();
            return bo.getLeadUnitNumber();
        }
        else {
            return "";
        }
    }

    public Negotiable getAssociatedNegotiable() {
        return getNegotiationService().getAssociatedObject(this);
    }

    private NegotiationService getNegotiationService() {
        return KcServiceLocator.getService(NegotiationService.class);
    }

    @Override
    public String getDocumentRoleTypeCode() {
        return RoleConstants.NEGOTIATION_ROLE_TYPE;
    }

    @Override
    public void populateAdditionalQualifiedRoleAttributes(Map<String, String> qualifiedRoleAttributes) {
        qualifiedRoleAttributes.put(KimConstants.AttributeConstants.DOCUMENT_TYPE_NAME, this.getDocumentKey());
    }

    /**
     * Gets the negotiationCustomDataList attribute.
     * 
     * @return Returns the negotiationCustomDataList.
     */
    public List<NegotiationCustomData> getNegotiationCustomDataList() {
        return negotiationCustomDataList;
    }

    /**
     * Sets the negotiationCustomDataList attribute value.
     * 
     * @param negotiationCustomDataList The negotiationCustomDataList to set.
     */
    public void setNegotiationCustomDataList(List<NegotiationCustomData> negotiationCustomDataList) {
        this.negotiationCustomDataList = negotiationCustomDataList;
    }


    public NegotiationDocument getDocument() {
        return negotiationDocument;
    }

    public NegotiationDocument getNegotiationDocument() {
        return negotiationDocument;
    }

    public void setNegotiationDocument(NegotiationDocument negotiationDocument) {
        this.negotiationDocument = negotiationDocument;
    }

    public Negotiable getAssociatedDocument() {
        if (associatedDocument == null
                || !StringUtils.equals(associatedDocument.getAssociatedDocumentId(), getAssociatedDocumentId())) {
            associatedDocument = getNegotiationService().getAssociatedObject(this);
        }
        return associatedDocument;
    }

    public void setAssociatedDocument(Negotiable associatedDocument) {
        this.associatedDocument = associatedDocument;
    }

    public String getNegotiatorName() {
        return negotiatorName;
    }

    public void setNegotiatorName(String negotiatorName) {
        this.negotiatorName = negotiatorName;
    }

    public boolean isPrintAll() {
        return printAll;
    }

    public void setPrintAll(boolean printAll) {
        this.printAll = printAll;
    }

    public Long getOldNegotiationAssociationTypeId() {
        return oldNegotiationAssociationTypeId;
    }

    public void setOldNegotiationAssociationTypeId(Long oldNegotiationAssociationTypeId) {
        this.oldNegotiationAssociationTypeId = oldNegotiationAssociationTypeId;
    }

    public List<NegotiationNotification> getNegotiationNotifications() {
        return negotiationNotifications;
    }

    public void setNegotiationNotifications(List<NegotiationNotification> negotiationNotifications) {
        this.negotiationNotifications = negotiationNotifications;
    }

    public void addNotification(NegotiationNotification negotiationNotification) {
        getNegotiationNotifications().add(negotiationNotification);        
    }

    /* IU Customization Starts */
    /**
     * @return the sponsorAwardNumber
     */
    public String getSponsorAwardNumber() {
        // UITSRA-2543
        return getCustomDataValue(NEGOTIATION_SPONSOR_AWARD_NUMBER_CUSTOM_ATTR_LABEL, SPONSOR_AWARD_NUMBER_CUSTOM_ATTRIBUTE_ID);
    }

    /**
     * @param sponsorAwardNumber the sponsorAwardNumber to set
     */
    public void setSponsorAwardNumber(String sponsorAwardNumber) {
        this.sponsorAwardNumber = sponsorAwardNumber;
    }

    /**
     * @return the recordResidesWith
     */
    public String getRecordResidesWith() {
        Long attrId = Long.valueOf(getCustomAttributeId("All Negotiations", "RECORD_RESIDES_WITH"));
        this.recordResidesWith = getCustomDataValueByAttrId(attrId);
        return this.recordResidesWith;
    }


    /**
     * @param recordResidesWith the recordResidesWith to set
     */
    public void setRecordResidesWith(String recordResidesWith) {
        this.recordResidesWith = recordResidesWith;
    }

    /**
     * @return the gsTeam
     */
    public String getGsTeam() {
        Long attrId = Long.valueOf(getCustomAttributeId("All Negotiations", "SP_TEAM"));
        this.gsTeam = getCustomDataValueByAttrId(attrId);
        return this.gsTeam;
    }

    /**
     * @param gsTeam the gsTeam to set
     */
    public void setGsTeam(String gsTeam) {
        this.gsTeam = gsTeam;
    }

    /**
     * @return the accountId
     */
    public String getAccountId() {
        return getCustomDataValue(NEGOTIATION_ACCOUNT_ID_CUSTOM_ATTR_LABEL, ACCOUNT_ID_CUSTOM_ATTRIBUTE_ID);
    }

    /**
     * @param accountId the accountId to set
     */
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    /**
     * @return the modificationID
     */
    public String getModification_id() {
        Long attrId = Long.valueOf(getCustomAttributeId("All Negotiations", "MOD_NUM"));
        this.modification_id = getCustomDataValueByAttrId(attrId);
        return this.modification_id;
    }

    /**
     * @param modification_id the modificationID to set
     */
    public void setModification_id(String modification_id) {
        this.modification_id = modification_id;
    }

    /**
     * @return the ipid
     */
    public String getCsuRefNum() {
        Long attrId = Long.valueOf(getCustomAttributeId("All Negotiations", "CSU_REF_NUM"));
        this.csuRefNum = getCustomDataValueByAttrId(attrId);
        return this.csuRefNum;
    }

    /**
     * @param csuRefNum the csuRefNum to set
     */
    public void setCsuRefNum(String csuRefNum) {

        this.csuRefNum = csuRefNum;
    }

    /**
     * @return the proposalDocID
     */
    public String getProposalDocID() {
        return proposalDocID;
    }

    /**
     * @param proposalDocID the proposalDocID to set
     */
    public void setProposalDocID(String proposalDocID) {
        this.proposalDocID = proposalDocID;
    }

    /**
     * @return the proposalType
     */
    public String getProposalType() {
        return proposalType;
    }

    /**
     * @param proposalType the proposalType to set
     */
    public void setProposalType(String proposalType) {
        this.proposalType = proposalType;
    }

	/* IU Customization Ends */

    /**
     * @return the agreementDate
     */
    public String getAgreementDate() {
        // UITSRA-4218
        return getCustomDataValue(NEGOTIATION_AGREEMENT_DATE_CUSTOM_ATTR_LABEL, AGREEMENT_DATE_CUSTOM_ATTRIBUTE_ID);
    }

    public String getCustomDataValue(String customAttributeLabel, long customAttributeId) {
        for (NegotiationCustomData customData : negotiationCustomDataList) {
            if (customData.getCustomAttribute() != null) {
                if (customData.getCustomAttribute().getLabel().equalsIgnoreCase(customAttributeLabel)) {
                    return customData.getValue();
                }
            }
            if (customData.getCustomAttributeId().equals(customAttributeId)) {
                return customData.getValue();
            }
        }
        return "";
    }

    /**
     * @param agreementDate the agreementDate to set
     */
    public void setAgreementDate(String agreementDate) {
        this.agreementDate = agreementDate;
    }


    public boolean isCoiCleared() {
        Long coiClearedCustAttrId = Long.valueOf(getCustomAttributeId("SP Office Negotiations", "COI_CLEARED"));
        String coiClearedString = getCustomDataValueByAttrId(coiClearedCustAttrId);
        this.coiCleared = StringUtils.equalsIgnoreCase("Y",coiClearedString);

        return coiCleared;
    }

    public void setCoiCleared(boolean coiCleared) {
        this.coiCleared = coiCleared;
    }


    public boolean isRicroCleared() {
        Long ricroCustAttrId = Long.valueOf(getCustomAttributeId("SP Office Negotiations", "RICRO_CLEARED"));
        String ricroClearedString = getCustomDataValueByAttrId(ricroCustAttrId);
        this.ricroCleared = StringUtils.equalsIgnoreCase("Y",ricroClearedString);

        return ricroCleared;
    }

    public void setRicroCleared(boolean ricroCleared) {
        this.ricroCleared = ricroCleared;
    }


    public String getCustomDataValueByAttrId(long customAttributeId) {
        for (NegotiationCustomData customData : negotiationCustomDataList) {
            if (customData.getCustomAttributeId().equals(customAttributeId)) {
                return customData.getValue();
            }
        }
        return "";
    }

    private String getCustomAttributeId(String groupName, String attributeName) {
        Map<String, String> fieldValues = new HashMap<String, String>();
        fieldValues.put("groupName", groupName);
        fieldValues.put("name", attributeName);
        List<CustomAttribute> customAttributes = (List<CustomAttribute>) getBusinessObjectService().findMatching(CustomAttribute.class, fieldValues);
        if (CollectionUtils.isNotEmpty(customAttributes)) {
            return customAttributes.get(0).getId().toString();
        }
        else {
            return null;
        }
    }

    public BusinessObjectService getBusinessObjectService() {
        if (businessObjectService==null) {
            businessObjectService =  KcServiceLocator.getService(BusinessObjectService.class);
        }
        return businessObjectService;
    }
    public void setBusinessObjectService(BusinessObjectService service) {
        businessObjectService=service;
    }


    public String getProposalActionType() {
        Long attrId = Long.valueOf(getCustomAttributeId("SP Office Negotiations", "PROP_ACTION_TYPE"));
        this.proposalActionType = getCustomDataValueByAttrId(attrId);

        return this.proposalActionType;
    }

    public void setProposalActionType(String proposalActionType) {    this.proposalActionType = proposalActionType; }

    protected KcPersonService getKcPersonService() {
        if (this.kcPersonService == null) {
            this.kcPersonService = KcServiceLocator.getService(KcPersonService.class);
        }
        return this.kcPersonService;
    }
}
