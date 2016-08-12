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

<c:set var="AssociatedDocumentAttributes" value="${DataDictionary.ExconProjectAssociatedDocument.attributes}" />
<c:set var="DocumentTypeAttributes" value="${DataDictionary.ExconProjectAssociatedDocumentType.attributes}" />

<c:set var="readOnly" value="${not KualiForm.editingMode['fullEntry']}" scope="request" />
<c:set var="action" value="exconProjectHome" />
<c:set var="returnsVals" value="${KualiForm.exconProjectAssociatedDocumentsBean.newAssociatedDocument.assocDocType.returnsVals}" />
<c:set var="assocDocIdAttr" value="${KualiForm.exconProjectAssociatedDocumentsBean.newAssociatedDocument.assocDocType.assocDocTypeNumberAttr}" />
<c:set var="assocDocLookupURL" value="${KualiForm.exconProjectAssociatedDocumentsBean.assocDocLookupURL}" />
<c:set var="isInternal" value="${!empty KualiForm.exconProjectAssociatedDocumentsBean.newAssociatedDocument.assocDocType.assocDocTypeClassName}" />
<c:if test="${not returnsVals}">
	<c:set var="lookupTarget" value="target='lookup'" />
</c:if>

<kul:tab tabTitle="Associated Documents" defaultOpen="true" tabErrorKey="exconProjectAssociatedDocumentsBean*" tabAuditKey="exconProjectAssociatedDocumentsBean*"
			auditCluster="associatedDocumentsAuditWarnings,associatedDocumentsAuditErrors">

	<div class="tab-container" align="center">
	<h3>
		<span class="subhead-left">&nbsp;</span>
		<span class="subhead-right"><kul:help businessObjectClassName="org.kuali.kra.excon.project.ExconProject" altText="help"/></span>
	</h3>
		<table cellpadding="0" cellspacing="0" summary="">
			<c:if test="${!readOnly}">
			<tr>
				<th class="infoline" colspan=6>
				<div align="center" valign="bottom">
				Add: 
				<kul:htmlControlAttribute property="exconProjectAssociatedDocumentsBean.newAssociatedDocument.assocDocTypeCode"
								attributeEntry="${AssociatedDocumentAttributes.assocDocTypeCode}"
								readOnly="${readOnly}" />
				<html:image property="methodToCall.addAssociatedDocumentStub"
									src="${ConfigProperties.kr.externalizable.images.url}tinybutton-add1.gif"
									title="Add Associated Document" alt="Add Associated Document"
									styleClass="tinybutton addButton" />
				</div>
				</th>
			</tr>
			</c:if>
			<tr>
				<th width="5%">&nbsp;</th>
				<th width="15%">Document Type</th>
				<th width="20%">Document Identifier</th>
				<th width="50%">Document Title</th>
				<th width="10%" colspan=2>Actions</th>
			</tr>
			<c:if test="${!empty KualiForm.exconProjectAssociatedDocumentsBean.newAssociatedDocument.assocDocTypeCode}">
			<tbody class="addline">
			<tr>
				<td>&nbsp;</td>
				<td>
					${KualiForm.exconProjectAssociatedDocumentsBean.newAssociatedDocument.assocDocType.assocDocTypeName}
				</td>
				<td>
					${kfunc:registerEditableProperty(KualiForm, "exconProjectAssociatedDocumentsBean.newAssociatedDocument.assocDocNumber")}
					<kul:htmlControlAttribute property="exconProjectAssociatedDocumentsBean.newAssociatedDocument.assocDocNumber"
								attributeEntry="${AssociatedDocumentAttributes.assocDocNumber}"
								readOnly="${readOnly || returnsVals}" />
					<c:if test="${isInternal}">
								<kul:lookup boClassName="${KualiForm.exconProjectAssociatedDocumentsBean.newAssociatedDocument.assocDocType.assocDocTypeClassName}"
									fieldConversions="${assocDocIdAttr}:exconProjectAssociatedDocumentsBean.newAssociatedDocument.assocDocNumber,title:exconProjectAssociatedDocumentsBean.newAssociatedDocument.assocDocTitle"
									anchor="${tabKey}" />
					</c:if>
					<c:if test="${!empty assocDocLookupURL}">
						<a href="${assocDocLookupURL}" ${lookupTarget}>
							<img src='${ConfigProperties.kr.externalizable.images.url}searchicon.gif'>
						</a>
					</c:if>
					<input type="hidden" class="input.changed" value="true"/>
				</td>
				<td>
					<kul:htmlControlAttribute property="exconProjectAssociatedDocumentsBean.newAssociatedDocument.assocDocTitle"
								attributeEntry="${AssociatedDocumentAttributes.assocDocTitle}"
								readOnly="${readOnly || returnsVals}" />
				</td>
				<td colspan=2>
				<div align="center">
					<html:image property="methodToCall.addAssociatedDocument"
									src="${ConfigProperties.kr.externalizable.images.url}tinybutton-add1.gif"
									title="Add Associated Document" alt="Add Associated Document"
									styleClass="tinybutton addButton" />
				</div>
				</td>
			</tr>
			</tbody>
			</c:if>
			<c:forEach var="associatedDocument"
				items="${KualiForm.exconProjectAssociatedDocumentsBean.exconProjectAssociatedDocuments}"
				varStatus="associatedDocumentRowStatus">
				<tr>
					<th class="infoline" scope="row"><c:out
							value="${associatedDocumentRowStatus.index + 1}" /></th>
					<td valign="middle">${associatedDocument.assocDocType.assocDocTypeName}</td>
					<td valign="middle">${associatedDocument.assocDocNumber}</td>
					<td valign="middle">${associatedDocument.assocDocTitle}</td>
					<td width="5%">
						<div align="center">
						<c:choose>
						<c:when test="${!empty associatedDocument.assocDocViewURL}">
							<a href="${associatedDocument.assocDocViewURL}" target="${associatedDocument.assocDocTypeCode}_view">
								<img src='${ConfigProperties.kra.externalizable.images.url}tinybutton-view.gif'>
							</a>
						</c:when>
						<c:otherwise>
							&nbsp;
						</c:otherwise>
						</c:choose>
						</div>
					</td>
					<td width="5%">
						<div align="center">
						<c:choose>
						<c:when test="${!readOnly}">
							<html:image
								property="methodToCall.deleteAssociatedDocument.line${associatedDocumentRowStatus.index}.anchor${currentTabIndex}"
								src='${ConfigProperties.kra.externalizable.images.url}tinybutton-delete1.gif'
								styleClass="tinybutton" />
						</c:when>
						<c:otherwise>
							&nbsp;
						</c:otherwise>
						</c:choose>
						</div>
					</td>
				</tr>
			</c:forEach>

		</table>
	</div>
</kul:tab>