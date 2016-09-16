package edu.iu.uits.kra.negotiations.rules;


import org.apache.commons.lang.StringUtils;
import org.kuali.kra.infrastructure.KeyConstants;
import org.kuali.kra.negotiations.bo.Negotiation;
import org.kuali.kra.negotiations.bo.NegotiationAssociationType;
import org.kuali.kra.negotiations.bo.NegotiationUnassociatedDetail;
import org.kuali.kra.negotiations.document.NegotiationDocument;
import org.kuali.coeus.common.framework.custom.SaveCustomDataEvent;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.kra.negotiations.rules.NegotiationDocumentRule;

import edu.iu.uits.kra.negotiations.bo.IUNegotiationAssociationTypeUtil;

/**
 * 
 * This class handles the business rules for the Negotiation Object.
 */
public class IUNegotiationDocumentRule extends NegotiationDocumentRule {
    
    private static final String NEGOTIATION_ERROR_PATH = "document.negotiationList[0]";
    
    /**
     * 
     * Constructs a NegotiationDocumentRule.java.
     */
    public IUNegotiationDocumentRule() {
        super();
    }
    
    /* IU Customization Starts UITSRA-3171 */
    @Override
    protected boolean processCustomSaveDocumentBusinessRules(Document document) {
        if (!(document instanceof NegotiationDocument)) {
            return false;
        }
        
        GlobalVariables.getMessageMap().addToErrorPath(DOCUMENT_ERROR_PATH);
        getDictionaryValidationService().validateDocumentAndUpdatableReferencesRecursively(
            document, getMaxDictionaryValidationDepth(), VALIDATION_REQUIRED, CHOMP_LAST_LETTER_S_FROM_COLLECTION_NAME);
        GlobalVariables.getMessageMap().removeFromErrorPath(DOCUMENT_ERROR_PATH);
        
        NegotiationDocument negotiationDocument = (NegotiationDocument) document;
        Negotiation negotiation = negotiationDocument.getNegotiation();
        
        boolean result = true;
        
        result &= processRules(new SaveCustomDataEvent(negotiationDocument, true));

        GlobalVariables.getMessageMap().addToErrorPath(NEGOTIATION_ERROR_PATH);
        
        result &= validateEndDate(negotiation);
        result &= validateNegotiationAssociations(negotiation);
        result &= validateNegotiationUnassociatedDetails(negotiation);
        result &= validateNegotiationActivities(negotiation); 
        
        /** Begin IU Customization: UITSRA-4102 - Check for null file data */
        result &= new IUNegotiationAttachmentFileDataNotNullRuleImpl(getErrorReporter()).checkAttachmentFileDataNotNull(negotiation);
        /** End IU Customization: UITSRA-4102 */
        
        GlobalVariables.getMessageMap().removeFromErrorPath(NEGOTIATION_ERROR_PATH);
        
        return result;
    }
    /* IU Customization Ends */
    
    /* IU Customization Starts UITSRA-3231 */
    private static final String ASSOCIATED_DOCMENT_ID = "associatedDocumentId"; //copied from NegotiationDocumentRule
    /**
     * 
     * This method validates the Negotiation Association.
     * @param negotiation
     * @return
     */
    public boolean validateNegotiationAssociations(Negotiation negotiation) {
        boolean valid = true;
        if (negotiation.getNegotiationAssociationType() != null 
                //&& !StringUtils.equals(negotiation.getNegotiationAssociationType().getCode(), NegotiationAssociationType.NONE_ASSOCIATION)
        		&& !IUNegotiationAssociationTypeUtil.isUnassociatedType(negotiation.getNegotiationAssociationType().getCode())
                && StringUtils.isEmpty(negotiation.getAssociatedDocumentId())) {
            //negotiation.setAssociatedDocumentWarning(expandErrorString(KeyConstants.NEGOTIATION_WARNING_ASSOCIATEDID_NOT_SET, 
               //     new String[]{negotiation.getNegotiationAssociationType().getDescription()}));
            valid = false;
            //can't do this because the document is final, when final and without error the messagemap is cleared during save
            //so must workaround to display this warning.
            getErrorReporter().reportError(ASSOCIATED_DOCMENT_ID, KeyConstants.NEGOTIATION_WARNING_ASSOCIATEDID_NOT_SET, 
                    negotiation.getNegotiationAssociationType().getDescription());
        }
        return valid;
    }

