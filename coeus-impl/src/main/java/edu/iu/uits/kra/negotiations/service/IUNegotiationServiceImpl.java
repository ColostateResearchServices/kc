package edu.iu.uits.kra.negotiations.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.kra.award.budget.AwardBudgetService;
import org.kuali.kra.award.home.Award;
import org.kuali.coeus.common.framework.version.history.VersionHistory;
import org.kuali.kra.institutionalproposal.home.InstitutionalProposal;
import org.kuali.kra.institutionalproposal.proposallog.ProposalLog;
import org.kuali.kra.institutionalproposal.service.InstitutionalProposalService;
import org.kuali.coeus.common.impl.unit.admin.UnitAdministratorDerivedRoleTypeServiceImpl;
import org.kuali.kra.negotiations.bo.Negotiable;
import org.kuali.kra.negotiations.bo.Negotiation;
import org.kuali.kra.negotiations.bo.NegotiationActivity;
import org.kuali.kra.negotiations.bo.NegotiationActivityHistoryLineBean;
import org.kuali.kra.negotiations.bo.NegotiationAssociationType;
import org.kuali.kra.negotiations.bo.NegotiationUnassociatedDetail;
import org.kuali.kra.negotiations.service.NegotiationServiceImpl;
import org.kuali.coeus.common.framework.person.KcPersonService;
import org.kuali.coeus.common.framework.version.history.VersionHistoryService;
import org.kuali.kra.subaward.bo.SubAward;
import org.kuali.rice.coreservice.framework.parameter.ParameterService;
import org.kuali.rice.krad.service.BusinessObjectService;

import edu.iu.uits.kra.negotiations.bo.IUNegotiationAssociationTypeUtil;

/**
 * Service impl for NegotiationService.
 */
public class IUNegotiationServiceImpl extends NegotiationServiceImpl {
    
    private ParameterService parameterService;
    private AwardBudgetService awardBudgetService;
    private InstitutionalProposalService institutionalProposalService;
    private UnitAdministratorDerivedRoleTypeServiceImpl unitAdministratorDerivedRoleTypeServiceImpl;
    private KcPersonService kcPersonService;
    private VersionHistoryService versionHistoryService;
    
    private BusinessObjectService businessObjectService;
    
    /**
     * 
     * @see org.kuali.kra.negotiations.service.NegotiationService#getNegotiationActivityHistoryLineBeans(List)
     */
    public List<NegotiationActivityHistoryLineBean> getNegotiationActivityHistoryLineBeans(List<NegotiationActivity> activities) {
        List<NegotiationActivityHistoryLineBean> beans = new ArrayList<NegotiationActivityHistoryLineBean>();
        for (NegotiationActivity activity : activities) {
            if (activity.getLocation() != null && activity.getActivityType() != null) {
                NegotiationActivityHistoryLineBean bean = new NegotiationActivityHistoryLineBean(activity);
                beans.add(bean);
            }
        }
        //IU Customization UITSRA-3791	
        //Collections.sort(beans);
        //End IU Customization
        
        // now set the effective dates and calculate the location days.
        Date previousStartDate = null;
        Date previousEndDate = null;
        String previousLocation = "";
        int counter = 1;
        List<NegotiationActivityHistoryLineBean> beansToReturn = new ArrayList<NegotiationActivityHistoryLineBean>();
        for (NegotiationActivityHistoryLineBean bean : beans) {
        	
            if (StringUtils.equals(previousLocation, bean.getLocation())) {
                if (isDateBetween(bean.getStartDate(), previousStartDate, previousEndDate)
                        && isDateBetween(bean.getEndDate(), previousStartDate, previousEndDate)) {
                    //current date range lies within the previous date range
                    setBeanStuff(bean, null, null, "0 Days");
                    //leave previous alone
                } else if (isDateBetween(bean.getStartDate(), previousStartDate, previousEndDate)) {
                	// UITSRA-2562
                    if (bean.getEndDate() != null && previousEndDate != null) {
                    	if ( bean.getEndDate().after(previousEndDate)) {    
		                	//current date range starts within the previous range, but finishes past it.
		                    Date previousEndDatePlusOneDay = new Date(previousEndDate.getTime() + NegotiationActivity.MILLISECS_PER_DAY);                    
		                    previousEndDate = bean.getEndDate();
		                    setBeanStuff(bean, previousEndDatePlusOneDay, bean.getEndDate(), NegotiationActivity.getNumberOfDays(previousEndDatePlusOneDay, bean.getEndDate()));
                    	}
                    }
                } else {
                    //completely separate range.
                    previousStartDate = bean.getStartDate();
                    previousEndDate = bean.getEndDate();
                    setBeanStuff(bean, bean.getStartDate(), bean.getEndDate(), NegotiationActivity.getNumberOfDays(bean.getStartDate(), bean.getEndDate()));
                }
            } else {
                // new location so set the effective date
                previousStartDate = bean.getStartDate();
                previousEndDate = bean.getEndDate();
                previousLocation = bean.getLocation();
                setBeanStuff(bean, bean.getStartDate(), bean.getEndDate(), NegotiationActivity.getNumberOfDays(bean.getStartDate(), bean.getEndDate()));
                if (!beansToReturn.isEmpty()) { 
                    beansToReturn.add(new NegotiationActivityHistoryLineBean());
                }
            }
        	
            bean.setLineNumber(String.valueOf(counter));
            beansToReturn.add(bean);
            counter++;
        }
        return beansToReturn;
    }
    
