package edu.colostate.kc.award.awardalternatenumber;

import org.apache.commons.lang.StringUtils;
import org.kuali.coeus.common.framework.version.sequence.associate.SequenceAssociate;
import org.kuali.kra.SkipVersioning;
import org.kuali.kra.award.home.Award;
import org.kuali.coeus.sys.framework.model.KcPersistableBusinessObjectBase;

public class AwardAlternateNumber extends KcPersistableBusinessObjectBase implements SequenceAssociate<Award> {
	
	private String awardAlternateNumberId;
    private Long awardId;

	private AwardAlternateNumberType awardAlternateNumberType;
	private String awardAlternateNumberTypeCode;
	private String description;
	private String number;
	private boolean showOnInvoice;
	
	@SkipVersioning
	private Award award;
	
	
	public AwardAlternateNumberType getAwardAlternateNumberType() {
        if (this.awardAlternateNumberType==null && !StringUtils.isEmpty(getAwardAlternateNumberTypeCode())) {
            this.refreshReferenceObject("awardAlternateNumberType");
        }
		return awardAlternateNumberType;
	}
	public void setAwardAlternateNumberType(AwardAlternateNumberType type) {
		this.awardAlternateNumberType = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public boolean isShowOnInvoice() {
		return showOnInvoice;
	}
	public void setShowOnInvoice(boolean showOnInvoice) {
		this.showOnInvoice = showOnInvoice;
	}
	public String getAwardAlternateNumberId() {
		return awardAlternateNumberId;
	}
	public void setAwardAlternateNumberId(String awardAlternateNumberId) {
		this.awardAlternateNumberId = awardAlternateNumberId;
	}
	public Long getAwardId() {
		return awardId;
	}
	public void setAwardId(Long awardId) {
		this.awardId = awardId;
	}
	public String getAwardAlternateNumberTypeCode() {
		return awardAlternateNumberTypeCode;
	}
	public void setAwardAlternateNumberTypeCode(String awardAlternateNumberTypeCode) {
		this.awardAlternateNumberTypeCode = awardAlternateNumberTypeCode;
        this.refreshReferenceObject("awardAlternateNumberType");

	}
	@Override
	public Integer getSequenceNumber() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void resetPersistenceState() {
		awardAlternateNumberId = null;		
	}
	

	@Override
	public Award getSequenceOwner() {
        if (this.award==null && getAwardId()!=null && getAwardId() >0 ) {
            this.refreshReferenceObject("award");
        }
		return award;
	}
	
	@Override
	public void setSequenceOwner(Award newlyVersionedOwner) {
        setAward(newlyVersionedOwner);
	}
	public Award getAward() {
		return award;
	}
	public void setAward(Award award) {
		this.award = award;
	}
		
}
