<%--
 Copyright 2005-2013 The Kuali Foundation
 
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
<%@ include file="/WEB-INF/jsp/award/awardTldHeader.jsp" %>

<c:set var="activityTypeAttributes" value="${DataDictionary.ActivityType.attributes}" />
<c:set var="awardAttributes" value="${DataDictionary.Award.attributes}" />
<c:set var="awardExtensionAttributes" value="${DataDictionary.AwardExtension.attributes}" />
<c:set var="awardAlternateNumberAttributes" value="${DataDictionary.AwardAlternateNumber.attributes}" />
<c:set var="awardAlternateNumberTypeAttributes" value="${DataDictionary.AwardAlternateNumberType.attributes}" />


<kul:tab tabTitle="Additional Document Numbers and Related Accounts" defaultOpen="false" tabErrorKey="document.awardList[0].alternateIds*,awardAlternateNumberHelper*">
	<div class="tab-container" align="right">
	
			<h3>
				<span class="subhead-left">Add Document Numbers & Related Accounts</span>
	    		<span class="subhead-right">
    				<kul:help parameterNamespace="KC-AWARD" parameterDetailType="Document" parameterName="awardAddFundingProposalsHelp" altText="help"/>
				</span>
			</h3>
			<table id="altAccountIdAddTable" cellpadding="0" cellspacing="0" summary="Add Alternate Account Id">
			     <tr>
			     <th></th>
			          <th><div align="center"><kul:htmlAttributeLabel attributeEntry="${awardAlternateNumberAttributes.description}" /></div></th>
			          <th><div align="center"><kul:htmlAttributeLabel attributeEntry="${awardAlternateNumberAttributes.number}" /></div></th>
			          <th><div align="center"><kul:htmlAttributeLabel attributeEntry="${awardAlternateNumberAttributes.showOnInvoice}" /></div></th>
			          <th><div align="center"><kul:htmlAttributeLabel attributeEntry="${awardAlternateNumberTypeAttributes.description}" /></div></th>
			          <th>Actions</th>
			          
			     </tr>
				<c:if test="${!readOnly}">
				<tr>
			          <th align="center" scope="row"><div align="right">Add:</div></th>

			    	<td class="infoline">
			    	  	<div align="center">

			    	  	 	<kul:htmlControlAttribute property="awardAlternateNumberHelper.newAwardAlternateNumber.description" 
			    	  	 								attributeEntry="${awardAlternateNumberAttributes.description}"
			    	  	 								readOnly="false" />		    	  	 	

			    	 	</div> 
			    	</td>
			    	<td class="infoline">
			    	  	<div align="center">

			    	  	 	<kul:htmlControlAttribute property="awardAlternateNumberHelper.newAwardAlternateNumber.number" 
			    	  	 								attributeEntry="${awardAlternateNumberAttributes.number}"
			    	  	 								readOnly="false" />		    	  	 	

			    	 	</div>
			    	</td>
			    	<td class="infoline">
			    	<div align="center">

			    	  	 	<kul:htmlControlAttribute property="awardAlternateNumberHelper.newAwardAlternateNumber.showOnInvoice" 
			    	  	 								attributeEntry="${awardAlternateNumberAttributes.showOnInvoice}"
			    	  	 								readOnly="false" />		    	  	 	

			    	 	</div> 
			    	</td>
			    				    	
			    	<td class="infoline">
			    	   <div align="center">
			    	    	${KualiForm.valueFinderResultDoNotCache}
			    	    	<kul:htmlControlAttribute property="awardAlternateNumberHelper.newAwardAlternateNumber.awardAlternateNumberTypeCode"
			    	    							  attributeEntry="${awardAlternateNumberTypeAttributes.awardAlternateNumberTypeCode}"
			    	    							  readOnly="false" />
			    	     </div>
			    	</td>
			    	    
			    	    <!--  change to add acct ID below  -->
			        <td class="infoline">
			        	<div align="center">
							<html:image property="methodToCall.addAlternateAwardNumber.anchor${tabKey}"
							src='${ConfigProperties.kra.externalizable.images.url}tinybutton-add1.gif' styleClass="tinybutton"/>
						</div>
			        </td>
			  	</tr>
			  	</c:if>
			  	
 			  	<c:forEach var="awardAltNumber" items="${KualiForm.document.award.extension.awardAlternateNumbers}" varStatus="status">
	             <tr>
					<th class="infoline">
						<c:out value="${status.index+1}" />
					</th>
	                <td valign="middle">
					    <div align="center">
						${KualiForm.document.award.extension.awardAlternateNumbers[status.index].description}&nbsp;
						</div>
					</td>
	                <td valign="middle">
					    <div align="center">
						${KualiForm.document.award.extension.awardAlternateNumbers[status.index].number}&nbsp;
						</div>
	                </td>
	                <td valign="middle">                	
					    <div align="center">
						${KualiForm.document.award.extension.awardAlternateNumbers[status.index].showOnInvoice}&nbsp;
				 	    </div>
					</td>
	                <td valign="middle">                	
					    <div align="center">
						${KualiForm.document.award.extension.awardAlternateNumbers[status.index].awardAlternateNumberType.description}&nbsp;
						</div>
					</td>
	                <td width="10%">
					<div align="center">&nbsp;
					   <c:if test="${!readOnly}">
						<html:image property="methodToCall.deleteAlternateAwardNumber.line${status.index}.anchor${currentTabIndex}"
						src='${ConfigProperties.kra.externalizable.images.url}tinybutton-delete1.gif' styleClass="tinybutton"/>
					   </c:if>
					</div>
	                </td>
	            </tr>
        	</c:forEach>  
		  	</table>
      
		

	</div>
</kul:tab>
