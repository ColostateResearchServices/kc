package edu.colostate.kc.award;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.coeus.sys.framework.service.KcServiceLocator;
import org.kuali.coeus.common.framework.version.sequence.associate.SequenceAssociate;
import org.kuali.kra.SkipVersioning;
import org.kuali.kra.award.home.Award;
import org.kuali.rice.krad.bo.PersistableBusinessObjectExtensionBase;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.springframework.util.AutoPopulatingList;


public class AwardExtension extends PersistableBusinessObjectExtensionBase implements SequenceAssociate<Award>  {
	
    private Long awardId;
    
    
	@SkipVersioning
    private Award award;
    
    public AwardExtension() {
    	init();
    }
    
    private void init() {
    }

	public Long getAwardId() {
		return awardId;
	}
	public void setAwardId(Long awardId) {
		this.awardId = awardId;
	}

	@Override
	public Integer getSequenceNumber() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void resetPersistenceState() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setSequenceOwner(Award newlyVersionedOwner) {
		// TODO Auto-generated method stub

	}

	@Override
	public Award getSequenceOwner() {
		// TODO Auto-generated method stub
		return getAward();
	}

	public Award getAward() {
		if (award == null  && getAwardId()!=null && getAwardId() >0 ) {
            this.refreshReferenceObject("award");
        }
		return award;
	}

	public void setAward(Award award) {
		this.award = award;
	}

    private BusinessObjectService getBusinessObjectService() {
    	return KcServiceLocator.getService(BusinessObjectService.class);
    }
    
}
