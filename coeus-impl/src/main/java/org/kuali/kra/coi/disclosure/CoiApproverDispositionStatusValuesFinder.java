package org.kuali.kra.coi.disclosure;

import org.apache.commons.lang3.StringUtils;
import org.kuali.kra.coi.CoiDisclosureDocument;
import org.kuali.kra.coi.CoiDispositionStatus;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.util.GlobalVariables;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Iterator;

/**
 * Created by chrisdenne on 10/17/16.
 */
public class CoiApproverDispositionStatusValuesFinder extends CoiDispositionStatusValuesFinder {
    @Override
    public List<KeyValue> getKeyValues() {


        List<KeyValue> approverKeyValues = new ArrayList<KeyValue>();
        List<KeyValue> baseValues = super.getKeyValues();
        List<String> approverValues = new ArrayList<String>();
        CoiDisclosureDocument coiDisclosureDocument = (CoiDisclosureDocument) getDocument();

        if (coiDisclosureDocument.getDocumentHeader().getWorkflowDocument().isEnroute()) {

            approverValues.add("select");
            approverValues.add("No Conflict Exists");
            approverValues.add("A relevant management plan is already in place");
            approverValues.add("A relevant mangement plan is already in place"); // <-- need to remove after maint cleanup
            approverValues.add("A management plan should be developed and submitted for review");


            Iterator<KeyValue> valueIterator = baseValues.iterator();

            while (valueIterator.hasNext()) {
                KeyValue kv = valueIterator.next();
                if (approverValues.contains(kv.getValue())) {
                    approverKeyValues.add(kv);
                }
            }
        } else {
            approverKeyValues=baseValues;
        }

        return approverKeyValues;
    }

}
