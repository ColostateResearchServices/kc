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
<c:set var="exconProjectEventAttributes" value="${DataDictionary.ExconProjectEvent.attributes}" />

<c:set var="readOnly" value="${not KualiForm.editingMode['fullEntry']}" scope="request" />
<c:set var="action" value="exconProjectEvents" />

<kul:tabTop tabTitle="Events" defaultOpen="true"  tabErrorKey="exconProjectEventsBean*">
	
	<div class="tab-container" align="center">
	<h3>
		<span class="subhead-left">Events</span>  		
		<span class="subhead-right"><kul:help parameterNamespace="KC-EXCON" parameterDetailType="Document" parameterName="exconProjectEventsHelpUrl" altText="help"/></span>
	</h3>
	<table cellpadding="0" cellspacing="0" summary="">
		<tr>
			<th>&nbsp;</th>
			<th>*Event</th>
			<th>Date</th>
			<th>Comment</th>
			<th>
			<div align="center">Actions</div>
			</th>
		</tr>

		<c:if test="${!readOnly}">
			<tbody class="addline">
			<tr>
				<th class="infoline">Add</th>
				<td id="projectEventTypeCode" class="grid" class="infoline">
					<kul:htmlControlAttribute 
						property="exconProjectEventsBean.newEvent.projectEventTypeCode"
						attributeEntry="${exconProjectEventAttributes.projectEventTypeCode}" 
						readOnly="${readOnly}" /> 

				</td>
				<td id="eventDate" class="infoline">
					<kul:htmlControlAttribute
						property="exconProjectEventsBean.newEvent.eventDate"
						attributeEntry="${exconProjectEventAttributes.eventDate}" 
						readOnly="${readOnly}" />
						
				</td>
				<td id="eventComment" class="infoline">
					<kul:htmlControlAttribute
						property="exconProjectEventsBean.newEvent.eventComment"
						attributeEntry="${exconProjectEventAttributes.eventComment}" 
						readOnly="${readOnly}" />
						
				</td>

				<td class="infoline">
				<div align="center"><html:image
					property="methodToCall.addEvent"
					src="${ConfigProperties.kr.externalizable.images.url}tinybutton-add1.gif"
					title="Add Event" alt="Add Event" styleClass="tinybutton addButton" /></div>
				</td>
			</tr>
			</tbody>
		</c:if>

		<c:forEach var="projectEvent"
			items="${KualiForm.exconProjectEventsBean.exconProjectEvents}"
			varStatus="projectEventRowStatus">
			<tr>
				<th class="infoline" scope="row">
					<c:out value="${projectEventRowStatus.index + 1}" />
				</th>
				<td valign="middle">
					${projectEvent.projectEventType.description}
				</td>
				<td valign="middle">
					${projectEvent.eventDateStr}
				</td>
				<td valign="middle">
					${projectEvent.eventComment}
				</td>

				<td>
				<div align="center">
				<c:choose>
				<c:when test="${!readOnly}">
					<html:image
						property="methodToCall.deleteEvent.line${projectEventRowStatus.index}.anchor${currentTabIndex}"
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
