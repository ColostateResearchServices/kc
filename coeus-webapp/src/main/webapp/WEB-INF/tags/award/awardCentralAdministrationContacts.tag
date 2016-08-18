<%--
   - Kuali Coeus, a comprehensive research administration system for higher education.
   - 
   - Copyright 2005-2016 Kuali, Inc.
   - 
   - This program is free software: you can redistribute it and/or modify
   - it under the terms of the GNU Affero General Public License as
   - published by the Free Software Foundation, either version 3 of the
   - License, or (at your option) any later version.
   - 
   - This program is distributed in the hope that it will be useful,
   - but WITHOUT ANY WARRANTY; without even the implied warranty of
   - MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   - GNU Affero General Public License for more details.
   - 
   - You should have received a copy of the GNU Affero General Public License
   - along with this program.  If not, see <http://www.gnu.org/licenses/>.
--%>
<%-- member of AwardContacts.jsp --%>

<%@ include file="/WEB-INF/jsp/kraTldHeader.jsp"%>

<c:set var="awardCentralAdminAttributes" value="${DataDictionary.AwardCentralAdminContact.attributes}" />
<c:set var="awardUnitContactAttributes"	value="${DataDictionary.AwardUnitContact.attributes}" />
<c:set var="awardCentralAdminContactAttributes"	value="${DataDictionary.CsuCentralAdminContact.attributes}" />

<c:set var="awardContactAttributes" value="${DataDictionary.AwardContact.attributes}" />
<c:set var="award" value="${KualiForm.document.award}" />
<c:set var="newContact" value="${KualiForm.csuCentralAdminContactsHelper.newCentralAdminContact}" />

