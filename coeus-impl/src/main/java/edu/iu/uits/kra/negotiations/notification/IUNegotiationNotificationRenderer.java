package edu.iu.uits.kra.negotiations.notification;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.kra.award.home.Award;
import org.kuali.coeus.common.framework.custom.attr.CustomAttribute;
import org.kuali.coeus.common.framework.person.KcPerson;
import org.kuali.coeus.common.notification.impl.NotificationRendererBase;
import org.kuali.kra.infrastructure.KeyConstants;
import org.kuali.coeus.sys.framework.service.KcServiceLocator;
import org.kuali.kra.negotiations.bo.Negotiable;
import org.kuali.kra.negotiations.bo.Negotiation;
import org.kuali.kra.negotiations.bo.NegotiationAssociationType;
import org.kuali.kra.negotiations.bo.NegotiationUnassociatedDetail;
import org.kuali.kra.negotiations.customdata.NegotiationCustomData;
import org.kuali.kra.negotiations.notifications.NegotiationNotificationRenderer;
import org.kuali.rice.core.api.CoreApiServiceLocator;
import org.kuali.rice.krad.service.BusinessObjectService;

//UITSRA-3668
public class IUNegotiationNotificationRenderer extends NegotiationNotificationRenderer {

    private static final long serialVersionUID = 408846376074619624L;
    private static String noneGiven = null;
        
    public IUNegotiationNotificationRenderer(Negotiation negotiation) {
        super();
        this.setNegotiation(negotiation);
    }
    
    @Override
    public Map<String, String> getDefaultReplacementParameters() {
        if (noneGiven == null) {
            noneGiven = CoreApiServiceLocator.getKualiConfigurationService().getPropertyValueAsString(KeyConstants.NO_REASON_GIVEN);
        }
        Map<String,String> result = super.getDefaultReplacementParameters();
        result.put("{PI_LAST_NAME}", ""); //PI last name should be blank if it doesn't exist not noneGiven
        Negotiation negotiation = this.getNegotiation();
        if (StringUtils.equals(negotiation.getNegotiationAssociationType().getCode(), NegotiationAssociationType.AWARD_ASSOCIATION)) {
            Negotiable negotiable = negotiation.getAssociatedDocument();
            if(negotiable != null) {
                if(StringUtils.isNotBlank(negotiable.getSponsorName())) {
                    result.put("{SPONSOR_NAME}", negotiable.getSponsorName());
                } else {
                    result.put("{SPONSOR_NAME}", noneGiven);
                }
                if(StringUtils.isNotBlank(negotiable.getPiName())) {
                    result.put("{PI}", negotiable.getPiName());
                } else {
                    result.put("{PI}", noneGiven);
                }
                if(StringUtils.isNotBlank(negotiable.getTitle())) {
                    result.put("{TITLE}", negotiable.getTitle());
                } else {
                    result.put("{TITLE}", noneGiven);
                }
                if(negotiable instanceof Award) {
                    Award award = (Award) negotiable;
                    if(award.getPrincipalInvestigator() != null) {
                        result.put("{PI_LAST_NAME}", award.getPrincipalInvestigator().getLastName());
                    }
                }
            }
        } else {
            NegotiationUnassociatedDetail details = negotiation.getUnAssociatedDetail();
            if(details != null) {
                KcPerson pi = details.getPIEmployee();
                if(pi != null) {
                    result.put("{PI_LAST_NAME}", pi.getLastName());
                }
            }
        }

        List<NegotiationCustomData> customData = negotiation.getNegotiationCustomDataList();
        result.put("{INSTITUTIONAL_PROPOSAL_ID}", noneGiven);

        Long institutionalProposalIdCustomAttribute = getCustomAttributeId("All Negotiations", "ipid");
        if(institutionalProposalIdCustomAttribute != null) {
            for (NegotiationCustomData ncd : customData) {
                if(ncd.getCustomAttributeId().equals(institutionalProposalIdCustomAttribute)) {
                    if(ncd.getValue() != null) {
                        result.put("{INSTITUTIONAL_PROPOSAL_ID}", ncd.getValue());
                    }
                }
            }
        }
        return result;
    }
    
    private Long getCustomAttributeId(String groupName, String attributeName) {
        Map<String, String> fieldValues = new HashMap<String, String>();
        fieldValues.put("groupName", groupName);
        fieldValues.put("name", attributeName);
        List<CustomAttribute> customAttributes = (List<CustomAttribute>) KcServiceLocator.getService(BusinessObjectService.class).findMatching(CustomAttribute.class, fieldValues);
        if (CollectionUtils.isNotEmpty(customAttributes)) {
            return customAttributes.get(0).getId().longValue();
        }
        else {
            return null;
        }
    }
    
}
