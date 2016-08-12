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
import org.kuali.coeus.common.framework.person.KcPersonService;
import org.kuali.coeus.sys.framework.service.KcServiceLocator;
import org.kuali.kra.excon.document.ExconProjectDocument;
import org.kuali.coeus.common.framework.mail.KcEmailService;
import org.kuali.coeus.common.framework.mail.EmailAttachment;
import org.kuali.rice.core.api.config.property.ConfigurationService;
import org.kuali.rice.core.api.membership.MemberType;
import org.kuali.rice.kim.api.group.Group;
import org.kuali.rice.kim.api.group.GroupMember;
import org.kuali.rice.kim.api.group.GroupService;
import org.kuali.rice.kns.util.KNSGlobalVariables;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.util.GlobalVariables;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * This is the base class for handling ExconProjectEvents
 */
public class ExconProjectEmailBean implements Serializable {

	private static final long serialVersionUID = 238453457L;
	protected ExconProjectEmailContent newBody;
	protected ExconProjectEmailContent newAttachment;
	protected ExconProjectEmailContent newAgenda;
    protected ExconProjectForm exconProjectForm;
    
    private transient BusinessObjectService businessObjectService;
  
    public ExconProjectEmailBean(ExconProjectForm exconProjectForm) {
        this.exconProjectForm = exconProjectForm;
        init();
    }
    
    public ExconProjectEmailContent getNewBody() {
        return newBody;
    }    

    public ExconProjectEmailContent getNewAttachment() {
    	return newAttachment;
    }
    
    public ExconProjectEmailContent getNewAgenda(){
    	return newAgenda;
    }
    
    public ExconProjectEmailContent getBodyObj() {
    	HashMap<String,String> criteriaMap=new HashMap<String,String>();
    	criteriaMap.put("contentCode", newBody.getContentCode());
    	return (ExconProjectEmailContent)getBusinessObjectService().findByPrimaryKey(ExconProjectEmailContent.class, criteriaMap);
    }
    
    public ExconProjectEmailContent getAttachmentObj() {
    	HashMap<String,String> criteriaMap=new HashMap<String,String>();
    	criteriaMap.put("contentCode", newAttachment.getContentCode());
    	return (ExconProjectEmailContent)getBusinessObjectService().findByPrimaryKey(ExconProjectEmailContent.class, criteriaMap);
    }
    public ExconProjectEmailContent getAgendaObj() {
    	HashMap<String,String> criteriaMap=new HashMap<String,String>();
    	criteriaMap.put("contentCode", newAgenda.getContentCode());
    	return (ExconProjectEmailContent)getBusinessObjectService().findByPrimaryKey(ExconProjectEmailContent.class, criteriaMap);
    }
    
