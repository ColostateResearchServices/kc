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
package org.kuali.kra.coi.personfinancialentity;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.coeus.sys.framework.controller.KcConfirmationQuestion;
import org.kuali.coeus.sys.framework.controller.StrutsConfirmation;
import org.kuali.kra.coi.CoiActionType;
import org.kuali.kra.coi.CoiDisclosure;
import org.kuali.kra.coi.CoiDisclosureDocument;
import org.kuali.kra.coi.CoiDisclosureForm;
import org.kuali.kra.coi.notesandattachments.attachments.FinancialEntityAttachment;
import org.kuali.kra.infrastructure.Constants;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

import edu.colostate.kc.infrastructure.CSUKeyConstants;


/**
 * 
 * This class is the struts action for maintaining new financial entity page
 */
public class FinancialEntityEditNewAction extends FinancialEntityAction {
    private static final String NEW_FINANCIAL_ENTITY = "financialEntityHelper.newPersonFinancialEntity";

    private static final String CONFIRM_YES_DELETE_ATTACHMENT = "confirmDeleteAttachment";
    private static final String CONFIRM_NO_DELETE = "";
    private static final String CONFIRM_YES_ADD_ANOTHER_FE_Q_ID = "yesDeclareAdditionalFE";
    private static final String CONFIRM_NO_ADD_ANOTHER_FE_Q_ID = "noDeclareAdditionalFE";
    
    

    /**
     * 
     * This method is to submit the new FE
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward submit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        FinancialEntityForm financialEntityForm = (FinancialEntityForm) form;
        FinancialEntityHelper financialEntityHelper = financialEntityForm.getFinancialEntityHelper();
        PersonFinIntDisclosure newFinancialEntityDisclosure = financialEntityHelper.getNewPersonFinancialEntity();            

        if (isValidToSave(newFinancialEntityDisclosure, NEW_FINANCIAL_ENTITY)) {
            saveNewFinancialEntity(form);
            // send notification to admins that FE has been modified
            sendNotificationAndPersist(CoiActionType.FE_CREATED_EVENT, "Financial Entity Modified", newFinancialEntityDisclosure);
            if (StringUtils.isNotBlank(financialEntityForm.getCoiDocId())) {
                return promptForAdditionalFE(mapping, form, request, response);            
            }
        }

        return mapping.findForward(Constants.MAPPING_BASIC);
    }


    public ActionForward promptForAdditionalFE(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        final StrutsConfirmation confirm = buildParameterizedConfirmationQuestion(mapping, form, request, response, 
        		CONFIRM_YES_ADD_ANOTHER_FE_Q_ID, CSUKeyConstants.ADD_ANOTHER_FE_QUESTION);
        
        return confirmAdditionalFE(confirm, CONFIRM_YES_ADD_ANOTHER_FE_Q_ID, CONFIRM_NO_ADD_ANOTHER_FE_Q_ID);
    }    
    
    /**
     * "borrowed" from KraTransactionalDocumentActionBase class
     */
    public ActionForward confirmAdditionalFE(StrutsConfirmation question, String yesMethodName, String noMethodName) throws Exception {
        // Figure out what the caller is. We want the direct caller of confirm()
      //  question.setCaller(((KualiForm) question.getForm()).getMethodToCall());
        question.setCaller("promptForAdditionalFE");

        if (question.hasQuestionInstAttributeName()) {
            Object buttonClicked = question.getRequest().getParameter(KRADConstants.QUESTION_CLICKED_BUTTON);
            if (KcConfirmationQuestion.YES.equals(buttonClicked) && StringUtils.isNotBlank(yesMethodName)) {
                return dispatchMethod(question.getMapping(), question.getForm(), question.getRequest(), question.getResponse(),
                        yesMethodName);
            }
            else if (StringUtils.isNotBlank(noMethodName)) {
                return dispatchMethod(question.getMapping(), question.getForm(), question.getRequest(), question.getResponse(),
                        noMethodName);
            }
        }
        else {
        	// to skip the initial submit logic the next time through.
        	question.getRequest().setAttribute(KRADConstants.METHOD_TO_CALL_ATTRIBUTE, KRADConstants.DISPATCH_REQUEST_PARAMETER + "."
					+ "promptForAdditionalFE" + ".x");
            return this.performQuestionWithoutInput(question, KRADConstants.EMPTY_STRING);
        }

        return question.getMapping().findForward(Constants.MAPPING_BASIC);
    }    


