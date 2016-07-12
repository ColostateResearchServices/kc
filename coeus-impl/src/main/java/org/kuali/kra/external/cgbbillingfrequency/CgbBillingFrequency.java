package org.kuali.kra.external.cgbbillingfrequency;

import org.kuali.rice.krad.bo.BusinessObject;

public class CgbBillingFrequency implements BusinessObject {

	private String description;
	private boolean active;
	private String code;
	private int gracePeriodDays;
	
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getGracePeriodDays() {
		return gracePeriodDays;
	}
	public void setGracePeriodDays(int gracePeriodDays) {
		this.gracePeriodDays = gracePeriodDays;
	}


	@Override
	public void refresh() {	}

}
