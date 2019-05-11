package com.rh.fieldguide.data.primitives;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Dao
@Entity(tableName = "calculation")
public class Calculation {
    @PrimaryKey(autoGenerate = true)
    private
    int _id;

    private String age;

    private int ct_calid;

    private int ct_dosageid;

    private Date ct_createdate;

    private Date ct_modifieddate;

    private String mg;

    private String ml;

    private String weight;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public int getCt_calid() {
        return ct_calid;
    }

    public void setCt_calid(int ct_calid) {
        this.ct_calid = ct_calid;
    }

    public int getCt_dosageid() {
        return ct_dosageid;
    }

    public void setCt_dosageid(int ct_dosageid) {
        this.ct_dosageid = ct_dosageid;
    }

    public Date getCt_createdate() {
        return ct_createdate;
    }

    public void setCt_createdate(Date ct_createdate) {
        this.ct_createdate = ct_createdate;
    }

    public Date getCt_modifieddate() {
        return ct_modifieddate;
    }

    public void setCt_modifieddate(Date ct_modifieddate) {
        this.ct_modifieddate = ct_modifieddate;
    }

    public String getMg() {
        return mg;
    }

    public void setMg(String mg) {
        this.mg = mg;
    }

    public String getMl() {
        return ml;
    }

    public void setMl(String ml) {
        this.ml = ml;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
