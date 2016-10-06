package edu.colostate.kc.award;

import org.kuali.coeus.sys.framework.model.KcPersistableBusinessObjectBase;

public class FundSourceCode extends KcPersistableBusinessObjectBase implements Comparable<FundSourceCode> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1123459073457893L;
	
	private String fundSourceCode;
	private String sponsorTypeCode;
	private String hasPrimeSponsor;
	
	public String getFundSourceCode() {
		return fundSourceCode;
	}
	
	public void setFundSourceCode(String fundSourceCode) {
		this.fundSourceCode=fundSourceCode;
	}
	
	public String getSponsorTypeCode() {
		return sponsorTypeCode;
	}
	
	public void setSponsorTypeCode(String sponsorTypeCode) {
		this.sponsorTypeCode=sponsorTypeCode;
	}
	
	public String getHasPrimeSponsor() {
		return hasPrimeSponsor;
	}
	
	public void setHasPrimeSponsor(String hasPrimeSponsor) {
		this.hasPrimeSponsor=hasPrimeSponsor;
	}
	
    public int compareTo(FundSourceCode reportCode) {
        return this.getFundSourceCode().compareTo(reportCode.getFundSourceCode());
    }
	
}