    private void setBeanStuff(NegotiationActivityHistoryLineBean bean, Date efectiveLocationStartDate, Date efectiveLocationEndDate, String locationDays) {
        bean.setEfectiveLocationEndDate(efectiveLocationEndDate);
        bean.setEfectiveLocationStartDate(efectiveLocationStartDate);
        bean.setLocationDays(locationDays); 
    }
    
    private boolean isDateBetween(Date checkDate, Date rangeStart, Date rangeEnd) {
        if (rangeStart == null) {
            return false;
        }
        if (checkDate == null) {
            checkDate = new Date(Calendar.getInstance().getTimeInMillis());
        }
        if (rangeEnd == null) {
            rangeEnd = new Date(Calendar.getInstance().getTimeInMillis());
        }
        boolean startOk = rangeStart.equals(checkDate) || rangeStart.before(checkDate);
        boolean endOk = rangeEnd.equals(checkDate) || rangeEnd.after(checkDate);
        return startOk && endOk;
    }
    
    /** UITSRA-2615: Need to copy over all of this b/c the bad method is private. */
    /** UITSRA-3231: Had to modify the unAssocaiatedDetail condition before*/
    public Negotiable getAssociatedObject(Negotiation negotiation) {
        if (negotiation != null && negotiation.getNegotiationAssociationType() != null) {
            Negotiable bo = null;
            if (StringUtils.equals(negotiation.getNegotiationAssociationType().getCode(), NegotiationAssociationType.AWARD_ASSOCIATION)) {
                bo = getAward(negotiation.getAssociatedDocumentId());
            } else if (StringUtils.equals(negotiation.getNegotiationAssociationType().getCode(), 
                    NegotiationAssociationType.INSTITUATIONAL_PROPOSAL_ASSOCIATION)) {
                bo = getInstitutionalProposal(negotiation.getAssociatedDocumentId());
            //} else if (StringUtils.equals(negotiation.getNegotiationAssociationType().getCode(), 
            //        NegotiationAssociationType.NONE_ASSOCIATION)) {
            } else if (IUNegotiationAssociationTypeUtil.isUnassociatedType(negotiation.getNegotiationAssociationType().getCode())) { //UITSRA-3231
                negotiation.refreshReferenceObject("unAssociatedDetail");
                bo = negotiation.getUnAssociatedDetail();
            } else if (StringUtils.equals(negotiation.getNegotiationAssociationType().getCode(), 
                    NegotiationAssociationType.PROPOSAL_LOG_ASSOCIATION)) {
                bo = getProposalLog(negotiation.getAssociatedDocumentId());
            } else if (StringUtils.equals(negotiation.getNegotiationAssociationType().getCode(), 
                    NegotiationAssociationType.SUB_AWARD_ASSOCIATION)) {
                bo = getSubAward(negotiation.getAssociatedDocumentId());
            }
            return bo;
        } else {
            return null;
        }
    }
    
    private Award getAward(String awardNumber) {
        Award award = this.getAwardBudgetService().getActiveOrNewestAward(awardNumber);
        return award;
    }
    
    private ProposalLog getProposalLog(String proposalNumber) {
        Map<String, String> primaryKeys = new HashMap<String, String>();
        primaryKeys.put("PROPOSAL_NUMBER", proposalNumber);
        ProposalLog pl = (ProposalLog) this.getBusinessObjectService().findByPrimaryKey(ProposalLog.class, primaryKeys);
        return pl;
    }
    
