package org.kuali.kra.award.cgb;

import org.kuali.coeus.sys.framework.model.KcPersistableBusinessObjectBase;

public class CgbBillingFrequency extends KcPersistableBusinessObjectBase {

    private Long id;
	private String description;
	private boolean active;
	private String code;
	
	
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
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}




}
