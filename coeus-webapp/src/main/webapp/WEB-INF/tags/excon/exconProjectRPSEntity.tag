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

<c:set var="exconProjectAttributes" value="${DataDictionary.ExconProject.attributes}" />
<c:set var="RPSEntityAttributes" value="${DataDictionary.ExconProjectRPSEntity.attributes}" />

<c:set var="readOnly" value="${not KualiForm.editingMode['fullEntry']}" scope="request" />
<c:set var="action" value="exconProjectContacts" />
<c:set var="rpsEntitySaved" value="${KualiForm.exconProjectRPSEntitiesBean.savedRPSEntity != null}" />

<kul:tab tabTitle="RPS" defaultOpen="true">
	
	<div class="tab-container" align="center">
	<h3>
		<span class="subhead-left">Person/Company Information</span>  		
		<span class="subhead-right"><kul:help parameterNamespace="KC-EXCON" parameterDetailType="Document" parameterName="exconProjectContactsHelpUrl" altText="help"/></span>
	</h3>
<table cellpAdding="0" cellspacing="0" summary="">
  	<tr>
    	<th width="25%">
    		<div align="right">
        		<kul:htmlAttributeLabel attributeEntry="${RPSEntityAttributes.firstName}" />
      		</div>
      	</th>
    	<td width="25%">
            <kul:htmlControlAttribute property="exconProjectRPSEntitiesBean.rpsEntity.firstName" attributeEntry="${RPSEntityAttributes.firstName}" readOnly="${readOnly || rpsEntitySaved}"/>
    	</td>
    	<th width="25%">
    		<div align="right">
    			<kul:htmlAttributeLabel attributeEntry="${RPSEntityAttributes.lastName}" />
    		</div>
    	</th>
    	<td width="25%">
    		<kul:htmlControlAttribute property="exconProjectRPSEntitiesBean.rpsEntity.lastName" attributeEntry="${RPSEntityAttributes.lastName}"  readOnly="${readOnly || rpsEntitySaved}"/>
		</td>
  	</tr>
  	<tr>
    	<th width="25%">
    		<div align="right">
        		<kul:htmlAttributeLabel attributeEntry="${RPSEntityAttributes.otherNames}" />
      		</div>
      	</th>
    	<td width="25%">
            <kul:htmlControlAttribute property="exconProjectRPSEntitiesBean.rpsEntity.otherNames" attributeEntry="${RPSEntityAttributes.otherNames}"  readOnly="${readOnly || rpsEntitySaved}"/>
    	</td>
    	<th width="25%">
    		<div align="right">
    			<kul:htmlAttributeLabel attributeEntry="${RPSEntityAttributes.birthdate}" />
    		</div>
    	</th>
    	<td width="25%">
    		<kul:htmlControlAttribute property="exconProjectRPSEntitiesBean.rpsEntity.birthdate" attributeEntry="${RPSEntityAttributes.birthdate}"  readOnly="${readOnly || rpsEntitySaved}"/>
		</td>
  	</tr>
  	<tr>
    	<th width="25%">
    		<div align="right">
        		<kul:htmlAttributeLabel attributeEntry="${RPSEntityAttributes.companyName}" />
      		</div>
      	</th>
    	<td width="25%">
            <kul:htmlControlAttribute property="exconProjectRPSEntitiesBean.rpsEntity.companyName" attributeEntry="${RPSEntityAttributes.companyName}"  readOnly="${readOnly || rpsEntitySaved}"/>
    	</td>
    	<th width="25%">
    		<div align="right">
    			<kul:htmlAttributeLabel attributeEntry="${RPSEntityAttributes.otherInfo}" />
    		</div>
    	</th>
    	<td width="25%">
    		<kul:htmlControlAttribute property="exconProjectRPSEntitiesBean.rpsEntity.otherInfo" attributeEntry="${RPSEntityAttributes.otherInfo}"  readOnly="${readOnly || rpsEntitySaved}"/>
		</td>
  	</tr>
  	<tr>
    	<th width="25%">
    		<div align="right">
        		<kul:htmlAttributeLabel attributeEntry="${RPSEntityAttributes.streetAddress}" />
      		</div>
      	</th>
    	<td width="25%">
            <kul:htmlControlAttribute property="exconProjectRPSEntitiesBean.rpsEntity.streetAddress" attributeEntry="${RPSEntityAttributes.streetAddress}"  readOnly="${readOnly || rpsEntitySaved}"/>
    	</td>
    	<th width="25%">
    		<div align="right">
    			<kul:htmlAttributeLabel attributeEntry="${RPSEntityAttributes.city}" />
    		</div>
    	</th>
    	<td width="25%">
    		<kul:htmlControlAttribute property="exconProjectRPSEntitiesBean.rpsEntity.city" attributeEntry="${RPSEntityAttributes.city}"  readOnly="${readOnly || rpsEntitySaved}"/>
		</td>
  	</tr>
  	<tr>
    	<th width="25%">
    		<div align="right">
        		<kul:htmlAttributeLabel attributeEntry="${RPSEntityAttributes.state}" />
      		</div>
      	</th>
    	<td width="25%">
            <kul:htmlControlAttribute property="exconProjectRPSEntitiesBean.rpsEntity.state" attributeEntry="${RPSEntityAttributes.state}"  readOnly="${readOnly || rpsEntitySaved}"/>
    	</td>
    	<th width="25%">
    		<div align="right">
    			<kul:htmlAttributeLabel attributeEntry="${RPSEntityAttributes.countryCode}" />
    		</div>
    	</th>
    	<td width="25%">
    		<kul:htmlControlAttribute property="exconProjectRPSEntitiesBean.rpsEntity.countryCode" attributeEntry="${RPSEntityAttributes.countryCode}"  readOnly="${readOnly || rpsEntitySaved}"/>
		</td>
  	</tr>
  	<c:if test="${!rpsEntitySaved}">
  	<tr>
    	<th colspan="2">
    		&nbsp;
      	</th>
    	<th width="25%" colspan="2">
    		<div align="center">
    		<c:if test="${!readOnly}">
    			<html:image
					property="methodToCall.addRPSEntity"
					src="${ConfigProperties.kr.externalizable.images.url}tinybutton-add1.gif"
					title="Add RPS Entity" alt="Add RPS Entity" styleClass="tinybutton addButton" />
			</c:if>
    	</th>
  	</tr>
  	</c:if>
	<c:if test="${rpsEntitySaved}">
  	<tr>
    	<th width="25%">
    		<div align="right">
        		<kul:htmlAttributeLabel attributeEntry="${RPSEntityAttributes.rpsMatchCode}" />
      		</div>
      	</th>
    	<td width="25%">
            <kul:htmlControlAttribute property="exconProjectRPSEntitiesBean.savedRPSEntity.rpsMatchCode" attributeEntry="${RPSEntityAttributes.rpsMatchCode}" />
    	</td>
    	<th width="25%" colspan="2">
    		<div align="center">
    		<c:if test="${!readOnly}">
				<html:image
						property="methodToCall.deleteRPSEntity.line${projectEventRowStatus.index}.anchor${currentTabIndex}"
						src='${ConfigProperties.kra.externalizable.images.url}tinybutton-delete1.gif'
						styleClass="tinybutton" />
				<html:image
						property="methodToCall.editRPSEntity.line${projectEventRowStatus.index}.anchor${currentTabIndex}"
						src='${ConfigProperties.kr.externalizable.images.url}tinybutton-edit1.gif'
						styleClass="tinybutton" />
				<a href="${KualiForm.exconProjectRPSEntitiesBean.rpsURL}" target="rpsSearch">
				<img src='${ConfigProperties.kr.externalizable.images.url}tinybutton-search.gif'>
				</a>
				<%-- 
				<html:image property="methodToCall.performRPS"
					src='${ConfigProperties.kr.externalizable.images.url}tinybutton-search.gif'
					styleClass="tinybutton" />
					--%>
			</c:if>
    		</div>
    	</th>
  	</tr>
  	</c:if>
</table>
		
<%--
	</div>
		<div class="tab-container" align="center">
	<h3>
		<span class="subhead-left">RPS Results</span>  		
		<span class="subhead-right"><kul:help parameterNamespace="KC-EXCON" parameterDetailType="Document" parameterName="exconProjectContactsHelpUrl" altText="help"/></span>
	</h3>
		<c:forEach var="result"
			items="${KualiForm.exconProjectRPSEntitiesBean.rpsResults}"
			varStatus="resultRowStatus">
		${result.name} <br>
		</c:forEach>
	</div>
	--%>
</kul:tab>
