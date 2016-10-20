package edu.colostate.kc.common.framework.sponsor;

import java.lang.String;
import edu.colostate.kc.common.api.sponsor.SponsorCrosswalkContract;
import org.kuali.coeus.common.framework.sponsor.Sponsor;
import org.kuali.coeus.sys.framework.model.KcPersistableBusinessObjectBase;

import javax.persistence.*;

@Entity
@Table(name = "CSU_SPONSOR_CROSSWALK")
public class SponsorCrosswalk extends KcPersistableBusinessObjectBase {

    @Id
    @Column(name = "SPONSOR_CODE")
    private String sponsorCode;

    @Column(name = "SPONSOR_ID")
    private String sponsorId;

    @OneToOne(cascade = { CascadeType.REFRESH })
    @JoinColumn(name = "SPONSOR_CODE", referencedColumnName = "SPONSOR_CODE", insertable = false, updatable = false)
    private Sponsor sponsor;

    public String getSponsorId() { return sponsorId; }
    public void setSponsorId(String sponsorId) { this.sponsorId = sponsorId; }

    public String getSponsorCode() { return sponsorCode; }
    public void setSponsorCode(String sponsorCode) { this.sponsorCode = sponsorCode;}

    /**
     * Sponsor reference referred by {@link #getSponsorCode()}
     *
     * @param sponsor
     */
    public void setSponsor(Sponsor sponsor) {
        this.sponsor = sponsor;
    }

    /**
     * Sponsor reference referred by {@link #getSponsorCode()}
     *
     * @return Sponsor
     */
    public Sponsor getSponsor() {
        return sponsor;
    }

}
