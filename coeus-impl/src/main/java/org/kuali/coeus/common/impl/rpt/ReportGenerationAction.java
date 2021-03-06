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
package org.kuali.coeus.common.impl.rpt;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.eclipse.birt.report.engine.api.*;
import org.eclipse.birt.report.model.api.DesignElementHandle;
import org.eclipse.birt.report.model.api.ReportDesignHandle;
import org.kuali.coeus.common.impl.rpt.cust.CustReportDetails;
import org.kuali.coeus.sys.framework.gv.GlobalVariableService;
import org.kuali.coeus.sys.framework.service.KcServiceLocator;
import org.kuali.coeus.sys.framework.validation.ErrorReporter;
import org.kuali.kra.infrastructure.Constants;
import org.kuali.kra.infrastructure.KeyConstants;
import org.kuali.rice.core.api.config.property.ConfigurationService;
import org.kuali.rice.core.api.datetime.DateTimeService;
import org.kuali.rice.kns.util.WebUtils;
import org.kuali.rice.krad.service.BusinessObjectService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.file.*;
import java.nio.file.attribute.FileAttribute;
import java.util.*;

import static org.kuali.kra.infrastructure.Constants.MAPPING_BASIC;




public class ReportGenerationAction extends ReportGenerationBaseAction {

    ConfigurationService configurationService;
    GlobalVariableService globalVariableService;
    /**
     * sets report parameters to action form     
     * @param mapping the ActionMapping
     * @param form the ActionForm
     * @param request the Request
     * @param response the Response     
     * @return ActionForward     
     */
    public ActionForward getReportParametersFromDesign(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
       
        ReportGenerationForm reportGenerationForm = (ReportGenerationForm) form;
        List<BirtParameterBean> parameterList = new ArrayList<BirtParameterBean>();
        if (request.getParameter("reportId") != null) {
            parameterList = KcServiceLocator.getService(BirtReportService.class).getInputParametersFromTemplateFile(
                    request.getParameter("reportId"));
            reportGenerationForm.setReportParameterList(parameterList);
            reportGenerationForm.setReportId(request.getParameter("reportId"));
            reportGenerationForm.setReportName(request.getParameter("reportLabel"));
        }
        return mapping.findForward(MAPPING_BASIC); 
    }

    private String addUserID(String reportDesignText, String userID) {
        int insertPoint=reportDesignText.indexOf(">", reportDesignText.indexOf("<report"))+1;
        String reportDesignStart=reportDesignText.substring(0,insertPoint);
        String reportDesignEnd=reportDesignText.substring(insertPoint);
        String userIDProperty="\n\t<property name=\"requesterId\">"+userID+"</property>";
        return reportDesignStart+userIDProperty+reportDesignEnd;

    }

    private String replaceTag(String tagBody, String tagStart, String tagEnd, String replaceValue) {
        String tagTextStart=tagBody.substring(0,tagBody.indexOf(tagStart)+tagStart.length()+1);
        String tagTextEnd=tagBody.substring(tagBody.indexOf(tagEnd));
        return tagTextStart+"<value type=\"constant\">"+replaceValue+"</value>"+tagTextEnd;
    }

    private String replaceParameter(String reportDesignText, String parameterName, String replaceValue) {
        // <value type="constant">aakers@colostate.edu</value>
        int parameterLocation=-1;
        StringBuffer newDesignBuffer=new StringBuffer();
        if ((parameterLocation=reportDesignText.indexOf("<scalar-parameter name=\""+parameterName+"\""))>-1) {
            String reportDesignTextStart = reportDesignText.substring(0, reportDesignText.indexOf(">", parameterLocation) + 1);
            newDesignBuffer.append(reportDesignTextStart);

            String parameterTagBody = reportDesignText.substring(reportDesignText.indexOf(">", parameterLocation) + 1, (parameterLocation = reportDesignText.indexOf("</scalar-parameter", parameterLocation)));

            parameterTagBody = replaceTag(parameterTagBody, "<simple-property-list name=\"defaultValue\">", "</simple-property-list>", replaceValue);
            newDesignBuffer.append(parameterTagBody);


            String reportDesignTextEnd = reportDesignText.substring(parameterLocation);
            newDesignBuffer.append(reportDesignTextEnd);
            reportDesignText = newDesignBuffer.toString();
        }
        return reportDesignText;
    }

