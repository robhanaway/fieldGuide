package com.rh.fieldguide.data.primitives;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;
@Entity(tableName = "medicinedetails")
public class MedicineDetails {
//    _id,mdcid,medicinename,indications,adultdose,paediatricdose,contraindications,sideeffects,additionalinformations,mdccreatedate,mdcmodifieddate

    @PrimaryKey(autoGenerate = true)
    private
    int _id;

    private int mdcid;
    private String medicinename;
    private String indications;
    private String adultdose;
    private String paediatricdose;
    private String contraindications;
    private String sideeffects;
    private String additionalinformations;
    private Date mdccreatedate;
    private Date mdcmodifieddate;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getMdcid() {
        return mdcid;
    }

    public void setMdcid(int mdcid) {
        this.mdcid = mdcid;
    }

    public String getMedicinename() {
        return medicinename;
    }

    public void setMedicinename(String medicinename) {
        this.medicinename = medicinename;
    }

    public String getIndications() {
        return indications;
    }

    public void setIndications(String indications) {
        this.indications = indications;
    }

    public String getAdultdose() {
        return adultdose;
    }

    public void setAdultdose(String adultdose) {
        this.adultdose = adultdose;
    }

    public String getPaediatricdose() {
        return paediatricdose;
    }

    public void setPaediatricdose(String paediatricdose) {
        this.paediatricdose = paediatricdose;
    }

    public String getContraindications() {
        return contraindications;
    }

    public void setContraindications(String contraindications) {
        this.contraindications = contraindications;
    }

    public String getSideeffects() {
        return sideeffects;
    }

    public void setSideeffects(String sideeffects) {
        this.sideeffects = sideeffects;
    }

    public String getAdditionalinformations() {
        return additionalinformations;
    }

    public void setAdditionalinformations(String additionalinformations) {
        this.additionalinformations = additionalinformations;
    }

    public Date getMdccreatedate() {
        return mdccreatedate;
    }

    public void setMdccreatedate(Date mdccreatedate) {
        this.mdccreatedate = mdccreatedate;
    }

    public Date getMdcmodifieddate() {
        return mdcmodifieddate;
    }

    public void setMdcmodifieddate(Date mdcmodifieddate) {
        this.mdcmodifieddate = mdcmodifieddate;
    }
}

