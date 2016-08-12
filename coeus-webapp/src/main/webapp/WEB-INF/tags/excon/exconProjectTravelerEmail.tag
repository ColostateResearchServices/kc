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
<c:set var="exconProjectPersonAttributes" value="${DataDictionary.ExconProjectPerson.attributes}" />
<c:set var="exconProjectEmailAttributes" value="${DataDictionary.ExconProjectEmailContent.attributes}" />

<c:set var="readOnly" value="${not KualiForm.editingMode['fullEntry']}" scope="request" />
<c:set var="action" value="exconProjectActions" />
<c:set var="isTravel" value="${KualiForm.exconProject.projectType.description == 'International Travel'}" />

<kul:tab tabTitle="Traveler Communication" defaultOpen="${isTravel}">
<c:if test="${isTravel}" >
	<div class="tab-container" align="center">
	<h3>
		<span class="subhead-left">Trip Info</span>  		
		<span class="subhead-right"><kul:help parameterNamespace="KC-EXCON" parameterDetailType="Document" parameterName="exconProjectEventsHelpUrl" altText="help"/></span>
	</h3>
	<table cellpadding="0" cellspacing="0" summary="">
		<c:set var="traveler" value="${KualiForm.exconProject.traveler}"/>
		<c:if test="${traveler != null}">
			<tr>
				<th>Name</th>
				<th>Email</th>
				<th>Unit</th>
			</tr>
			<tr>
				<td><div align="center">${traveler.fullName}</div></td>
				<td><div align="center"><a href="mailto:${traveler.person.emailAddress}">${traveler.person.emailAddress}</a></div></td>
				<td><div align="center">
				<c:if test="${traveler.isUnitRestricted}">
					<font color="red">
				</c:if>
				${traveler.person.unit.unitName}
				<c:if test="${traveler.isUnitRestricted}">
					</font>
				</c:if>
				</div>
				</td>
			</tr>
		</c:if>			
		
	</table>
	<table cellpadding="0" cellspacing="0" summary="">
		<tr>
			<th>Destination Country</th>
			<th>Arrival Date</th>
			<th>Departure Date</th>
			<th>Sanction List</th>
			<th>Comment</th>
		</tr>
		
		<c:forEach var="destinations"
			items="${KualiForm.exconProject.exconProjectDestinations}"
			varStatus="projectDestinationRowStatus">
			<tr>
				<td>
					<div align="center">${destinations.destinationCountryName}</div>
				</td>
				<td align="center">
					<div align="center">${destinations.arrivalDateStr}</div>
				</td>
				<td align="center">
					<div align="center">${destinations.departureDateStr}</div>
				</td>
				<td align="center">
					<div align="center"><font color="red">${destinations.sanctionListName}</font></div>
				</td>
				<td align="center">
					<div align="center">${destinations.destinationComment}</div>
				</td>
			</tr>
		</c:forEach>			
	</table>
	<h3>
		<span class="subhead-left">Traveler Communications</span>  		
	</h3>
	<table cellpadding="0" cellspacing="0">
		<tr>
			<th width="25%">Communication Date</th>
			<th width="50%">Content/Agenda</th>
			<th>Comment</th>
		</tr>
		<c:forEach var="communication"
			items="${KualiForm.exconProject.travelerCommunications}"
			varStatus="communicationRowStatus">	
			<tr>
				<td><div align="center">${communication.communicationDateStr}</div></td>
				<td><div align="center">${communication.description}</div></td>
				<td><div align="center">${communication.travelComment}</div></td>
			</tr>	
		</c:forEach>
	</table>
	
	<c:if test="${not readOnly}">
	<table cellpadding="0" cellspacing="0">
		<tr><th colspan="3">&nbsp;</th></tr>
		<tr>
		<td width="30%">
		<div align="right">
		<b>Meeting Agenda</b>
		</div>
		</td>
		<td>
		<div align="right">
		<kul:htmlControlAttribute property="exconProjectEmailBean.newAgenda.contentCode" attributeEntry="${exconProjectEmailAttributes.contentCodeAgenda}" />
		</div>
		</td>
		<td>
			<div align="center">
				<c:choose>
				<c:when test="${!readOnly}">
					<html:image
						property="methodToCall.addTravelerMeetingAgenda"
						src='${ConfigProperties.kr.externalizable.images.url}tinybutton-add1.gif'
						styleClass="tinybutton" />
				</c:when>
				<c:otherwise>&nbsp;
				</c:otherwise>
				</c:choose>
			</div>
		</td>
		<td width="30%">
		<div align="right">
		<b>Send Email:</b>
		</div>
		<td>
		<div align="right">
		<kul:htmlControlAttribute property="exconProjectEmailBean.newBody.contentCodeBody" attributeEntry="${exconProjectEmailAttributes.contentCodeBody}" />
		&nbsp;
		<kul:htmlControlAttribute property="exconProjectEmailBean.newAttachment.contentCodeAttachment" attributeEntry="${exconProjectEmailAttributes.contentCodeAttachment}" />
		</div>
		</td>
		<td>
			<div align="center">
				<c:choose>
				<c:when test="${!readOnly}">
					<html:image
						property="methodToCall.sendTravelerEmail"
						src='${ConfigProperties.kr.externalizable.images.url}tinybutton-send.gif'
						styleClass="tinybutton" />
				</c:when>
				<c:otherwise>&nbsp;
				</c:otherwise>
				</c:choose>
			</div>
		</td>
	</table>
	</c:if>
	</div>
</c:if>
</kul:tab>
