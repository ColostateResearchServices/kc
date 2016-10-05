/*
 * Kuali Coeus, a comprehensive research administration system for higher education.
 * 
 * Copyright 2005-2016 Kuali, Inc.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.kuali.kra.award;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.kuali.coeus.sys.framework.service.KcServiceLocator;
import org.kuali.kra.award.home.Award;
import org.kuali.kra.award.home.AwardBasisOfPayment;
import org.kuali.kra.award.home.AwardMethodOfPayment;
import org.kuali.kra.award.home.ValidAwardBasisPayment;
import org.kuali.kra.award.paymentreports.closeout.AwardCloseout;
import org.kuali.kra.bo.NsfCode;
import org.kuali.kra.award.customdata.AwardCustomData;
import org.kuali.kra.infrastructure.Constants;
import org.kuali.kra.award.service.AwardHierarchyUIService;
import org.kuali.kra.timeandmoney.AwardHierarchyNode;
import org.kuali.rice.kns.inquiry.KualiInquirableImpl;
import org.kuali.rice.kns.web.ui.Field;
import org.kuali.rice.kns.web.ui.Row;
import org.kuali.rice.kns.web.ui.Section;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.ObjectUtils;

import edu.colostate.kc.award.AwardExtension;

import java.sql.Date;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.kuali.rice.kns.service.KNSServiceLocator.getBusinessObjectService;

public class AwardInquirable extends KualiInquirableImpl {
    
    private static final Log LOG = LogFactory.getLog(AwardInquirable.class);

    @Override
    public List<Section> getSections(BusinessObject bo) {
        List<Section> sections = new ArrayList<Section>();
        Section section = new Section();
        
        section.setRows(new ArrayList<Row>());
        section.setDefaultOpen(true);
        section.setNumberOfColumns(2);
        
        AwardHierarchyUIService service = getAwardHierarchyUIService();
        Award award = (Award) bo;
        AwardHierarchyNode awardNode = null;
        try {
            awardNode = service.getRootAwardNode(award);
        }
        catch (ParseException e) {
            LOG.error("Error parsing award information" ,e);
        }
        
        // Adding the section title
        String sectionTitle = "";
        sectionTitle += awardNode.getAwardNumber();
        sectionTitle += KRADConstants.BLANK_SPACE;
        sectionTitle += Constants.COLON;
        sectionTitle += KRADConstants.BLANK_SPACE;
        
        if (ObjectUtils.isNotNull(award.getAccountNumber())) {
            sectionTitle += awardNode.getAccountNumber();
            sectionTitle += KRADConstants.BLANK_SPACE;
            sectionTitle += Constants.COLON;
            sectionTitle += KRADConstants.BLANK_SPACE;
        }
        if (ObjectUtils.isNotNull(awardNode.getPrincipalInvestigatorName())) {
            sectionTitle += awardNode.getPrincipalInvestigatorName();
            sectionTitle += KRADConstants.BLANK_SPACE;
            sectionTitle += Constants.COLON;
            sectionTitle += KRADConstants.BLANK_SPACE;
        } 
        if (ObjectUtils.isNotNull(awardNode.getLeadUnitName())) {
            sectionTitle += awardNode.getLeadUnitName();
        }
        
        section.setSectionTitle(sectionTitle);

        //Adding the rows to the sections
        section.setRows(new ArrayList<Row>());

        String teamId = "";
        String ovrExpStatus = "";
        String GSASINCode = "";
        String SCOCode = "";

        List<AwardCustomData> customDataList = award.getAwardCustomDataList();
        for ( AwardCustomData data: customDataList) {
            if (data.getCustomAttribute()!=null && data.getCustomAttribute().getLabel()!=null) {
            	if (StringUtils.equals(data.getCustomAttribute().getLabel(),"Team ID")) {
            		teamId = data.getValue();
            	}
            	else if (StringUtils.equals(data.getCustomAttribute().getLabel(),"Overspent")) {
            		ovrExpStatus = data.getValue();
            	}
            	else if (StringUtils.equals(data.getCustomAttribute().getLabel(),"GSA SIN Code")) {
            		GSASINCode = data.getValue();
            	}
            	else if (StringUtils.equals(data.getCustomAttribute().getLabel(),"SCO Code")) {
            		SCOCode = data.getValue();
            	}
             }
        }

        NumberFormat formatter=NumberFormat.getCurrencyInstance();

        Row row1a = new Row();
        addField(awardNode.getTitle(), row1a, "title", "Title");
        addField(award.getAwardNumber(), row1a, "awardNumber", "Award Number");
        section.getRows().add(row1a);

        Row row1b = new Row();
        addField(award.getSponsorName(), row1b, "sponsorName", "Sponsor Name");
        addField(award.getPrimeSponsorName(), row1b, "sponsorName", "Prime Sponsor Name");
        section.getRows().add(row1b);


        Row row1c = new Row();
        addField(awardNode.getProjectStartDate() + "", row1c, "projectStartDate", "Project Start Date");
        addField(awardNode.getCurrentFundEffectiveDate() + "", row1c, "obligationStartDate", "Obligation Start Date");
        section.getRows().add(row1c);

        Row row2 = new Row();        
        addField(awardNode.getFinalExpirationDate() + "", row2, "projectEndDate", "Project End Date");
        addField(awardNode.getObligationExpirationDate() + "", row2, "obligationEndDate", "Obligation End Date");
        section.getRows().add(row2);

        Row row3 = new Row(); 
        addField(getAwardCloseoutStarted(award), row3, "closeoutDate", "Closeout Started");
        addField(formatter.format(awardNode.getAmountObligatedToDate().bigDecimalValue()), row3, "obligatedAmount", "Obligated Amount");
        section.getRows().add(row3);

        String payMethod = getMethodOfPaymentDesc(award);
        String payBasis = getBasisOfPaymentDesc(award);
        Row row8 = new Row();
        addField(payMethod, row8, "paymentMethod", "Payment Method");
        addField(payBasis, row8, "paymentBasis", "Payment Basis");
        section.getRows().add(row8);

        Row row4 = new Row();
        addField(award.getAwardStatus().getDescription() + "", row4, "awardStatus", "Award Status");
        addField(ovrExpStatus + "", row4, "overspentCode", "Over Expense Status Code");
        section.getRows().add(row4);

        Row row5 = new Row();
        addField(award.getOspAdministratorName(), row5, "ospAdminName", "OSP Administrator Name");
        addField(award.getSponsorAwardNumber(), row5, "sponsorAwardNumber", "Sponsor Award Number");
        section.getRows().add(row5);

        Row row6 = new Row();
        addField(award.getCfdaNumber(), row6, "cfdaNumber", "CFDA Number");
        addField(teamId + "", row6, "teamId", "Team ID");
        section.getRows().add(row6);

/*
        Row row7 = new Row();
        addField(((AwardExtension)award.getExtension()).getResearchReportCode(), row7, "researchReportCode", "Research Report Code");
        addField(((AwardExtension)award.getExtension()).getFundSourceCode(), row7, "fundSourceCode", "Fund Source Code");
        section.getRows().add(row7);
*/

        Row row9 = new Row();
        addField(SCOCode,row9,"SCOCode","SCO Code");
        addField(GSASINCode,row9,"GSASINCode","GSA SIN CODE");
        section.getRows().add(row9);


        Row row10 = new Row();
        addField(getNsfCategory(award), row10, "nsfCode", "NSF Category");
        String icrRateCode="";
        if (award.getCurrentFandaRate() != null && award.getCurrentFandaRate().getApplicableFandaRate() != null) {
        	icrRateCode=award.getCurrentFandaRate().getApplicableFandaRate().toString()+"%";
        }
        addField(icrRateCode, row10, "icrRateCode", "ICR Rate Code");

        section.getRows().add(row10);



        sections.add(section);
        return sections;
    }

    private void addField(String text, Row row1, String propertyName, String fieldLabel) {
        Field field = new Field();
        field.setPropertyName(propertyName);        
        field.setFieldLabel(fieldLabel);        
        field.setFieldType(Field.TEXT);
        if(StringUtils.equalsIgnoreCase(text, " &nbsp; ")){
            text = "";
        }
        field.setPropertyValue(text);        
        row1.getFields().add(field);
    }


    protected String getBasisOfPaymentDesc(Award award) {
        String basisOfPay = "";
        Map<String, Object> values = new HashMap<String, Object>();
        values.put("basisOfPaymentCode", award.getBasisOfPaymentCode());

        AwardBasisOfPayment awdBasisOfPayment = (AwardBasisOfPayment)getBusinessObjectService().findByPrimaryKey(AwardBasisOfPayment.class, values);
        if (awdBasisOfPayment != null) {
           basisOfPay =  awdBasisOfPayment.getDescription();
        }
        return basisOfPay;
    }


    protected String getMethodOfPaymentDesc(Award award) {
        String methodOfPay = "";
        Map<String, Object> values = new HashMap<String, Object>();
        values.put("methodOfPaymentCode", award.getMethodOfPaymentCode());
        AwardMethodOfPayment awdMethodOfPayment = (AwardMethodOfPayment)getBusinessObjectService().findByPrimaryKey(AwardMethodOfPayment.class, values);
        if (awdMethodOfPayment != null) {
            methodOfPay =  awdMethodOfPayment.getDescription();
        }
        return methodOfPay;
    }

    protected String getAwardCloseoutStarted(Award award) {
        Date closeoutStartedDate=null;
        List<AwardCloseout> items = award.getAwardCloseoutItems();

        for (AwardCloseout item: items) {
            if (item.getCloseoutReportName().equalsIgnoreCase("Closeout Started")) {
                closeoutStartedDate = item.getFinalSubmissionDate();
            }
        }
        return (closeoutStartedDate == null) ? "" : closeoutStartedDate.toString();
    }

    protected String getNsfCategory(Award award) {
        String nsfCategory = "";

        Map<String, Object> values = new HashMap<String, Object>();
        values.put("nsfCode", award.getNsfCode());
        NsfCode code = (NsfCode)getBusinessObjectService().findByPrimaryKey(NsfCode.class, values);
        if (code != null) {
            nsfCategory =  code.getDescription();
        }

        return nsfCategory;
    }


    private AwardHierarchyUIService getAwardHierarchyUIService() {
        return KcServiceLocator.getService(AwardHierarchyUIService.class);
    }
} 
