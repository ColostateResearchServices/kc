package edu.colostate.kc.award.reservation;

import java.sql.Date;
import java.text.SimpleDateFormat;

import org.kuali.kra.bo.KraPersistableBusinessObjectBase;


public class AwardAccount extends KraPersistableBusinessObjectBase implements Comparable<AwardAccount> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8844737172296257042L;

	protected Long accountReservationNumber;
	protected String fullAccountNumber;
	protected String reservationUser;
	protected Date reservationDate;
	protected String accountUsed;
	protected AwardAccountReservation accountReservation;
	protected ResearchAccount researchAccount;
	protected AvailableResearchAccount availableResearchAccount;
	protected Boolean selectAccount = false;
	protected Boolean needsSaved = false;
	
	public Long getAccountReservationNumber() {
		return accountReservationNumber;
	}

	public void setAccountReservationNumber(Long accountReservationNumber) {
		this.accountReservationNumber = accountReservationNumber;
	}

	public String getFullAccountNumber() {
		return fullAccountNumber;
	}
	
	public void setFullAccountNumber(String fullAccountNumber) {
		this.fullAccountNumber = fullAccountNumber;
	}

	public String getReservationUser() {
		return reservationUser;
	}
	public void setReservationUser(String reservationUser) {
		this.reservationUser = reservationUser;
	}
	public Date getReservationDate() {
		return reservationDate;
	}
	public void setReservationDate(Date reservationDate) {
		this.reservationDate = reservationDate;
	}
	
    public String getFormattedReservationDate() {
    	Date inDate=getReservationDate();
    	String outStr="";
    	if (inDate!=null) {
    		SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");
    		outStr=sdf.format(inDate);
    	}
    	return outStr;
    }
    
	public String getAccountUsed() {
		return accountUsed;
	}

	public void setAccountUsed(String accountUsed) {
		this.accountUsed = accountUsed;
	}

	public AwardAccountReservation getAccountReservation() {
		return accountReservation;
	}

	public void setAccountReservation(AwardAccountReservation accountReservation) {
		this.accountReservation = accountReservation;
	}
	
	public ResearchAccount getResearchAccount() {
		return researchAccount;
	}
	
	public void setResearchAccount(ResearchAccount researchAccount) {
		this.researchAccount=researchAccount;
	}
	
	public AvailableResearchAccount getAvailableResearchAccount() {
		return availableResearchAccount;
	}

	public void setAvailableResearchAccount(AvailableResearchAccount availableResearchAccount) {
		this.availableResearchAccount = availableResearchAccount;
	}

	public Boolean getSelectAccount() {
		return selectAccount;
	}

	public void setSelectAccount(Boolean selectAccount) {
		this.selectAccount = selectAccount;
	}

	@Override
	public int compareTo(AwardAccount otherAccount) {
		return this.getFullAccountNumber().compareTo(otherAccount.getFullAccountNumber());
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof AwardAccount && ((AwardAccount)o).getFullAccountNumber()==this.getFullAccountNumber()) {
			return true;
		}
		return false;
	}
	
	public void setSaveFlag() {
		needsSaved=true;
	}
	
	public Boolean getSaveFlag() {
		return needsSaved;
	}
	
	protected void clearSaveFlag() {
		needsSaved=false;
	}
		
	@Override
	public void prePersist() {
		clearSaveFlag();
		super.prePersist();
	}
	
	@Override
	public void preUpdate() {
		clearSaveFlag();
		super.preUpdate();
	}
	
}