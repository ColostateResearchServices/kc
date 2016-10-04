/*
 * Copyright 2005-2013 The Kuali Foundation
 * 
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.osedu.org/licenses/ECL-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.colostate.kc.bo;


/**
 * Class representing a Sponsor Business Object
 */
public class SponsorCrosswalk extends org.kuali.kra.bo.KraPersistableBusinessObjectBase {

    private String sponsorCode;

    private String sponsorId;

    private org.kuali.kra.bo.Sponsor sponsor;

    public SponsorCrosswalk() {
        super();
    }

    public String getSponsorCode() {
        return sponsorCode;
    }

    public void setSponsorCode(String sponsorCode) {
        this.sponsorCode = sponsorCode;
    }

    public String getSponsorId() {
        return sponsorId;
    }

    public void setSponsorId(String sponsorId) {
        this.sponsorId = sponsorId;
    }

    public org.kuali.kra.bo.Sponsor getSponsor() {
        return sponsor;
    }

    public void setSponsor(org.kuali.kra.bo.Sponsor sponsor) {
        this.sponsor = sponsor;
    }

    
}
