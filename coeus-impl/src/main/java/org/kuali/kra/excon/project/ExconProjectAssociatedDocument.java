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

import org.apache.commons.lang.StringUtils;
import org.kuali.coeus.sys.framework.service.KcServiceLocator;
import org.kuali.rice.core.api.CoreApiServiceLocator;
import org.kuali.rice.krad.bo.PersistableBusinessObject;
import org.kuali.rice.krad.document.DocumentBase;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.service.DocumentService;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;

/**
 * ExconProjectAssociatedDocument business object
 * 
 */
public class ExconProjectAssociatedDocument extends ExconProjectAssociate implements Comparable<ExconProjectAssociatedDocument> {

    private static final long serialVersionUID = 12345233343469217L;

    private Long exconProjectAssocDocId;
    private String assocDocTypeCode;
    private String assocDocNumber;
    private String assocDocTitle;
    private ExconProjectAssociatedDocumentType assocDocType;
    private Long projectId;

    
    public ExconProjectAssociatedDocument() {
    }

	public Long getExconProjectAssocDocId() {
		return exconProjectAssocDocId;
	}

	public void setExconProjectAssocDocId(Long exconProjectAssocDocId) {
		this.exconProjectAssocDocId = exconProjectAssocDocId;
	}

	public String getAssocDocTypeCode() {
		return assocDocTypeCode;
	}

	public void setAssocDocTypeCode(String assocDocTypeCode) {
		this.assocDocTypeCode = assocDocTypeCode;
	}

	public String getAssocDocNumber() {
		return assocDocNumber;
	}

	public void setAssocDocNumber(String assocDocNumber) {
		this.assocDocNumber = assocDocNumber;
	}

	public String getAssocDocTitle() {
		return assocDocTitle;
	}

	public void setAssocDocTitle(String assocDocTitle) {
		this.assocDocTitle = assocDocTitle;
	}

	public ExconProjectAssociatedDocumentType getAssocDocType() {
		if (assocDocTypeCode != null) {
			this.refreshReferenceObject("assocDocType");
		}
		return assocDocType;
	}

	public void setAssocDocType(ExconProjectAssociatedDocumentType assocDocType) {
		this.assocDocType = assocDocType;
	}
	
	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	
	protected Class<PersistableBusinessObject> getAssocDocTypeClass() {
		try {
			return (Class<PersistableBusinessObject>)Class.forName(getAssocDocType().getAssocDocTypeClassName());
		} catch(Exception e) {}
		return null;
	}
	
	public String getAssocDocViewURL() {
		String docViewURL=CoreApiServiceLocator.getKualiConfigurationService().getPropertyValueAsString("kc.excon.assocDoc.viewURL."+getAssocDocTypeCode());
		if (!StringUtils.isEmpty(docViewURL)) {
			docViewURL=docViewURL.replace("[assocDocNumber]", getAssocDocNumber());
		}
		else
		{
			if (!StringUtils.isEmpty(getAssocDocType().getAssocDocTypeClassName())) {
				String className=getAssocDocType().getAssocDocTypeClassName();
				String shortClassName=className.substring(className.lastIndexOf('.')+1);
				String documentName=shortClassName+"Document";
				if (StringUtils.equals("DevelopmentProposal", shortClassName)) {
					documentName="ProposalDocument";
				}
				HashMap<String,String> lookupVals=new HashMap<String,String>();
				lookupVals.put(getAssocDocType().getAssocDocTypeNumberAttr(), getAssocDocNumber());
				Collection<PersistableBusinessObject> results=getBusinessObjectService().findMatchingOrderBy(getAssocDocTypeClass(), lookupVals, "updateTimestamp", false);

				if (results!=null && results.size()>0) {
					PersistableBusinessObject bo=results.iterator().next();
					String docId="";
					try {
						Method m=Class.forName(className).getDeclaredMethod("get"+documentName, null);
						DocumentBase docClass=(DocumentBase)m.invoke(bo, null);
						m=Class.forName("org.kuali.rice.krad.document.DocumentBase").getDeclaredMethod("getDocumentNumber", null);
						docId=(String)m.invoke(docClass, null);
					} catch (Exception e) {System.err.println(e.getMessage());}
					if (!StringUtils.isEmpty(docId)) {
						String docViewURLParm=CoreApiServiceLocator.getKualiConfigurationService().getPropertyValueAsString("kew.url");
						docViewURL=docViewURLParm+"/DocHandler.do?command=displayDocSearchView&docId="+docId;					
						}
				}
			}
		}
		return docViewURL;
	}
	
	public String getAssocDocLookupURL(String formKey) {
		String docLookupURL=null;
		String docLookupURLParm=CoreApiServiceLocator.getKualiConfigurationService().getPropertyValueAsString("kc.excon.assocDoc.lookupURL."+getAssocDocTypeCode());
		if (!StringUtils.isEmpty(docLookupURLParm)) {
			docLookupURL=docLookupURLParm.replace("[formKey]", formKey);
		}
		return docLookupURL;
	}
	
	private BusinessObjectService getBusinessObjectService() {
		return KcServiceLocator.getService(BusinessObjectService.class);
	}
	
	private DocumentService getDocumentService() {
		return KcServiceLocator.getService(DocumentService.class);
	}

	public void resetPersistenceState() {
        exconProjectAssocDocId = null;
        versionNumber = null;
    }

    public int compareTo(ExconProjectAssociatedDocument exconProjectAssociatedDocumentArg) {
        return exconProjectAssociatedDocumentArg.getExconProjectAssocDocId().compareTo(this.getExconProjectAssocDocId());
    }

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof ExconProjectAssociatedDocument)) {return false;}
		ExconProjectAssociatedDocument oDoc=(ExconProjectAssociatedDocument)o;
		if (getExconProjectAssocDocId()!=null) {
			if (oDoc.getExconProjectAssocDocId()==this.getExconProjectAssocDocId()) {
				return true;
			}
		}
		else
		{
			if (oDoc.getAssocDocTypeCode()==this.getAssocDocTypeCode() && oDoc.getAssocDocNumber()==this.getAssocDocNumber()) {
				return true;
			}
		}
		
		return false;
	}
    
    


}
