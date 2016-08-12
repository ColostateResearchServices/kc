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
<%@ include file="/WEB-INF/jsp/kraExconTldHeader.jsp" %>

<kul:documentPage
	showDocumentInfo="true"
	htmlFormAction="exconProjectCommentsAndAttachments"
	documentTypeName="${KualiForm.documentTypeName}"
	renderMultipart="true"
	showTabButtons="true"
	auditCount="0"
  	headerDispatch="${KualiForm.headerDispatch}"
  	headerTabActive="commentsAndAttachments" >
  	
  	<c:set var="readOnly" value="${not KualiForm.editingMode['fullEntry']}" scope="request" />
  	
  	<div align="right"><kul:help documentTypeName="ExconProjectDocument" pageName="Comments%20%26%20Attachments" /></div>
  	
<kra-excon:exconProjectComments/>
<kra-excon:exconProjectAttachments/>
<kul:panelFooter />


<kul:documentControls transactionalDocument="true" suppressRoutingControls="true" suppressCancelButton="true" />

</kul:documentPage>