<%-- kra:section permission="modifyAward" --%>
<kul:tab defaultOpen="false" tabItemCount="${fn:length(KualiForm.awardDocument.award.extension.centralAdminContacts)}"
				tabTitle="Central Administration Contacts" tabErrorKey="csuCentralAdminContactsHelper*" >
	<div class="tab-container" align="center">
		<h3>
			<span class="subhead-left">Central Administration Contacts</span>
	      	<span class="subhead-right"><kul:help parameterNamespace="KC-AWARD" parameterDetailType="Document" parameterName="awardCentralAdminContactsHelpUrl" altText="help"/></span>
		</h3>
	    <table id="central-admin-table" cellpadding="0" cellspacing="0" summary="Central Admin Contacts">
			<tr>
				<th scope="row" width="5%">&nbsp;</th>
				<th width="20%">Person</th>
				<th width="15%">Unit</th>
				<th width="20%">Project Role</th>
				<th width="20%">Office Phone</th>
				<th width="20%">Email</th>
				<th width="15%">
					<div align="center">Actions</div>
				</th>
			</tr>

			<c:if test="${!readOnly}">
				<tr>
					<th class="infoline" scope="row">Add</th>
					<td nowrap class="grid" class="infoline">
						Employee User Name:
						<kul:htmlControlAttribute
								property="csuCentralAdminContactsHelper.newCentralAdminContact.fullName"
								attributeEntry="${awardContactAttributes.fullName}"
								onblur="loadContactPersonName('csuCentralAdminContactsHelper.newCentralAdminContact.fullName',
	                               				 			'centralAdminFullName.div',
	                	        				     		'centralAdminUnitNumber',
	                	        				  			'centralAdminPhoneNumber',
           	        							  			'centralAdminEmailAddress',
           	        							  			'centralAdminPersonId');"
								readOnly="${readOnly}" /> <c:if test="${!readOnly}">
						<kul:lookup boClassName="org.kuali.coeus.common.framework.person.KcPerson"
									fieldConversions="personId:csuCentralAdminContactsHelper.newCentralAdminContact.personId"
									lookupParameters="csuCentralAdminContactsHelper.newCentralAdminContact.personId:personId"
									anchor="${tabKey}" />

					</c:if> <c:if test="${readOnly}">
						<html:hidden styleId ="centralAdminFullName" property="csuCentralAdminContactsHelper.newCentralAdminContact.fullName" />
					</c:if>

							${kfunc:registerEditableProperty(KualiForm, "csuCentralAdminContactsHelper.newCentralAdminContact.personId")}
						<html:hidden styleId ="centralAdminPersonId" property="csuCentralAdminContactsHelper.newCentralAdminContact.personId" />

						<div id="centralAdminFullName.div">&nbsp; <c:if
								test="${!empty newContact.contact}">
							<c:choose>
								<c:when
										test="${empty newContact.contact}">
									<span style='color: red;'>not found</span>
								</c:when>
								<c:otherwise>
									<c:out
											value="${newContact.fullName}" />
								</c:otherwise>
							</c:choose>
						</c:if></div>

					</td>
					<td id="centralAdminUnitNumber" class="infoline"><%--<div align="center">
	        			<c:out value="${newContact.unitAdministratorUnitNumberByPersonId}" />&nbsp;
	        		</div>--%> &nbsp;</td>
					<td class="infoline" style="font-size: 80%">
						<div align="center"><kul:htmlControlAttribute
								property="csuCentralAdminContactsHelper.newCentralAdminContact.unitAdministratorTypeCode"
								attributeEntry="${awardCentralAdminContactAttributes.unitAdministratorTypeCode}" />
						</div>
					</td>
					<td id="centralAdminPhoneNumber" class="infoline"><c:out
							value="${newContact.contact.phoneNumber}" />&nbsp;
					</td>
					<td id="centralAdminEmailAddress" class="infoline"><c:out
							value="${newContact.contact.emailAddress}" />&nbsp;
					</td>

					<td class="infoline">
						<div align="center"><html:image
								property="methodToCall.addCentralAdminContact"
								src="${ConfigProperties.kr.externalizable.images.url}tinybutton-add1.gif"
								title="Add Contact" alt="Add Contact" styleClass="tinybutton" /></div>
					</td>
				</tr>
			</c:if>


			<c:forEach var="awardContact" items="${KualiForm.awardDocument.award.extension.centralAdminContacts}" varStatus="awardContactRowStatus">
				<tr>
					<th class="infoline" scope="row">
						<c:out value="${awardContactRowStatus.index + 1}" />
					</th>
	                <td valign="middle">
	                	<div align="center">
	                		<input type="hidden" name="admin_contact.identifier_${awardContactRowStatus.index}" value="${awardContact.contact.identifier}" />
	                		${awardContact.fullName}&nbsp;
	                		<kul:directInquiry boClassName="org.kuali.coeus.common.framework.person.KcPerson" inquiryParameters="admin_contact.identifier_${awardContactRowStatus.index}:personId" anchor="${tabKey}" />		                	
						</div>
					</td>
	                <td valign="middle">
	                	<div align="center">
	                		<input type="hidden" name="admin_contact.orgNumber_${awardContactRowStatus.index}" value="${awardContact.organizationIdentifier}" />
							${awardContact.unitAdministratorUnitNumberByPersonId}&nbsp;
							<kul:directInquiry boClassName="org.kuali.coeus.common.framework.unit.Unit" inquiryParameters="admin_contact.orgNumber_${awardContactRowStatus.index}:unitNumber" anchor="${tabKey}" />
						</div>
					</td>
					<td valign="middle"><c:choose>
						<c:when test="${empty awardContact.unitAdministratorType.description}">
							<div align="left"><kul:htmlControlAttribute
									property="awardDocument.award.extension.centralAdminContacts[${awardContactRowStatus.index}].unitAdministratorTypeCode"
									attributeEntry="${awardCentralAdminContactAttributes.unitAdministratorTypeCode}"
									readOnlyAlternateDisplay="${awardContact.unitAdministratorType.description}" readOnly="true" />
							</div>
						</c:when>
						<c:otherwise>
							<c:out
									value="${awardContact.unitAdministratorType.description}" />
						</c:otherwise>
					</c:choose></td>
					<td valign="middle">
						<div align="center">
	                		${awardContact.phoneNumber}&nbsp;
	                	</div> 
					</td>
	                <td valign="middle">
	                	<div align="center">                	
							${awardContact.emailAddress}&nbsp;
						</div> 
					</td>
					<td width="10%">
						<div align="center">&nbsp;
							<c:if test="${!readOnly}">
								<html:image property="methodToCall.deleteCentralAdminContact.line${awardContactRowStatus.index}.anchor${currentTabIndex}"
											src='${ConfigProperties.kra.externalizable.images.url}tinybutton-delete1.gif' styleClass="tinybutton"/>
							</c:if>
						</div>
					</td>
	            </tr>
    		</c:forEach>	    	
    	</table>
	</div>
</kul:tab>