    private String addDataSource(String reportDesignText, String dataSourceName, String configParmPrefix) {
        int dataSourceLocation=-1;
        if ((dataSourceLocation=reportDesignText.indexOf("jdbc\" name=\""+dataSourceName+"\""))>-1) {
            String reportDesignTextStart=reportDesignText.substring(0,reportDesignText.indexOf(">",dataSourceLocation)+1);
            String reportDesignTextEnd=reportDesignText.substring(reportDesignText.indexOf("</oda-data-source",dataSourceLocation));
            StringBuffer sb=new StringBuffer();
            sb.append("\n\t\t<property name=\"odaDriverClass\">"+getConfigurationService().getPropertyValueAsString(configParmPrefix+".driver.name")+"</property>\n");
            sb.append("\t\t<property name=\"odaURL\">"+getConfigurationService().getPropertyValueAsString(configParmPrefix+".url")+"</property>\n");
            sb.append("\t\t<property name=\"odaUser\">"+getConfigurationService().getPropertyValueAsString(configParmPrefix+".username")+"</property>\n");
            String encryptedPassword=Base64.getEncoder().encodeToString(getConfigurationService().getPropertyValueAsString(configParmPrefix+".password").getBytes());
            sb.append("\t\t<encrypted-property name=\"odaPassword\" encryptionID=\"base64\">"+encryptedPassword+"</encrypted-property>\n\t");
            reportDesignText=reportDesignTextStart+sb.toString()+reportDesignTextEnd;
        }
        return reportDesignText;
    }

            /*
        <oda-data-source extensionID="org.eclipse.birt.report.data.oda.jdbc" name="Data Source" id="27">
            <property name="odaDriverClass">oracle.jdbc.OracleDriver</property>
            <property name="odaURL">jdbc:oracle:thin:@isdbt301.is.colostate.edu:1524:kfstrain</property>
            <property name="odaUser">kcuser</property>
            <encrypted-property name="odaPassword" encryptionID="base64">Y2FzZXlvMTA=</encrypted-property>
        </oda-data-source>
         */

    public ActionForward proxyReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
//        String reportId = request.getParameter("custReportDetails.reportLabelDisplay");
        String reportId = request.getParameter("reportId");
        if (reportId.equalsIgnoreCase("0")) {
            (KcServiceLocator.getService(ErrorReporter.class)).reportError("custReportDetails.reportLabelDisplay",
                    KeyConstants.INVALID_BIRT_REPORT, "select");
            return mapping.findForward(MAPPING_BASIC);
        }
        CustReportDetails reportDetails = KcServiceLocator.getService(BusinessObjectService.class).findBySinglePrimaryKey(
                CustReportDetails.class, reportId);
        String reportDesignDoc=new String(reportDetails.getAttachmentContent());

        String birtReplaceDatasource=getConfigurationService().getPropertyValueAsString("birt.replace.datasource");
        String sessionUserID = getGlobalVariableService().getUserSession().getPrincipalName();
        if (!"false".equalsIgnoreCase(birtReplaceDatasource)) {
            reportDesignDoc = addDataSource(reportDesignDoc, "ResearchDataSource", "datasource");
            reportDesignDoc = addDataSource(reportDesignDoc, "RiceDataSource", "server.datasource");
        }
        String fullPermission = reportDetails.getPermissionName();
        String namespaceCode = "KC-UNT";
        String permission = fullPermission;
        if (fullPermission.contains(":")) {
            namespaceCode = fullPermission.substring(0, fullPermission.indexOf(':'));
            permission = fullPermission.substring(fullPermission.indexOf(':') + 1);
        }
        reportDesignDoc = replaceParameter(reportDesignDoc,"requesterId",sessionUserID);
        reportDesignDoc = replaceParameter(reportDesignDoc,"reportPermNamespace",namespaceCode);
        reportDesignDoc = replaceParameter(reportDesignDoc,"reportPermName",permission);
        String birtRequireSecurity=getConfigurationService().getPropertyValueAsString("birt.require.security");
        String reportSuffix="";

