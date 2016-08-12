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

import org.drools.core.util.StringUtils;
import org.kuali.coeus.sys.framework.model.KcPersistableBusinessObjectBase;
import org.kuali.coeus.sys.framework.service.KcServiceLocator;
import org.kuali.rice.krad.service.BusinessObjectService;

import java.sql.Date;

public class ExconProjectTravelerCommunication extends KcPersistableBusinessObjectBase{
    
    private static final long serialVersionUID = 13452345234587L;
    private Long projectTravelerComId;
    private String personId; 
    private Date communicationDate;
    private String emailBodyContentCode;
    private String emailAttachmentContentCode;
    private String agendaContentCode;
    private ExconProjectEmailContent emailBodyContent;
    private ExconProjectEmailContent emailAttachmentContent;
    private ExconProjectEmailContent agendaContent;
    private String travelComment;
	private ExconProjectTraveler traveler;
    
    
    /**
     * Constructs a ExconProjectTravelerCommunication.java.
     */
    public ExconProjectTravelerCommunication() {
    }

    public Long getProjectTravelerComId(){
    	return projectTravelerComId;
    }
    
    public void setProjectTravelerComId(Long projectTravelerComId){
    	this.projectTravelerComId = projectTravelerComId;
    }

	public String getPersonId() {
		return personId;
	}


	public void setPersonId(String personId) {
		this.personId = personId;
	}
	
	public Date getCommunicationDate() {
		return communicationDate;
	}
	
	public String getCommunicationDateStr() {
		return ExconProject.formattedDate(communicationDate);
	}
	
	public void setCommunicationDate(Date communicationDate) {
		this.communicationDate = communicationDate;
	}
	
	public String getEmailBodyContentCode() {
		return emailBodyContentCode;
	}
	
	public void setEmailBodyContentCode(String emailBodyContentCode) {
		this.emailBodyContentCode = emailBodyContentCode;
	}


	public String getEmailAttachmentContentCode() {
		return emailAttachmentContentCode;
	}


	public void setEmailAttachmentContentCode(String emailAttachmentContentCode) {
		this.emailAttachmentContentCode = emailAttachmentContentCode;
	}


	public String getAgendaContentCode() {
		return agendaContentCode;
	}

	public void setAgendaContentCode(String agendaContentCode) {
		this.agendaContentCode = agendaContentCode;
	}

	public ExconProjectEmailContent getEmailBodyContent() {
		if (!StringUtils.isEmpty(emailBodyContentCode)) {
			emailBodyContent=getBusinessObjectService().findBySinglePrimaryKey(ExconProjectEmailContent.class, emailBodyContentCode);
		}
		return emailBodyContent;
	}

	public void setEmailBodyContent(ExconProjectEmailContent emailBodyContent) {
		this.emailBodyContent = emailBodyContent;
	}

	public ExconProjectEmailContent getEmailAttachmentContent() {
		if (!StringUtils.isEmpty(emailAttachmentContentCode)) {
			emailAttachmentContent=getBusinessObjectService().findBySinglePrimaryKey(ExconProjectEmailContent.class, emailAttachmentContentCode);
		}		
		return emailAttachmentContent;
	}

	public void setEmailAttachmentContent(ExconProjectEmailContent emailAttachmentContent) {
		this.emailAttachmentContent = emailAttachmentContent;
	}

	public ExconProjectEmailContent getAgendaContent() {
		if (!StringUtils.isEmpty(agendaContentCode)) {
			agendaContent=getBusinessObjectService().findBySinglePrimaryKey(ExconProjectEmailContent.class, agendaContentCode);
		}
		return agendaContent;
	}

	public void setAgendaContent(ExconProjectEmailContent agendaContent) {
		this.agendaContent = agendaContent;
	}
	
	public String getDescription() {
		ExconProjectEmailContent contentObj=getAgendaContent();
		if (contentObj!=null) {
			return contentObj.getDescription();
		}
		else
		{
			contentObj=getEmailBodyContent();
			if (contentObj!=null) {
				return contentObj.getDescription()+"/"+getEmailAttachmentContent().getDescription();
			}
		}
		return "No Description";
	}

	public String getTravelComment() {
		return travelComment;
	}


	public void setTravelComment(String travelComment) {
		this.travelComment = travelComment;
	}


	public void setTraveler(ExconProjectTraveler traveler) {
		this.traveler = traveler;
	}
	
	public ExconProjectTraveler getTraveler() {
		return traveler;
	}
	
	private BusinessObjectService getBusinessObjectService() {
		return KcServiceLocator.getService(BusinessObjectService.class);
	}
}
