/*
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
package edu.colostate.kc.award.reservation;


import edu.colostate.kc.award.reservation.document.AwardAccountReservationDocument;
//import edu.colostate.kc.excon.rules.AwardAccountReservationAddRuleImpl;

import edu.colostate.kc.award.reservation.web.struts.form.AwardAccountReservationForm;

import org.kuali.coeus.sys.framework.service.KcServiceLocator;
import org.kuali.rice.krad.service.BusinessObjectService;

import java.io.Serializable;
import java.sql.Date;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is the base class for handling AwardAccountReservations
 */
public class AwardAccountReservationsBean implements Serializable {

	private static final long serialVersionUID = 23456757457L;
	protected AwardAccount newReservedAccount;
    protected AwardAccountReservationForm awardAccountReservationForm;
    
    private transient BusinessObjectService businessObjectService;
  
    public AwardAccountReservationsBean(AwardAccountReservationForm awardAccountReservationForm) {
        this.awardAccountReservationForm = awardAccountReservationForm;
        init();
    }
    
    public void addReservedAccount() {
 //       boolean success = new AwardAccountReservationAddRuleImpl().processAddAwardAccountReservationBusinessRules(getExconProject(), getAwardAccountReservation());
    	boolean success = true;
        if(success){
            getAwardAccountReservation().add(getNewReservedAccount());
            init();
        }
    }
    
    /**
     * Delete an AwardAccountReservation
     * @param lineToDelete
     */
    public void deleteReservedAccount(int lineToDelete) {
        deleteReservedAccount(getReservedAccounts(), lineToDelete);                
    }
    
    public AwardAccount getNewReservedAccount() {
        return newReservedAccount;
    }    

    /**
     * @return
     */
    public AwardAccount getNewAwardAccount() {
       return (AwardAccount)newReservedAccount; 
    }
    
    /**
     * This method returns the list of AwardAccountReservations
     * @return The list; may be empty
     */
    public List<AwardAccount> getReservedAccounts() {
        return ((AwardAccountReservationDocument)awardAccountReservationForm.getDocument()).getAwardAccountReservation().getReservedAccounts();
    }
    
    /**
     * This method finds the count of AwardAccountReservations
     * @return The count; may be 0
     */
    public int getAwardAccountReservationsCount() {
        return getReservedAccounts().size();
    }
    
    /**
     * Find an AwardAccountReservation in a collection of AwardAccountReservation types and remove it from 
     * the main ExconProject collection of AwardAccountReservations
     * @param events
     * @param lineToDelete
     */
    protected void deleteReservedAccount(List<AwardAccount> reservations, int lineToDelete) {
        if(reservations.size() > lineToDelete) {
            AwardAccount foundReservation = reservations.get(lineToDelete);
            getAwardAccountReservation().getReservedAccounts().remove(foundReservation);
        }
    }

    /**
     * @return
     */
    protected BusinessObjectService getBusinessObjectService() {
        if(businessObjectService == null) {
            businessObjectService = (BusinessObjectService) KcServiceLocator.getService(BusinessObjectService.class);
        }
        return businessObjectService;
    }
    
    
    protected void init() {
        this.newReservedAccount = new AwardAccount();
    }
    

    protected AwardAccountReservation getAwardAccountReservation() {
        return getDocument().getAwardAccountReservation();
    }
    
    protected AwardAccountReservationDocument getDocument() {
        return awardAccountReservationForm.getAwardAccountReservationDocument();
    }

    void setBusinessObjectService(BusinessObjectService bos) {
        businessObjectService = bos;
    }
}