    private SubAward getSubAward(String subAwardId) {
        VersionHistory versionHistory = getVersionHistoryService().getActiveOrNewestVersion(SubAward.class, subAwardId);
        if (versionHistory != null) {
            return (SubAward) versionHistory.getSequenceOwner();
        } else {
            return null;
        }
    }
    
    private InstitutionalProposal getInstitutionalProposal(String proposalNumber) {
        InstitutionalProposal ip = this.getInstitutionalProposalService().getActiveInstitutionalProposalVersion(proposalNumber);
        if (ip == null) {
            //the proposal_number doesn't have an active one associated with it. so grab an inactive one, this will happen when a
            //a proposal log has been promoted to an institutional proposal but not completed yet.
            Map params = new HashMap();
            params.put("PROPOSAL_NUMBER", proposalNumber);
            @SuppressWarnings("unchecked")
			Collection<InstitutionalProposal> proposals = this.getBusinessObjectService().findMatching(InstitutionalProposal.class, params);
            if (proposals != null && proposals.size() > 0) {
                ip = proposals.iterator().next();
            }
        }
        return ip;
    }
    /** End UITSRA-2615 */
    
    protected ParameterService getParameterService() {
        return parameterService;
    }

    public void setParameterService(ParameterService parameterService) {
        this.parameterService = parameterService;
    }

    public BusinessObjectService getBusinessObjectService() {
        return businessObjectService;
    }

    public void setBusinessObjectService(BusinessObjectService businessObjectService) {
        this.businessObjectService = businessObjectService;
    }

    public AwardBudgetService getAwardBudgetService() {
        return awardBudgetService;
    }

    public void setAwardBudgetService(AwardBudgetService awardBudgetService) {
        this.awardBudgetService = awardBudgetService;
    }

    public InstitutionalProposalService getInstitutionalProposalService() {
        return institutionalProposalService;
    }

    public void setInstitutionalProposalService(InstitutionalProposalService institutionalProposalService) {
        this.institutionalProposalService = institutionalProposalService;
    }

    public UnitAdministratorDerivedRoleTypeServiceImpl getUnitAdministratorDerivedRoleTypeServiceImpl() {
        return unitAdministratorDerivedRoleTypeServiceImpl;
    }

    public void setUnitAdministratorDerivedRoleTypeServiceImpl(
            UnitAdministratorDerivedRoleTypeServiceImpl unitAdministratorDerivedRoleTypeServiceImpl) {
        this.unitAdministratorDerivedRoleTypeServiceImpl = unitAdministratorDerivedRoleTypeServiceImpl;
    }

    public KcPersonService getKcPersonService() {
        return kcPersonService;
    }

    public void setKcPersonService(KcPersonService kcPersonService) {
        this.kcPersonService = kcPersonService;
    }

    public VersionHistoryService getVersionHistoryService() {
        return versionHistoryService;
    }

    public void setVersionHistoryService(VersionHistoryService versionHistoryService) {
        this.versionHistoryService = versionHistoryService;
    }
    
    /* Begin UITSRA-3231 */
    @Override
    public boolean isNoModuleLinkingEnabled() {
        return this.isNegotaitionAssociationTypeActive(NegotiationAssociationType.NONE_ASSOCIATION);
    }

    /**
     * 
     * @see org.kuali.kra.negotiations.service.NegotiationService#findAndLoadNegotiationUnassociatedDetail(Negotiation)
     */
    public NegotiationUnassociatedDetail findAndLoadNegotiationUnassociatedDetail(Negotiation negotiation) {
        if (negotiation.getNegotiationAssociationType() != null 
                //&& StringUtils.equalsIgnoreCase(negotiation.getNegotiationAssociationType().getCode(), NegotiationAssociationType.NONE_ASSOCIATION)
        		&& IUNegotiationAssociationTypeUtil.isUnassociatedType(negotiation.getNegotiationAssociationType().getCode())
                && StringUtils.isNotEmpty(negotiation.getAssociatedDocumentId()) && negotiation.getAssociatedDocumentId().matches("\\d*")) {
            NegotiationUnassociatedDetail unAssociatedDetail = (NegotiationUnassociatedDetail) 
                    this.getBusinessObjectService().findBySinglePrimaryKey(NegotiationUnassociatedDetail.class, negotiation.getAssociatedDocumentId());
            return unAssociatedDetail;
        } else {
            return null;
        }
    }

    
    /* End UITSRA-3231 */
    
}