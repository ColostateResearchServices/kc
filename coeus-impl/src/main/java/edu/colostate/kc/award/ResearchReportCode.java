package edu.colostate.kc.award;

import org.kuali.coeus.sys.framework.model.KcPersistableBusinessObjectBase;

public class ResearchReportCode extends KcPersistableBusinessObjectBase implements Comparable<ResearchReportCode> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1123459073457893L;
	
	private String researchReportCode;
	private String sponsorTypeCode;
	private String sponsorNameWildcard;
	
	public String getResearchReportCode() {
		return researchReportCode;
	}
	
	public void setResearchReportCode(String researchReportCode) {
		this.researchReportCode=researchReportCode;
	}
	
	public String getSponsorTypeCode() {
		return sponsorTypeCode;
	}
	
	public void setSponsorTypeCode(String sponsorTypeCode) {
		this.sponsorTypeCode=sponsorTypeCode;
	}
	
	public String getSponsorNameWildcard() {
		return sponsorNameWildcard;
	}
	
	public void setSponsorNameWildcard(String sponsorNameWildcard) {
		this.sponsorNameWildcard=sponsorNameWildcard;
	}
	
    public int compareTo(ResearchReportCode reportCode) {
        return this.getResearchReportCode().compareTo(reportCode.getResearchReportCode());
    }
	
}