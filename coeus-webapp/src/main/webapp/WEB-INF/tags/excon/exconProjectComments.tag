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
<c:set var="exconProjectCommentAttributes" value="${DataDictionary.ExconProjectComment.attributes}" />

<c:set var="readOnly" value="${not KualiForm.editingMode['fullEntry']}" scope="request" />
<c:set var="action" value="exconProjectCommentsAndAttachments" />

<kul:tabTop tabTitle="Comments" defaultOpen="true" tabErrorKey="exconProjectCommentsBean*">
	
	<div class="tab-container" align="center">
	<h3>
		<span class="subhead-left">Comments</span>  		
		<span class="subhead-right"><kul:help parameterNamespace="KC-EXCON" parameterDetailType="Document" parameterName="exconProjectCommentsHelpUrl" altText="help"/></span>
	</h3>
	<table cellpadding="0" cellspacing="0" summary="">
		<tr>
			<th>&nbsp;</th>
			<th>Comment Date</th>
			<th>Comment Author</th>
			<th>*Comment Type</th>
			<th>*Comment</th>
			<th>
			<div align="center">Actions</div>
			</th>
		</tr>

		<c:if test="${!readOnly}">
			<tbody class="addline">
			<tr>
				<th class="infoline">Add</th>
				<th class="infoline">&nbsp;</th>
				<th class="infoline">&nbsp;</th>
				<td id="commentTypeCode" class="grid" class="infoline">
					<kul:htmlControlAttribute 
						property="exconProjectCommentsBean.newComment.commentTypeCode"
						attributeEntry="${exconProjectCommentAttributes.commentTypeCode}"
						readOnly="${readOnly}" /> 

				</td>
				<td id="comments" class="infoline">
					<kul:htmlControlAttribute
						property="exconProjectCommentsBean.newComment.comments"
						attributeEntry="${exconProjectCommentAttributes.comments}" 
						readOnly="${readOnly}"/>
						
				</td>

				<td class="infoline">
				<div align="center"><html:image
					property="methodToCall.addComment"
					src="${ConfigProperties.kr.externalizable.images.url}tinybutton-add1.gif"
					title="Add Comment" alt="Add Comment" styleClass="tinybutton addButton" /></div>
				</td>
			</tr>
			</tbody>
		</c:if>

		<c:forEach var="comments"
			items="${KualiForm.exconProjectCommentsBean.exconProjectComments}"
			varStatus="projectCommentRowStatus">
			<tr>
				<th class="infoline" scope="row">
					<c:out value="${projectCommentRowStatus.index + 1}" />
				</th>
				<td valign="middle">
					${comments.commentDateStr}
				</td>
				<td valign="middle">
					${comments.commentAuthorName}
				</td>
				<td valign="middle">
					${comments.commentType.description}
				</td>
				<td valign="middle">
					${comments.comments}
				</td>


				<td>
				<div align="center">
				<c:choose>
				<c:when test="${!readOnly}">
					<html:image
						property="methodToCall.deleteComment.line${projectCommentRowStatus.index}.anchor${currentTabIndex}"
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
