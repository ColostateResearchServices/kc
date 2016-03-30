package edu.colostate.kc.award.reservation.web.struts.form;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.colostate.kc.award.reservation.*;
import edu.colostate.kc.award.reservation.document.AwardAccountReservationDocument;

import org.kuali.kra.authorization.KraAuthorizationConstants;
import org.kuali.kra.bo.versioning.VersionHistory;
import org.kuali.kra.common.customattributes.CustomDataHelperBase;
import org.kuali.kra.common.notification.web.struts.form.NotificationHelper;
import org.kuali.kra.common.permissions.web.struts.form.PermissionsForm;
import org.kuali.kra.common.permissions.web.struts.form.PermissionsHelperBase;
import org.kuali.kra.external.award.web.AccountCreationPresentationHelper;
import org.kuali.kra.infrastructure.Constants;
import org.kuali.kra.infrastructure.KraServiceLocator;
import org.kuali.kra.medusa.MedusaBean;
import org.kuali.kra.service.VersionHistoryService;
import org.kuali.kra.web.struts.form.Auditable;
import org.kuali.kra.web.struts.form.BudgetVersionFormBase;
import org.kuali.kra.web.struts.form.CustomDataDocumentForm;
import org.kuali.kra.web.struts.form.KraTransactionalDocumentFormBase;
import org.kuali.kra.web.struts.form.MultiLookupFormBase;
import org.kuali.rice.core.api.CoreApiServiceLocator;
import org.kuali.rice.core.api.config.property.ConfigurationService;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.coreservice.framework.parameter.ParameterConstants;
import org.kuali.rice.coreservice.framework.parameter.ParameterService;
import org.kuali.rice.kew.api.KewApiConstants;
import org.kuali.rice.kew.api.WorkflowDocument;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.kuali.rice.kns.datadictionary.HeaderNavigation;
import org.kuali.rice.kns.util.ActionFormUtilMap;
import org.kuali.rice.kns.web.ui.ExtraButton;
import org.kuali.rice.kns.web.ui.HeaderField;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.service.DocumentService;
import org.kuali.rice.krad.util.GlobalVariables;
//import org.kuali.rice.krad.service.KeyValuesService;
import org.kuali.rice.krad.util.KRADConstants;

import java.text.ParseException;
import java.util.*;

/**
 * 
 * This class represents the AwardAccountReservation Form Struts class.
 */
