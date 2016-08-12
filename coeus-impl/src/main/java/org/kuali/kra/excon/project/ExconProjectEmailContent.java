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

import org.apache.struts.upload.FormFile;
import org.drools.core.util.StringUtils;
import org.kuali.coeus.sys.framework.model.KcPersistableBusinessObjectBase;
import org.kuali.rice.krad.bo.PersistableAttachment;

public class ExconProjectEmailContent extends KcPersistableBusinessObjectBase implements PersistableAttachment{
    
    private static final long serialVersionUID = 1345897234587L;

    private String contentCode; 
    private String description;
    private String contentTypeCode;
    private String fileName;
    private String contentType;
    private byte[] attachmentContent;
    private transient FormFile templateFile;
    private ExconProjectContentType contentTypeObj;
    
    
    /**
     * Constructs a ExconProjectEmailContent.java.
     */
    public ExconProjectEmailContent() {
        super();
    }


	public String getContentCode() {
		return contentCode;
	}


	public void setContentCode(String contentCode) {
		this.contentCode = contentCode;
	}
	
	public String getContentCodeBody() {
		return contentCode;
	}
	
	public void setContentCodeBody(String contentCode) {
		this.contentCode = contentCode;
	}
	
	public String getContentCodeAttachment() {
		return contentCode;
	}
	
	public void setContentCodeAttachment(String contentCode) {
		this.contentCode = contentCode;
	}

	public String getContentCodeAgenda(){
		return contentCode;
	}
	
	public void setContentCodeAgenda(String contentCode){
		this.contentCode = contentCode;
	}
	
	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getContentTypeCode() {
		return contentTypeCode;
	}


	public void setContentTypeCode(String contentTypeCode) {
		this.contentTypeCode = contentTypeCode;
	}

	@Override
	public byte[] getAttachmentContent() {
		return attachmentContent;
	}


	@Override
	public void setAttachmentContent(byte[] attachmentContent) {
		this.attachmentContent=attachmentContent;
	}


	@Override
	public String getFileName() {
		return fileName;
	}


	@Override
	public void setFileName(String fileName) {
		this.fileName=fileName;
	}


	@Override
	public String getContentType() {
		return contentType;
	}


	@Override
	public void setContentType(String contentType) {
		this.contentType=contentType;
	}


	public ExconProjectContentType getContentTypeObj() {
		if (!StringUtils.isEmpty(contentTypeCode)) {
			this.refreshReferenceObject("contentTypeObj");
		}
		return contentTypeObj;
	}


	public void setContentTypeObj(ExconProjectContentType contentTypeObj) {
		this.contentTypeObj = contentTypeObj;
	}
   
	/**.
	 * This is the Getter Method for templateFile
	 * @return Returns the templateFile.
	 */
	public FormFile getTemplateFile() {
		return templateFile;
	}

	/**.
	 * This is the Setter Method for templateFile
	 * @param templateFile The templateFile to set.
	 */
	public void setTemplateFile(FormFile templateFile) {
		this.templateFile = templateFile;
	}   

   

}
