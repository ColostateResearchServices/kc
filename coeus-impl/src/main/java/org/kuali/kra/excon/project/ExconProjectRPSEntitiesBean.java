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
package org.kuali.kra.excon.project;


import org.drools.core.util.StringUtils;
import org.kuali.coeus.sys.framework.service.KcServiceLocator;
import org.kuali.kra.excon.document.ExconProjectDocument;
import org.kuali.kra.infrastructure.Constants;
import org.kuali.rice.coreservice.framework.parameter.ParameterService;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.util.KRADConstants;

import java.io.Serializable;
import java.net.URLEncoder;

//import com.ecustoms.RPSService;
//import com.ecustoms.RPSServiceSoap;
//import com.ecustoms.Rps;
//import Rpsresult;
//import org.tempuri.*;

/**
 * This is the base class for handling ExconProjectRPSEntities
 */
public class ExconProjectRPSEntitiesBean implements Serializable {

	private static final long serialVersionUID = 23434534457L;
	protected ExconProjectRPSEntity newRPSEntity;
    protected ExconProjectForm exconProjectForm;
//    protected List<Rpsresult> rpsResults;
    
    private transient BusinessObjectService businessObjectService;
  
    public ExconProjectRPSEntitiesBean(ExconProjectForm exconProjectForm) {
        this.exconProjectForm = exconProjectForm;
        init();
    }
    
    public void addRpsEntity() {
    	boolean success = true;
//        boolean success = new AwardUnitContactAddRuleImpl().processAddAwardUnitContactBusinessRules(getAward(), getUnitContact()); // add to implement add validation rules
        if(success){
            getExconProject().add(getRpsEntity());
        }
    }
    
    public ExconProjectRPSEntity getRpsEntity() {
    	ExconProjectRPSEntity savedRpsEntity;
    	if ((savedRpsEntity=getExconProject().getRpsEntity())!=null) {
    		return savedRpsEntity;
    	}
        return newRPSEntity;
    }    

    public void deleteRpsEntity() {
    	getExconProject().deleteRPSEntity();
    	init();
    }

    public void editRpsEntity() {
    	if (newRPSEntity.getConcatNames()==null || StringUtils.isEmpty(newRPSEntity.getConcatNames().trim())) {
    		newRPSEntity=getExconProject().getRpsEntity();
    	}
    	getExconProject().deleteRPSEntity();
    }
    
    public ExconProjectRPSEntity getSavedRPSEntity() {
    	return getExconProject().getRpsEntity();
    }
    
    private String parmReplace(String inStr) {
        ExconProjectRPSEntity entity=getRpsEntity();
        String outStr=inStr.replace("[firstName]", encode(entity.getFirstName()))
                            .replace("[otherNames]", encode(entity.getOtherNames()))
                            .replace("[lastName]", encode(entity.getLastName()))
                            .replace("[companyName]", encode(entity.getCompanyName()))
                            .replace("[streetAddress]", encode(entity.getStreetAddress()))
                            .replace("[city]", encode(entity.getCity()))
                            .replace("[state]", encode(entity.getState()))
                            .replace("[shortCountryCode]", encode(entity.getShortCountryCode()))
                            .replace("[countryCode]", encode(entity.getCountryCode()));
        return outStr;
    }
    
    public String getRpsURL() {
//    	https://www.visualcompliance.com/vcr/rps3.cfm?dnsName=${KualiForm.exconProjectRPSEntitiesBean.savedRPSEntity.encodedNames}&ISOCountry=${KualiForm.exconProjectRPSEntitiesBean.savedRPSEntity.shortCountryCode}&match=FuzzyMatch
/*    	String rpsURL="https://www.visualcompliance.com/vcr/rps3.cfm?";
    	String rpsParms="dnsName="+encode(getRpsEntity().getFirstName()+" "+getRpsEntity().getOtherNames()+" "+getRpsEntity().getLastName())+"&";
    	rpsParms+="dnsCompany="+encode(getRpsEntity().getCompanyName())+"&";
    	rpsParms+="dnsAddress="+encode(getRpsEntity().getStreetAddress())+"&";
    	rpsParms+="dnsCity="+encode(getRpsEntity().getCity())+"&";
    	rpsParms+="dnsState="+encode(getRpsEntity().getState())+"&";
    	rpsParms+="ISOCountry="+encode(getRpsEntity().getShortCountryCode())+"&";
    	rpsParms+="match=FuzzyMatch";
    	
		rpsURL=rpsURL+rpsParms;
*/
        String rawRpsURL = KcServiceLocator.getService(ParameterService.class).getParameterValueAsString(Constants.KC_GENERIC_PARAMETER_NAMESPACE,
                KRADConstants.DetailTypes.DOCUMENT_DETAIL_TYPE, "rpsURL"); // TODO: needs to be changed from hard-coded strings
        if (rawRpsURL==null) {return "rpsURL parameter is not set";}
        String rpsURL=parmReplace(rawRpsURL);
    	return rpsURL;
    }
    
    public String encode(String inStr) {
        if (inStr==null) {return "";}
    	try {
    		return URLEncoder.encode(inStr,"US-ASCII");
    	}
    	catch (Exception e) {
    	}
    	return inStr;
    }
    
/*    
    public List<Rpsresult> getRpsResults() {
    	return rpsResults;
    }
    
    public void setRpsResults(List<Rpsresult> rpsResults) {
    	this.rpsResults=rpsResults;
    }
    
    public void performRPS() {
    	try {
    		RPSServiceSoap rps=getRPSService();
 //   		rps.webSearch(sSecno, sPassword, sOptionalID, sName, sCompany, sAddress, sCountry, sModes, sRPSGroupBypass);
//    		WebSearchResponse response=rps.webSearch("Q56QL", "488EXP858", null, getSavedRPSEntity().getConcatNames(), null, null, getSavedRPSEntity().getShortCountryCode(), null, null);
    		String concatNames=getSavedRPSEntity().getConcatNames();
    		WebSearchResponse wsr=rps.webSearch("Q56QL", "488EXP858", null, getSavedRPSEntity().getConcatNames(), null, null, getSavedRPSEntity().getShortCountryCode(), "F05|ST0|TH0|FS1|EC0", null);
    		setRpsResults(wsr.getWebSearchResult().getRps().getRpsresult());
    	}
    	catch (Exception e)
    	{
    		System.err.println("performRPS:"+e.getMessage());
    	}
    }
*/
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
        this.newRPSEntity = new ExconProjectRPSEntity();
    }
    

    protected ExconProject getExconProject() {
        return getDocument().getExconProject();
    }
    
    protected ExconProjectDocument getDocument() {
        return exconProjectForm.getExconProjectDocument();
    }

    void setBusinessObjectService(BusinessObjectService bos) {
        businessObjectService = bos;
    }
/*    
    protected RPSServiceSoap getRPSService() throws Exception {
    	return (new RPSService()).getRPSServiceSoap12();
    }
*/
}
