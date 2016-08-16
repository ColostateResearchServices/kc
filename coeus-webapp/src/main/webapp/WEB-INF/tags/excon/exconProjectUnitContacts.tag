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

<c:set var="exconProjectAttributes"
	value="${DataDictionary.ExconProject.attributes}" />
<c:set var="exconProjectUnitPersonAttributes"
	value="${DataDictionary.ExconProjectUnitPerson.attributes}" />

<c:set var="readOnly" value="${not KualiForm.editingMode['fullEntry']}"
	scope="request" />
<c:set var="action" value="exconProjectContacts" />

<kul:tab tabTitle="Unit Contacts" defaultOpen="true" tabErrorKey="exconProjectUnitPersonsBean*" tabAuditKey="exconProjectUnitPersonsBean*"
			auditCluster="unitPersonsAuditWarnings,unitPersonsAuditErrors">

	<div class="tab-container" align="center">
		<h3>
			<span class="subhead-left">Unit Contacts</span> <span
				class="subhead-right"><kul:help parameterNamespace="KC-EXCON"
					parameterDetailType="Document"
					parameterName="exconProjectContactsHelpUrl" altText="help" /></span>
		</h3>
		<table cellpadding="0" cellspacing="0" summary="">
			<tr>
				<th>&nbsp;</th>
				<th>*Person</th>
				<th>*Type Code</th>
				<th>Unit</th>
				<th>Phone</th>
				<th>Email</th>
				<th>
					<div align="center">Actions</div>
				</th>
			</tr>

			<c:if test="${!readOnly}">
				<tbody class="addline">
					<tr>
						<th class="infoline">Add</th>
						<td id="personId" class="grid" class="infoline">
							<kul:htmlControlAttribute
								property="exconProjectUnitPersonsBean.newUnitPerson.userName"
								attributeEntry="${exconProjectUnitPersonAttributes.userName}"
								onblur="loadContactPersonName('exconProjectUnitPersonsBean.newUnitPerson.userName',
	                               				 			'unitContactFullName.div',
	                	        				     		'unitNumber',
	                	        				  			'phoneNumber',
           	        							  			'emailAddress',
           	        							  			'personId');"
								readOnly="${readOnly}" /> 
							<c:if test="${!readOnly}">
								<kul:lookup boClassName="org.kuali.kra.bo.KcPerson"
									fieldConversions="personId:exconProjectUnitPersonsBean.newUnitPerson.personId"
									lookupParameters="exconProjectUnitPersonsBean.newUnitPerson.personId:personId"
									anchor="${tabKey}" />

							</c:if>
							<c:if test="${readOnly}">
								<html:hidden styleId ="fullName" property="exconProjectUnitPersonsBean.newUnitPerson.fullName" />
							</c:if>
							
							${kfunc:registerEditableProperty(KualiForm, "exconProjectUnitPersonsBean.newUnitPerson.personId")}
							<html:hidden styleId ="personId" property="exconProjectUnitPersonsBean.newUnitPerson.personId" />
							
							<div id="unitContactFullName.div">&nbsp; 
								<c:if test="${!empty KualiForm.exconProjectUnitPersonsBean.newUnitPerson}">
									<c:choose>
										<c:when test="${empty KualiForm.exconProjectUnitPersonsBean.newUnitPerson}">
											<span style='color: red;'>not found</span>
										</c:when>
										<c:otherwise>
										<c:out
											value="${KualiForm.exconProjectUnitPersonsBean.newUnitPerson.fullName}" />
										</c:otherwise>
									</c:choose>
								</c:if>
							</div>
						</td>
						
						<td id="unitAdministratorTypeCode" class="grid" class="infoline">
							<kul:htmlControlAttribute
								property="exconProjectUnitPersonsBean.newUnitPerson.unitAdministratorTypeCode"
								attributeEntry="${exconProjectUnitPersonAttributes.unitAdministratorTypeCode}"/>
						</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td class="infoline">
							<div align="center">
								<html:image property="methodToCall.addUnitPerson"
									src="${ConfigProperties.kr.externalizable.images.url}tinybutton-add1.gif"
									title="Add Unit Person" alt="Add Unit Person"
									styleClass="tinybutton addButton" />
							</div>
						</td>
					</tr>
				</tbody>
			</c:if>

			<c:forEach var="projectUnitPerson"
				items="${KualiForm.exconProjectUnitPersonsBean.exconProjectUnitPersons}"
				varStatus="projectUnitPersonRowStatus">
				<tr>
					<th class="infoline" scope="row"><c:out
							value="${projectUnitPersonRowStatus.index + 1}" /></th>
					<td valign="middle">${projectUnitPerson.fullName}</td>
					<td valign="middle">${projectUnitPerson.unitAdministratorTypeName}</td>
					<td valign="middle">${projectUnitPerson.unitAdministratorUnitNumber} (${projectUnitPerson.person.unit.unitName})</td>
					<td valign="middle">${projectUnitPerson.person.phoneNumber}</td>
					<td valign="middle"><a href="mailto:${projectUnitPerson.person.emailAddress}">${projectUnitPerson.person.emailAddress}</a></td>
					<td>
						<div align="center">
							<c:choose>
								<c:when test="${!readOnly}">
									<html:image
										property="methodToCall.deleteUnitPerson.line${projectUnitPersonRowStatus.index}.anchor${currentTabIndex}"
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
</kul:tab>