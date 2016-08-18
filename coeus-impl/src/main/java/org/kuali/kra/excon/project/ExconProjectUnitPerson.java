package org.kuali.kra.excon.project;
 
import org.kuali.coeus.common.framework.person.KcPerson;
import org.kuali.coeus.common.framework.person.KcPerson;
import org.kuali.coeus.common.framework.person.KcPerson;
import org.kuali.coeus.common.framework.person.KcPerson;
import org.kuali.coeus.common.framework.person.KcPersonService;
import org.kuali.coeus.sys.framework.service.KcServiceLocator;
import org.kuali.kra.bo.AbstractProjectPerson;
import org.kuali.rice.krad.bo.PersistableBusinessObject;

public class ExconProjectUnitPerson extends ExconProjectAssociate implements AbstractProjectPerson {
	
	private static final long serialVersionUID = 40636319234557985L;
	Long exconProjectUnitPersonId;
	String personId;
	Integer rolodexId;
	String fullName;
	String unitAdministratorTypeCode;
	String unitAdministratorUnitNumber;
	Long projectId;
	String userName;
	
	public ExconProjectUnitPerson() {
		
	}
	
    public Long getExconProjectUnitPersonId() {
		return exconProjectUnitPersonId;
	}

	public void setExconProjectPersonId(Long exconProjectUnitPersonId) {
		this.exconProjectUnitPersonId = exconProjectUnitPersonId;
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

	public String getUnitAdministratorTypeCode() {
		return unitAdministratorTypeCode;
	}

	public void setUnitAdministratorTypeCode(String unitAdministratorTypeCode) {
		this.unitAdministratorTypeCode = unitAdministratorTypeCode;
	}

	public String getUnitAdministratorTypeName(){
		if(unitAdministratorTypeCode != null){
			return new ExconProjectUnitContactTypeValuesFinder().getKeyLabel(unitAdministratorTypeCode);
		}
		return null;
			
	}
	
	public String getUnitAdministratorUnitNumber() {
		if (unitAdministratorUnitNumber==null) {
			KcPerson person=getPerson();
			if (person!=null) {
				unitAdministratorUnitNumber=person.getUnit().getUnitNumber();
			}
		}
		return unitAdministratorUnitNumber;
	}

	public void setUnitAdministratorUnitNumber(String unitAdministratorUnitNumber) {
		this.unitAdministratorUnitNumber = unitAdministratorUnitNumber;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

    public void resetPersistenceState() {
        exconProjectUnitPersonId = null;
        versionNumber = null;
    }

    public int compareTo(ExconProjectComment exconProjectCommentArg) {
        return exconProjectCommentArg.getUpdateTimestamp().compareTo(this.getUpdateTimestamp());
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
		return this.unitAdministratorTypeCode;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof ExconProjectUnitPerson && ((ExconProjectUnitPerson)o).getExconProjectUnitPersonId()==this.getExconProjectUnitPersonId()) {
			return true;
		}
		return false;
	}
}