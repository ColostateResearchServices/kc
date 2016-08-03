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
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.kuali.coeus.sys.api.model.ScaleTwoDecimal;
import org.kuali.kra.award.cgb.AwardCgb;
import org.kuali.kra.award.cgb.LetterOfCreditFund;
import org.kuali.kra.award.contacts.AwardUnitContact;
import org.kuali.kra.award.home.Award;
import org.kuali.kra.award.home.AwardConstants;
import org.kuali.kra.award.home.AwardService;
import org.kuali.coeus.common.framework.version.VersionStatus;
import org.kuali.kra.award.dao.AwardLookupDao;
import org.kuali.kra.award.document.AwardDocument;
import org.kuali.kra.external.service.KcDtoService;
import org.kuali.rice.coreservice.framework.parameter.ParameterService;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;


public class AwardWebServiceImpl implements AwardWebService {
	
	public static final String FUND_MANAGER_TYPE_CODE_PARAM = "FIN_SYS_UNIT_ADMIN_TYPE_FUND_MANAGER";

	private BusinessObjectService businessObjectService;
	private AwardService awardService;
	private AwardLookupDao awardLookupDao;
	private KcDtoService<AwardDTO, Award> awardDtoService;
	private ParameterService parameterService;
	
	@Transactional
	public AwardDTO getAward(Long awardId) {
		String awardNumber = getAwardService().getAwardNumber(awardId);
		if (StringUtils.isNotBlank(awardNumber)) {
			Award newestAward = getAwardService().getActiveOrNewestAward(awardNumber);
			AwardDTO result = getAwardDtoService().buildDto(newestAward);
			return result;
		} else {
			return null;
		}
	}
	
	public List<AwardDTO> searchAwards(AwardSearchCriteriaDto searchDto) {
		List<AwardDTO> results = new ArrayList<AwardDTO>();
		Map<String, String> values = new HashMap<String, String>();
		values.put("awardId", searchDto.getAwardId());
		values.put("financialChartOfAccountsCode", searchDto.getChartOfAccounts());
		values.put("accountNumber", searchDto.getAccountNumber());
		values.put("awardNumber", searchDto.getAwardNumber());
		values.put("projectPersons.personId", searchDto.getPrincipalInvestigatorId());
		values.put("sponsorCode", searchDto.getSponsorCode());
		values.put("beginDate",	searchDto.getStartDate());
		values.put("rangeLowerBoundKeyPrefix_beginDate", searchDto.getStartDateLowerBound());
		values.put("awardAmountInfos.finalExpirationDate", searchDto.getEndDate());
		values.put("rangeLowerBoundKeyPrefix_awardAmountInfos.finalExpirationDate", searchDto.getEndDateLowerBound());
		values.put("awardAmountInfos.amountObligatedToDate", searchDto.getAwardTotal());
		
		String invoiceReportDesc =  getParameterService().getParameterValueAsString(Award.class, AwardConstants.INVOICE_REPORT_DESC_PARAM);
		values.put("awardReportTermItems.report.description", invoiceReportDesc);
		values.put("awardReportTermItems.frequencyCode", searchDto.getBillingFrequency());
		
		String locFundCodes = "";
		if (StringUtils.isNotEmpty(searchDto.getLetterOfCreditFundGroupCode())) {
			String [] groupCodes = searchDto.getLetterOfCreditFundGroupCode().split("\\|");
			for (String groupCode: groupCodes) {
				Map<String, String> locValues = new HashMap<String, String>();
				locValues.put("groupCode", groupCode);
				List<LetterOfCreditFund> funds =  (List<LetterOfCreditFund>) getBusinessObjectService().findMatching(LetterOfCreditFund.class, locValues);
				for (LetterOfCreditFund fund : funds) {
					if (!locFundCodes.isEmpty()) {
						locFundCodes = locFundCodes.concat("|");
					}
					locFundCodes = locFundCodes.concat(fund.getFundCode());
				}
			}
			
			// if user specified bogus group code(s) with no referencing fund(s) return the still empty results.
			if (StringUtils.isEmpty(locFundCodes)) {
				return results;
			}
			values.put("awardCgbList.locFundCode", locFundCodes);

		} else if (StringUtils.isNotEmpty(searchDto.getLetterOfCreditFundCode())) {
		   values.put("awardCgbList.locFundCode", searchDto.getLetterOfCreditFundCode());
		}

		
		List<Award> awards = (List<Award>) getAwardLookupDao().getAwardSearchResults(values, false);
		if (awards != null && !awards.isEmpty()) {
			for (Award award : awards) {
				results.add(getAwardDtoService().buildDto(award));
			}
		}
		return results;
		
	}

