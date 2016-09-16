/*
 * Copyright 2005-2014 The Kuali Foundation
 * 
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/ecl1.php
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.kra.excon.project;
 
import org.kuali.coeus.common.framework.person.KcPerson;
import org.kuali.coeus.common.framework.sponsor.Sponsor;
import org.kuali.coeus.common.framework.unit.Unit;
import org.kuali.coeus.common.framework.sponsor.Sponsor;
import org.kuali.coeus.common.framework.unit.Unit;
import org.kuali.coeus.common.framework.person.KcPerson;
import org.kuali.coeus.common.framework.sponsor.Sponsor;
import org.kuali.coeus.common.framework.unit.Unit;
import org.kuali.coeus.common.framework.unit.Unit;
import org.apache.commons.lang.StringUtils;
import org.kuali.coeus.common.framework.auth.SystemAuthorizationService;
import org.kuali.coeus.common.framework.auth.perm.Permissionable;
import org.kuali.coeus.common.framework.person.KcPersonService;
import org.kuali.coeus.common.framework.unit.UnitService;
import org.kuali.coeus.common.framework.version.history.VersionHistorySearchBo;
import org.kuali.coeus.common.framework.version.sequence.owner.SequenceOwner;
import org.kuali.coeus.sys.framework.model.KcPersistableBusinessObjectBase;
import org.kuali.coeus.sys.framework.service.KcServiceLocator;
import org.kuali.kra.excon.customdata.ExconProjectCustomData;
import org.kuali.kra.excon.document.ExconProjectDocument;
import org.kuali.rice.kim.api.role.Role;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.util.GlobalVariables;
import org.springframework.util.AutoPopulatingList;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import org.kuali.kra.bo.*;

/**
 * 
 * This class is for ExconProject...
 */
public class ExconProject extends KcPersistableBusinessObjectBase implements Permissionable, SequenceOwner<ExconProject> {

    private static final long serialVersionUID = 18919289567474574L;
    public static final String NOTIFICATION_TYPE_SUBMIT = "501";
 
    private Long projectId;
    private String projectNumber;
    private String documentNumber;
    private ExconProjectDocument exconProjectDocument;
    private String title;
    private String projectTypeCode;
    private ExconProjectType projectType;
    private String unitNumber;
    private String unitName;
    private Unit unit;
    private String sponsorCode;
    private String sponsorName;
    private Sponsor sponsor;
    private Boolean fundamentalResearch;
    private Date projectStartDate;
    private Date projectEndDate;
    private String projectStatusCode="DON";
    private String projectStatusDescription;
    private ExconProjectStatusType projectStatus;
    private String exconProjectSequenceStatus;
    private String documentStatus;
    private String travelerId;
    private List<ExconProjectTravelerCommunication> travelerCommunications;
//    private Long rpsPersonId;

    private List<ExconProjectRPSEntity> exconProjectRPSEntities;
//    private ExconProjectRPSEntity exconProjectRPSEntity;
    private List<ExconProjectPerson> exconProjectPersons;
    private List<ExconProjectUnitPerson> exconProjectUnitPersons;
    private List<ExconProjectEvent> exconProjectEvents;
    private List<ExconProjectDestination> exconProjectDestinations;
    private List<ExconProjectComment> exconProjectComments;
    private List<ExconProjectAttachment> exconProjectAttachments;
    private List<ExconProjectCustomData> exconProjectCustomDataList;
    private List<ExconProjectExternalInstitution> exconProjectExternalInstitutions;
    private List<ExconProjectReview> exconProjectReviews;
    private List<ExconProjectAssociatedDocument> exconProjectAssociatedDocuments;
	private ExconProjectHRExtension hrExtension;
	private ExconProjectHRExtension tempHRExtension;
	private Integer sequenceNumber;
	private boolean allowUpdateTimestampToBeReset=true;
	private VersionHistorySearchBo versionHistory;
	
