/*
 * Kuali Coeus, a comprehensive research administration system for higher education.
 * 
 * Copyright 2005-2016 Kuali, Inc.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.kuali.kra.external.award;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.kuali.coeus.common.framework.sponsor.Sponsor;
import org.kuali.kra.award.awardhierarchy.AwardHierarchy;
import org.kuali.kra.award.awardhierarchy.AwardHierarchyService;
import org.kuali.kra.award.cgb.AwardCgbConstants;
import org.kuali.kra.award.contacts.AwardUnitContact;
import org.kuali.kra.award.document.AwardDocument;
import org.kuali.kra.award.home.Award;
import org.kuali.kra.award.home.AwardMethodOfPayment;
import org.kuali.kra.award.paymentreports.Frequency;
import org.kuali.kra.external.awardpayment.AwardMethodOfPaymentDTO;
import org.kuali.kra.external.cgbbillingfrequency.CgbBillingFrequency;
import org.kuali.kra.external.frequency.FrequencyDto;
import org.kuali.kra.external.service.KcDtoService;
import org.kuali.kra.external.service.KcDtoServiceBase;
import org.kuali.kra.external.sponsor.SponsorDTO;
import org.kuali.kra.institutionalproposal.home.InstitutionalProposal;
import org.kuali.rice.coreservice.framework.parameter.ParameterService;
import org.kuali.rice.krad.service.BusinessObjectService;

public class AwardDtoService extends KcDtoServiceBase<AwardDTO, Award> {

	public static final String FUND_MANAGER_TYPE_CODE_PARAM = "FIN_SYS_UNIT_ADMIN_TYPE_FUND_MANAGER";
	
	private BusinessObjectService businessObjectService;
	private ParameterService parameterService;
	private KcDtoService<ProposalDTO, InstitutionalProposal> proposalDtoService;
	private KcDtoService<SponsorDTO, Sponsor> sponsorDtoService;
	private KcDtoService<AwardMethodOfPaymentDTO, AwardMethodOfPayment> awardMethodOfPaymentDtoService;
	private KcDtoService<FrequencyDto, CgbBillingFrequency> frequencyDtoService;
	private AwardAccountDtoService awardAccountDtoService;
	private AwardHierarchyService awardHierarchyService;
	
	@Override
	public AwardDTO buildDto(Award award) {
		if (award != null) {
			List<String> orderList = new ArrayList<String>();
			Map<String, AwardHierarchy>  hierarchyMap =  getAwardHierarchyService().getAwardHierarchy(award.getAwardNumber(), orderList);
			
			return buildDto(award,hierarchyMap, orderList);
		} else {
			return null;
		}
	}

	
	public AwardDTO buildDto(Award award, Map<String, AwardHierarchy>  hierarchyMap, List<String> orderList) {
		if (award != null) {
			AwardDTO dto = new AwardDTO();
			dto.setAwardId(award.getAwardId());
			dto.setAwardNumber(award.getAwardNumber());
			dto.setAccountNumber(award.getAccountNumber());
			dto.setChartOfAccountsCode(award.getFinancialChartOfAccountsCode());
			dto.setAwardStartDate(award.getBeginDate());
			dto.setAwardEndDate(award.getLastAwardAmountInfo().getFinalExpirationDate());
			dto.setAwardTotalAmount(award.getLastAwardAmountInfo().getObliDistributableAmount().bigDecimalValue());
			dto.setAwardDocumentNumber(award.getDocumentKey());
			dto.setAwardLastUpdateDate(award.getUpdateTimestamp());
			dto.setAwardDirectCostAmount(award.getLastAwardAmountInfo().getObligatedTotalDirect().bigDecimalValue());
			dto.setAwardIndirectCostAmount(award.getLastAwardAmountInfo().getObligatedTotalIndirect().bigDecimalValue());
			dto.setProposalAwardTypeCode(award.getAwardTypeCode().toString());
			dto.setAwardStatusCode(award.getStatusCode().toString());
			dto.setActive(StringUtils.equalsIgnoreCase(award.getAwardStatus().getDescription(), "Active"));
			dto.setSponsorCode(award.getSponsorCode());
			dto.setTitle(award.getTitle());
			dto.setUnitNumber(award.getLeadUnitNumber());
			dto.setAwardCommentText(award.getAwardGeneralComments().getComments());
			dto.setPrincipalInvestigatorId(award.getPrincipalInvestigator().getPersonId());
			dto.setAdditionalFormsRequired(award.getAwardCgb().isAdditionalFormsRequired());
			dto.setAutoApproveInvoice(award.getAwardCgb().isAutoApproveInvoice());
			dto.setStopWork(award.getAwardCgb().isStopWork());
			dto.setInvoicingOption(award.getAwardCgb().getInvoicingOption());
			dto.setInvoicingOptionDescription(AwardCgbConstants.InvoicingOptions.Types.get(award.getAwardCgb().getInvoicingOption()));
			dto.setDunningCampaignId(award.getAwardCgb().getDunningCampaignId());
			dto.setAdditionalFormsDescription(award.getAdditionalFormsDescriptionComment().getComments());
			dto.setStopWorkReason(award.getStopWorkReasonComment().getComments());
			dto.setMinInvoiceAmount(award.getAwardCgb().getMinInvoiceAmount() != null ? award.getAwardCgb().getMinInvoiceAmount().bigDecimalValue() : null);
			dto.setExcludedFromInvoicing(award.getAwardCgb().isSuspendInvoicing());
			dto.setExcludedFromInvoicingReason(award.getSuspendInvoicingComment().getComments());
			dto.setSequenceNumber(award.getSequenceNumber().toString());
			dto.setSequenceStatus(award.getAwardSequenceStatus());

			dto.setObligationStartDate(award.getLastAwardAmountInfo().getCurrentFundEffectiveDate());
			dto.setObligationEndDate(award.getLastAwardAmountInfo().getObligationExpirationDate());

			
			if (award.getAwardCgb().getLocFund() != null) {
				dto.setLocFund(award.getAwardCgb().getLocFund().getDescription());
				dto.setLocFundAmount(award.getAwardCgb().getLocFund().getAmount() != null ? award.getAwardCgb().getLocFund().getAmount().bigDecimalValue() : null );
				dto.setLocFundCode(award.getAwardCgb().getLocFund().getFundCode());
				dto.setLocFundExpirationDate(award.getAwardCgb().getLocFund().getExpirationDate());
				dto.setLocFundStartDate(award.getAwardCgb().getLocFund().getStartDate());
				dto.setLocFundGroup(award.getAwardCgb().getLocFund().getFundGroup().getDescription());
				dto.setLocFundGroupCode(award.getAwardCgb().getLocFund().getGroupCode());

			}
			
			if (StringUtils.isNotEmpty(award.getMethodOfPaymentCode())) {
				AwardMethodOfPayment awardMethodOfPayment = getBusinessObjectService().findBySinglePrimaryKey(AwardMethodOfPayment.class, award.getMethodOfPaymentCode());
			    dto.setMethodOfPayment(awardMethodOfPaymentDtoService.buildDto(awardMethodOfPayment));
			}
			
			if (award.getFundingProposals() != null && !award.getFundingProposals().isEmpty()) {
				InstitutionalProposal instProp = getBusinessObjectService().findBySinglePrimaryKey(InstitutionalProposal.class, award.getFundingProposals().get(0).getProposalId());
				dto.setProposal(proposalDtoService.buildDto(instProp));
			}
			if (award.getSponsor() != null) {
				dto.setSponsor(sponsorDtoService.buildDto(award.getSponsor()));
			} else {
				//this shouldn't ever happen and so we will simply avoid returning a dto in this case
				return null;
			}
			
			String fundManagerTypeCode = getParameterService().getParameterValueAsString(AwardDocument.class, FUND_MANAGER_TYPE_CODE_PARAM);
			for (AwardUnitContact contact : award.getAwardUnitContacts()) {
				if (StringUtils.equals(contact.getUnitAdministratorTypeCode(), fundManagerTypeCode)) {
					dto.setFundManagerId(contact.getPersonId());
				}
			}

			dto.setInvoiceBillingFrequency(award.getAwardCgb().getBillFreqCode());

			dto.setAwardAccounts(getAwardAccountsHierarchy(hierarchyMap, orderList));
			return dto;
		} else {
			return null;
		}
	}
	
	
	protected BusinessObjectService getBusinessObjectService() {
		return businessObjectService;
	}

	public void setBusinessObjectService(BusinessObjectService businessObjectService) {
		this.businessObjectService = businessObjectService;
	}

	protected ParameterService getParameterService() {
		return parameterService;
	}

	public void setParameterService(ParameterService parameterService) {
		this.parameterService = parameterService;
	}

	protected KcDtoService<ProposalDTO, InstitutionalProposal> getProposalDtoService() {
		return proposalDtoService;
	}

	public void setProposalDtoService(
			KcDtoService<ProposalDTO, InstitutionalProposal> proposalDtoService) {
		this.proposalDtoService = proposalDtoService;
	}

	protected KcDtoService<SponsorDTO, Sponsor> getSponsorDtoService() {
		return sponsorDtoService;
	}

	public void setSponsorDtoService(
			KcDtoService<SponsorDTO, Sponsor> sponsorDtoService) {
		this.sponsorDtoService = sponsorDtoService;
	}

	protected KcDtoService<AwardMethodOfPaymentDTO, AwardMethodOfPayment> getAwardMethodOfPaymentDtoService() {
		return awardMethodOfPaymentDtoService;
	}

	public void setAwardMethodOfPaymentDtoService(
			KcDtoService<AwardMethodOfPaymentDTO, AwardMethodOfPayment> awardMethodOfPaymentDtoService) {
		this.awardMethodOfPaymentDtoService = awardMethodOfPaymentDtoService;
	}

	public KcDtoService<FrequencyDto, CgbBillingFrequency> getFrequencyDtoService() {
		return frequencyDtoService;
	}

	public void setFrequencyDtoService(
			KcDtoService<FrequencyDto, CgbBillingFrequency> frequencyDtoService) {
		this.frequencyDtoService = frequencyDtoService;
	}
	
	protected List<AwardAccountDTO> getAwardAccountsHierarchy(Map<String, AwardHierarchy>  hierarchyMap, List<String> orderList ) {
		
		List<AwardAccountDTO> awardAccounts = new ArrayList<AwardAccountDTO>();
		if (hierarchyMap != null && orderList != null && !orderList.isEmpty()) {
		  for (String awardNum : orderList) {
			  AwardAccountDTO awardAccountDto = new AwardAccountDTO();
			  Award awardFromHierarchy = hierarchyMap.get(awardNum).getAward();
			  awardAccountDto = getAwardAccountDtoService().buildDto(awardFromHierarchy);
			  awardAccounts.add(awardAccountDto);			
		   }
		}
		
		return awardAccounts;
	}
	
	
	
	public AwardHierarchyService getAwardHierarchyService() {
		return awardHierarchyService;
	}

	public AwardAccountDtoService getAwardAccountDtoService() {
		return awardAccountDtoService;
	}

	public void setAwardAccountDtoService(
			AwardAccountDtoService awardAccountDtoservice) {
		this.awardAccountDtoService = awardAccountDtoservice;
	}

	public void setAwardHierarchyService(AwardHierarchyService awardHierarchyService) {
		this.awardHierarchyService = awardHierarchyService;
	}

}
