package edu.colostate.kc.award.reservation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.sql.Date;

import org.drools.core.util.StringUtils;
import org.kuali.kra.bo.KraPersistableBusinessObjectBase;
import org.kuali.kra.bo.ScienceKeyword;
import org.kuali.kra.document.KeywordsManager;
import org.kuali.kra.infrastructure.Constants;
import org.kuali.kra.infrastructure.KraServiceLocator;
import org.kuali.kra.service.KeywordsService;
import org.kuali.kra.web.struts.form.MultiLookupFormBase;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.krad.bo.PersistableBusinessObject;
import org.kuali.rice.krad.util.GlobalVariables;
import org.springframework.util.AutoPopulatingList;

import edu.colostate.kc.award.reservation.document.AwardAccountReservationDocument;
import edu.colostate.kc.award.reservation.web.struts.form.AwardAccountReservationForm;

public class AwardAccountReservation extends KraPersistableBusinessObjectBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -198773641807371850L;
	private String reservationUser;
	private String documentNumber;
	private List<AwardAccount> reservedAccounts;
	private AwardAccountReservationDocument awardAccountReservationDocument;
	private String documentStatus;
	public static final String NOTIFICATION_TYPE_SUBMIT = "501";
	private Boolean needsSaved=false;
	
	public AwardAccountReservation() {
		setReservationUser(GlobalVariables.getUserSession().getPrincipalName());
		reservedAccounts = new AutoPopulatingList<AwardAccount>(AwardAccount.class);
		this.refreshReferenceObject("reservedAccounts");
	}
	
	public String getReservationUser() {
		return reservationUser;
	}
	public void setReservationUser(String reservationUser) {
		this.reservationUser = reservationUser;
	}
	
	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public void setReservedAccounts(ArrayList<AwardAccount> reservedAccounts) {
		this.reservedAccounts=reservedAccounts;
	}
	
	public List<AwardAccount> getReservedAccounts() {
		
/*
		List<AwardAccount> filteredAccounts=new ArrayList<AwardAccount>(reservedAccounts.size());
		Collections.copy(filteredAccounts, reservedAccounts);
		for (int i=0; i<reservedAccounts.size();i++) {
			AwardAccount thisAccount=filteredAccounts.get(i);
			if (!thisAccount.getReservationUser().equals(getReservationUser())) {
				filteredAccounts.remove(thisAccount);
			}
		}
*/
		Collections.sort(reservedAccounts);
		return reservedAccounts;
	}
	
	public void add(AwardAccount newAccount) {
		newAccount.setReservationUser(reservationUser);
		newAccount.setReservationDate(new Date(System.currentTimeMillis()));
		getReservedAccounts().add(newAccount);
	}
	
	public void delete(AwardAccount oldAccount) {
		getReservedAccounts().remove(oldAccount);
	}
	
    public void addAccounts(MultiLookupFormBase multiLookUpForm) {
        try{
            // check to see if we are coming back from a lookup
//            if (Constants.MULTIPLE_VALUE.equals(multiLookUpForm.getRefreshCaller())) {
                // Multivalue lookup. Note that the multivalue keyword lookup results are returned persisted to avoid using session.
                // Since URLs have a max length of 2000 chars, field conversions can not be done.
                String lookupResultsSequenceNumber = multiLookUpForm.getLookupResultsSequenceNumber();//implement MultiLookupFormSupport
                if (!StringUtils.isEmpty(lookupResultsSequenceNumber)) {
                    Class lookupResultsBOClass = Class.forName(multiLookUpForm.getLookupResultsBOClassName());
                    Collection<PersistableBusinessObject> rawValues = KNSServiceLocator.getLookupResultsService()
                    .retrieveSelectedResultBOs(lookupResultsSequenceNumber, lookupResultsBOClass, GlobalVariables.getUserSession().getPrincipalId());
                    if (lookupResultsBOClass.isAssignableFrom(ResearchAccount.class)) {
                        for (Iterator iter = rawValues.iterator(); iter.hasNext();) {
                            ResearchAccount researchAccount = (ResearchAccount) iter.next();
                            AwardAccount awardAccount = new AwardAccount();
                            awardAccount.setFullAccountNumber(researchAccount.getFullAccountNumber());
                            awardAccount.setAccountUsed("N");
                            awardAccount.setSaveFlag();
                            add(awardAccount);
                        }
                    }
                }
//            }   
        }catch(Exception ex){
//            LOG.error(ex);
        	System.err.println(ex.toString());
        }
        
    }
    
    public void deleteAccounts(AwardAccountReservationForm resForm) {
    	List<AwardAccount> accountList=getReservedAccounts();
    	for (int accountIndex=0; accountIndex<accountList.size(); accountIndex++) {
    		if (accountIndex<0) {break;}
    		AwardAccount account=accountList.get(accountIndex);
    		if (account.getSelectAccount()) {
    			account.setSelectAccount(false);
    			delete(account);
    			accountIndex--;
    			needsSaved=true;
    		}
    	}
    }
	
	public void resetPersistenceState() {
		reservationUser=null;
		versionNumber=null;
	}

	public void setAwardAccountReservationDocument(
			AwardAccountReservationDocument awardAccountReservationDocument) {
		this.awardAccountReservationDocument=awardAccountReservationDocument;
		
	}
	
	public AwardAccountReservationDocument getAwardAccountReservationDocument() {
		if (awardAccountReservationDocument==null) {
			this.refreshReferenceObject("awardAccountReservationDocument");
		}
		return awardAccountReservationDocument;
	}
	
	

	public String getDocumentKey() {
		return "AwardAccountReservationDocument";
	}

	public String getDocumentStatus() {
		return documentStatus;
	}

	public void setDocumentStatus(String documentStatus) {
		this.documentStatus = documentStatus;
	}
	
	public Boolean getSaveFlag() {
		return needsSaved;
	}
	
	protected void clearSaveFlag() {
		needsSaved=false;
	}
	
	@Override
	public void prePersist() {
		clearSaveFlag();
		super.prePersist();
	}
	
	@Override
	public void preUpdate() {
		clearSaveFlag();
		super.preUpdate();
	}
		
}