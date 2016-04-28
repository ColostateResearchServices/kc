package edu.colostate.kc.award.web.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.colostate.kc.award.contacts.CsuCentralAdminContactsHelper;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.kra.award.AwardForm;
import org.kuali.kra.award.web.struts.action.AwardContactsAction;
import org.kuali.kra.infrastructure.Constants;

import edu.colostate.kc.award.CsuAwardForm;

public class CsuAwardContactsAction extends AwardContactsAction {
	
	private CsuCentralAdminContactsHelper centralAdminContactsHelper;

	
	public CsuAwardContactsAction() {
		centralAdminContactsHelper = new CsuCentralAdminContactsHelper();
	}
	
	  /**
     * This method is used to add a delete Csu Central Admin Contacts
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return mapping forward
     * @throws Exception
     */
    public ActionForward deleteCentralAdminContact(ActionMapping mapping, ActionForm form, 
                                            HttpServletRequest request,
                                            HttpServletResponse response) throws Exception {
    	
        int numberToDelete = getLineToDelete(request); 	
        centralAdminContactsHelper.deleteCentralAdminContact((AwardForm)form, numberToDelete);        
        return mapping.findForward(Constants.MAPPING_BASIC);
    }    

    
    
    
	  /**
     * This method is used to add a delete Csu Central Admin Contacts
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return mapping forward
     * @throws Exception
     */
    public ActionForward addCentralAdminContact(ActionMapping mapping, ActionForm form, 
                                            HttpServletRequest request,
                                            HttpServletResponse response) throws Exception {
    	
    	((CsuAwardForm) form).getCsuCentralAdminContactsHelper().addCentralAdminContact((CsuAwardForm) form);        
        return mapping.findForward(Constants.MAPPING_BASIC);
    }

 
    
}
