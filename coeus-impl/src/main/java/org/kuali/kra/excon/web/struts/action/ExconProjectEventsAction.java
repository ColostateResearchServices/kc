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
import org.kuali.kra.excon.project.ExconProjectEventsBean;
import org.kuali.kra.excon.project.ExconProjectForm;
import org.kuali.kra.infrastructure.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExconProjectEventsAction extends ExconProjectAction {
	
    @Override
    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ActionForward forward=super.save(mapping, form, request, response);
    	return forward;
    }
    
    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward addEvent(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) 
                                                                                                                        throws Exception {
        getExconProjectEventsBean(form).addExconProjectEvent();
        return mapping.findForward(Constants.MAPPING_BASIC);
    }

    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward deleteEvent(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) 
                                                                                                                        throws Exception {
        getExconProjectEventsBean(form).deleteExconProjectEvent(getLineToDelete(request));
        return mapping.findForward(Constants.MAPPING_BASIC);
    }
    
    private ExconProjectEventsBean getExconProjectEventsBean(ActionForm form) {
        return ((ExconProjectForm) form).getExconProjectEventsBean();
    }
	
}