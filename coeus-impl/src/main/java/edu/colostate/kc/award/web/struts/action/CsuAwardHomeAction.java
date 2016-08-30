package edu.colostate.kc.award.web.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.colostate.kc.award.awardalternatenumber.AwardAlternateNumberHelper;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.kra.award.AwardForm;
import org.kuali.kra.award.web.struts.action.AwardHomeAction;
import org.kuali.kra.infrastructure.Constants;

import edu.colostate.kc.award.CsuAwardForm;


public class CsuAwardHomeAction extends AwardHomeAction {
	
	private AwardAlternateNumberHelper awardAlternateNumberHelper;



	public CsuAwardHomeAction() {
		awardAlternateNumberHelper = new AwardAlternateNumberHelper();
	}

    /**
     * This method is used to add a new Award Alternate Number
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return mapping forward
     * @throws Exception
     */
    public ActionForward addAlternateAwardNumber(ActionMapping mapping, ActionForm form,
                                                 HttpServletRequest request,
                                                 HttpServletResponse response) throws Exception {

        ((CsuAwardForm) form).getAwardAlternateNumberHelper().addAwardAlternateNumber((CsuAwardForm) form);
        return mapping.findForward(Constants.MAPPING_BASIC);
    }


    /**
     * This method is used to add a delete Award Alternate Number
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return mapping forward
     * @throws Exception
     */
    public ActionForward deleteAlternateAwardNumber(ActionMapping mapping, ActionForm form,
                                                    HttpServletRequest request,
                                                    HttpServletResponse response) throws Exception {

        int numberToDelete = getLineToDelete(request);
        awardAlternateNumberHelper.deleteAwardAlternateNumber((AwardForm)form, numberToDelete);
        return mapping.findForward(Constants.MAPPING_BASIC);
    }



    public AwardAlternateNumberHelper getAwardAlternateNumberHelper() {
        return awardAlternateNumberHelper;
    }


    public void setAwardAlternateNumberHelper(AwardAlternateNumberHelper helper) {
        this.awardAlternateNumberHelper = helper;
    }

    /**
     * This method is used to clear Account Info
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return mapping forward
     * @throws Exception
     */
    public ActionForward clearAccountInfo(ActionMapping mapping, ActionForm form, 
                                            HttpServletRequest request,
                                            HttpServletResponse response) throws Exception {
    	
        ((AwardForm)form).getAwardDocument().getAward().setAccountNumber(null);
        ((AwardForm)form).getAwardDocument().getAward().setFinancialChartOfAccountsCode(null);
        return mapping.findForward(Constants.MAPPING_BASIC);
    }	

}
