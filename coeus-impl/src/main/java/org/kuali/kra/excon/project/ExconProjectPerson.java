package org.kuali.kra.excon.project;

import org.apache.commons.lang.StringUtils;
import org.kuali.coeus.common.framework.person.KcPersonService;
import org.kuali.coeus.sys.framework.service.KcServiceLocator;
import org.kuali.kra.bo.AbstractProjectPerson;
import org.kuali.coeus.common.framework.person.KcPerson;
import org.kuali.rice.krad.bo.PersistableBusinessObject;


public class ExconProjectPerson extends ExconProjectAssociate implements AbstractProjectPerson {
	
	private static final long serialVersionUID = 4063631952041257985L;
	Long exconProjectPersonId;
	String personId;
	Integer rolodexId;
	String fullName;
	String roleTypeCode;
	ExconProjectPersonRoleType roleType;
	ExconProjectTraveler traveler;
	Long projectId;
	String userName;
	
	public ExconProjectPerson() {
        
	}
	
    public Long getExconProjectPersonId() {
		return exconProjectPersonId;
	}

	public void setExconProjectPersonId(Long exconProjectPersonId) {
		this.exconProjectPersonId = exconProjectPersonId;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public Integer getRolodexId() {
		return rolodexId;
	}

	public void setRolodexId(Integer rolodexId) {
		this.rolodexId = rolodexId;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName=userName;
	}

	public String getFullName() {
		if (fullName==null) {
			KcPerson person=getPerson();
			if (person!=null) {
				fullName=person.getFullName();
			}
		}
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getRoleTypeCode() {
		return roleTypeCode;
	}

	public void setRoleTypeCode(String roleTypeCode) {
		this.roleTypeCode = roleTypeCode;
	}
	
	public ExconProjectPersonRoleType getRoleType() {
        if (!StringUtils.isEmpty(roleTypeCode)) {
            this.refreshReferenceObject("roleType");
        }
        return roleType;
	}

	public String getPersonRoleTypeName(){
		if(roleTypeCode != null){
			return new ExconProjectUnitContactTypeValuesFinder().getKeyLabel(roleTypeCode);
		}
		return null;
			
	}
	
	public void setRoleType(ExconProjectPersonRoleType roleType) {
		this.roleType = roleType;
	}
	
	public ExconProjectTraveler getTraveler() {
		/*
		if (traveler==null) {
			traveler=new ExconProjectTraveler();
			traveler.setPersonId(personId);
		}
		*/
		return traveler;
	}
	
	public void setTraveler(ExconProjectTraveler traveler) {
		this.traveler=traveler;
	}
	
	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	
	public boolean getIsUnitRestricted() {
		if (getPerson()==null || getPerson().getUnit()==null) {
			return false;
		}
		return getExconProject().isUnitRestricted(getPerson().getUnit().getUnitNumber());
	}

    public void resetPersistenceState() {
        exconProjectPersonId = null;
        versionNumber = null;
    }


	/**
     * Gets the value of person, or null if no personId is defined (this is the case for non-employees).
     *
     * @return the value of person
     */
    public KcPerson getPerson() {
        if (this.personId != null) {
            return getKcPersonService().getKcPersonByPersonId(this.personId);
        }
        else
        {
        	if (this.userName != null) {
        		KcPerson person = getKcPersonService().getKcPersonByUserName(this.userName);
        		if (person != null) {
        			setPersonId(person.getPersonId());
        		}
        		return person;
        	}
        }
        return null;
    }

    /**
     * Gets the KC Person Service.
     * @return KC Person Service.
     */
    protected KcPersonService getKcPersonService() {
        return KcServiceLocator.getService(KcPersonService.class);
    }

	@Override
	public PersistableBusinessObject getParent() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String getRoleCode() {
		return this.roleTypeCode;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof ExconProjectPerson && ((ExconProjectPerson)o).getExconProjectPersonId()==this.getExconProjectPersonId()) {
			return true;
		}
		return false;
	}
}