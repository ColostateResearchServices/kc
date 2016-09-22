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
<c:set var="exconProjectExternalInstitutionAttributes"
	value="${DataDictionary.ExconProjectExternalInstitution.attributes}" />

<c:set var="readOnly" value="${not KualiForm.editingMode['fullEntry']}"
	scope="request" />
<c:set var="action" value="exconProjectAgreements" />

<kul:tab tabTitle="External Institutions" defaultOpen="true" tabErrorKey="exconProjectExternalInstitutionsBean*" tabAuditKey="exconProjectExternalInstitutionsBean*"
			auditCluster="externalInstitutionsAuditWarnings,externalInstitutionsAuditErrors">

	<div class="tab-container" align="center">
	<h3>
		<span class="subhead-left">&nbsp;</span>
		<span class="subhead-right"><kul:help businessObjectClassName="org.kuali.kra.excon.project.ExconProject" altText="help"/></span>
	</h3>
		<table cellpadding="0" cellspacing="0" summary="">
			<tr>
				<th>&nbsp;</th>
				<th>*Institution</th>
				<th>City</th>
				<th>State</th>
				<th>Country</th>
				<th>
					<div align="center">Actions</div>
				</th>
			</tr>

			<c:if test="${!readOnly}">
				<tbody class="addline">
					<tr>
						<th class="infoline">Add</th>
						<td id="rolodexId" class="grid" class="infoline">
							<kul:htmlControlAttribute
								property="exconProjectExternalInstitutionsBean.newExternalInstitution.rolodexId"
								attributeEntry="${exconProjectExternalInstitutionAttributes.rolodexId}"
								readOnly="${readOnly}" /> 
							<c:if test="${!readOnly}">
								<kul:lookup boClassName="org.kuali.coeus.common.framework.rolodex.Rolodex"
									fieldConversions="rolodexId:exconProjectExternalInstitutionsBean.newExternalInstitution.rolodexId"
									lookupParameters="exconProjectExternalInstitutionsBean.newExternalInstitution.rolodexId:rolodexId"
									anchor="${tabKey}" />

							</c:if>
							
							${kfunc:registerEditableProperty(KualiForm, "exconProjectExternalInstitutionsBean.newExternalInstitution.rolodexId")}
							<html:hidden styleId ="rolodexId" property="exconProjectExternalInstitutionsBean.newExternalInstitution.rolodexId" />
							
							<div id="rolodexOrgName.div">&nbsp; 
								<c:if test="${!empty KualiForm.exconProjectExternalInstitutionsBean.newExternalInstitution.rolodexId}">
									<c:choose>
										<c:when test="${empty KualiForm.exconProjectExternalInstitutionsBean.newExternalInstitution.rolodexEntry}">
											<span style='color: red;'>not found</span>
										</c:when>
										<c:otherwise>
										<c:out
											value="${KualiForm.exconProjectExternalInstitutionsBean.newExternalInstitution.rolodexEntry.organization}" />
										</c:otherwise>
									</c:choose>
								</c:if>
							</div>
						</td>
						
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td class="infoline">
							<div align="center">
								<html:image property="methodToCall.addExternalInstitution"
									src="${ConfigProperties.kr.externalizable.images.url}tinybutton-add1.gif"
									title="Add External Institution" alt="Add External Institution"
									styleClass="tinybutton addButton" />
							</div>
						</td>
					</tr>
				</tbody>
			</c:if>

			<c:forEach var="projectExternalInstitution"
				items="${KualiForm.exconProjectExternalInstitutionsBean.exconProjectExternalInstitutions}"
				varStatus="projectExternalInstitutionRowStatus">
				<tr>
					<th class="infoline" scope="row"><c:out
							value="${projectExternalInstitutionRowStatus.index + 1}" /></th>
					<td valign="middle">${projectExternalInstitution.rolodexEntry.organization}</td>
					<td valign="middle">${projectExternalInstitution.rolodexEntry.city}</td>
					<td valign="middle">${projectExternalInstitution.rolodexEntry.state}</td>
					<td valign="middle">${projectExternalInstitution.rolodexEntry.countryCode}</td>
					<td>
						<div align="center">
							<c:choose>
								<c:when test="${!readOnly}">
									<html:image
										property="methodToCall.deleteExternalInstitution.line${projectExternalInstitutionRowStatus.index}.anchor${currentTabIndex}"
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