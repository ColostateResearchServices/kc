package edu.colostate.kc.award;

import java.util.ArrayList;
import java.util.List;

import edu.colostate.kc.award.awardalternatenumber.AwardAlternateNumber;
import org.apache.commons.lang.StringUtils;
import org.kuali.coeus.sys.framework.service.KcServiceLocator;
import org.kuali.coeus.common.framework.version.sequence.associate.SequenceAssociate;
import org.kuali.kra.SkipVersioning;
import org.kuali.kra.award.contacts.AwardUnitContact;
import org.kuali.kra.award.home.Award;
import org.kuali.coeus.common.framework.unit.UnitContactType;
import org.kuali.rice.krad.bo.PersistableBusinessObjectExtensionBase;
import org.kuali.rice.krad.service.BusinessObjectService;

import edu.colostate.kc.award.contacts.CsuCentralAdminContact;
import edu.colostate.kc.award.reservation.AvailableResearchAccount;

public class AwardExtension extends PersistableBusinessObjectExtensionBase implements SequenceAssociate<Award>  {
	
    private Long awardId;


    private List<AwardAlternateNumber> awardAlternateNumbers;


	private List<CsuCentralAdminContact> centralAdminContacts;

	private String fullAccountNumber;

	private AvailableResearchAccount researchAccount;

	@SkipVersioning
    private Award award;
    
    public AwardExtension() {
    	init();
    }
    
    private void init() {
    	awardAlternateNumbers = new ArrayList<AwardAlternateNumber>();
		centralAdminContacts = new ArrayList<CsuCentralAdminContact>();

	}


    public void addNewAwardAlternateNumber(AwardAlternateNumber newAwardAlternateNumber ) {
    	awardAlternateNumbers.add(newAwardAlternateNumber);
    }

	public void deleteAwardAlternateNumber(int numberToDelete) {
		getAwardAlternateNumbers().remove(numberToDelete);
	}



	public Long getAwardId() {
		return awardId;
	}
	public void setAwardId(Long awardId) {
		this.awardId = awardId;
	}
	public List<AwardAlternateNumber> getAwardAlternateNumbers() {
		return awardAlternateNumbers;
	}
	public void setAwardAlternateNumbers(ArrayList<AwardAlternateNumber> awardAlternateNumbers) {
		this.awardAlternateNumbers = awardAlternateNumbers;
	}

	public void setAwardAlternateNumbers(List<AwardAlternateNumber> awardAlternateNumbers) {
		this.awardAlternateNumbers = awardAlternateNumbers;
	}

	@Override
	public Integer getSequenceNumber() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void resetPersistenceState() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setSequenceOwner(Award newlyVersionedOwner) {
		// TODO Auto-generated method stub

	}

	@Override
	public Award getSequenceOwner() {
		// TODO Auto-generated method stub
		return getAward();
	}

	public Award getAward() {
		if (award == null  && getAwardId()!=null && getAwardId() >0 ) {
            this.refreshReferenceObject("award");
        }
		return award;
	}

	public void setAward(Award award) {
		this.award = award;
	}

	public List<CsuCentralAdminContact> getCentralAdminContacts() {
		if ((centralAdminContacts == null || centralAdminContacts.size() == 0) && getAward()!=null) {
			setContactsFromAwardUnitContacts(getAward().getCentralAdminContacts());
		}
		return centralAdminContacts;
	}

	public void setCentralAdminContacts(List<CsuCentralAdminContact> contacts) {
		this.centralAdminContacts = contacts;
	}




	private List<CsuCentralAdminContact> setContactsFromAwardUnitContacts(List<AwardUnitContact> contacts) {

		// This code is only safe/reasonable while our default list of
		// Central admin contacts is small - typically 4-6 elements only
		for (AwardUnitContact contact : contacts)
		{
			CsuCentralAdminContact contactToInsert;
			if (contact.getPerson() != null) {
				contactToInsert = new CsuCentralAdminContact(contact.getPerson(), contact.getContactRole(), contact.getUnitContactType());
			} else {
				contactToInsert = new CsuCentralAdminContact(contact.getRolodex(), contact.getContactRole(), contact.getUnitContactType());
			}
			contactToInsert.setAward(contact.getAward());
            contactToInsert.setUnitAdministratorType(contact.getUnitAdministratorType());
            contactToInsert.setUnitAdministratorTypeCode(contact.getUnitAdministratorType().getCode());
			contactToInsert.setUnitContactType(UnitContactType.CONTACT);
			this.centralAdminContacts.add(contactToInsert);
		}

		return centralAdminContacts;

	}

	public void addCentralAdminContact(CsuCentralAdminContact newCentralAdminContact) {
		newCentralAdminContact.setAward(getAward());
		centralAdminContacts.add(newCentralAdminContact);
	}


	public void deleteCentralAdminContact(int numberToDelete) {
		getCentralAdminContacts().remove(numberToDelete);
	}

	public String getFullAccountNumber() {
		populateFullAccountNumber();
		return fullAccountNumber;
	}

	public void setFullAccountNumber(String fullAccountNumber) {
		this.fullAccountNumber = fullAccountNumber;
	}

	public AvailableResearchAccount getResearchAccount() {
		if (!StringUtils.isEmpty(getFullAccountNumber()) && (researchAccount==null || !researchAccount.getFullAccountNumber().equals(getFullAccountNumber()))) {
			this.refreshReferenceObject("researchAccount");
//			researchAccount.refreshReferenceObject("awardAccountList");
		}
		return researchAccount;
	}

	public void setResearchAccount(AvailableResearchAccount researchAccount) {
		this.researchAccount = researchAccount;
	}

	public void populateFullAccountNumber() {
		if (getAward()==null || StringUtils.isEmpty(getAward().getAccountNumber())) {
			return;
		}
		fullAccountNumber=getAward().getFinancialChartOfAccountsCode()+getAward().getAccountNumber();
	}

	private BusinessObjectService getBusinessObjectService() {
    	return KcServiceLocator.getService(BusinessObjectService.class);
    }
    
}
