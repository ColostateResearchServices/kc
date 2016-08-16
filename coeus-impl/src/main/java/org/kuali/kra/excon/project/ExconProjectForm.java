package org.kuali.kra.excon.project;

import org.kuali.coeus.common.framework.version.history.VersionHistoryService;
import org.kuali.coeus.sys.framework.service.KcServiceLocator;
import org.kuali.coeus.common.framework.version.history.VersionHistory;
import org.kuali.coeus.common.framework.custom.CustomDataHelperBase;
import org.kuali.coeus.common.permissions.impl.web.struts.form.PermissionsForm;
import org.kuali.coeus.common.permissions.impl.web.struts.form.PermissionsHelperBase;
import org.kuali.kra.excon.customdata.ExconProjectCustomDataHelper;
import org.kuali.kra.excon.document.ExconProjectDocument;
import org.kuali.coeus.sys.framework.validation.Auditable;
import org.kuali.coeus.common.framework.custom.CustomDataDocumentForm;
import org.kuali.coeus.sys.framework.model.KcTransactionalDocumentFormBase;
import org.kuali.coeus.sys.framework.model.MultiLookupForm;
import org.kuali.rice.core.api.CoreApiServiceLocator;
import org.kuali.rice.kew.api.KewApiConstants;
import org.kuali.rice.kew.api.WorkflowDocument;
import org.kuali.rice.kns.web.ui.HeaderField;

import java.util.Map;

//import org.kuali.rice.krad.service.KeyValuesService;

/**
 * 
 * This class represents the ExconProject Form Struts class.
 */
