/*
 * Copyright 2005-2010 The Kuali Foundation
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
package org.kuali.rice.contrib.uif.element;

import java.util.ArrayList;
import java.util.List;

import org.kuali.rice.krad.uif.component.Component;
import org.kuali.rice.krad.uif.container.Group;
import org.kuali.rice.krad.uif.util.ComponentUtils;
import org.kuali.rice.krad.uif.widget.Disclosure;
import org.kuali.rice.krad.uif.widget.Scrollpane;
import org.kuali.rice.krad.web.form.UifFormBase;

public class WizardGroup extends Group {

    @Override
    public void performApplyModel(Object model, Component parent) {
        UifFormBase form = (UifFormBase) model;

        String stepStr = form.getActionParameters().get(this.getId()+".step");
        Integer step = 0;

        if (stepStr != null && stepStr.matches("\\d")) {
            step = Integer.valueOf(stepStr);
        }

        List<Component> currentItems = new ArrayList<Component>();
        for (int i = 0, len = getItems().size(); i < len; i++) {
            Component component = getItems().get(i);

            if (i == step) {
                currentItems.add(component);
            }
        }

        setItems(currentItems);

        super.performApplyModel(model, parent);
    }
}