    public void sendTravelerEmail() {
    	ExconProject exconProject=getExconProject();
    	String destStr="";
    	ArrayList<EmailAttachment> attachments=new ArrayList<EmailAttachment>();
    	for (ExconProjectDestination destination:exconProject.getExconProjectDestinations()) {
    		if (!StringUtils.isEmpty(destStr)) {
    			destStr+=", ";
    		}
    		destStr+=destination.getDestinationCountryName();
    	}
    	String currentEnv=getConfigurationService().getPropertyValueAsString("environment");
    	String prodEnv=getConfigurationService().getPropertyValueAsString("production.environment.code");
    	String senderEmail=getPersonService().getKcPersonByUserName(GlobalVariables.getUserSession().getPrincipalName()).getEmailAddress();
    	String recipEmail=senderEmail;
    	String prodEmail="";
    	String firstName="Traveler";
    	
    	ExconProjectPerson traveler=exconProject.getTraveler();
    	if (traveler!=null) {
    		prodEmail=traveler.getPerson().getEmailAddress();
			if (!StringUtils.isEmpty(traveler.getPerson().getFirstName())) {
				firstName=traveler.getPerson().getFirstName();
			}
    	}

    	if (currentEnv.equals(prodEnv)) {
    		recipEmail=prodEmail;
    	}
    	else
    	{
    		destStr+=" (to "+prodEmail+" in production)";
    	}
    	HashSet<String> recipSet=new HashSet<String>();
    	recipSet.add(recipEmail);
    	HashSet<String> bccSet=new HashSet<String>();
    	bccSet.add(senderEmail);
    	Group approverGroup = getGroupService().getGroupByNamespaceCodeAndName("KC-WKFLW", "Export Control Project Approvers");
    	if (approverGroup!=null) {
    		for (GroupMember member : getGroupService().getMembersOfGroup(approverGroup.getId())) {
    			if (member.isActive() && member.getType().compareTo(MemberType.PRINCIPAL)==0) {
    				bccSet.add(getPersonService().getKcPersonByPersonId(member.getMemberId()).getEmailAddress());
    			}
    		}
    	}

    	
    	EmailAttachment attachment=new EmailAttachment();
    	ExconProjectEmailContent bodyContent=getBodyObj();
    	ExconProjectEmailContent attachmentContent=getAttachmentObj();
    	attachment.setContents(attachmentContent.getAttachmentContent());
    	attachment.setFileName(attachmentContent.getFileName());
    	attachment.setMimeType(attachmentContent.getContentType());
    	attachments.add(attachment);
    	String bodyStr=new String(bodyContent.getAttachmentContent());
    	bodyStr=bodyStr.replace("[firstName]", firstName);
    	
    	getEmailService().sendEmailWithAttachments(senderEmail,recipSet, "Regarding your upcoming trip to: "+destStr, null, bccSet, bodyStr, true, attachments);
    	ExconProjectEvent newEvent=new ExconProjectEvent();
    	newEvent.setProjectEventTypeCode("TVA");
    	newEvent.setEventDate(new Date(System.currentTimeMillis()));
    	exconProject.add(newEvent);
    	
    	if (traveler!=null) {
    		ExconProjectTravelerCommunication newCommunication=new ExconProjectTravelerCommunication();
    		newCommunication.setEmailBodyContentCode(bodyContent.getContentCodeBody());
    		newCommunication.setEmailAttachmentContentCode(attachmentContent.getContentCodeAttachment());
    		newCommunication.setPersonId(traveler.getPersonId().toString());
    		newCommunication.setCommunicationDate(new Date(System.currentTimeMillis()));
    		exconProject.getTravelerCommunications().add(newCommunication);
    	}
    	
    	KNSGlobalVariables.getMessageList().add("info.exconProjectTravelerEmail.sent", recipEmail);
    	return;
    }

    private KcEmailService getEmailService() {
    	return KcServiceLocator.getService(KcEmailService.class);
    }
    
    private GroupService getGroupService() {
    	return KcServiceLocator.getService(GroupService.class);
    }
    
    private KcPersonService getPersonService() {
    	return KcServiceLocator.getService(KcPersonService.class);
    }
    
    private ConfigurationService getConfigurationService() {
    	return KcServiceLocator.getService(ConfigurationService.class);
    }

    /**
     * @return
     */
    protected BusinessObjectService getBusinessObjectService() {
        if(businessObjectService == null) {
            businessObjectService = (BusinessObjectService) KcServiceLocator.getService(BusinessObjectService.class);
        }
        return businessObjectService;
    }
    
    
    protected void init() {
        this.newBody = new ExconProjectEmailContent();
        this.newAttachment = new ExconProjectEmailContent();
        this.newAgenda = new ExconProjectEmailContent();
    }
    

    protected ExconProject getExconProject() {
        return getDocument().getExconProject();
    }
    
    protected ExconProjectDocument getDocument() {
        return exconProjectForm.getExconProjectDocument();
    }

    void setBusinessObjectService(BusinessObjectService bos) {
        businessObjectService = bos;
    }

	public void addTravelerMeetingAgenda() {
		ExconProject exconProject=getExconProject();
    	ExconProjectEvent newEvent=new ExconProjectEvent();
    	newEvent.setProjectEventTypeCode("TVA");
    	newEvent.setEventDate(new Date(System.currentTimeMillis()));
    	exconProject.add(newEvent);
    	
    	ExconProjectPerson traveler = exconProject.getTraveler();
    	ExconProjectEmailContent agendaContent = getAgendaObj();
    	if (traveler!=null) {
    		ExconProjectTravelerCommunication newCommunication = new ExconProjectTravelerCommunication();
    		newCommunication.setAgendaContentCode(agendaContent.getContentCodeBody());
    		newCommunication.setPersonId(traveler.getPersonId());
    		newCommunication.setCommunicationDate(new Date(System.currentTimeMillis()));
    		exconProject.getTravelerCommunications().add(newCommunication);
    	}
    	KNSGlobalVariables.getMessageList().add("info.exconProjectTravelerAgenda.added");
	}
}
