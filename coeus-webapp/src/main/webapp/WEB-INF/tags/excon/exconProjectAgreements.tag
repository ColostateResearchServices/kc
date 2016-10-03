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
<script type='text/javascript' src='dwr/interface/KraPersonService.js'></script>
<script type='text/javascript' src='dwr/interface/PersonService.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>

<c:set var="exconProjectAttributes" value="${DataDictionary.ExconProject.attributes}" />

<c:set var="readOnly" value="${not KualiForm.editingMode['fullEntry']}" scope="request" />
<c:set var="action" value="exconProjectAgreements" />

<kul:tabTop tabTitle="Basic Agreement Info" defaultOpen="true" tabErrorKey="document.exconProjectList[0].agreementRole,document.exconProjectList[0].respPartyUsername">

<div class="tab-container" align="center">

<h3>
	<span class="subhead-left">&nbsp;</span>
	<span class="subhead-right"><kul:help businessObjectClassName="org.kuali.kra.excon.project.ExconProject" altText="help"/></span>
</h3>
<table cellpAdding="0" cellspacing="0" summary="">
  	<tr>
    	<th width="25%">
    		<div align="right">
        		<kul:htmlAttributeLabel attributeEntry="${exconProjectAttributes.agreementRole}" />
      		</div>
      	</th>
    	<td width="25%">
            <kul:htmlControlAttribute property="document.exconProjectList[0].agreementRole" attributeEntry="${exconProjectAttributes.agreementRole}" />
    	</td>
    	<th width="25%">
    		<div align="right"><kul:htmlAttributeLabel attributeEntry="${exconProjectAttributes.responsibleParty}" /></div>
    	</th>
 <%--    	
    	<td width="25%">
    		<kul:htmlControlAttribute property="document.exconProjectList[0].responsibleParty" attributeEntry="${exconProjectAttributes.responsibleParty}" />
		</td>
--%>	
						<td id="responsibleParty" class="grid" class="infoline">
							<kul:htmlControlAttribute
								property="document.exconProjectList[0].respPartyUsername"
								attributeEntry="${exconProjectAttributes.respPartyUsername}"
								onblur="loadContactPersonName('document.exconProjectList[0].respPartyUsername',
	                               				 			'respPartyName.div',
	                	        				     		'unitNumber',
	                	        				  			'phoneNumber',
           	        							  			'emailAddress',
           	        							  			'personId');"
								readOnly="${readOnly}" /> 
							<c:if test="${!readOnly}">
								<kul:lookup boClassName="org.kuali.coeus.common.framework.person.KcPerson"
									fieldConversions="userName:document.exconProjectList[0].respPartyUsername"
									lookupParameters="document.exconProjectList[0].respPartyUsername:userName"
									anchor="${tabKey}" />

							</c:if>
							<c:if test="${readOnly}">
								<html:hidden styleId ="fullName" property="document.exconProjectList[0].respPartyPerson.fullName" />
							</c:if>
							
							<div id="respPartyName.div">&nbsp; 
								<c:if test="${!empty KualiForm.document.exconProjectList[0].respPartyPerson}">
									<c:choose>
										<c:when test="${empty KualiForm.document.exconProjectList[0].respPartyPerson}">
											<span style='color: red;'>not found</span>
										</c:when>
										<c:otherwise>
										<c:out
											value="${KualiForm.document.exconProjectList[0].respPartyPerson.fullName}" />
										</c:otherwise>
									</c:choose>
								</c:if>
							</div>
						</td>	
  	</tr>

</table>
</div>
</kul:tabTop>
