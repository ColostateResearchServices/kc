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

<c:set var="awardAttributes" value="${DataDictionary.Award.attributes}" />
<c:set var="awardExtensionAttributes" value="${DataDictionary.AwardExtension.attributes}" />

<kul:tab tabTitle="Derived Codes" defaultOpen="true">
	<div class="tab-container">
	<table cellpadding="0" cellspacing="0">
		<tr>
			<th width="50%">
				<div align="center">
					<kul:htmlAttributeLabel attributeEntry="${awardExtensionAttributes.researchReportCode}" />
					${KualiForm.document.awardList[0].extension.researchReportCode}
				</div>
			</th>
			<th width="50%">
				<div align="center">
					<kul:htmlAttributeLabel attributeEntry="${awardExtensionAttributes.fundSourceCode}" />
					${KualiForm.document.awardList[0].extension.fundSourceCode}
				</div>
			</th>
		</tr>
	</table>
	</div>
</kul:tab>
