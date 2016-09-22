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
<c:set var="exconProjectPersonAttributes" value="${DataDictionary.ExconProjectPerson.attributes}" />

<c:set var="readOnly" value="${not KualiForm.editingMode['fullEntry']}" scope="request" />
<c:set var="action" value="exconProjectContacts" />

<kul:tabTop tabTitle="Project Persons" defaultOpen="true" tabErrorKey="exconProjectPersonsBean*" tabAuditKey="exconProjectPersonsBean*"
			auditCluster="personsAuditWarnings,personsAuditErrors">
	
	<div class="tab-container" align="center">
	<h3>
		<span class="subhead-left">Project Persons</span>  		
		<span class="subhead-right"><kul:help parameterNamespace="KC-EXCON" parameterDetailType="Document" parameterName="exconProjectContactsHelpUrl" altText="help"/></span>
	</h3>
	<table cellpadding="0" cellspacing="0" summary="">
			<tr>
				<th>&nbsp;</th>
				<th>*Person</th>
				<th>*Role</th>
				<th>Unit</th>
				<th>Phone</th>
				<th>Email</th>
				<th>
					<div align="center">Actions</div>
				</th>
			</tr>
			
			
			<%-- ADD --%>
			<c:if test="${!readOnly}">
				<tbody class="addline">
					<tr>
						<th class="infoline">Add</th>
						<td id="personId" class="grid" class="infoline">
							<kul:htmlControlAttribute
								property="exconProjectPersonsBean.newPerson.userName"
								attributeEntry="${exconProjectPersonAttributes.userName}"
								onblur="loadContactPersonName('exconProjectPersonsBean.newPerson.userName',
	                               				 			'fullName.div',
	                	        				     		'unitNumber',
	                	        				  			'phoneNumber',
           	        							  			'emailAddress',
           	        							  			'personId');"
								readOnly="${readOnly}" /> 
							<c:if test="${!readOnly}">
								<kul:lookup boClassName="org.kuali.coeus.common.framework.person.KcPerson"
									fieldConversions="personId:exconProjectPersonsBean.newPerson.personId"
									lookupParameters="exconProjectPersonsBean.newPerson.personId:personId"
									anchor="${tabKey}" />

							</c:if>
							<c:if test="${readOnly}">
								<html:hidden styleId ="fullName" property="exconProjectPersonsBean.newPerson.fullName" />
							</c:if>
							
							${kfunc:registerEditableProperty(KualiForm, "exconProjectPersonsBean.newPerson.personId")}
							<html:hidden styleId ="personId" property="exconProjectPersonsBean.newPerson.personId" />
							
							<div id="fullName.div">&nbsp; 
								<c:if test="${!empty KualiForm.exconProjectPersonsBean.newPerson}">
									<c:choose>
										<c:when test="${empty KualiForm.exconProjectPersonsBean.newPerson}">
											<span style='color: red;'>not found</span>
										</c:when>
										<c:otherwise>
										<c:out
											value="${KualiForm.exconProjectPersonsBean.newPerson.fullName}" />
										</c:otherwise>
									</c:choose>
								</c:if>
							</div>
						</td>
												
						<td id="roleTypeCode" class="grid" class="infoline">
							<kul:htmlControlAttribute
								property="exconProjectPersonsBean.newPerson.roleTypeCode"
								attributeEntry="${exconProjectPersonAttributes.roleTypeCode}"/>
						</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td class="infoline">
							<div align="center">
								<html:image property="methodToCall.addPerson"
									src="${ConfigProperties.kr.externalizable.images.url}tinybutton-add1.gif"
									title="Add Person" alt="Add Person"
									styleClass="tinybutton addButton" />
							</div>
						</td>
					</tr>
				</tbody>
			</c:if>
			
			<%-- DISPLAY --%>
			<c:forEach var="projectPerson"
				items="${KualiForm.exconProjectPersonsBean.exconProjectPersons}"
				varStatus="projectPersonRowStatus">
				<c:set var="isRestrictedUnit" value="${KualiForm.exconProject.projectType.description == 'International Travel' && projectPerson.roleType.description == 'Traveler' && projectPerson.isUnitRestricted}" />
				
				<tr>
					<th class="infoline" scope="row"><c:out
							value="${projectPersonRowStatus.index + 1}" /></th>
					<td valign="middle">${projectPerson.fullName}</td>
					<!-- ${projectPerson.personId} -->
					<td valign="middle">${projectPerson.roleType.description}</td>
					<td valign="middle">
					<c:if test="${isRestrictedUnit}">
						<font color="red">
					</c:if>
					${projectPerson.person.unit.unitNumber} (${projectPerson.person.unit.unitName})
					<c:if test="${isRestrictedUnit}">
						</font>
					</c:if>
					</td>
					<td valign="middle">${projectPerson.person.phoneNumber}</td>
					<td valign="middle"><a href="mailto:${projectPerson.person.emailAddress}">${projectPerson.person.emailAddress}</a></td>
					<td>
						<div align="center">
							<c:choose>
								<c:when test="${!readOnly}">
									<html:image
										property="methodToCall.deletePerson.line${projectPersonRowStatus.index}.anchor${currentTabIndex}"
										src='${ConfigProperties.kra.externalizable.images.url}tinybutton-delete1.gif'
										styleClass="tinybutton" />
								</c:when>
								<c:otherwise>&nbsp;</c:otherwise>
							</c:choose>
						</div>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</kul:tabTop>
