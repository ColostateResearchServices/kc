/*.
 * Copyright 2005-2014 The Kuali Foundation
 * 
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/ecl1.php
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.kra.excon.web.struts.action;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.kuali.coeus.sys.framework.controller.KcHoldingPageConstants;
import org.kuali.kra.excon.project.ExconProjectForm;
import org.kuali.kra.infrastructure.Constants;
import org.kuali.coeus.sys.framework.service.KcServiceLocator;
import org.kuali.rice.kns.web.struts.action.AuditModeAction;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.coeus.sys.framework.validation.AuditHelper;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**.
 *
 * This class represents the Struts Action for
 *  ExconProject Actions page(ExconProjectActions.jsp)
 */
public class ExconProjectActionsAction  extends
ExconProjectAction implements AuditModeAction {
    @Override
    public ActionForward execute(ActionMapping mapping,
    ActionForm form, ServletRequest request, ServletResponse response)
       throws Exception {
        ActionForward actionForward = super.
        execute(mapping, form, request, response);
        return actionForward;
    }
    /** {@inheritDoc} */
    public ActionForward activate(ActionMapping mapping,
    ActionForm form, HttpServletRequest request,
    HttpServletResponse response)
    throws Exception {
        return KcServiceLocator.getService(AuditHelper.class).
        setAuditMode(mapping, (ExconProjectForm) form, true);
    }
    /** {@inheritDoc} */
    public ActionForward deactivate(ActionMapping mapping,
    ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return KcServiceLocator.getService(AuditHelper.class).setAuditMode(mapping, (ExconProjectForm) form, false);
    }
    @Override
    public ActionForward blanketApprove(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception {
        ActionForward forward = super.blanketApprove(mapping, form, request, response);
        if (forward == null) {
            return routeExconProjectToHoldingPage(mapping, (ExconProjectForm) form);
        } else {
            return forward;
        }
    }
    @Override
    public ActionForward route(ActionMapping mapping, ActionForm form,
    HttpServletRequest request, HttpServletResponse response) throws Exception {
        ActionForward forward = super.route(mapping, form, request, response);
        if (forward == null) {
            return routeExconProjectToHoldingPage(mapping, (ExconProjectForm) form);
        } else {
            return forward;
        }
    }

    @Override
    public ActionForward approve(ActionMapping mapping,
    ActionForm form, HttpServletRequest request,
    HttpServletResponse response) throws Exception {
        ActionForward forward = super.approve(mapping, form, request, response);
        if(!GlobalVariables.getMessageMap().getErrorMessages().isEmpty()) {
            return forward;
        }else {
            return routeExconProjectToHoldingPage(mapping, (ExconProjectForm) form);
        }
    }

    private ActionForward routeExconProjectToHoldingPage(ActionMapping mapping, ExconProjectForm exconProjectForm) {
        String routeHeaderId = exconProjectForm.getExconProjectDocument().getDocumentNumber();
        String returnLocation = buildActionUrl(routeHeaderId, "projectActions", "ExconProjectDocument");
        
        ActionForward basicForward = mapping.findForward(KRADConstants.MAPPING_PORTAL);
        ActionRedirect holdingPageForward = new ActionRedirect(mapping.findForward(KcHoldingPageConstants.MAPPING_HOLDING_PAGE));
        holdingPageForward.addParameter(KcHoldingPageConstants.HOLDING_PAGE_DOCUMENT_ID, routeHeaderId);
        return routeToHoldingPage(basicForward, basicForward, holdingPageForward, returnLocation, routeHeaderId);
    }
    
    public ActionForward sendTravelerEmail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	((ExconProjectForm)form).getExconProjectEmailBean().sendTravelerEmail();
    	return mapping.findForward(Constants.MAPPING_BASIC);
    }
    
    public ActionForward addTravelerMeetingAgenda(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	((ExconProjectForm)form).getExconProjectEmailBean().addTravelerMeetingAgenda();
    	return mapping.findForward(Constants.MAPPING_BASIC);
    }

}
