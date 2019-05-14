package com.rh.fieldguide.data.primitives;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "clininalalllevel")
public class ClinicalAllLevel {
    @PrimaryKey(autoGenerate = true)
    private
    int _id;
    private String clinicallevelname;
    private int levelid;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getClinicallevelname() {
        return clinicallevelname;
    }

    public void setClinicallevelname(String clinicallevelname) {
        this.clinicallevelname = clinicallevelname;
    }

    public int getLevelid() {
        return levelid;
    }

    public void setLevelid(int levelid) {
        this.levelid = levelid;
    }
}
