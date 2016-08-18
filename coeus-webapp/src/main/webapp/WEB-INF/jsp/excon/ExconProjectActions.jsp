<%--
 Copyright 2005-2014 The Kuali Foundation

 Licensed under the Educational Community License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.osedu.org/licenses/ECL-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
--%>
<%@ include file="/WEB-INF/jsp/kraTldHeader.jsp"%>
<%@ include file="/WEB-INF/jsp/kraExconTldHeader.jsp"%>

<kul:documentPage
	showDocumentInfo="true"
	htmlFormAction="exconProjectActions"
	documentTypeName="${KualiForm.documentTypeName}"
	renderMultipart="false"
	showTabButtons="true"
	auditCount="0"
  	headerDispatch="${KualiForm.headerDispatch}"
  	headerTabActive="exconProjectActions" >
  	
  	<div align="right">
  	    <kul:help parameterNamespace="KC-EXCON" parameterDetailType="Document" parameterName="exconProjectActionsHelpUrl" altText="help"/>    
	</div>
  	
<div id="workarea">
<kra-excon:exconProjectDataValidation />
<kra-excon:exconProjectTravelerEmail />
<kul:adHocRecipients />
<kul:routeLog /> 
<kul:panelFooter />
</div>
<kul:documentControls transactionalDocument="true" viewOnly="${KualiForm.editingMode['viewOnly']}" suppressCancelButton="false" />
<script language="javascript" src="scripts/kuali_application.js"></script>
</kul:documentPage>