public class ExconProjectForm extends KcTransactionalDocumentFormBase
                                        implements MultiLookupForm,
                                                    Auditable,
                                                    PermissionsForm,
                                                    CustomDataDocumentForm {
	
	private static final long serialVersionUID = -8037279104282425721L;
	private ExconProjectCustomDataHelper customDataHelper = new ExconProjectCustomDataHelper(this);
	private String lookupResultsSequenceNumber;
	private String lookupResultsBOClassName;
//	KeyValuesService keyValuesService = (KeyValuesService) KcServiceLocator.getService("keyValuesService");
    private boolean auditActivated;
    private ExconProjectEventsBean exconProjectEventsBean = new ExconProjectEventsBean(this);
    private ExconProjectDestinationsBean exconProjectDestinationsBean = new ExconProjectDestinationsBean(this);
    private ExconProjectCommentsBean exconProjectCommentsBean = new ExconProjectCommentsBean(this);
    private ExconProjectAttachmentsBean exconProjectAttachmentsBean = new ExconProjectAttachmentsBean(this);
    private ExconProjectUnitPersonsBean exconProjectUnitPersonsBean = new ExconProjectUnitPersonsBean(this);
    private ExconProjectPersonsBean exconProjectPersonsBean = new ExconProjectPersonsBean(this);
    private ExconProjectRPSEntitiesBean exconProjectRPSEntitiesBean = new ExconProjectRPSEntitiesBean(this);
    private ExconProjectEmailBean exconProjectEmailBean = new ExconProjectEmailBean(this);
    private ExconProjectExternalInstitutionsBean exconProjectExternalInstitutionsBean = new ExconProjectExternalInstitutionsBean(this);
    private ExconProjectReviewsBean exconProjectReviewsBean = new ExconProjectReviewsBean(this);
    private ExconProjectAssociatedDocumentsBean exconProjectAssociatedDocumentsBean = new ExconProjectAssociatedDocumentsBean(this);
	
	public void initialize() {
		// need to initialize variables here
//		if (exconProjectEventsBean==null) {
//			exconProjectEventsBean=new ExconProjectEventsBean(this);
//			exconProjectCommentsBean=new ExconProjectCommentsBean(this);
//			exconProjectAttachmentsBean=new ExconProjectAttachmentsBean(this);
//		}
		return;
	}
	
	public ExconProjectDocument getExconProjectDocument() {
		return (ExconProjectDocument) super.getDocument();
	}
	
	@Override
	public CustomDataHelperBase getCustomDataHelper() {
		return customDataHelper;
	}
	@Override
	public PermissionsHelperBase getPermissionsHelper() {
		// TODO Auto-generated method stub
		return null;
	}
	public ExconProjectEventsBean getExconProjectEventsBean() {
		return exconProjectEventsBean;
	}
	
	public ExconProjectUnitPersonsBean getExconProjectUnitPersonsBean() {
		return exconProjectUnitPersonsBean;
	}
	
	public ExconProjectPersonsBean getExconProjectPersonsBean() {
		return exconProjectPersonsBean;
	}
	
	public ExconProjectRPSEntitiesBean getExconProjectRPSEntitiesBean() {
		return exconProjectRPSEntitiesBean;
	}
	public ExconProjectDestinationsBean getExconProjectDestinationsBean() {
		return exconProjectDestinationsBean;
	}
	
	public ExconProjectCommentsBean getExconProjectCommentsBean() {
		return exconProjectCommentsBean;
	}
	
	public ExconProjectAttachmentsBean getExconProjectAttachmentsBean() {
		return exconProjectAttachmentsBean;
	}
	
	public ExconProjectEmailBean getExconProjectEmailBean() {
		return exconProjectEmailBean;
	}
	
	public ExconProjectExternalInstitutionsBean getExconProjectExternalInstitutionsBean() {
		return exconProjectExternalInstitutionsBean;
	}
	
	public ExconProjectReviewsBean getExconProjectReviewsBean() {
		return exconProjectReviewsBean;
	}
	
	public ExconProjectAssociatedDocumentsBean getExconProjectAssociatedDocumentsBean() {
		return exconProjectAssociatedDocumentsBean;
	}

	@Override
	protected String getDefaultDocumentTypeName() {
		return "ExconProjectDocument";
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
	
	public ExconProject getExconProject() {
		return this.getExconProjectDocument().getExconProject();
	}
	
    /**
     * 
     * @see org.kuali.rice.kns.web.struts.form.KualiDocumentFormBase#populateHeaderFields(org.kuali.rice.kew.api.WorkflowDocument)
     */
    @Override
    public void populateHeaderFields(WorkflowDocument workflowDocument) {
        // super.populateHeaderFields(workflowDocument);

        ExconProjectDocument exconProjectDocument = getExconProjectDocument();
        ExconProject exconProject = exconProjectDocument.getExconProject();
        String projectStatusDescription="";
        String updateUser="";
        String updateTimestamp="";
        if (exconProject!=null) {
        	if (exconProject.getProjectStatusCode()!=null) {projectStatusDescription=exconProject.getProjectStatus().getDescription();}
        	if (exconProject.getUpdateUser()!=null) {updateUser=exconProject.getUpdateUser();}
        	if (exconProject.getUpdateTimestamp()!=null) {
        		updateTimestamp=CoreApiServiceLocator.getDateTimeService().toString(exconProject.getUpdateTimestamp(), "MM/dd/yy HH:mm");
        		}
        }
        getDocInfo().clear();
        

        String documentNumber = "";
        String documentStatus = "";
        if (workflowDocument != null) {
            documentNumber = exconProjectDocument.getDocumentNumber();
            documentStatus = workflowDocument.getStatus().getLabel();
        }
        getDocInfo().add(new HeaderField("DataDictionary.DocumentHeader.attributes.documentNumber", documentNumber));
        getDocInfo().add(new HeaderField("DataDictionary.ExconProject.attributes.documentStatus", documentStatus));
        getDocInfo().add(new HeaderField("DataDictionary.ExconProject.attributes.projectNumber", exconProjectDocument.getExconProject().getProjectNumber()));
        getDocInfo().add(new HeaderField("DataDictionary.ExconProject.attributes.projectStatusCode", projectStatusDescription));
        getDocInfo().add(new HeaderField("DataDictionary.ExconProject.attributes.updateUser", updateUser));
        getDocInfo().add(new HeaderField("DataDictionary.ExconProject.attributes.updateTimestamp", updateTimestamp));
        

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
        boolean displayEditButton = !isViewOnly() && !getExconProjectDocument().isCanceled();
        /* probably not needed
        if (isDocOpenedFromExconProjectSearch()) {
            displayEditButton = true;
        }
        */
        
        VersionHistory activeVersion = getVersionHistoryService().findActiveVersion(ExconProject.class, getExconProjectDocument().getExconProject().getProjectNumber());
        if (activeVersion != null) {
            displayEditButton &= activeVersion.getSequenceOwnerSequenceNumber().equals(getExconProjectDocument().getExconProject().getSequenceNumber());
        }
        
        return displayEditButton;
    }
    
    protected VersionHistoryService getVersionHistoryService() {
        return KcServiceLocator.getService(VersionHistoryService.class);
    }
	
    /**
     * 
     * This method initializes either the document or the form based on the command value.
     */
    public void initializeFormOrDocumentBasedOnCommand(){
        if (KewApiConstants.INITIATE_COMMAND.equals(getCommand())) {
        	initialize();
            getExconProjectDocument().initialize();
        }else{
            initialize();
        }
    }
}