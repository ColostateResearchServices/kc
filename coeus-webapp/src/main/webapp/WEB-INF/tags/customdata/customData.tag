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
<%@ include file="/WEB-INF/jsp/kraTldHeader.jsp"%>
<%@ attribute name="fullName" required="true"%>
<%@ attribute name="fieldCount" required="true"%>
<%@ attribute name="excludeInactive" required="false" %>
<%@ attribute name="customAttributeGroups" required="true" type="java.util.Map" %>
<%@ attribute name="customDataList" required="true" type="java.util.List" %>
<%@ attribute name="customDataListPrefix" required="true" %>
<%@ attribute name="readOnly" required="false" %>


<c:if test="${empty excludeInactive}" >
	<c:set var="excludeInactive" value="false" />
</c:if>

<c:choose>
		<c:when test="${fn:length(fullName) > 90}">
 					<c:set var="displayName" value="${fn:substring(fullName, 0, 90)}..."/>
		</c:when> 
		<c:otherwise>
 					<c:set var="displayName" value="${fullName}"/>
		</c:otherwise>

</c:choose>

<div class="tab-container" align="center">
	<h3>
		<span class="subhead-left">${displayName}</span>
		<span class="subhead-right"><kul:help businessObjectClassName="org.kuali.coeus.common.framework.custom.attr.CustomAttribute" altText="help"/></span>
	</h3>
	<div align="left" style="padding:12px;"><strong> Full Group Name: </strong>${fullName}</div>
	<table cellpadding=0 cellspacing="0" class="result-table">
		<c:forEach items="${customAttributeGroups[fullName]}" var="customAttributeDocument" varStatus="status">
		<c:if test="${(excludeInactive eq false) or (excludeInactive eq true && customAttributeDocument.active eq true)}">
			<tr class="datatable">
				<th  align="right">
					<c:if test="${customAttributeDocument.required}">*&nbsp;</c:if>${customAttributeDocument.customAttribute.label}:
				</th>
				<td width="45%">
				<c:forEach var="customAttribute" items="${customDataList}" varStatus="status"> 
					<c:if test="${customAttribute.id == customAttributeDocument.id}">
						<c:set var="customAttributeIndex" value="${status.index}" />
						<c:set var="customAttributeValue" value="${customAttribute.value}" />
						<c:set var="customAttributeId" value="${customDataListPrefix}[${customAttributeIndex}].value" />
					</c:if>
				</c:forEach>
                        
          	  <c:set var="customAttributeErrorStyle" value="" scope="request"/>
				<c:forEach items="${ErrorPropertyList}" var="key">
				    <c:if test="${key eq customAttributeId}">
					  <c:set var="customAttributeErrorStyle" value="border-color: red" scope="request"/>
				    </c:if>
			     </c:forEach>
					
				<c:if test="${empty customAttributeErrorStyle}" >			
					<c:forEach items="${AuditErrors}" var="cluster">
						<c:forEach items="${cluster.value.auditErrorList}" var="audit">
						    <c:if test="${audit.errorKey eq customAttributeId}">
							  <c:set var="customAttributeErrorStyle" value="border-color: red" scope="request"/>
						    </c:if>
						</c:forEach>
					</c:forEach>
				</c:if>
					
				<c:choose>
                	<c:when test="${readOnly}">
                		<c:out value="${fn:escapeXml(customAttributeValue)}" />
                	</c:when>
                	<c:otherwise>
                		${kfunc:registerEditableProperty(KualiForm, customAttributeId)}


						<c:if test="${customAttributeDocument.customAttribute.customAttributeDataType.description != 'Boolean'  and
							customAttributeDocument.customAttribute.lookupClass ne 'org.kuali.coeus.common.framework.custom.arg.ArgValueLookup' }">
							<input size="60" id="${customAttributeId}" type="text" name="${customAttributeId}" value='${customAttributeValue}'
								   style="${customAttributeErrorStyle}" ${readOnly == 'true' ? "disabled" : ''} />
						</c:if>



						<!-- UITSRA-3298  Create Custom Data Field type of DropDown ArgValue -->
						<c:if test="${not empty customAttributeDocument.customAttribute.lookupClass}">
							<c:choose>
								<c:when test="${empty customAttributeValue}" >
									<c:set var="savedRecord" value="false" />
								</c:when>
								<c:otherwise>
									<c:set var="savedRecord" value="yes" />
								</c:otherwise>
							</c:choose>
							<jsp:useBean id="paramMap"  class="java.util.HashMap"/>
							<c:set target="${paramMap}" property="argName" value="${customAttributeDocument.customAttribute.lookupReturn}" />
							<c:set target="${paramMap}" property="savedArgValue" value="${savedRecord}" />


						 <c:choose>

							 <c:when test="${customAttributeDocument.customAttribute.lookupClass eq 'org.kuali.coeus.common.framework.custom.arg.ArgValueLookup' or
							  customAttributeDocument.customAttribute.customAttributeDataType.description == 'Drop Down List' }">
								 <c:set var="customDataArgValueLookupId" value="" />
								 <html:select property="${customAttributeId}" tabindex="0" disabled="${readOnly}"   >
									 <option value="">select</option>
									 <c:forEach items="${krafn:getOptionList('edu.iu.uits.kra.lookup.keyvalue.CustomDataArgValueLookupValuesFinder', paramMap)}" var="option">
										 <c:if test="${!fn:contains(option.key, '(inactive)')}">
											 <c:choose>
												 <c:when test="${customAttributeValue == option.value}">
													 <option value="${option.value}" selected="selected">${option.value}</option>
													 <c:set var="customDataArgValueLookupId" value="${option.key}" />
												 </c:when>
												 <c:otherwise>
													 <option value="${option.value}">${option.value}</option>
												 </c:otherwise>
											 </c:choose>
										 </c:if>

										 <c:if test="${fn:contains(option.key, '(inactive)')}">
											 <c:choose>
												 <c:when test="${customAttributeValue == option.value}">
													 <option value="${option.value}" selected="selected">${option.value}(inactive)</option>
													 <c:set var="customDataArgValueLookupId" value="${fn:substringBefore(option.key, '(inactive)')}" />
												 </c:when>
												 <c:otherwise>
													 <c:if test="${!fn:contains(option.key, '(inactive)')}">
														 <option value="${option.value}">${option.value}</option>
													 </c:if>
												 </c:otherwise>
											 </c:choose>
										 </c:if>
									 </c:forEach>
								 </html:select>

								 <!-- End of UITSRA-3298 -->



						   </c:when>
						   <c:otherwise>						   
							<kul:lookup boClassName="${customAttributeDocument.customAttribute.lookupClass}" fieldConversions="${customAttributeDocument.customAttribute.lookupReturn}:${customAttributeId}," fieldLabel="${customAttributeDocument.customAttribute.label}"  anchor="${tabKey}"/>
					       </c:otherwise>
					     </c:choose>
						</c:if>
					
						<c:if test="${customAttributeDocument.customAttribute.customAttributeDataType.description == 'Date'}">
				            <img src="${ConfigProperties.kr.externalizable.images.url}cal.gif" id="${customAttributeId}_datepicker" style="cursor: pointer;"
				             title="Date selector" alt="Date selector"
				             onmouseover="this.style.backgroundColor='red';" onmouseout="this.style.backgroundColor='transparent';" />
					        <script type="text/javascript">
					            Calendar.setup(
					                   {
					                      inputField : "${customAttributeId}", // ID of the input field
					                      ifFormat : "%m/%d/%Y", // the date format
					                      button : "${customAttributeId}_datepicker" // ID of the button
					                    }
					             );
					        </script>
						</c:if>
						</c:otherwise>
					</c:choose>

					<!-- IU Customization Starts: UITSRA-3070 -->
					<c:if test="${customAttributeDocument.customAttribute.customAttributeDataType.description == 'Boolean'}">

						<input type="radio" class="Custom Data answer QanswerYesNo" name="${customAttributeId}" value="Y"
							${customAttributeValue == 'Y' ? "checked='true'" : ''} />Yes
						<input type="radio" class="Custom Data answer QanswerYesNo" name="${customAttributeId}" value="N"
							${customAttributeValue == 'N' ? "checked='true'" : ''} />No
					</c:if>
					<!-- IU Customization Ends -->

				</td>
			</tr>
		</c:if>
		</c:forEach>
	</table>
</div>
