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

<c:set var="readOnly" value="${not KualiForm.editingMode['fullEntry']}" scope="request" />
<c:set var="action" value="exconProjectHome" />

<kul:tabTop tabTitle="Export Control Project" defaultOpen="true" tabErrorKey="document.exconProjectList[0].projectStatusCode,document.exconProjectList[0].projectTypeCode">

<div class="tab-container" align="center">

<h3>
	<span class="subhead-left">Export Control Project</span>
	<span class="subhead-right"><kul:help businessObjectClassName="org.kuali.kra.excon.project.ExconProject" altText="help"/></span>
</h3>
<table cellpAdding="0" cellspacing="0" summary="">
  	<tr>
    	<th width="25%">
    		<div align="right">
        		<kul:htmlAttributeLabel attributeEntry="${exconProjectAttributes.title}" />
      		</div>
      	</th>
    	<td width="25%">
            <kul:htmlControlAttribute property="document.exconProjectList[0].title" attributeEntry="${exconProjectAttributes.title}" />
    	</td>
    	<th width="25%">
    		<div align="right"><kul:htmlAttributeLabel attributeEntry="${exconProjectAttributes.fundamentalResearch}" /></div>
    	</th>
    	<td width="25%">
    		<kul:htmlControlAttribute property="document.exconProjectList[0].fundamentalResearch" attributeEntry="${exconProjectAttributes.fundamentalResearch}" />
		</td>
  	</tr>
  	<tr>
    	<th>
    		<div align="right"><kul:htmlAttributeLabel attributeEntry="${exconProjectAttributes.projectTypeCode}" /></div>
    	</th>
    	<td>
    		<kul:htmlControlAttribute property="document.exconProjectList[0].projectTypeCode" attributeEntry="${exconProjectAttributes.projectTypeCode}" />
		</td>
    	<th>
    		<div align="right"><kul:htmlAttributeLabel attributeEntry="${exconProjectAttributes.projectStatusCode}" /></div>
    	</th>
    	<td>
    		<kul:htmlControlAttribute property="document.exconProjectList[0].projectStatusCode" attributeEntry="${exconProjectAttributes.projectStatusCode}" />
		</td>
  	</tr>
  	<tr>
    	<th>
    		<div align="right"><kul:htmlAttributeLabel attributeEntry="${exconProjectAttributes.projectStartDate}" /></div>
    	</th>
    	<td>
    		<kul:htmlControlAttribute property="document.exconProjectList[0].projectStartDate" attributeEntry="${exconProjectAttributes.projectStartDate}" />
		</td>
    	<th>
    		<div align="right"><kul:htmlAttributeLabel attributeEntry="${exconProjectAttributes.projectEndDate}" /></div>
    	</th>
    	<td>
    		<kul:htmlControlAttribute property="document.exconProjectList[0].projectEndDate" attributeEntry="${exconProjectAttributes.projectEndDate}" />
		</td>
  	</tr>
  	<tr>
  	    <th>
            <div align="right"><kul:htmlAttributeLabel attributeEntry="${exconProjectAttributes.sponsorCode}" /></div>
        </th>
        <td>
        	<kul:htmlControlAttribute property="document.exconProjectList[0].sponsorCode" attributeEntry="${exconProjectAttributes.sponsorCode}" 
        	                          onblur="loadSponsorName('document.exconProjectList[0].sponsorCode', 'sponsorName');" readOnly="${readOnly}" /> 
        	<c:if test="${!readOnly}">
                <kul:lookup boClassName="org.kuali.coeus.common.framework.sponsor.Sponsor" fieldConversions="sponsorCode:document.exconProjectList[0].sponsorCode,sponsorName:document.exconProjectList[0].sponsor.sponsorName" anchor="${tabKey}" />
            </c:if>
	        <c:if test="${readOnly}">
	           <html:hidden property="document.exconProjectList[0].sponsorCode" />
	        </c:if>
            <kul:directInquiry boClassName="org.kuali.coeus.common.framework.sponsor.Sponsor" inquiryParameters="document.exconProjectList[0].sponsorCode:sponsorCode" anchor="${tabKey}" />
            <div id="sponsorName.div" >
            	<c:if test="${!empty KualiForm.document.exconProjectList[0].sponsorCode}">
            		<c:choose>
						<c:when test="${empty KualiForm.document.exconProjectList[0].sponsor}">
	                    	<span style='color: red;'>not found</span>
	               		</c:when>
	                  	<c:otherwise>
							<c:out value="${KualiForm.document.exconProjectList[0].sponsor.sponsorName}" />
						</c:otherwise>
					</c:choose>
            	</c:if>
			</div>
        </td>
        <th>
        	<div align="right"><kul:htmlAttributeLabel attributeEntry="${exconProjectAttributes.unitNumber}" skipHelpUrl="true"/></div>
        </th>
    	<td>
            <kul:htmlControlAttribute property="document.exconProjectList[0].unitNumber" attributeEntry="${exconProjectAttributes.unitNumber}" readOnly="${readOnly}" /> 
            <c:if test="${!readOnly}">
               <kul:lookup boClassName="org.kuali.coeus.common.framework.unit.Unit" fieldConversions="unitNumber:document.exconProjectList[0].unitNumber"
  			         anchor="${tabKey}" lookupParameters="document.exconProjectList[0].unitNumber:unitNumber"/>
  			</c:if>
            <c:if test="${readOnly}">
                <html:hidden property="document.exconProjectList[0].unitNumber" />
                -
                <kul:htmlControlAttribute property="document.exconProjectList[0].unitName" attributeEntry="${exconProjectAttributes.unitName}" readOnly="true" />
            </c:if>
            <kul:directInquiry boClassName="org.kuali.coeus.common.framework.unit.Unit" inquiryParameters="document.exconProjectList[0].unitNumber:unitNumber" anchor="${tabKey}" />
    	</td>
	</tr>
</table>
</div>
</kul:tabTop>
