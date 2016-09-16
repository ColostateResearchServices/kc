/*
 * Copyright 2005-2014 The Kuali Foundation
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
package org.kuali.kra.excon.project;

import java.sql.Timestamp;

import org.apache.commons.lang.StringUtils;
import org.kuali.coeus.common.budget.framework.personnel.JobCode;
import org.kuali.coeus.common.budget.framework.personnel.JobCodeService;
import org.kuali.coeus.sys.framework.service.KcServiceLocator;
import org.kuali.rice.krad.bo.PersistableBusinessObjectExtensionBase;
import org.kuali.rice.krad.util.KRADConstants;


/**
 * ExconProjectHRExtension business object
 *
 */
public class ExconProjectHRExtension extends ExconProjectAssociate {

    private static final long serialVersionUID = 534538833758069217L;

    private String requestType;
    private String employeeType;
    private String jobCode;
    private String bcResult;
    private String bcStatus;
    private String selfDisclosed;
    private String criminalDiscrepancy;
    private String criminalOffenses;
    private String mvrDiscrepancy;
    private String notes;
    private String contact;
    private Boolean criminalCheck;
    private Boolean criminalPrevious;
    private Boolean mvrCheck;
    private Boolean mvrPrevious;
    private Boolean stateCheck;
    private Boolean creditCheck;
    private Boolean sexOffenderCheck;
    private Boolean educationCheck;
    private Boolean ssnNoData;
    private Boolean hardCopyReq;
    private Boolean candDispute;
    private String rerunReason;

    private JobCode job;

    private Long projectId;

    public ExconProjectHRExtension() {
    }


    public String getRequestType() {
        return requestType;
    }


    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }


    public String getEmployeeType() {
        return employeeType;
    }


    public void setEmployeeType(String employeeType) {
        this.employeeType = employeeType;
    }


    public String getJobCode() {
        return jobCode;
    }


    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
    }


    public String getBcResult() {
        return bcResult;
    }


    public void setBcResult(String bcResult) {
        this.bcResult = bcResult;
    }


    public String getBcStatus() {
        return bcStatus;
    }


    public void setBcStatus(String bcStatus) {
        this.bcStatus = bcStatus;
    }


    public String getSelfDisclosed() {
        return selfDisclosed;
    }


    public void setSelfDisclosed(String selfDisclosed) {
        this.selfDisclosed = selfDisclosed;
    }


    public String getCriminalDiscrepancy() {
        return criminalDiscrepancy;
    }


    public void setCriminalDiscrepancy(String criminalDiscrepancy) {
        this.criminalDiscrepancy = criminalDiscrepancy;
    }


    public String getCriminalOffenses() {
        return criminalOffenses;
    }


    public void setCriminalOffenses(String criminalOffenses) {
        this.criminalOffenses = criminalOffenses;
    }


    public String getMvrDiscrepancy() {
        return mvrDiscrepancy;
    }


    public void setMvrDiscrepancy(String mvrDiscrepancy) {
        this.mvrDiscrepancy = mvrDiscrepancy;
    }


    public String getNotes() {
        return notes;
    }


    public void setNotes(String notes) {
        this.notes = notes;
    }


    public String getContact() {
        return contact;
    }


    public void setContact(String contact) {
        this.contact = contact;
    }


    public Boolean getCriminalCheck() {
        return criminalCheck;
    }


    public void setCriminalCheck(Boolean criminalCheck) {
        this.criminalCheck = criminalCheck;
    }


    public Boolean getCriminalPrevious() {
        return criminalPrevious;
    }


    public void setCriminalPrevious(Boolean criminalPrevious) {
        this.criminalPrevious = criminalPrevious;
    }


    public Boolean getMvrCheck() {
        return mvrCheck;
    }


    public void setMvrCheck(Boolean mvrCheck) {
        this.mvrCheck = mvrCheck;
    }


    public Boolean getMvrPrevious() {
        return mvrPrevious;
    }


    public void setMvrPrevious(Boolean mvrPrevious) {
        this.mvrPrevious = mvrPrevious;
    }


    public Boolean getStateCheck() {
        return stateCheck;
    }


    public void setStateCheck(Boolean stateCheck) {
        this.stateCheck = stateCheck;
    }


    public Boolean getCreditCheck() {
        return creditCheck;
    }


    public void setCreditCheck(Boolean creditCheck) {
        this.creditCheck = creditCheck;
    }


    public Boolean getSexOffenderCheck() {
        return sexOffenderCheck;
    }


    public void setSexOffenderCheck(Boolean sexOffenderCheck) {
        this.sexOffenderCheck = sexOffenderCheck;
    }


    public Boolean getEducationCheck() {
        return educationCheck;
    }


    public void setEducationCheck(Boolean educationCheck) {
        this.educationCheck = educationCheck;
    }

    public Boolean getSsnNoData() {
        return ssnNoData;
    }


    public void setSsnNoData(Boolean ssnNoData) {
        this.ssnNoData = ssnNoData;
    }


    public Boolean getHardCopyReq() {
        return hardCopyReq;
    }


    public void setHardCopyReq(Boolean hardCopyReq) {
        this.hardCopyReq = hardCopyReq;
    }


    public Boolean getCandDispute() {
        return candDispute;
    }


    public void setCandDispute(Boolean candDispute) {
        this.candDispute = candDispute;
    }


    public String getRerunReason() {
        return rerunReason;
    }


    public void setRerunReason(String rerunReason) {
        this.rerunReason = rerunReason;
    }

    public JobCode getJob() {
        if (!StringUtils.isEmpty(jobCode)) {
            job=getJobCodeService().findJobCodeRef(jobCode);
        }
        return job;
    }


    public void setJob(JobCode job) {
        this.job = job;
    }

    private JobCodeService getJobCodeService() {
        return KcServiceLocator.getService(JobCodeService.class);
    }


    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }


    @Override
    public void resetPersistenceState() {
        projectId=null;
    }


}
