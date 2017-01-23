/*
 * Kuali Coeus, a comprehensive research administration system for higher education.
 * 
 * Copyright 2005-2016 Kuali, Inc.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.kuali.coeus.common.impl.rpt.cust;

import org.apache.struts.upload.FormFile;
import org.kuali.coeus.sys.framework.model.KcPersistableBusinessObjectBase;
import org.kuali.rice.krad.bo.PersistableAttachment;

public class CustReportDetails extends KcPersistableBusinessObjectBase implements PersistableAttachment{
    
    private static final long serialVersionUID = 1L;

    private Integer reportId; 
    
    private String reportLabel; 
    
    private String reportLabelDisplay;
    
    private String reportDescription; 
    
    private Integer reportTypeCode; 
    
    private String permissionName;
    
    private String fileName;
    
    private String contentType;
    
    private byte[] attachmentContent; 
    
    private CustReportType custReportType;
    
    private transient FormFile templateFile; 
    
    
    public CustReportDetails() { 

    }

    /**
     * Gets the reportId attribute. 
     * @return Returns the reportId.
     */
    public Integer getReportId() {
        return reportId;
    }

    /**
     * Sets the reportId attribute value.
     * @param reportId The reportId to set.
     */
    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }

    /**
     * Gets the reportLabel attribute. 
     * @return Returns the reportLabel.
     */
    public String getReportLabel() {
        return reportLabel;
    }

    /**
     * Sets the reportLabel attribute value.
     * @param reportLabel The reportLabel to set.
     */
    public void setReportLabel(String reportLabel) {
        this.reportLabel = reportLabel;
    }

    /**
     * Gets the reportDescription attribute. 
     * @return Returns the reportDescription.
     */
    public String getReportDescription() {
        return reportDescription;
    }

    /**
     * Sets the reportDescription attribute value.
     * @param reportDescription The reportDescription to set.
     */
    public void setReportDescription(String reportDescription) {
        this.reportDescription = reportDescription;
    }

    /**
     * Gets the reportTypeCode attribute. 
     * @return Returns the reportTypeCode.
     */
    public Integer getReportTypeCode() {
        return reportTypeCode;
    }

    /**
     * Sets the reportTypeCode attribute value.
     * @param reportTypeCode The reportTypeCode to set.
     */
    public void setReportTypeCode(Integer reportTypeCode) {
        this.reportTypeCode = reportTypeCode;
    }

    /**
     * Gets the name attribute. 
     * @return Returns the name.
     */
    public String getPermissionName() {
        return permissionName;
    }

    /**
     * Sets the name attribute value.
     * @param name The name to set.
     */
    public void setPermissionName(String permissionName) {
            this.permissionName = permissionName;
    }

    /**
     * Gets the fileName attribute. 
     * @return Returns the fileName.
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Sets the fileName attribute value.
     * @param fileName The fileName to set.
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Gets the contentType attribute. 
     * @return Returns the contentType.
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * Sets the contentType attribute value.
     * @param contentType The contentType to set.
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * Gets the attachmentContent attribute. 
     * @return Returns the attachmentContent.
     */
    public byte[] getAttachmentContent() {
        return attachmentContent;
    }

    private String stripDataSource(String reportDesignText, String dataSourceName) {
        int dataSourceLocation=-1;
        if ((dataSourceLocation=reportDesignText.indexOf("jdbc\" name=\""+dataSourceName+"\""))>-1) {
            String reportDesignTextStart=reportDesignText.substring(0,reportDesignText.indexOf(">",dataSourceLocation)+1);
            String reportDesignTextEnd=reportDesignText.substring(reportDesignText.indexOf("</oda-data-source",dataSourceLocation));
            reportDesignText=reportDesignTextStart+reportDesignTextEnd;
        }
        return reportDesignText;
    }

    private String stripTag(String tagBody, String tagStart, String tagEnd) {
        String tagTextStart=tagBody.substring(0,tagBody.indexOf(tagStart));
        String tagTextEnd=tagBody.substring(tagBody.indexOf(tagEnd)+tagEnd.length());
        return tagTextStart+tagTextEnd;
    }

    private String stripDefaults(String reportDesignText) {
        int parameterLocation=-1;
        int lastParameterLocation=0;
        StringBuffer newDesignBuffer=new StringBuffer();
        while ((parameterLocation=reportDesignText.indexOf("<scalar-parameter",lastParameterLocation))>-1) {
            String reportDesignTextStart=reportDesignText.substring(lastParameterLocation,reportDesignText.indexOf(">",parameterLocation)+1);
            newDesignBuffer.append(reportDesignTextStart);

            String parameterTagBody=reportDesignText.substring(reportDesignText.indexOf(">",parameterLocation)+1,(lastParameterLocation=reportDesignText.indexOf("</scalar-parameter",parameterLocation)));

            if (parameterTagBody.indexOf("removeDefault")>-1) {
                parameterTagBody=stripTag(parameterTagBody,"<simple-property-list name=\"defaultValue\">","</simple-property-list>");
                parameterTagBody=parameterTagBody.concat("");
            }
            newDesignBuffer.append(parameterTagBody);

        }
        String reportDesignTextEnd=reportDesignText.substring(lastParameterLocation);
        newDesignBuffer.append(reportDesignTextEnd);
        return newDesignBuffer.toString();
    }

    /**
     * Sets the attachmentContent attribute value.
     * @param attachmentContent The attachmentContent to set.
     */
    public void setAttachmentContent(byte[] attachmentContent) {
        if (attachmentContent!=null && this.attachmentContent==null) {
            String contentStr = new String(attachmentContent);
            contentStr = stripDataSource(contentStr,"ResearchDataSource");
            contentStr = stripDataSource(contentStr,"RiceDataSource");
            contentStr = stripDefaults(contentStr);
            attachmentContent=contentStr.getBytes();
        }
        this.attachmentContent = attachmentContent;
    }

    /**
     * Gets the custReportType attribute. 
     * @return Returns the custReportType.
     */
    public CustReportType getCustReportType() {
        return custReportType;
    }

    /**
     * Sets the custReportType attribute value.
     * @param custReportType The custReportType to set.
     */
    public void setCustReportType(CustReportType custReportType) {
        this.custReportType = custReportType;
    }

    /**
     * Gets the templateFile attribute. 
     * @return Returns the templateFile.
     */
    public FormFile getTemplateFile() {
        return templateFile;
    }

    /**
     * Sets the templateFile attribute value.
     * @param templateFile The templateFile to set.
     */
    public void setTemplateFile(FormFile templateFile) {
        this.templateFile = templateFile;
    }

    /**
     * Sets the reportLabelDisplay attribute value.
     * @param reportLabelDisplay The reportLabelDisplay to set.
     */
    public void setReportLabelDisplay(String reportLabelDisplay) {
        this.reportLabelDisplay = reportLabelDisplay;
    }

    /**
     * Gets the reportLabelDisplay attribute. 
     * @return Returns the reportLabelDisplay.
     */
    public String getReportLabelDisplay() {
        return reportLabelDisplay;
    }
}
