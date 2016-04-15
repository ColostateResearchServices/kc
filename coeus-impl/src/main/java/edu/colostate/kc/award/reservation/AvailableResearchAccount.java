package edu.colostate.kc.award.reservation;

public class AvailableResearchAccount extends ResearchAccount {

	private static final long serialVersionUID = 2349572349572L;
	
	public void dropReservation() {
		awardAccountList.clear();
	}
	
}