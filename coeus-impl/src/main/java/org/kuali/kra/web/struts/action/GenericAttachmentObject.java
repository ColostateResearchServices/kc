package org.kuali.kra.web.struts.action;

import org.kuali.coeus.common.framework.print.AttachmentDataSource;

public class GenericAttachmentObject extends AttachmentDataSource {

    private String fileName; 
	private String contentType; 
    private byte[] attachmentData;
	
    public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public byte[] getContent() {
		return attachmentData;
	}
    public byte[] getAttachmentData() {
        return attachmentData;
    }

    public void setAttachmentData(byte[] narrativePdf) {
        this.attachmentData = narrativePdf;
    }


}

