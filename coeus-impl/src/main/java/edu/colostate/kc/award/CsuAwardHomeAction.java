package edu.colostate.kc.award;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.kra.award.AwardForm;
import org.kuali.kra.award.document.AwardDocument;
import org.kuali.kra.award.web.struts.action.AwardHomeAction;
import org.kuali.kra.infrastructure.Constants;

public class CsuAwardHomeAction extends AwardHomeAction {
	
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
