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
package edu.colostate.kc.award.reservation.service.impl;

import org.kuali.kra.lookup.KraLookupableHelperServiceImpl;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.krad.bo.BusinessObject;

import edu.colostate.kc.award.reservation.AwardAccount;
import edu.colostate.kc.award.reservation.ResearchAccount;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UnreservedAccountLookupableHelperServiceImpl extends KraLookupableHelperServiceImpl {
    private static final long serialVersionUID = 371231231123416L;
    
    @Override
    public List<HtmlData> getCustomActionUrls(BusinessObject businessObject, @SuppressWarnings("unchecked") List pkNames) {
        return new ArrayList<HtmlData>();
    }
   
    @Override
    public List<? extends BusinessObject> getSearchResults(Map<String, String> fieldValues) {
    	fieldValues.put("accountUsed", "N");
    	
    	List<? extends BusinessObject> searchResults=super.getSearchResults(fieldValues);
    	// filter out accounts with reservations already
    	for (int i=0; i<searchResults.size(); i++) {
    		if (i<0) {break;}
    		ResearchAccount account=(ResearchAccount)searchResults.get(i);
    		if (account.getAwardAccount()!=null) {
    			searchResults.remove(account);
    			i--;
    		}
    	}
    	return searchResults;
    }
    @Override
    protected String getHtmlAction() {
        return "awardAccountReservation.do";
    }
    
    @Override
    protected String getDocumentTypeName() {
        return "AwardAccountReservationDocument";
    }
    
    @Override
    protected String getKeyFieldName() {
        return "accountNumber";
    }
    
    

}
