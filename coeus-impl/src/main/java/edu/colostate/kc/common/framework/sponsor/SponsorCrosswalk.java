package edu.colostate.kc.common.framework.sponsor;

import com.sun.org.apache.xpath.internal.operations.String;
import edu.colostate.kc.common.api.sponsor.SponsorCrosswalkContract;
import org.kuali.coeus.common.framework.sponsor.Sponsor;
import org.kuali.coeus.sys.framework.model.KcPersistableBusinessObjectBase;

import javax.persistence.*;

@Entity
@Table(name = "CSU_SPONSOR_CROSSWALK")
public class SponsorCrosswalk extends KcPersistableBusinessObjectBase implements SponsorCrosswalkContract {

    @Id
    @Column(name = "SPONSOR_ID")
    private String sponsorId;

    @Transient
    private String sponsorCode;

    @Override
    public String getSponsorId() { return sponsorId; }
    public void setSponsorId(String sponsorId) { this.sponsorId = sponsorId; }

    @Override
    public String getSponsorCode() { return sponsorCode; }
    public void setSponsorCode(String sponsorCode) { this.sponsorCode = sponsorCode;}

    @ManyToOne(cascade = { CascadeType.REFRESH })
    @JoinColumn(name = "SPONSOR_CODE", referencedColumnName = "SPONSOR_CODE", insertable = false, updatable = false)
    private Sponsor sponsor;

}
