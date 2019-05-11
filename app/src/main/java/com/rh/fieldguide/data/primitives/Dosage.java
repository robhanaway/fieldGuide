package com.rh.fieldguide.data.primitives;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "dosage")
public class Dosage {
    @PrimaryKey(autoGenerate = true)
    private
    int _id;

    private String concentration;

    private int dosageid;

    private int medicineid;

    private String paediatricdose;

    private Date dosagecreatedate;

    private Date dosagemodifieddate;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getConcentration() {
        return concentration;
    }

    public void setConcentration(String concentration) {
        this.concentration = concentration;
    }

    public int getDosageid() {
        return dosageid;
    }

    public void setDosageid(int dosageid) {
        this.dosageid = dosageid;
    }

    public int getMedicineid() {
        return medicineid;
    }

    public void setMedicineid(int medicineid) {
        this.medicineid = medicineid;
    }

    public String getPaediatricdose() {
        return paediatricdose;
    }

    public void setPaediatricdose(String paediatricdose) {
        this.paediatricdose = paediatricdose;
    }

    public Date getDosagecreatedate() {
        return dosagecreatedate;
    }

    public void setDosagecreatedate(Date dosagecreatedate) {
        this.dosagecreatedate = dosagecreatedate;
    }

    public Date getDosagemodifieddate() {
        return dosagemodifieddate;
    }

    public void setDosagemodifieddate(Date dosagemodifieddate) {
        this.dosagemodifieddate = dosagemodifieddate;
    }
}
