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
package org.kuali.kra.proposaldevelopment.bo;

import org.kuali.kra.bo.KraPersistableBusinessObjectBase;

public class BudgetStatus extends KraPersistableBusinessObjectBase {

    private String budgetStatusCode;

    private String description;

    public String getBudgetStatusCode() {
        return budgetStatusCode;
    }

    public void setBudgetStatusCode(String budgetStatusCode) {
        this.budgetStatusCode = budgetStatusCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
