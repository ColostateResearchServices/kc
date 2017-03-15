package org.kuali.kra.negotiations.customdata;

import org.kuali.coeus.common.framework.custom.attr.CustomAttribute;
import org.kuali.coeus.sys.framework.model.KcPersistableBusinessObjectBase;

/**
 * Created by chrisdenne on 2/22/17.
 */
public class LookupableNegotiationCustomData extends KcPersistableBusinessObjectBase {

    private static final long serialVersionUID = 1L;
    private Long negotiationCustomDataId;
    private Long negotiationId;
    private Long customAttributeId;
    private String value;
    private String negotiationNumber;
    private CustomAttribute customAttribute;


    public Long getNegotiationCustomDataId() {
        return negotiationCustomDataId;
    }

    public void setNegotiationCustomDataId(Long negotiationCustomDataId) {
        this.negotiationCustomDataId = negotiationCustomDataId;
    }

    public Long getNegotiationId() {
        return negotiationId;
    }

    public void setNegotiationId(Long negotiationId) {
        this.negotiationId = negotiationId;
    }

    public Long getCustomAttributeId() {
        return customAttributeId;
    }

    public void setCustomAttributeId(Long customAttributeId) {
        this.customAttributeId = customAttributeId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public CustomAttribute getCustomAttribute() {
        return customAttribute;
    }

    public void setCustomAttribute(CustomAttribute customAttribute) {
        this.customAttribute = customAttribute;
    }


    public String getNegotiationNumber() { return negotiationNumber; }

    public void setNegotiationNumber(String negotiationNumber) {  this.negotiationNumber = negotiationNumber;}
}
