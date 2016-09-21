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
<c:set var="exconProjectBCAttributes" value="${DataDictionary.ExconProjectHRExtension.attributes}" />

<c:set var="readOnly" value="${not KualiForm.editingMode['fullEntry']}" scope="request" />

<kul:tabTop tabTitle="Background Check" defaultOpen="true" tabErrorKey="document.exconProjectList[0].hrExtension.*">

    <div class="tab-container" align="center">

        <h3>
            <span class="subhead-left">Background Check for: ${KualiForm.exconProject.rpsEntity.concatNames}</span>
            <span class="subhead-right"><kul:help businessObjectClassName="org.kuali.kra.excon.project.ExconProject" altText="help"/></span>
        </h3>
        <table cellpAdding="0" cellspacing="0" summary="">
            <tr>
                <th width="25%">
                    <div align="right">
                        <kul:htmlAttributeLabel attributeEntry="${exconProjectBCAttributes.requestType}" />
                    </div>
                </th>
                <td width="25%">
                    <kul:htmlControlAttribute property="document.exconProjectList[0].hrExtension.requestType" attributeEntry="${exconProjectBCAttributes.requestType}" />
                </td>
                <th colspan="2" rowspan="3" width="50%">
                    <table cellpadding="0" cellspacing="0">
                        <tr>
                            <th>
                                <div align="right"><kul:htmlAttributeLabel attributeEntry="${exconProjectBCAttributes.criminalCheck}" />
                                    <kul:htmlControlAttribute property="document.exconProjectList[0].hrExtension.criminalCheck" attributeEntry="${exconProjectBCAttributes.criminalCheck}" /></div>
                            </th>
                            <th>
                                <div align="right"><kul:htmlAttributeLabel attributeEntry="${exconProjectBCAttributes.creditCheck}" />
                                    <kul:htmlControlAttribute property="document.exconProjectList[0].hrExtension.creditCheck" attributeEntry="${exconProjectBCAttributes.creditCheck}" /></div>
                            </th>
                            <th>
                                <div align="right"><kul:htmlAttributeLabel attributeEntry="${exconProjectBCAttributes.criminalPrevious}" />
                                    <kul:htmlControlAttribute property="document.exconProjectList[0].hrExtension.criminalPrevious" attributeEntry="${exconProjectBCAttributes.criminalPrevious}" /></div>
                            </th>
                            <th>
                                <div align="right"><kul:htmlAttributeLabel attributeEntry="${exconProjectBCAttributes.ssnNoData}" />
                                    <kul:htmlControlAttribute property="document.exconProjectList[0].hrExtension.ssnNoData" attributeEntry="${exconProjectBCAttributes.ssnNoData}" /></div>
                            </th>
                        </tr>
                        <tr>
                            <th>
                                <div align="right"><kul:htmlAttributeLabel attributeEntry="${exconProjectBCAttributes.sexOffenderCheck}" />
                                    <kul:htmlControlAttribute property="document.exconProjectList[0].hrExtension.sexOffenderCheck" attributeEntry="${exconProjectBCAttributes.sexOffenderCheck}" /></div>
                            </th>
                            <th>
                                <div align="right"><kul:htmlAttributeLabel attributeEntry="${exconProjectBCAttributes.educationCheck}" />
                                    <kul:htmlControlAttribute property="document.exconProjectList[0].hrExtension.educationCheck" attributeEntry="${exconProjectBCAttributes.educationCheck}" /></div>
                            </th>
                            <th>
                                <div align="right"><kul:htmlAttributeLabel attributeEntry="${exconProjectBCAttributes.mvrPrevious}" />
                                    <kul:htmlControlAttribute property="document.exconProjectList[0].hrExtension.mvrPrevious" attributeEntry="${exconProjectBCAttributes.mvrPrevious}" /></div>
                            </th>
                            <th>
                                <div align="right"><kul:htmlAttributeLabel attributeEntry="${exconProjectBCAttributes.candDispute}" />
                                    <kul:htmlControlAttribute property="document.exconProjectList[0].hrExtension.candDispute" attributeEntry="${exconProjectBCAttributes.candDispute}" /></div>
                            </th>
                        </tr>
                        <tr>
                            <th>
                                <div align="right"><kul:htmlAttributeLabel attributeEntry="${exconProjectBCAttributes.mvrCheck}" />
                                    <kul:htmlControlAttribute property="document.exconProjectList[0].hrExtension.mvrCheck" attributeEntry="${exconProjectBCAttributes.mvrCheck}" /></div>
                            </th>
                            <th>
                                <div align="right"><kul:htmlAttributeLabel attributeEntry="${exconProjectBCAttributes.stateCheck}" />
                                    <kul:htmlControlAttribute property="document.exconProjectList[0].hrExtension.stateCheck" attributeEntry="${exconProjectBCAttributes.stateCheck}" /></div>
                            </th>
                            <th>&nbsp;</th>
                            <th>
                                <div align="right"><kul:htmlAttributeLabel attributeEntry="${exconProjectBCAttributes.hardCopyReq}" />
                                    <kul:htmlControlAttribute property="document.exconProjectList[0].hrExtension.hardCopyReq" attributeEntry="${exconProjectBCAttributes.hardCopyReq}" /></div>
                            </th>
                        </tr>
                    </table>
                </th>

            </tr>
            <tr>
                <th>
                    <div align="right"><kul:htmlAttributeLabel attributeEntry="${exconProjectBCAttributes.bcStatus}" /></div>
                </th>
                <td>
                    <kul:htmlControlAttribute property="document.exconProjectList[0].hrExtension.bcStatus" attributeEntry="${exconProjectBCAttributes.bcStatus}" />
                </td>
            </tr>
            <tr>
                <th>
                    <div align="right"><kul:htmlAttributeLabel attributeEntry="${exconProjectBCAttributes.employeeType}" /></div>
                </th>
                <td>
                    <kul:htmlControlAttribute property="document.exconProjectList[0].hrExtension.employeeType" attributeEntry="${exconProjectBCAttributes.employeeType}" />
                </td>
            </tr>
            <tr>
                <th>
                    <div align="right"><kul:htmlAttributeLabel attributeEntry="${exconProjectBCAttributes.jobCode}" /></div>
                </th>
                <td>
                    <kul:htmlControlAttribute property="document.exconProjectList[0].hrExtension.jobCode" attributeEntry="${exconProjectBCAttributes.jobCode}"
                                              onblur="loadJobCodeTitle('document.exconProjectList[0].hrExtension.jobCode', 'jobTitle');" readOnly="${readOnly}" />
                    <c:if test="${!readOnly}">
                        <kul:lookup boClassName="org.kuali.kra.budget.personnel.JobCode"
                                    fieldConversions="jobCode:document.exconProjectList[0].hrExtension.jobCode,jobTitle:document.exconProjectList[0].hrExtension.job.jobTitle"
                                    anchor="${tabKey}" />
                    </c:if>
                    <c:if test="${readOnly}">
                        <html:hidden property="document.exconProjectList[0].hrExtension.jobCode" />
                    </c:if>
                    <kul:directInquiry boClassName="org.kuali.kra.budget.personnel.JobCode" inquiryParameters="document.exconProjectList[0].hrExtension.jobCode:jobCode" anchor="${tabKey}" />
                    <div id="jobTitle.div" >
                        <c:if test="${!empty KualiForm.document.exconProjectList[0].hrExtension.jobCode}">
                            <c:choose>
                                <c:when test="${empty KualiForm.document.exconProjectList[0].hrExtension.job}">
                                    <span style='color: red;'>not found</span>
                                </c:when>
                                <c:otherwise>
                                    <c:out value="${KualiForm.document.exconProjectList[0].hrExtension.job.jobTitle}" />
                                </c:otherwise>
                            </c:choose>
                        </c:if>
                    </div>
                </td>
                <th width="25%">
                    <div align="right"><kul:htmlAttributeLabel attributeEntry="${exconProjectBCAttributes.contact}" /></div>
                </th>
                <td width="25%">
                    <kul:htmlControlAttribute property="document.exconProjectList[0].hrExtension.contact" attributeEntry="${exconProjectBCAttributes.contact}" />
                </td>
            </tr>
            <tr>
                <th>
                    <div align="right"><kul:htmlAttributeLabel attributeEntry="${exconProjectBCAttributes.bcResult}" /></div>
                </th>
                <td>
                    <kul:htmlControlAttribute property="document.exconProjectList[0].hrExtension.bcResult" attributeEntry="${exconProjectBCAttributes.bcResult}" />
                </td>
                <th>
                    <div align="right"><kul:htmlAttributeLabel attributeEntry="${exconProjectBCAttributes.selfDisclosed}" /></div>
                </th>
                <td>
                    <kul:htmlControlAttribute property="document.exconProjectList[0].hrExtension.selfDisclosed" attributeEntry="${exconProjectBCAttributes.selfDisclosed}" />
                </td>
            </tr>
            <tr>
                <th>
                    <div align="right"><kul:htmlAttributeLabel attributeEntry="${exconProjectBCAttributes.rerunReason}" /></div>
                </th>
                <td>
                    <kul:htmlControlAttribute property="document.exconProjectList[0].hrExtension.rerunReason" attributeEntry="${exconProjectBCAttributes.rerunReason}" />
                </td>
                <th>
                    <div align="right"><kul:htmlAttributeLabel attributeEntry="${exconProjectBCAttributes.mvrDiscrepancy}" /></div>
                </th>
                <td>
                    <kul:htmlControlAttribute property="document.exconProjectList[0].hrExtension.mvrDiscrepancy" attributeEntry="${exconProjectBCAttributes.mvrDiscrepancy}" />
                </td>
            </tr>
            <tr>
                <th>
                    <div align="right"><kul:htmlAttributeLabel attributeEntry="${exconProjectBCAttributes.criminalDiscrepancy}" /></div>
                </th>
                <td>
                    <kul:htmlControlAttribute property="document.exconProjectList[0].hrExtension.criminalDiscrepancy" attributeEntry="${exconProjectBCAttributes.criminalDiscrepancy}" />
                </td>
                <th>
                    <div align="right"><kul:htmlAttributeLabel attributeEntry="${exconProjectBCAttributes.criminalOffenses}" /></div>
                </th>
                <td>
                    <kul:htmlControlAttribute property="document.exconProjectList[0].hrExtension.criminalOffenses" attributeEntry="${exconProjectBCAttributes.criminalOffenses}" />
                </td>
            </tr>
            <tr>
                <th>
                    <div align="right"><kul:htmlAttributeLabel attributeEntry="${exconProjectBCAttributes.notes}" /></div>
                </th>
                <td colspan="3">
                    <kul:htmlControlAttribute property="document.exconProjectList[0].hrExtension.notes" attributeEntry="${exconProjectBCAttributes.notes}" />
                </td>
            </tr>
        </table>
    </div>
</kul:tabTop>
