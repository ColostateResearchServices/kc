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
<c:set var="exconProjectDestinationAttributes" value="${DataDictionary.ExconProjectDestination.attributes}" />

<c:set var="readOnly" value="${not KualiForm.editingMode['fullEntry']}" scope="request" />
<c:set var="action" value="exconProjectDestinations" />

<kul:tabTop tabTitle="Destinations" defaultOpen="true" tabErrorKey="exconProjectDestinationsBean*" tabAuditKey="exconProjectDestinationsBean*"
			auditCluster="destinationsAuditWarnings,destinationsAuditErrors">
	
	<div class="tab-container" align="center">
	<h3>
		<span class="subhead-left">Destinations</span>  		
		<span class="subhead-right"><kul:help parameterNamespace="KC-EXCON" parameterDetailType="Document" parameterName="exconProjectDestinationsHelpUrl" altText="help"/></span>
	</h3>
	<table cellpadding="0" cellspacing="0" summary="">
		<tr>
			<th>&nbsp;</th>
			<th>*Destination Country</th>
			<th>Arrival Date</th>
			<th>Departure Date</th>
			<th>Sanction List</th>
			<th>Comment</th>
			<th>
			<div align="center">Actions</div>
			</th>
		</tr>

		<c:if test="${!readOnly}">
			<tbody class="addline">
			<tr>
				<th class="infoline">Add</th>
				<td id="destinationCountryCode" class="grid" class="infoline">
					<kul:htmlControlAttribute 
						property="exconProjectDestinationsBean.newDestination.destinationCountryCode"
						attributeEntry="${exconProjectDestinationAttributes.destinationCountryCode}"
						readOnly="${readOnly}" /> 

				</td>
				<td id="arrivalDate" class="infoline">
					<kul:htmlControlAttribute
						property="exconProjectDestinationsBean.newDestination.arrivalDate"
						attributeEntry="${exconProjectDestinationAttributes.arrivalDate}" 
						readOnly="${readOnly}"/>
						
				</td>
				<td id="departureDate" class="infoline">
					<kul:htmlControlAttribute
						property="exconProjectDestinationsBean.newDestination.departureDate"
						attributeEntry="${exconProjectDestinationAttributes.departureDate}" 
						readOnly="${readOnly}"/>
						
				</td>
				<td id="sanctionList" class="infoline">
					<kul:htmlControlAttribute
						property="exconProjectDestinationsBean.newDestination.sanctionList"
						attributeEntry="${exconProjectDestinationAttributes.sanctionList}" 
						readOnly="true"/>
						
				</td>
				<td id="destinationComment" class="infoline">
					<kul:htmlControlAttribute
						property="exconProjectDestinationsBean.newDestination.destinationComment"
						attributeEntry="${exconProjectDestinationAttributes.destinationComment}" 
						readOnly="${readOnly}"/>
						
				</td>
				<td class="infoline">
				<div align="center"><html:image
					property="methodToCall.addDestination"
					src="${ConfigProperties.kr.externalizable.images.url}tinybutton-add1.gif"
					title="Add Destination" alt="Add Destination" styleClass="tinybutton addButton" /></div>
				</td>
			</tr>
			</tbody>
		</c:if>

		<c:forEach var="destinations"
			items="${KualiForm.exconProjectDestinationsBean.exconProjectDestinations}"
			varStatus="projectDestinationRowStatus">
			<tr>
				<th class="infoline" scope="row">
					<c:out value="${projectDestinationRowStatus.index + 1}" />
				</th>
				<td valign="middle">
					${destinations.destinationCountryName}
				</td>
				<td valign="middle">
					${destinations.arrivalDateStr}
				</td>
				<td valign="middle">
					${destinations.departureDateStr}
				</td>
				<td valign="middle">
					<font color="red">${destinations.sanctionListName}</font>
				</td>
				<td valign="middle">
					${destinations.destinationComment}
				</td>

				<td>
				<div align="center">
				<c:choose>
				<c:when test="${!readOnly}">
					<html:image
						property="methodToCall.deleteDestination.line${projectDestinationRowStatus.index}.anchor${currentTabIndex}"
						src='${ConfigProperties.kra.externalizable.images.url}tinybutton-delete1.gif'
						styleClass="tinybutton" />
				</c:when>
				<c:otherwise>&nbsp;
				</c:otherwise>
				</c:choose>
				</div>
				</td>
			</tr>
		</c:forEach>		

	</table>
	</div>
</kul:tabTop>