    public boolean validateNegotiationUnassociatedDetails(Negotiation negotiation) {
        boolean valid = true;
        GlobalVariables.getMessageMap().addToErrorPath("unAssociatedDetail");
        if (negotiation.getNegotiationAssociationType() != null 
                //&& StringUtils.equals(negotiation.getNegotiationAssociationType().getCode(), NegotiationAssociationType.NONE_ASSOCIATION)
        		&& IUNegotiationAssociationTypeUtil.isUnassociatedType(negotiation.getNegotiationAssociationType().getCode())
                && negotiation.getUnAssociatedDetail() != null) {
            NegotiationUnassociatedDetail detail = negotiation.getUnAssociatedDetail();
            valid &= getDictionaryValidationService().isBusinessObjectValid(detail);
            
            detail.refreshReferenceObject("sponsor");
            if (detail.getSponsorCode() != null && !this.getSponsorService().isValidSponsor(getSponsorService().getSponsor(detail.getSponsorCode()))) {
                valid = false;
                getErrorReporter().reportError("sponsorCode", KeyConstants.ERROR_MISSING, getDataDictionaryService().getAttributeErrorLabel(
                        NegotiationUnassociatedDetail.class, "sponsorCode"));
            }
            
            detail.refreshReferenceObject("leadUnit");
            if (detail.getLeadUnitNumber() != null && detail.getLeadUnit() == null) {
                valid = false;
                getErrorReporter().reportError("leadUnitNumber", KeyConstants.ERROR_MISSING, getDataDictionaryService().getAttributeErrorLabel(
                        NegotiationUnassociatedDetail.class, "leadUnitNumber"));
            }
            
            detail.refreshReferenceObject("primeSponsor");
            if (detail.getPrimeSponsorCode() != null && !this.getSponsorService().isValidSponsor(getSponsorService().getSponsor(detail.getSponsorCode()))) {
                valid = false;
                getErrorReporter().reportError("primeSponsorCode", KeyConstants.ERROR_MISSING, getDataDictionaryService().getAttributeErrorLabel(
                        NegotiationUnassociatedDetail.class, "primeSponsorCode"));
            }
            
            detail.refreshReferenceObject("subAwardOrganization");
            if (detail.getSubAwardOrganizationId() != null && detail.getSubAwardOrganization() == null) {
                valid = false;
                getErrorReporter().reportError("subAwardOrganizationId", KeyConstants.ERROR_MISSING, getDataDictionaryService().getAttributeErrorLabel(
                        NegotiationUnassociatedDetail.class, "subAwardOrganizationId"));
            }
            if (detail.getContactAdminUserName() != null && detail.getContactAdmin() == null) {
                valid = false;
                getErrorReporter().reportError("contactAdminUserName", KeyConstants.ERROR_MISSING, getDataDictionaryService().getAttributeErrorLabel(
                        NegotiationUnassociatedDetail.class, "contactAdminPersonId"));
            }
            if (detail.getPiEmployeeUserName() != null && detail.getPIEmployee() == null) {
                valid = false;
                getErrorReporter().reportError("piEmployeeUserName", KeyConstants.ERROR_MISSING, getDataDictionaryService().getAttributeErrorLabel(
                        NegotiationUnassociatedDetail.class, "piPersonId"));                
            }
            if (detail.getPIEmployee() != null && detail.getPINonEmployee() != null) {
                valid = false;
                getErrorReporter().reportError("piEmployeeUserName", KeyConstants.NEGOTIATION_MULTIPLE_PI, getDataDictionaryService().getAttributeErrorLabel(
                        NegotiationUnassociatedDetail.class, "piPersonId"));                                
            }
        }
        GlobalVariables.getMessageMap().removeFromErrorPath("unAssociatedDetail");
        return valid;
    }
    
    
    
    /* IU Customization Ends */

}