	private String agreementRole;
	private String responsibleParty;
	private String respPartyUsername;
	private String respPartyFullname;
    
    public ExconProjectType getProjectType() {
    	if (!StringUtils.isEmpty(projectTypeCode)) {
            this.refreshReferenceObject("projectType");
        }
		return projectType;
	}

	public void setProjectType(ExconProjectType projectType) {
		this.projectType = projectType;
	}

	public Unit getUnit() {
        if (!StringUtils.isEmpty(unitNumber)) {
            this.refreshReferenceObject("unit");
        }
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public Sponsor getSponsor() {
        if (!StringUtils.isEmpty(sponsorCode)) {
            this.refreshReferenceObject("sponsor");
        }
        return sponsor;
	}

	public void setSponsor(Sponsor sponsor) {
		this.sponsor = sponsor;
	}

	public ExconProjectStatusType getProjectStatus() {
		if (!StringUtils.isEmpty(projectStatusCode)) {
            this.refreshReferenceObject("projectStatus");
        }
		return projectStatus;
	}

	public void setProjectStatus(ExconProjectStatusType projectStatus) {
		this.projectStatus = projectStatus;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getProjectNumber() {
		return projectNumber;
	}

	public void setProjectNumber(String projectNumber) {
		this.projectNumber = projectNumber;
	}


	public String getDocumentNumber() {
		return documentNumber;
	}
	
    public ExconProjectDocument getExconProjectDocument() {
        if (exconProjectDocument == null) {
            this.refreshReferenceObject("exconProjectDocument");
        }
        return exconProjectDocument;
    }
  

    public String getExconProjectDocumentUrl() {
        return getExconProjectDocument().buildForwardUrl();
    }

    
    public void setExconProjectDocument(ExconProjectDocument exconProjectDocument) {
        this.exconProjectDocument = exconProjectDocument;
    }
	

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getProjectTypeCode() {
		return projectTypeCode;
	}

	public void setProjectTypeCode(String projectTypeCode) {
		this.projectTypeCode = projectTypeCode;
	}

	public String getUnitNumber() {
		return unitNumber;
	}

	public void setUnitNumber(String unitNumber) {
		this.unitNumber = unitNumber;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public Boolean getFundamentalResearch() {
		return fundamentalResearch;
	}

	public void setFundamentalResearch(Boolean fundamentalResearch) {
		this.fundamentalResearch = fundamentalResearch;
	}

	public Date getProjectStartDate() {
		return projectStartDate;
	}
	
	public String getProjectStartDateStr() {
		return ExconProject.formattedDate(getProjectStartDate());
	}

	public void setProjectStartDate(Date projectStartDate) {
		this.projectStartDate = projectStartDate;
	}

	public Date getProjectEndDate() {
		return projectEndDate;
	}
	
	public String getProjectEndDateStr() {
		return ExconProject.formattedDate(getProjectEndDate());
	}

	public void setProjectEndDate(Date projectEndDate) {
		this.projectEndDate = projectEndDate;
	}

	public String getProjectStatusCode() {
		return projectStatusCode;
	}

	public void setProjectStatusCode(String projectStatusCode) {
		this.projectStatusCode = projectStatusCode;
	}

	public String getProjectStatusDescription() {
		return projectStatusDescription;
	}

	public void setProjectStatusDescription(String projectStatusDescription) {
		this.projectStatusDescription = projectStatusDescription;
	}
	
	public List<ExconProjectPerson> getExconProjectPersons() {
		if (exconProjectPersons == null) {
			exconProjectPersons = new ArrayList<ExconProjectPerson>();
        }
		return exconProjectPersons;
	}

	public void setExconProjectPersons(List<ExconProjectPerson> exconProjectPersons) {
		this.exconProjectPersons = exconProjectPersons;
	}
	
	public List<ExconProjectRPSEntity> getExconProjectRPSEntities() {
		if (exconProjectRPSEntities == null) {
			exconProjectRPSEntities = new ArrayList<ExconProjectRPSEntity>();
        }
		return exconProjectRPSEntities;
	}

	public void setExconProjectRPSEntities(List<ExconProjectRPSEntity> exconProjectRPSEntities) {
		this.exconProjectRPSEntities = exconProjectRPSEntities;
	}
	
	public ExconProjectRPSEntity getRpsEntity() {
		if (!getExconProjectRPSEntities().isEmpty()) {
			return getExconProjectRPSEntities().get(0);
		}
		return null;
	}

	public List<ExconProjectUnitPerson> getExconProjectUnitPersons() {
		if (exconProjectUnitPersons == null) {
			exconProjectUnitPersons = new ArrayList<ExconProjectUnitPerson>();
        }
		return exconProjectUnitPersons;
	}

	public void setExconProjectUnitPersons(List<ExconProjectUnitPerson> exconProjectUnitPersons) {
		this.exconProjectUnitPersons = exconProjectUnitPersons;
	}

	public List<ExconProjectEvent> getExconProjectEvents() {
		if (exconProjectEvents == null) {
			exconProjectEvents = new ArrayList<ExconProjectEvent>();
        }
		return exconProjectEvents;
	}

	public void setExconProjectEvents(List<ExconProjectEvent> exconProjectEvents) {
		this.exconProjectEvents = exconProjectEvents;
	}
	
	public List<ExconProjectDestination> getExconProjectDestinations() {
		if (exconProjectDestinations == null) {
			exconProjectDestinations = new ArrayList<ExconProjectDestination>();
        }
		return exconProjectDestinations;
	}

	public void setExconProjectDestinations(List<ExconProjectDestination> exconProjectDestinations) {
		this.exconProjectDestinations = exconProjectDestinations;
	}

	public List<ExconProjectComment> getExconProjectComments() {
		if (exconProjectComments == null) {
			exconProjectComments = new ArrayList<ExconProjectComment>();
        }
		return exconProjectComments;
	}

	public void setExconProjectComments(List<ExconProjectComment> exconProjectComments) {
		this.exconProjectComments = exconProjectComments;
	}
	
	public List<ExconProjectExternalInstitution> getExconProjectExternalInstitutions() {
		if (exconProjectExternalInstitutions == null) {
			exconProjectExternalInstitutions = new ArrayList<ExconProjectExternalInstitution>();
		}
		return exconProjectExternalInstitutions;
	}

	public void setExconProjectExternalInstitutions(
			List<ExconProjectExternalInstitution> exconProjectExternalInstitutions) {
		this.exconProjectExternalInstitutions = exconProjectExternalInstitutions;
	}

	public List<ExconProjectReview> getExconProjectReviews() {
		if (exconProjectReviews == null) {
			exconProjectReviews = new ArrayList<ExconProjectReview>();
		}
		return exconProjectReviews;
	}

	public void setExconProjectReviews(List<ExconProjectReview> exconProjectReviews) {
		this.exconProjectReviews = exconProjectReviews;
	}
	
	public List<ExconProjectAssociatedDocument> getExconProjectAssociatedDocuments() {
		if (exconProjectAssociatedDocuments == null) {
			exconProjectAssociatedDocuments = new ArrayList<ExconProjectAssociatedDocument>();
		}
		return exconProjectAssociatedDocuments;
	}

	public void setExconProjectAssociatedDocuments(List<ExconProjectAssociatedDocument> exconProjectAssociatedDocuments) {
		this.exconProjectAssociatedDocuments = exconProjectAssociatedDocuments;
	}
	
	public ExconProjectPerson getTraveler() {
		ExconProjectPerson traveler=null;
		for (ExconProjectPerson person:getExconProjectPersons()) {
			if (person.getRoleType().getDescription().equals("Traveler")) {
				traveler=person;
				travelerId=traveler.getPersonId();
			}
		}
		if (traveler==null) {
			travelerId=null;
		}
		return traveler;
	}

	public List<ExconProjectTravelerCommunication> getTravelerCommunications() {
		ExconProjectPerson traveler=null;
		if ((traveler=getTraveler())!=null) {
			travelerCommunications=traveler.getTraveler().getTravelerCommunications();
		}
		else
		{
			travelerCommunications=new AutoPopulatingList<ExconProjectTravelerCommunication>(ExconProjectTravelerCommunication.class);
		}
		return travelerCommunications;
	}
	
	public String getSponsorCode() {
		return sponsorCode;
	}
	
	public void setSponsorCode(String sponsorCode) {
		this.sponsorCode = sponsorCode;
	}
	
	public String getSponsorName() {
		return sponsorName;
	}

	public void setSponsorName(String sponsorName) {
		this.sponsorName = sponsorName;
	}

	public String getAgreementRole() {
		return agreementRole;
	}

	public void setAgreementRole(String agreementRole) {
		this.agreementRole = agreementRole;
	}

	public String getResponsibleParty() {
		return responsibleParty;
	}

	public void setResponsibleParty(String responsibleParty) {
		this.responsibleParty = responsibleParty;
		if (responsibleParty==null) {this.respPartyUsername=null;}
	}

	public String getRespPartyUsername() {
		return respPartyUsername;
	}

	public void setRespPartyUsername(String respPartyUsername) {
		this.respPartyUsername = respPartyUsername;
		if (respPartyUsername==null) {this.responsibleParty=null;}
	}
	
	public String getRespPartyFullname() {
		return respPartyFullname;
	}

	public void setRespPartyFullname(String respPartyFullname) {
		this.respPartyFullname = respPartyFullname;
	}

	/**
     * Gets the value of person, or null if no personId is defined (this is the case for non-employees).
     *
     * @return the value of person
     */
    public KcPerson getRespPartyPerson() {
    	KcPerson person=null;
    	
    	if (this.respPartyUsername != null) {
    			person = getKcPersonService().getKcPersonByUserName(this.respPartyUsername);
    			if (person != null) {
    				this.responsibleParty=person.getPersonId();
    			}
    	}
        else
        {
        	if (this.responsibleParty != null) {
        		person = getKcPersonService().getKcPersonByPersonId(this.responsibleParty);
        		if (person != null) {
        			this.respPartyUsername=person.getUserName();
        		}
        	}
        }
        return person;
    }

    /**
     * Gets the KC Person Service.
     * @return KC Person Service.
     */
    protected KcPersonService getKcPersonService() {
        return KcServiceLocator.getService(KcPersonService.class);
    }

	public List<ExconProjectAttachment> getExconProjectAttachments() {
		if (exconProjectAttachments == null) {
			exconProjectAttachments = new ArrayList<ExconProjectAttachment>();
        }
		return exconProjectAttachments;
	}

	public void setExconProjectAttachments(List<ExconProjectAttachment> exconProjectAttachments) {
		this.exconProjectAttachments = exconProjectAttachments;
	}
	
	public List<ExconProjectCustomData> getExconProjectCustomDataList() {
		if (exconProjectCustomDataList == null ) {
			exconProjectCustomDataList = new ArrayList<ExconProjectCustomData>();
		}
		return exconProjectCustomDataList;
	}
	
	public void setExconProjectCustomDataList(List<ExconProjectCustomData> exconProjectCustomDataList) {
		this.exconProjectCustomDataList=exconProjectCustomDataList;
	}

	public ExconProjectHRExtension getHrExtension() {
		if (hrExtension==null) {
			this.refreshReferenceObject("hrExtension");
		}
		return hrExtension;
	}

	public void setHrExtension(ExconProjectHRExtension hrExtension) {
		this.hrExtension = hrExtension;
	}
		 
    public void add(ExconProjectEvent newExconProjectEvent) {
    	exconProjectEvents.add(newExconProjectEvent);
    	newExconProjectEvent.setExconProject(this);
    }
    
    public void add(ExconProjectDestination newExconProjectDestination) {
    	exconProjectDestinations.add(newExconProjectDestination);
    	newExconProjectDestination.setExconProject(this);
    }
    
    public void add(ExconProjectPerson newExconProjectPerson) {
    	exconProjectPersons.add(newExconProjectPerson);
    	newExconProjectPerson.setExconProject(this);
//    	if (StringUtils.equals(newExconProjectPerson.getRoleType().getDescription(), "Traveler")) {
    		ExconProjectTraveler newTraveler=new ExconProjectTraveler();
    		newExconProjectPerson.setTraveler(newTraveler);
    		newTraveler.setPersonId(newExconProjectPerson.getPersonId().toString());
//    	}
    }
    
    public void add(ExconProjectUnitPerson newExconProjectUnitPerson) {
    	exconProjectUnitPersons.add(newExconProjectUnitPerson);
    	newExconProjectUnitPerson.setExconProject(this);
    }
    
    public void add(ExconProjectRPSEntity newExconProjectRPSEntity) {
    	exconProjectRPSEntities.add(newExconProjectRPSEntity);
    	newExconProjectRPSEntity.setExconProject(this);
    	newExconProjectRPSEntity.setConcatNames(newExconProjectRPSEntity.getFirstName()+" "+newExconProjectRPSEntity.getOtherNames()+" "+newExconProjectRPSEntity.getLastName()+" "+newExconProjectRPSEntity.getCompanyName());
    }
    
    public void deleteRPSEntity() {
    	if (exconProjectRPSEntities!=null && !exconProjectRPSEntities.isEmpty()) {
    		exconProjectRPSEntities.remove(0);
    	}
    }
    
    public void add(ExconProjectComment newExconProjectComment) {
    	exconProjectComments.add(newExconProjectComment);
    	newExconProjectComment.setExconProject(this);
    	newExconProjectComment.setCommentAuthor(GlobalVariables.getUserSession().getPrincipalName());
    	newExconProjectComment.setCommentDate(new Date(System.currentTimeMillis()));
    }
    
    public void add(ExconProjectAttachment newExconProjectAttachment) {
    	exconProjectAttachments.add(newExconProjectAttachment);
    	newExconProjectAttachment.setExconProject(this);
    }
    
    public void add(ExconProjectExternalInstitution newExconProjectExternalInstitution) {
    	exconProjectExternalInstitutions.add(newExconProjectExternalInstitution);
    	newExconProjectExternalInstitution.setExconProject(this);
    }
    
    public void add(ExconProjectReview newExconProjectReview) {
    	exconProjectReviews.add(newExconProjectReview);
    	newExconProjectReview.setExconProject(this);
    }
    
    public void add(ExconProjectAssociatedDocument newExconProjectAssociatedDocument) {
    	exconProjectAssociatedDocuments.add(newExconProjectAssociatedDocument);
    	newExconProjectAssociatedDocument.setExconProject(this);
    }
	
    public VersionHistorySearchBo getVersionHistory() {
        return versionHistory;
    }

    public void setVersionHistory(VersionHistorySearchBo versionHistory) {
        this.versionHistory = versionHistory;
    }

    public String getDocumentStatus() {
		return documentStatus;
	}

	public void setDocumentStatus(String documentStatus) {
		this.documentStatus = documentStatus;
	}

	/**.
	 * This creates exconProjectConstructor
	 */
    public ExconProject() {
        super();
        initializeCollections();
        initialize();

    }

    protected void initialize() {
    	setProjectNumber("000000");
    	setSequenceNumber(1);
		initHRExtension();
        return;
    }

	protected void initHRExtension() {
		hrExtension=new ExconProjectHRExtension();
		hrExtension.setExconProject(this);
	}

	/**.
	 * This is the Getter Method for initializeCollections
	 */
    protected void initializeCollections() {
        exconProjectPersons = new AutoPopulatingList<ExconProjectPerson>(ExconProjectPerson.class);
        exconProjectUnitPersons = new AutoPopulatingList<ExconProjectUnitPerson>(ExconProjectUnitPerson.class);
        exconProjectRPSEntities = new AutoPopulatingList<ExconProjectRPSEntity>(ExconProjectRPSEntity.class);
        exconProjectEvents = new AutoPopulatingList<ExconProjectEvent>(ExconProjectEvent.class);
        exconProjectDestinations = new AutoPopulatingList<ExconProjectDestination>(ExconProjectDestination.class);
        exconProjectComments = new AutoPopulatingList<ExconProjectComment>(ExconProjectComment.class);
        exconProjectAttachments = new AutoPopulatingList<ExconProjectAttachment>(ExconProjectAttachment.class);
        exconProjectCustomDataList = new AutoPopulatingList<ExconProjectCustomData>(ExconProjectCustomData.class);
        exconProjectExternalInstitutions = new AutoPopulatingList<ExconProjectExternalInstitution>(ExconProjectExternalInstitution.class);
        exconProjectReviews = new AutoPopulatingList<ExconProjectReview>(ExconProjectReview.class);
        exconProjectAssociatedDocuments = new AutoPopulatingList<ExconProjectAssociatedDocument>(ExconProjectAssociatedDocument.class);
    }

    protected UnitService getUnitService() {
  	  return KcServiceLocator.getService(UnitService.class);
    }
    
    protected BusinessObjectService getBusinessObjectService() {
    	return KcServiceLocator.getService(BusinessObjectService.class);
    }
    
    public boolean isLeadUnitRestricted() {
    	if (StringUtils.isEmpty(unitNumber)) {
    		return false;
    	}
    	return isUnitRestricted(unitNumber);
    }

    public boolean isUnitRestricted (String unitNumber) {
  	  UnitService unitService=getUnitService();
  	  for (Unit unit:unitService.getUnitHierarchyForUnit(unitNumber)) {
  		  for (ExconProjectRestrictedUnit restrictedUnit:getBusinessObjectService().findAll(ExconProjectRestrictedUnit.class)) {
  			  if (restrictedUnit.getUnitNumber().equals(unit.getUnitNumber())) {
  				  return true;
  			  }
  		  }
  	  }
  	  
  	  return false;
    }
    
    public static String formattedDate(Date inDate) {
    	String outStr="";
    	if (inDate!=null) {
//    		Calendar cal = Calendar.getInstance();
//    		cal.setTime(inDate);
    		SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");
    		outStr=sdf.format(inDate);
//    		outStr=Integer.toString(cal.get(Calendar.MONTH)) + "/" + Integer.toString(cal.get(Calendar.DAY_OF_MONTH)) + "/" + Integer.toString(cal.get(Calendar.YEAR));
    	}
    	return outStr;
    }
    
	/**.
     * Build an identifier map for the BOS lookup
     * @param identifierField
     * @param identifierValue
     * @return
     */
    protected Map<String, Object> getIdentifierMap(String identifierField, Object identifierValue) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(identifierField, identifierValue);
        return map;
    }

	@Override
	public String getDocumentKey() {
	//	return Permissionable.AWARD_KEY;
		return "ExconProjectDocument";
	}

	@Override
	public String getDocumentNumberForPermission() {
		return projectId != null ? projectId.toString() : "";
	}

	@Override
	public String getDocumentRoleTypeCode() {
		// return RoleConstants.AWARD_ROLE_TYPE;
		return "KC-EXCON";
	}

	@Override
	public String getLeadUnitNumber() {
		return getUnitNumber();
	}

	@Override
	public String getNamespace() {
		return "KC-EXCON"; // TODO need to switch from hardcoded
	}

	@Override
	public List<String> getRoleNames() {
        List<String> roles = new ArrayList<String>();

        SystemAuthorizationService systemAuthorizationService = KcServiceLocator.getService("systemAuthorizationService");
        List<Role> roleBOs = systemAuthorizationService.getRoles(getNamespace()); // TODO need to switch from hardcoded
        for(Role role : roleBOs) {
            roles.add(role.getName());
        }
 
        return roles;
	}

	@Override
	public void populateAdditionalQualifiedRoleAttributes(Map<String, String> qualifiedRoleAttributes) {
		String documentNumber = getExconProjectDocument() != null ? getExconProjectDocument().getDocumentNumber() : "";
        qualifiedRoleAttributes.put("documentNumber", documentNumber);
	}
	
    /**
     * @see org.kuali.kra.Sequenceable#getSequenceNumber()
     */
    public Integer getSequenceNumber() {
        return sequenceNumber;
    }

    /**
     * 
     * @param sequenceNumber
     */
    public void setSequenceNumber(Integer sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }
    
    /**
     * @see org.kuali.coeus.common.framework.version.sequence.owner.SequenceOwner#getOwnerSequenceNumber()
     */
    public Integer getOwnerSequenceNumber() {
        return null;
    }

    /**
     * @see org.kuali.coeus.common.framework.version.sequence.owner.SequenceOwner#incrementSequenceNumber()
     */
    public void incrementSequenceNumber() {
        this.sequenceNumber++;
    }

    /**
     * @see org.kuali.coeus.common.framework.version.sequence.associate.SequenceAssociate#getSequenceOwner()
     */
    public ExconProject getSequenceOwner() {
        return this;
    }

    /**
     * @see org.kuali.coeus.common.framework.version.sequence.associate.SequenceAssociate#setSequenceOwner(org.kuali.coeus.common.framework.version.sequence.owner.SequenceOwner)
     */
    public void setSequenceOwner(ExconProject newOwner) {
        // no-op
    }

    /**
     * @see org.kuali.kra.Sequenceable#resetPersistenceState()
     */
    public void resetPersistenceState() {
        this.projectId = null;
    }

    /**
     * @see org.kuali.coeus.common.framework.version.sequence.owner.SequenceOwner#getName()
     */
    public String getVersionNameField() {
        return "projectNumber";
    }

	@Override
	public String getVersionNameFieldValue() { return getProjectNumber();}

	public void setExconProjectSequenceStatus(String exconProjectSequenceStatus) {
		this.exconProjectSequenceStatus=exconProjectSequenceStatus;
		return;	
	}
	
	public String getExconProjectSequenceStatus() {
		return this.exconProjectSequenceStatus;
	}
	
    public boolean isAllowUpdateTimestampToBeReset() {
        return allowUpdateTimestampToBeReset;
    }
    
    /**
     * 
     * Setting this value to false will prevent the update timestamp field from being upddate just once.  After that, the update timestamp field will update as regular.
     * @param allowUpdateTimestampToBeReset
     */
    public void setAllowUpdateTimestampToBeReset(boolean allowUpdateTimestampToBeReset) {
        this.allowUpdateTimestampToBeReset = allowUpdateTimestampToBeReset;
    }

    @Override
    public void setUpdateTimestamp(Timestamp updateTimestamp) {
        if (isAllowUpdateTimestampToBeReset()) {
            super.setUpdateTimestamp(updateTimestamp);
        } else {
            setAllowUpdateTimestampToBeReset(true);
        }
    }


	@Override
	@SuppressWarnings("unchecked")
	protected void preUpdate() {
		super.preUpdate();
		if (getHrExtension()==null) {
			initHRExtension();
		}
		if (getHrExtension().getProjectId()==null && getProjectId()!=null) {
			getHrExtension().setProjectId(getProjectId());
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void postPersist() {
		if (getHrExtension()==null) {
			hrExtension=tempHRExtension;
			tempHRExtension=null;
			if (getHrExtension()==null) {
				initHRExtension();
			}
			getHrExtension().setExconProject(this);
			getHrExtension().setProjectId(this.getProjectId());
		}
		super.postPersist();
	}
}