	public List<AwardDTO> getMatchingAwards(AwardFieldValuesDto fieldValuesDto) {
		List<AwardDTO> results = new ArrayList<AwardDTO>();
		Map<String, Object> fieldValues = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(fieldValuesDto.getAwardId()) ) {
			fieldValues.put("awardId", fieldValuesDto.getAwardId());
		}
		if (StringUtils.isNotBlank(fieldValuesDto.getChartOfAccounts())) {
			fieldValues.put("financialChartOfAccountsCode", fieldValuesDto.getChartOfAccounts());
		}
		if (StringUtils.isNotBlank(fieldValuesDto.getAccountNumber())) {
			fieldValues.put("accountNumber", fieldValuesDto.getAccountNumber());
		}
		if (StringUtils.isNotBlank(fieldValuesDto.getPrincipalInvestigatorId())) {
			fieldValues.put("projectPersons.personId", fieldValuesDto.getPrincipalInvestigatorId());
		}
		if (StringUtils.isNotBlank(fieldValuesDto.getAwardNumber() )) {
			fieldValues.put("awardNumber", fieldValuesDto.getAwardNumber());
		}
		if (StringUtils.isNotBlank(fieldValuesDto.getFundManagerId() )) {
			String fundManagerTypeCode = getParameterService().getParameterValueAsString(AwardDocument.class, FUND_MANAGER_TYPE_CODE_PARAM);
			fieldValues.put("awardUnitContacts.unitAdministratorTypeCode", fundManagerTypeCode);
			fieldValues.put("awardUnitContacts.personId", fieldValuesDto.getFundManagerId());
		}		

		// use the awardSequenceStatus to return the latest active award
		fieldValues.put("awardSequenceStatus", VersionStatus.ACTIVE.name());
		Collection<Award> awards = getAwardService().retrieveAwardsByCriteria(fieldValues);

		if (awards != null && !awards.isEmpty()) {
			for (Award award : awards) {
				results.add(getAwardDtoService().buildDto(award));
			}
		}
		return results;
	}

	protected AwardService getAwardService() {
		return awardService;
	}

	@Autowired
	@Qualifier("awardService")
	public void setAwardService(AwardService awardService) {
		this.awardService = awardService;
	}

	public AwardLookupDao getAwardLookupDao() {
		return awardLookupDao;
	}

	@Autowired
	@Qualifier("awardLookupDao")
	public void setAwardLookupDao(AwardLookupDao awardLookupDao) {
		this.awardLookupDao = awardLookupDao;
	}

	public KcDtoService<AwardDTO, Award> getAwardDtoService() {
		return awardDtoService;
	}

	public void setAwardDtoService(KcDtoService<AwardDTO, Award> awardDtoService) {
		this.awardDtoService = awardDtoService;
	}

	public BusinessObjectService getBusinessObjectService() {
		return businessObjectService;
	}

	public void setBusinessObjectService(BusinessObjectService businessObjectService) {
		this.businessObjectService = businessObjectService;
	}

	@Override
	public AwardBillingUpdateStatusDto updateAwardBillingStatus(AwardFieldValuesDto searchDto,
			AwardBillingUpdateDto updateDto) {
		AwardBillingUpdateStatusDto result = new AwardBillingUpdateStatusDto();
		AwardCgb awardCgb = null;
		Award award = null;
		Map<String, Object> values = new HashMap<String, Object>();

		if (StringUtils.isNotEmpty(searchDto.getChartOfAccounts())) {
			values.put("financialChartOfAccountsCode", searchDto.getChartOfAccounts());
		}
		if  (StringUtils.isNotEmpty(searchDto.getAccountNumber())) {
			values.put("accountNumber", searchDto.getAccountNumber());
		}
        if (StringUtils.isNotEmpty(searchDto.getAwardId())) {
			values.put("awardId", searchDto.getAwardId());
        }
        if (StringUtils.isNotEmpty(searchDto.getAwardNumber())) {
			values.put("awardNumber", searchDto.getAwardNumber());
        }       
        if (!values.isEmpty()) {
		    // use the awardSequenceStatus to return the latest active award
            values.put("awardSequenceStatus", VersionStatus.ACTIVE.name());
            List<Award> awards = new ArrayList<Award>(businessObjectService.findMatching(Award.class, values));

            
            if (awards.size()>1) {
    			result.setSuccess(false);
    			result.getErrorMessages().add("Cannot update mulitple awards, found "+ awards.size() +" awards based on critera provided.");
    			return result;            	
            }
            else if (!awards.isEmpty()) {
            	award = awards.get(0);
            }
		}
	
		if (award == null || award.getAwardCgb() == null) {
			result.setSuccess(false);
			result.getErrorMessages().add("Unable to find an award for update based on unique identifiers.");
			return result;
		}
		
		awardCgb = award.getAwardCgb();
		
		if (updateDto.isDoFinalBilledUpdate()) {
			awardCgb.setFinalBill(updateDto.isFinalBilledIndicator());
		}
		if (updateDto.isDoLastBillDateUpdate()) {
			awardCgb.setPreviousLastBilledDate(awardCgb.getLastBilledDate());
			awardCgb.setLastBilledDate(updateDto.getLastBillDate());
		}
		if (updateDto.isDoInvoiceDocStatusUpdate()) {
			awardCgb.setInvoiceDocumentStatus(updateDto.getInvoiceDocumentStatus());
		}
		if (updateDto.isRestorePreviousBillDate()) {
			awardCgb.setLastBilledDate(awardCgb.getPreviousLastBilledDate());
			awardCgb.setPreviousLastBilledDate(null);
		}
		
		getBusinessObjectService().save(awardCgb);
		result.setAwardNumber(awardCgb.getAwardNumber());
		result.setSuccess(true);
		return result;
	}

	public ParameterService getParameterService() {
		return parameterService;
	}

	public void setParameterService(ParameterService parameterService) {
		this.parameterService = parameterService;
	}

}
