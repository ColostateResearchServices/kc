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
package org.kuali.kra.excon.service.impl;

import org.kuali.coeus.common.framework.version.VersionStatus;
import org.kuali.coeus.common.framework.version.history.VersionHistoryService;
import org.kuali.coeus.common.framework.version.history.VersionHistory;
import org.kuali.kra.excon.document.ExconProjectDocument;
import org.kuali.kra.excon.project.ExconProject;
import org.kuali.kra.lookup.KraLookupableHelperServiceImpl;
import org.kuali.rice.kew.api.KewApiConstants;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.HtmlData.AnchorHtmlData;
import org.kuali.rice.kns.web.ui.Field;
import org.kuali.rice.kns.web.ui.Row;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.UrlFactory;

import java.util.*;

/**
 * This class is for ExconProjectLookupableHelperServiceImpl
 * for lookup searches...
 */
public class ExconProjectLookupableHelperServiceImpl extends KraLookupableHelperServiceImpl {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6369873655376681944L;
	private static final String PROJECT_NUMBER = "projectNumber";
    private VersionHistoryService versionHistoryService; 
    
    @Override
    public List<? extends BusinessObject>
    getSearchResults(Map<String, String> fieldValues) {
        super.setBackLocationDocFormKey(fieldValues);
    //    String projectNumber = fieldValues.get(PROJECT_NUMBER);
    //    fieldValues.remove(PROJECT_NUMBER);
        fieldValues.remove(ExconProject.EXCON_SEQUENCE_STATUS_PROPERTY_STRING);
        fieldValues.put(ExconProject.EXCON_SEQUENCE_STATUS_PROPERTY_STRING, VersionStatus.ACTIVE.toString());
        List<ExconProject> unboundedResults =
                (List<ExconProject>) super.getSearchResultsUnbounded(fieldValues);

        return unboundedResults;
    }



    /**.
     * this method for getCustomActionUrls
     * @param businessObject
     * @param pkNames
     *
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<HtmlData> getCustomActionUrls(
    BusinessObject businessObject, List pkNames) {
        List<HtmlData> htmlDataList =
        super.getCustomActionUrls(businessObject, pkNames);
        htmlDataList.add(getOpenLink((ExconProject) businessObject, false));
        return htmlDataList;
    }
   /**.
    * This method is for getOpenLink
     * @param subaward
     * @param viewOnly
     * @return htmlData
     */
    protected AnchorHtmlData getOpenLink(ExconProject exconProject, Boolean viewOnly) {
        ExconProjectDocument exconProjectDocument = exconProject.getExconProjectDocument();
        AnchorHtmlData htmlData = new AnchorHtmlData();
        htmlData.setDisplayText("open");
        Properties parameters = new Properties();
        parameters.put(KRADConstants.DISPATCH_REQUEST_PARAMETER,
        KRADConstants.DOC_HANDLER_METHOD);
        parameters.put(KRADConstants.PARAMETER_COMMAND,
        KewApiConstants.DOCSEARCH_COMMAND);
        parameters.put(KRADConstants.DOCUMENT_TYPE_NAME, getDocumentTypeName());
        parameters.put("viewDocument", viewOnly.toString());
        parameters.put("docOpenedFromAwardSearch", "true");
        parameters.put("docId", exconProjectDocument.getDocumentNumber());
        parameters.put("placeHolderAwardId",
        exconProject.getProjectId().toString());
        String href = UrlFactory.parameterizeUrl(
        "../" + getHtmlAction(), parameters);
        htmlData.setHref(href);
        return htmlData;
    }

      /**.
       * This override is reset field definitions
       * @see org.kuali.core.lookup.AbstractLookupableHelperServiceImpl#getRows()
       */
      @Override
    public List<Row> getRows() {
        List<Row> rows = super.getRows();
        for (Row row : rows) {
            for (Field field : row.getFields()) {

                if (field.getPropertyName().equals("projectStartDate") || field.getPropertyName().equals("projectEndDate")) {
                    field.setDatePicker(true);
                }
            }
        }
        return rows;
    }
      @Override
      protected void addEditHtmlData(List<HtmlData> htmlDataList, BusinessObject businessObject) {
          //no-op
      }

      protected List<ExconProject> filterForActiveExconProjects(
              Collection<ExconProject> collectionByQuery, String projectNumber, String statusCode) throws WorkflowException {
          Set<String> exconProjectCodes = new TreeSet<String>();
          List<Integer> exconProjectCodeList = new ArrayList<Integer>();
          List<String> exconProjectCodeSortedList = new ArrayList<String>();
          for (ExconProject exconProject: collectionByQuery) {
              exconProjectCodes.add(exconProject.getProjectNumber());
          }
          for (String exconProjectCode: exconProjectCodes) {
              exconProjectCodeSortedList.add(exconProjectCode);
          }
          Collections.sort(exconProjectCodeSortedList);
          List<ExconProject> activeExconProjects = new ArrayList<ExconProject>();
          for (String versionName: exconProjectCodeSortedList) {
              VersionHistory versionHistory = versionHistoryService.
              findActiveVersion(ExconProject.class, versionName);
              if (versionHistory != null) {
                  ExconProject activeExconProject =
                      (ExconProject) versionHistory.getSequenceOwner();
                  if (activeExconProject != null) {
                      activeExconProjects.add(activeExconProject);
                  }
              }
          }
          
        return activeExconProjects;
    }

      @Override
      protected String getHtmlAction() {
          return "exconProjectHome.do";
      }

    @Override
    protected String getDocumentTypeName() {
        return "ExconProjectDocument";
    }

    @Override
    protected String getKeyFieldName() {
        return "projectId";
    }
    /**
     * This Method is for setVersionHistoryService
     * @param versionHistoryService
     */
    public void setVersionHistoryService(
    VersionHistoryService versionHistoryService) {
       this.versionHistoryService = versionHistoryService;
    }
}