        if (!"false".equalsIgnoreCase(birtRequireSecurity) && reportDetails.getPermissionName()!=null) {
            String userID = getGlobalVariableService().getUserSession().getLoggedInUserPrincipalName();
 //           reportSuffix="."+userID.replace('@','_').replace('.','_');
            reportSuffix="."+Base64.getEncoder().encodeToString(userID.getBytes()).replace("=","");
        }

        String birtReportsDir=getConfigurationService().getPropertyValueAsString("birt.reports.dir");
        String birtViewerUrl=getConfigurationService().getPropertyValueAsString("birt.viewer.url");
        Path path = Paths.get(birtReportsDir);

        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (Exception e) {
                //fail to create directory
                e.printStackTrace();
            }
        }
        String fullFileName=reportDetails.getFileName()+reportSuffix;
        path=Paths.get(birtReportsDir+"/"+fullFileName);
        try {
            Files.deleteIfExists(path);
            BufferedWriter bw=Files.newBufferedWriter(path, StandardOpenOption.CREATE_NEW);
            bw.write(reportDesignDoc);
            bw.flush();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ActionForward birtViewer=new ActionForward();
        birtViewer.setPath(birtViewerUrl+fullFileName);
        birtViewer.setRedirect(true);
        return birtViewer;


      //  return mapping.findForward(MAPPING_BASIC);
    }
    
    /**
     * prints the selected report     
     * @param mapping the ActionMapping
     * @param form the ActionForm
     * @param request the Request
     * @param response the Response     
     * @return ActionForward     
     */
    public ActionForward printReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
       
        InputStream reportDesignInputStream;
        ByteArrayOutputStream birtFilePrintArrayOutputStream = new ByteArrayOutputStream();
        IReportRunnable iReportRunnableDesign;
        ReportDesignHandle designHandle;  
        int birtCounter = 0;
        String reportId = request.getParameter("custReportDetails.reportLabelDisplay");
        if (reportId.equalsIgnoreCase("0")) {
            (KcServiceLocator.getService(ErrorReporter.class)).reportError("custReportDetails.reportLabelDisplay",
                    KeyConstants.INVALID_BIRT_REPORT, "select");
            return mapping.findForward(MAPPING_BASIC);
        }       

        String printReportNameAndExtension = Constants.PDF_FILE_EXTENSION;

        ReportGenerationForm reportGenerationForm = (ReportGenerationForm) form;
        reportDesignInputStream = KcServiceLocator.getService(BirtReportService.class).getReportDesignFileStream(reportId);
        iReportRunnableDesign = BirtHelper.getEngine().openReportDesign(reportDesignInputStream);
        designHandle = (ReportDesignHandle) iReportRunnableDesign.getDesignHandle();
        if (designHandle.getDataSources().canContain(BirtHelper.getDataSourceHandle())) {
            designHandle.getDataSources().add(BirtHelper.getDataSourceHandle());
        }

        designHandle.close();
        iReportRunnableDesign.setDesignHandle(designHandle);
        IRunAndRenderTask reportTask = BirtHelper.getEngine().createRunAndRenderTask(iReportRunnableDesign);
        HashMap<String, Object> parameters = new HashMap<>();
        List<BirtParameterBean> parameterList = KcServiceLocator.getService(BirtReportService.class).getInputParametersFromTemplateFile(reportId);
        CustReportDetails reportDetails = KcServiceLocator.getService(BusinessObjectService.class).findBySinglePrimaryKey(
                CustReportDetails.class, reportId);
        reportGenerationForm.setReportParameterList(parameterList);
        reportGenerationForm.setReportId(reportId);
        reportGenerationForm.setReportName(reportDetails.getReportLabel());

        for (BirtParameterBean parameterBean : parameterList) {
            if (parameterBean.getDataType() == Constants.DATE_TIME_TYPE) {
                try {
                      Date inputDate = KcServiceLocator.getService(DateTimeService.class).convertToDateTime( request.getParameter("reportParameterList[" + birtCounter + "].inputParameterText"));
                      parameters.put(parameterBean.getName(), inputDate);
                } catch (Exception exception) {
                    (KcServiceLocator.getService(ErrorReporter.class)).reportError("reportParameterList[0].inputParameterText",
                            KeyConstants.REPORT_INPUT_PARAMETER_DATE_TYPE, "select");
                    return mapping.findForward(MAPPING_BASIC);
                }
            } else {
                parameters.put(parameterBean.getName(),
                        request.getParameter("reportParameterList[" + birtCounter + "].inputParameterText"));
            }
            birtCounter = birtCounter + 1;
        }
       
        HashMap contextMap = new HashMap();
        reportTask.setAppContext(contextMap);
        reportTask.setParameterValues(parameters);
        boolean isValid = reportTask.validateParameters();
        if (!isValid) {
            (KcServiceLocator.getService(ErrorReporter.class)).reportError("reportParameterList[0].inputParameterText",
                    KeyConstants.ERROR_BIRT_REPORT_INPUT_MISSING, "select");
        } else {
            final RenderOption renderOption;
            final String printReportFormat;
            if (reportGenerationForm.getReportFormat().equalsIgnoreCase(Constants.REPORT_FORMAT_PDF)) {
                renderOption = new PDFRenderOption();
                printReportFormat = Constants.PDF_REPORT_CONTENT_TYPE;
                printReportNameAndExtension = reportDetails.getReportLabel() + Constants.PDF_FILE_EXTENSION;
                renderOption.setOutputFormat(reportGenerationForm.getReportFormat());
            } else if (reportGenerationForm.getReportFormat().equalsIgnoreCase(Constants.REPORT_FORMAT_HTML)) {
                renderOption = new HTMLRenderOption();
                printReportFormat = Constants.HTML_REPORT_CONTENT_TYPE;
                printReportNameAndExtension = reportDetails.getReportLabel() + Constants.REPORT_FORMAT_HTML_EXTENSION;
                renderOption.setOutputFormat(reportGenerationForm.getReportFormat());
            } else if (reportGenerationForm.getReportFormat().equalsIgnoreCase(Constants.REPORT_FORMAT_EXCEL)) {
                renderOption = new EXCELRenderOption();
                printReportFormat = Constants.EXCEL_REPORT_CONTENT_TYPE;
                printReportNameAndExtension = reportDetails.getReportLabel() + Constants.REPORT_FORMAT_EXCEL_EXTENSION;
                renderOption.setOutputFormat("xls");
            } else {
                printReportFormat = Constants.PDF_REPORT_CONTENT_TYPE;
                renderOption = new PDFRenderOption();
            }

            renderOption.setOutputStream(birtFilePrintArrayOutputStream);
            reportTask.setRenderOption(renderOption);
            reportTask.run();
            WebUtils.saveMimeOutputStreamAsFile(response, printReportFormat, birtFilePrintArrayOutputStream,
                    printReportNameAndExtension);
            
            reportTask.close();
            DesignElementHandle designElementHandle = designHandle.findDataSource(Constants.BIRT_DATA_SOURCE);
            designHandle.getDataSources().dropAndClear(designElementHandle);
            designHandle.clearAllProperties();
        }
        return mapping.findForward(MAPPING_BASIC);
    }
    
    public ActionForward close(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return super.close(mapping, form, request, response);
    }
    
    public BirtReportService getBirtReportService() {
        return KcServiceLocator.getService(BirtReportService.class);
    }

    public ConfigurationService getConfigurationService() {
        if (configurationService == null) {
            configurationService =  KcServiceLocator.getService(ConfigurationService.class);
        }
        return configurationService;
    }

    public void setConfigurationService(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    public GlobalVariableService getGlobalVariableService() {
        if (globalVariableService == null ) {
            globalVariableService = KcServiceLocator.getService(GlobalVariableService.class);
        }
        return globalVariableService;
    }

    public void setGlobalVariableService(GlobalVariableService globalVariableService) {
        this.globalVariableService = globalVariableService;
    }
}
