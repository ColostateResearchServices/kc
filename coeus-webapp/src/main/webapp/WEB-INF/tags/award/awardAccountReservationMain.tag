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

<c:set var="reservationAttributes" value="${DataDictionary.AwardAccountReservation.attributes}" />
<c:set var="accountAttributes" value="${DataDictionary.AwardAccount.attributes}" />
<kul:tabTop tabTitle="Current Reservations" defaultOpen="true" tabErrorKey="document.awardAccountReservations[0].*">
	<div class="tab-container" align="center">
    	<h3>
    		<span class="subhead-left">Reservation Info</span>
            <span class="subhead-right"><kul:help parameterNamespace="KC-AWARD" parameterDetailType="Document" parameterName="awardAccountReservationHelpUrl" altText="help"/></span>
        </h3>
        <table cellpadding="0" cellspacing="0" summary="userInfo">
        	<tr>
        	<th>
        		<kul:htmlAttributeLabel attributeEntry="${reservationAttributes.reservationUser}"/>
        		<kul:htmlControlAttribute property="document.awardAccountReservation.reservationUser" attributeEntry="${reservationAttributes.reservationUser}" readOnly="true" />
        	</th>
        	</tr>
        </table>
        <h3>
    		<span class="subhead-left">Existing Reservations</span>
        </h3>
     
        <table cellpadding=0 cellspacing="0"  summary="">
             <tr>
             	<th width="20">
             	&nbsp;
                </th>
				<th><div align="left"><kul:htmlAttributeLabel attributeEntry="${accountAttributes.fullAccountNumber}" noColon="true" /></div></th>
				<th><div align="left"><kul:htmlAttributeLabel attributeEntry="${accountAttributes.reservationDate}" noColon="true" /></div></th>
				<th width="50">
				Action
				</th>
             </tr>
			<c:forEach var="reservedAccount" items="${KualiForm.document.awardAccountReservation.reservedAccounts}" varStatus="status">
			<c:if test="${reservedAccount.researchAccount == null || 'N' == reservedAccount.researchAccount.accountUsed}">	
			<c:if test="${reservedAccount.saveFlag}">
			 <tbody class="addline">	
			</c:if>
              <tr>
                <td><div align="center">
                  <kul:htmlControlAttribute property="document.awardAccountReservation.reservedAccounts[${status.index}].selectAccount" attributeEntry="${accountAttributes.selectAccount}" readOnly="${readOnly}" />
                </div></td>
                <td>
                	 ${KualiForm.document.awardAccountReservation.reservedAccounts[status.index].fullAccountNumber}
                </td>
                <td>
                	 ${KualiForm.document.awardAccountReservation.reservedAccounts[status.index].formattedReservationDate}
                </td>
                <td>&nbsp;</td>
              </tr>
              <c:if test="${reservedAccount.saveFlag}">
             </tbody>
             </c:if>
            </c:if>
            </c:forEach>
             <c:if test="${!readOnly}">
            <tbody class="addline">
            <tr>
            <td colspan=3>&nbsp;
            <c:if test="${KualiForm.document.awardAccountReservation.saveFlag}">
            	<input type="hidden" class="input.changed" value="true"/>
            </c:if>
            </td>
            <td>
            <div align=center>
            	<html:image property="methodToCall.deleteSelectedReservedAccount.anchor${tabKey}" src="${ConfigProperties.kra.externalizable.images.url}tinybutton-deleteselected.gif" title="Delete Selected" alt="Delete Selected" styleClass="tinybutton" />
            </div>
            </td>
            </tr>
            </tbody>
            </c:if>
            
         </table>
         <h3>
    		<span class="subhead-left">Add Accounts</span>
        </h3>
     
        <table cellpadding=0 cellspacing="0"  summary="">             
              <tr>
              <th width="10%" class="infoline">Add:</th>
              <td width="70%" class="infoline">(select)
              		<kul:multipleValueLookup boClassName="edu.colostate.kc.award.reservation.ResearchAccount" 
              		lookedUpCollectionName="reservedAccounts" 
              		anchor="${tabKey}"/>
			  </td>

              <td width="20%" class="infoline"><div align="center">
              &nbsp;
              </div></td>
            </tr>
        </table>
        
    </div>
</kul:tabTop>
