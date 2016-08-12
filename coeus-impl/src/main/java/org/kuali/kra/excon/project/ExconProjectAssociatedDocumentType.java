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

import org.kuali.coeus.sys.framework.model.KcPersistableBusinessObjectBase;

/**
 * ExconProjectAssociatedDocumentType business object
 * 
 */
public class ExconProjectAssociatedDocumentType extends KcPersistableBusinessObjectBase implements Comparable<ExconProjectAssociatedDocumentType> {

    private static final long serialVersionUID = 95752333758069217L;

    private String assocDocTypeCode;
    private String assocDocTypeName;
    private String assocDocTypeClassName;
    private String assocDocTypeNumberAttr;
    private Boolean returnsVals;
   

    public ExconProjectAssociatedDocumentType() {
    }

	public String getAssocDocTypeCode() {
		return assocDocTypeCode;
	}

	public void setAssocDocTypeCode(String assocDocTypeCode) {
		this.assocDocTypeCode = assocDocTypeCode;
	}

	public String getAssocDocTypeName() {
		return assocDocTypeName;
	}

	public void setAssocDocTypeName(String assocDocTypeName) {
		this.assocDocTypeName = assocDocTypeName;
	}

	public String getAssocDocTypeClassName() {
		return assocDocTypeClassName;
	}

	public void setAssocDocTypeClassName(String assocDocTypeClassName) {
		this.assocDocTypeClassName = assocDocTypeClassName;
	}
	
	public String getAssocDocTypeNumberAttr() {
		return assocDocTypeNumberAttr;
	}

	public void setAssocDocTypeNumberAttr(String assocDocTypeNumberAttr) {
		this.assocDocTypeNumberAttr = assocDocTypeNumberAttr;
	}

	public Boolean getReturnsVals() {
		return returnsVals;
	}

	public void setReturnsVals(Boolean returnsVals) {
		this.returnsVals = returnsVals;
	}

	public String getAssocDocTypeLookupURL() {

		return null;
	}
	
	public String getAssocDocTypeOpenURL() {
		
		return null;
	}

	public void resetPersistenceState() {
        assocDocTypeCode = null;
        versionNumber = null;
    }

    public int compareTo(ExconProjectAssociatedDocumentType exconProjectAssociatedDocumentTypeArg) {
        return exconProjectAssociatedDocumentTypeArg.getAssocDocTypeName().compareTo(this.getAssocDocTypeName());
    }

	@Override
	public boolean equals(Object o) {
		if (o instanceof ExconProjectAssociatedDocumentType && ((ExconProjectAssociatedDocumentType)o).getAssocDocTypeCode()==this.getAssocDocTypeCode()) {
			return true;
		}
		return false;
	}
    
    


}
