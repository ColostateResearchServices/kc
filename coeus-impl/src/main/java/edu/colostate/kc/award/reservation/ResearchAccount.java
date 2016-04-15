package edu.colostate.kc.award.reservation;

import java.sql.Date;
import java.util.List;

import org.kuali.coeus.sys.framework.model.KcPersistableBusinessObjectBase ;
import org.springframework.util.AutoPopulatingList;

public class ResearchAccount extends KcPersistableBusinessObjectBase implements Comparable<ResearchAccount> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1234237172296257042L;

	protected String fullAccountNumber;
	protected String accountNumber;
	protected String coaCode;
	protected Boolean selectAccount = false;
	protected List<AwardAccount> awardAccountList;
	protected String accountUsed;
	
	public ResearchAccount() {
		awardAccountList = new AutoPopulatingList<AwardAccount>(AwardAccount.class);
	}
	
	public String getFullAccountNumber() {
		return fullAccountNumber;
	}
	
	public void setFullAccountNumber(String fullAccountNumber) {
		this.fullAccountNumber=fullAccountNumber;
	}
	
	public String getAccountNumber() {
		return accountNumber;
	}
	
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getCoaCode() {
		return coaCode;
	}
	public void setCoaCode(String coaCode) {
		this.coaCode = coaCode;
	}
	public String getAccountUsed() {
		return accountUsed;
	}

	public void setAccountUsed(String accountUsed) {
		this.accountUsed = accountUsed;
	}

	public Boolean getSelectAccount() {
		return selectAccount;
	}
	
	public void setSelectAccount(Boolean selectAccount) {
		this.selectAccount = selectAccount;
	}

	public List<AwardAccount> getAwardAccountList() {
		if (awardAccountList==null || (awardAccountList.size()>0 && !fullAccountNumber.equals(awardAccountList.get(0).getFullAccountNumber())) || awardAccountList.size()<1) {
			this.refreshReferenceObject("awardAccountList");
		}
		return awardAccountList;
	}

	public void setAwardAccountList(List<AwardAccount> awardAccountList) {
		this.awardAccountList = awardAccountList;
	}
	
	public AwardAccount getAwardAccount() {
		AwardAccount awardAccount=null;
		if (getAwardAccountList()!=null && awardAccountList.size()>0) {
			awardAccount=awardAccountList.get(0);
		}
		return awardAccount;
	}
	
	public void setAwardAccount(AwardAccount awardAccount) {
		if (awardAccountList.size()<1) {
			awardAccountList.add(awardAccount);
		}
	}
	
	@Override
	public int compareTo(ResearchAccount otherAccount) {
		return this.getFullAccountNumber().compareTo(otherAccount.getFullAccountNumber());
	}
	
}