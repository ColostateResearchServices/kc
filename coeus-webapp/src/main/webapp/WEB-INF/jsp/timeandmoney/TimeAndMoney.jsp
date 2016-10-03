<%--
   - Kuali Coeus, a comprehensive research administration system for higher education.
   - 
   - Copyright 2005-2016 Kuali, Inc.
   - 
   - This program is free software: you can redistribute it and/or modify
   - it under the terms of the GNU Affero General Public License as
   - published by the Free Software Foundation, either version 3 of the
   - License, or (at your option) any later version.
   - 
   - This program is distributed in the hope that it will be useful,
   - but WITHOUT ANY WARRANTY; without even the implied warranty of
   - MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   - GNU Affero General Public License for more details.
   - 
   - You should have received a copy of the GNU Affero General Public License
   - along with this program.  If not, see <http://www.gnu.org/licenses/>.
--%>
<%@ include file="/WEB-INF/jsp/kraTldHeader.jsp"%>


  <kul:documentPage
	showDocumentInfo="true"
	htmlFormAction="timeAndMoney"
	documentTypeName="TimeAndMoneyDocument"
	renderMultipart="false"
	showTabButtons="true"
	auditCount="0"
  	headerDispatch="${KualiForm.headerDispatch}"
  	headerTabActive="timeAndMoney"
  	extraTopButtons="${KualiForm.extraTopButtons}" >
  	
<c:set var="readOnly" value="${not KualiForm.editingMode['fullEntry']}" scope="request" />


<script language="JavaScript" type="text/javascript" src="dwr/util.js"></script>
	
<link rel="stylesheet" href="css/jquery/screen.css" type="text/css" />
<link rel="stylesheet" href="css/jquery/new_kuali.css" type="text/css" />
<link rel="stylesheet" href="css/jquery/kuali-stylesheet.css" type="text/css" />
<link rel="stylesheet" href="css/jquery/jquery.treeview.css" type="text/css" />
	<link rel="stylesheet" href="css/jquery/new_kuali.css" type="text/css" />
	<link rel="stylesheet" href="css/jquery/kuali-stylesheet.css" type="text/css" />
	<link rel="stylesheet" href="css/jquery/jquery.treeview.css" type="text/css" />

<script type="text/javascript" src="scripts/jquery/jquery.treeview.js"></script>

<c:set var="acctNum" value="${KualiForm.document.award.accountNumber}" />
<c:set var="docNum" value="${KualiForm.document.documentNumber}" />
<c:set var="wfDocStatus" value="${KualiForm.workflowDocument.status.code}" />
<c:if test="${wfDocStatus == 'F'}">
  <c:choose>
	  <c:when test="${acctNum != null}">
		  <div align="center"><a href="${ConfigProperties.csu.kc.budget.util.url}?TMDocNum=${docNum}&AccNum=${acctNum}" target="budgetWindow"><img src="${ConfigProperties.kra.externalizable.images.url}tinybutton1-modifybudget.gif"></a></div>
	  </c:when>
	  <c:otherwise>
		  <div style="color:#FF0000;text-align:center">No Account Number</div>
	  </c:otherwise>
  </c:choose>
</c:if>
<div align="right"><kul:help documentTypeName="TimeAndMoneyDocument" pageName="Time And Money" /></div>
<kul:documentOverview editingMode="${KualiForm.editingMode}" />

<kra-timeandmoney:awardHierarchyTimeAndMoney />
	<c:choose>
		<c:when test="${KualiForm.directIndirectViewEnabled == '1'}">	
			<kra-timeandmoney:directIndirectTransactions />
		</c:when>
		<c:otherwise>
			<kra-timeandmoney:transactions />
		</c:otherwise>
	</c:choose>
<kra-a:awardDirectFnAFundsDistribution />
<kra-timeandmoney:summary />

<kul:adHocRecipients />
<kul:routeLog />
<kul:panelFooter />

<SCRIPT type="text/javascript">
var kualiForm = document.forms['KualiForm'];
var kualiElements = kualiForm.elements;
</SCRIPT>
<script language="javascript" src="scripts/kuali_application.js"></script>

<c:if test="${readOnly && KualiForm.displayEditButton}">
	<c:set var="extraButtonSource" value="${ConfigProperties.kra.externalizable.images.url}buttonsmall_edit_temp.gif"/>
	<c:set var="extraButtonProperty" value="methodToCall.editOrVersion"/>
	<c:set var="extraButtonAlt" value="Edit or Version"/>
</c:if>

<c:if test="${readOnly}">
    <c:set var="extraButtons" value="${KualiForm.extraButtons}" scope="request"/>
</c:if>

<kul:documentControls transactionalDocument="true" suppressRoutingControls="false"
													extraButtons="${extraButtons}"
													extraButtonSource="${extraButtonSource}" 
													extraButtonProperty="${extraButtonProperty}"
													extraButtonAlt="${extraButtonAlt}" />

</kul:documentPage>
