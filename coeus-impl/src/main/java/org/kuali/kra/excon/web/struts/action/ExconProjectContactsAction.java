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
import org.drools.core.util.StringUtils;
import org.kuali.kra.excon.project.*;
import org.kuali.kra.infrastructure.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExconProjectContactsAction extends ExconProjectAction {
	
    @Override
    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ExconProject exconProject = ((ExconProjectForm)form).getExconProject();
    	if (exconProject.getProjectTypeCode().equals(exconProject.getTitle())) {
    		if ("RPS".equals(exconProject.getProjectTypeCode())) {
    			if (exconProject.getRpsEntity()!=null && !StringUtils.isEmpty(exconProject.getRpsEntity().getFirstName()) && !StringUtils.isEmpty(exconProject.getRpsEntity().getLastName())) {
    				exconProject.setTitle(exconProject.getProjectType().getDescription()+" for: "+exconProject.getRpsEntity().getFirstName()+" "+exconProject.getRpsEntity().getLastName());
    			}
    		}
    		else if ("TRV".equals(exconProject.getProjectTypeCode())) {
    			if (exconProject.getTraveler()!=null) {
    				exconProject.setTitle(exconProject.getProjectType().getDescription()+" for: "+exconProject.getTraveler().getFullName());
    			}
    		}
    	}
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
    public ActionForward addUnitPerson(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) 
                                                                                                                        throws Exception {
        getExconProjectUnitPersonsBean(form).addExconProjectUnitPerson();
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
    public ActionForward deleteUnitPerson(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) 
                                                                                                                        throws Exception {
        getExconProjectUnitPersonsBean(form).deleteExconProjectUnitPerson(getLineToDelete(request));
        return mapping.findForward(Constants.MAPPING_BASIC);
    }
    
    private ExconProjectUnitPersonsBean getExconProjectUnitPersonsBean(ActionForm form) {
        return ((ExconProjectForm) form).getExconProjectUnitPersonsBean();
    }
    
    
    
    
    
    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward addPerson(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) 
                                                                                                                        throws Exception {
        getExconProjectPersonsBean(form).addExconProjectPerson();
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
    public ActionForward deletePerson(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) 
                                                                                                                        throws Exception {
        getExconProjectPersonsBean(form).deleteExconProjectPerson(getLineToDelete(request));
        return mapping.findForward(Constants.MAPPING_BASIC);
    }
    
    private ExconProjectPersonsBean getExconProjectPersonsBean(ActionForm form) {
        return ((ExconProjectForm) form).getExconProjectPersonsBean();
    }

    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward addRPSEntity(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) 
                                                                                                                        throws Exception {
        getExconProjectRPSEntitiesBean(form).addRpsEntity();
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
    public ActionForward deleteRPSEntity(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) 
                                                                                                                        throws Exception {
//        getExconProjectRPSEntitiesBean(form).deleteRpsEntity(getLineToDelete(request));
    	getExconProjectRPSEntitiesBean(form).deleteRpsEntity();
        return mapping.findForward(Constants.MAPPING_BASIC);
    }
    
    public ActionForward editRPSEntity(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) 
            throws Exception {
    	getExconProjectRPSEntitiesBean(form).editRpsEntity();
    	return mapping.findForward(Constants.MAPPING_BASIC);
    }
 
/*
    public ActionForward performRPS(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	getExconProjectRPSEntitiesBean(form).performRPS();
    	return mapping.findForward(Constants.MAPPING_BASIC);
    }
*/
    
    private ExconProjectRPSEntitiesBean getExconProjectRPSEntitiesBean(ActionForm form) {
        return ((ExconProjectForm) form).getExconProjectRPSEntitiesBean();
    }
	
}