public class AwardAccountReservationForm extends KraTransactionalDocumentFormBase 
                                        implements MultiLookupFormBase,
                                                    Auditable,
                                                    PermissionsForm {
	
	private static final long serialVersionUID = -8033452344282425721L;
	private String lookupResultsSequenceNumber;
	private String lookupResultsBOClassName;
//	KeyValuesService keyValuesService = (KeyValuesService) KraServiceLocator.getService("keyValuesService");
    private boolean auditActivated;
    private AwardAccountReservationsBean awardAccountReservationsBean = new AwardAccountReservationsBean(this);
    /*
    private AwardAccountReservationEventsBean awardAccountReservationEventsBean = new AwardAccountReservationEventsBean(this);
    private AwardAccountReservationDestinationsBean awardAccountReservationDestinationsBean = new AwardAccountReservationDestinationsBean(this);
    private AwardAccountReservationCommentsBean awardAccountReservationCommentsBean = new AwardAccountReservationCommentsBean(this);
    private AwardAccountReservationAttachmentsBean awardAccountReservationAttachmentsBean = new AwardAccountReservationAttachmentsBean(this);
    private AwardAccountReservationUnitPersonsBean awardAccountReservationUnitPersonsBean = new AwardAccountReservationUnitPersonsBean(this);
    private AwardAccountReservationPersonsBean awardAccountReservationPersonsBean = new AwardAccountReservationPersonsBean(this);
    private AwardAccountReservationRPSPersonsBean awardAccountReservationRPSPersonsBean = new AwardAccountReservationRPSPersonsBean(this);
    private AwardAccountReservationEmailBean awardAccountReservationEmailBean = new AwardAccountReservationEmailBean(this);
	*/
    
	public void initialize() {
		// need to initialize variables here
		return;
	}
	
	public AwardAccountReservationDocument getAwardAccountReservationDocument() {
		return (AwardAccountReservationDocument) super.getDocument();
	}
	
	public AwardAccountReservationDocument getOrFindAwardAccountReservationDocument() {
		AwardAccountReservation existingReservation=null;
		Map<String,String> primaryKeys=new HashMap<String,String>();
		primaryKeys.put("reservationUser", GlobalVariables.getUserSession().getPrincipalName());
		if ((existingReservation=getBusinessObjectService().findByPrimaryKey(AwardAccountReservation.class, primaryKeys)) != null) {
//			AwardAccountReservationDocument existingDocument=existingReservation.getAwardAccountReservationDocument();
			try {
				super.setDocument(getDocumentService().getByDocumentHeaderId(existingReservation.getAwardAccountReservationDocument().getDocumentNumber()));
			}
			catch (Exception e) {
				System.err.println(e.toString());
			}
		}
		return (AwardAccountReservationDocument) super.getDocument();
	}
	
	private DocumentService getDocumentService() {
		return KraServiceLocator.getService(DocumentService.class);
	}

    private BusinessObjectService getBusinessObjectService() {
        return KraServiceLocator.getService(BusinessObjectService.class);
    }
    
	@Override
	public PermissionsHelperBase getPermissionsHelper() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	public AwardAccountReservationEmailBean getAwardAccountReservationEmailBean() {
		return awardAccountReservationEmailBean;
	}
	*/
	
	public AwardAccountReservationsBean getAwardAccountReservationsBean() {
		return awardAccountReservationsBean;
	}

	@Override
	protected String getDefaultDocumentTypeName() {
		return "AwardAccountReservationDocument";
	}
	
	public String getDocumentTypeName() {
		return getDefaultDocumentTypeName();
	}
	
	@Override
	protected String getLockRegion() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	protected void setSaveDocumentControl(Map arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public AwardAccountReservation getAwardAccountReservation() {
		return this.getAwardAccountReservationDocument().getAwardAccountReservation();
	}
	
    /**
     * 
     * @see org.kuali.rice.kns.web.struts.form.KualiDocumentFormBase#populateHeaderFields(org.kuali.rice.kew.api.WorkflowDocument)
     */
    @Override
    public void populateHeaderFields(WorkflowDocument workflowDocument) {
        // super.populateHeaderFields(workflowDocument);

        AwardAccountReservationDocument awardAccountReservationDocument = getAwardAccountReservationDocument();
        AwardAccountReservation awardAccountReservation = awardAccountReservationDocument.getAwardAccountReservation();
        String updateUser="";
        String updateTimestamp="";
        if (awardAccountReservation!=null) {
        	if (awardAccountReservation.getUpdateUser()!=null) {updateUser=awardAccountReservation.getUpdateUser();}
        	if (awardAccountReservation.getUpdateTimestamp()!=null) {
        		updateTimestamp=CoreApiServiceLocator.getDateTimeService().toString(awardAccountReservation.getUpdateTimestamp(), "MM/dd/yy HH:mm");
        		}
        }
        getDocInfo().clear();
        

        String documentNumber = "";
        String documentStatus = "";
        if (workflowDocument != null) {
            documentNumber = awardAccountReservationDocument.getDocumentNumber();
            documentStatus = workflowDocument.getStatus().getLabel();
        }
        getDocInfo().add(new HeaderField("DataDictionary.DocumentHeader.attributes.documentNumber", documentNumber));
        getDocInfo().add(new HeaderField("DataDictionary.AwardAccountReservation.attributes.documentStatus", documentStatus));
        getDocInfo().add(new HeaderField("DataDictionary.AwardAccountReservation.attributes.updateUser", updateUser));
        getDocInfo().add(new HeaderField("DataDictionary.AwardAccountReservation.attributes.updateTimestamp", updateTimestamp));
        

    }

	/**.
	 * This is the Getter Method for auditActivated
	 * @return Returns the auditActivated.
	 */
	public boolean isAuditActivated() {
		return auditActivated;
	}

	/**.
	 * This is the Setter Method for auditActivated
	 * @param auditActivated The auditActivated to set.
	 */
	public void setAuditActivated(boolean auditActivated) {
		this.auditActivated = auditActivated;
	}

	public void setLookupResultsSequenceNumber(String lookupResultsSequenceNumber) {
        this.lookupResultsSequenceNumber = lookupResultsSequenceNumber;
    }

    public String getLookupResultsSequenceNumber() {
        return lookupResultsSequenceNumber;
    }

    public void setLookupResultsBOClassName(String lookupResultsBOClassName) {
        this.lookupResultsBOClassName = lookupResultsBOClassName;
    }

    public String getLookupResultsBOClassName() {
        return lookupResultsBOClassName;
    }
    
    public boolean getDisplayEditButton() {
        boolean displayEditButton = !isViewOnly() && !getAwardAccountReservationDocument().isCanceled();
        /* probably not needed
        if (isDocOpenedFromAwardAccountReservationSearch()) {
            displayEditButton = true;
        }
        */
    
        /*
        VersionHistory activeVersion = getVersionHistoryService().findActiveVersion(AwardAccountReservation.class, getAwardAccountReservationDocument().getAwardAccountReservation().getProjectNumber());
        if (activeVersion != null) {
            displayEditButton &= activeVersion.getSequenceOwnerSequenceNumber().equals(getAwardAccountReservationDocument().getAwardAccountReservation().getSequenceNumber());
        }
        */
        
        return displayEditButton;
    }
    
    protected VersionHistoryService getVersionHistoryService() {
        return KraServiceLocator.getService(VersionHistoryService.class);
    }
	
    /**
     * 
     * This method initializes either the document or the form based on the command value.
     */
    public void initializeFormOrDocumentBasedOnCommand(){
        if (KewApiConstants.INITIATE_COMMAND.equals(getCommand())) {
        	initialize();
            getOrFindAwardAccountReservationDocument().initialize();
        }else{
            initialize();
        }
    }

	@Override
	public String getRefreshCaller() {
		// TODO Auto-generated method stub
		return null;
	}
}