    /*
     * 
     */
    public ActionForward yesDeclareAdditionalFE(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
                                                                                                                throws Exception {
    	return additionalNewFinancialEntity(mapping, form, request, response);
    }
    
    /*
     * 
     */
    public ActionForward additionalNewFinancialEntity(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ActionForward actionForward = editNew(mapping, form, request, response);
        if (GlobalVariables.getMessageMap().hasNoErrors()) {
            FinancialEntityForm financialEntityForm = (FinancialEntityForm) form;
            String forward = ConfigContext.getCurrentContextConfig().getProperty("kuali.docHandler.url.prefix")
                    + "/financialEntityEditNew.do?methodToCall=addNewCoiDiscFinancialEntity&coiDocId="
                    + financialEntityForm.getCoiDocId() + "&financialEntityHelper.reporterId="
                    + financialEntityForm.getFinancialEntityHelper().getFinancialEntityReporter().getPersonId();
            return new ActionForward(forward, true);
        }
        return actionForward;
    }    
    
    
    /*
     * if user answers "no", then return to Disclosure if we came from there
     */
    public ActionForward noDeclareAdditionalFE(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        FinancialEntityForm financialEntityForm = (FinancialEntityForm) form;

        if (StringUtils.isNotBlank(financialEntityForm.getCoiDocId())) {
            String forward = buildForwardUrl(financialEntityForm.getCoiDocId());
            financialEntityForm.setCoiDocId(null);
            financialEntityForm.getFinancialEntityHelper().setReporterId(null);
            return new ActionForward(forward, true);
        }
        return whereToGoAfterCancel(mapping, form, request, response);
    }
        
    
    /*
     * 
     */
    public ActionForward newFinancialEntity(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ActionForward actionForward = editNew(mapping, form, request, response);
        if (GlobalVariables.getMessageMap().hasNoErrors()) {
            CoiDisclosureForm coiDisclosureForm = (CoiDisclosureForm) form;
            CoiDisclosure coiDisclosure = ((CoiDisclosureDocument)coiDisclosureForm.getDocument()).getCoiDisclosure();
            String forward = ConfigContext.getCurrentContextConfig().getProperty("kuali.docHandler.url.prefix")
                    + "/financialEntityEditNew.do?methodToCall=addNewCoiDiscFinancialEntity&coiDocId="
                    + ((CoiDisclosureForm) form).getDocument().getDocumentNumber() + "&financialEntityHelper.reporterId="
                    + coiDisclosure.getPersonId();
            return new ActionForward(forward, true);
        }
        return actionForward;
    }    
    

    
    /*
     * utility method to set up the new financial entity for save
     */
    private void saveNewFinancialEntity(ActionForm form) {
        FinancialEntityHelper financialEntityHelper = ((FinancialEntityForm) form).getFinancialEntityHelper();
        PersonFinIntDisclosure personFinIntDisclosure = financialEntityHelper.getNewPersonFinancialEntity();
        personFinIntDisclosure.setEntityNumber(getFinancialEntityService().getNextEntityNumber()); 
        if (ObjectUtils.isNotNull(personFinIntDisclosure.getSponsor()) && ObjectUtils.isNotNull(personFinIntDisclosure.getSponsor().getSponsorName())) {
            personFinIntDisclosure.setSponsorName(personFinIntDisclosure.getSponsor().getSponsorName());
        }
        // it seems coeus always save 1.  not sure we need this because it should be in disclosure details
        personFinIntDisclosure.setRelationshipTypeCode("1");
        personFinIntDisclosure.setProcessStatus("F");
        personFinIntDisclosure.setSequenceNumber(1);
        personFinIntDisclosure.setPerFinIntDisclDetails(getFinancialEntityService().getFinDisclosureDetails(
                financialEntityHelper.getNewRelationDetails(), personFinIntDisclosure.getEntityNumber(),
                personFinIntDisclosure.getSequenceNumber()));
        personFinIntDisclosure.setFinEntityAttachments(financialEntityHelper.getFinEntityAttachmentList());
        saveFinancialEntity(form, personFinIntDisclosure);
        financialEntityHelper.setNewPersonFinancialEntity(new PersonFinIntDisclosure());
        financialEntityHelper.getNewPersonFinancialEntity().setCurrentFlag(true);
        financialEntityHelper.getNewPersonFinancialEntity().setPersonId(GlobalVariables.getUserSession().getPrincipalId());
        financialEntityHelper.getNewPersonFinancialEntity().setFinancialEntityReporterId(
                financialEntityHelper.getFinancialEntityReporter().getFinancialEntityReporterId());
        financialEntityHelper.setNewRelationDetails(getFinancialEntityService().getFinancialEntityDataMatrix());
        financialEntityHelper.setFinEntityAttachmentList(new ArrayList<FinancialEntityAttachment>());
    }

    /**
     * 
     * This method is for Coi disclosure FE 'newFinancialEntity'.  It will be forwarded to here
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward addNewCoiDiscFinancialEntity(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        ((FinancialEntityForm) form).getFinancialEntityHelper().initiate();
        return mapping.findForward(Constants.MAPPING_BASIC);
    }
    
    /**
     * 
     * This method is to add a new attachment for Financial Entity
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward addNewFinancialEntityAttachment(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        FinancialEntityForm financialEntityForm = (FinancialEntityForm) form;
        financialEntityForm.getFinancialEntityHelper().addNewFinancialEntityAttachment();
        return mapping.findForward(Constants.MAPPING_BASIC);
    }

    /**
     * Method called when deleting an attachment from a Financial Entity.
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response= 
     * @return
     * @throws Exception
     */
    public ActionForward deleteFinancialEntityAttachment(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
            HttpServletResponse response) throws Exception {
        FinancialEntityForm financialEntityForm = (FinancialEntityForm) form;
        int selectedLine = getSelectedLine(request);
        financialEntityForm.getFinancialEntityHelper().removeNewFinancialEntityAttachment(selectedLine);
        return mapping.findForward(Constants.MAPPING_BASIC);
    }

    /*
     * for new FE and user cancels, go back to main page
     */
    @Override
    public ActionForward whereToGoAfterCancel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return mapping.findForward(KRADConstants.MAPPING_PORTAL);
    }

}
