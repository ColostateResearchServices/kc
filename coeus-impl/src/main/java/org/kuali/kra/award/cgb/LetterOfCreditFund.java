package org.kuali.kra.award.cgb;

import java.sql.Date;

import org.kuali.coeus.sys.api.model.ScaleTwoDecimal;
import org.kuali.coeus.sys.framework.model.KcPersistableBusinessObjectBase;

public class LetterOfCreditFund extends KcPersistableBusinessObjectBase  {


	private String fundCode;

	private String description;
	private Date startDate;
	private Date expirationDate;
	private boolean active;
	private ScaleTwoDecimal amount;
	private String groupCode;
	private LetterOfCreditFundGroup fundGroup;

	
	public String getFundCode() {
		return fundCode;
	}

	public void setFundCode(String fundCode) {
		this.fundCode = fundCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public ScaleTwoDecimal getAmount() {
		return amount;
	}

	public void setAmount(ScaleTwoDecimal amount) {
		this.amount = amount;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public LetterOfCreditFundGroup getFundGroup() {
		return fundGroup;
	}

	public void setFundGroup(LetterOfCreditFundGroup fundGroup) {
		this.fundGroup = fundGroup;
	}

}
