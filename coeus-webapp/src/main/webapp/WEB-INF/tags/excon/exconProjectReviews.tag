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
<c:set var="exconProjectReviewAttributes" value="${DataDictionary.ExconProjectReview.attributes}" />

<c:set var="readOnly" value="${not KualiForm.editingMode['fullEntry']}" scope="request" />
<c:set var="action" value="exconProjectAgreements" />

<kul:tab tabTitle="Reviews" defaultOpen="true" tabErrorKey="exconProjectReviewsBean*" tabAuditKey="exconProjectReviewsBean*"
			auditCluster="reviewsAuditWarnings,reviewsAuditErrors">
	
	<div class="tab-container" align="center">
	<h3>
		<span class="subhead-left">&nbsp;</span>  		
		<span class="subhead-right"><kul:help parameterNamespace="KC-EXCON" parameterDetailType="Document" parameterName="exconProjectDestinationsHelpUrl" altText="help"/></span>
	</h3>
	<table cellpadding="0" cellspacing="0" summary="">
		<tr>
			<th>&nbsp;</th>
			<th>*Review Type</th>
			<th>Comment</th>
			<th>
			<div align="center">Actions</div>
			</th>
		</tr>

		<c:if test="${!readOnly}">
			<tbody class="addline">
			<tr>
				<th class="infoline">Add</th>
				<td id="projectReviewTypeCode" class="grid" class="infoline">
					<kul:htmlControlAttribute 
						property="exconProjectReviewsBean.newReview.projectReviewTypeCode"
						attributeEntry="${exconProjectReviewAttributes.projectReviewTypeCode}"
						readOnly="${readOnly}" /> 

				</td>
				<td id="reviewComment" class="infoline">
					<kul:htmlControlAttribute
						property="exconProjectReviewsBean.newReview.reviewComment"
						attributeEntry="${exconProjectReviewAttributes.reviewComment}" 
						readOnly="${readOnly}"/>
						
				</td>
				<td class="infoline">
				<div align="center"><html:image
					property="methodToCall.addReview"
					src="${ConfigProperties.kr.externalizable.images.url}tinybutton-add1.gif"
					title="Add Review" alt="Add Review" styleClass="tinybutton addButton" /></div>
				</td>
			</tr>
			</tbody>
		</c:if>

		<c:forEach var="reviews"
			items="${KualiForm.exconProjectReviewsBean.exconProjectReviews}"
			varStatus="projectReviewRowStatus">
			<tr>
				<th class="infoline" scope="row">
					<c:out value="${projectReviewRowStatus.index + 1}" />
				</th>
				<td valign="middle">
					${reviews.projectReviewTypeName}
				</td>
				<td valign="middle">
					${reviews.reviewComment}
				</td>

				<td>
				<div align="center">
				<c:choose>
				<c:when test="${!readOnly}">
					<html:image
						property="methodToCall.deleteReview.line${projectReviewRowStatus.index}.anchor${currentTabIndex}"
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
</kul:tab>
