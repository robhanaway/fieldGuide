package com.rh.fieldguide.data.primitives;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "hospitals")
public class Hospital implements HospitalBC {
    @PrimaryKey(autoGenerate = true)
    private
    int _id;

    private String county;
    private String name;
    private String main;
    private String emergency;
    private String emergency_other;
    private String code;
    private int ring_times;
    private int favourite;

    @Override
    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    @Override
    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    @Override
    public String getEmergency() {
        return emergency;
    }

    public void setEmergency(String emergency) {
        this.emergency = emergency;
    }

    @Override
    public String getEmergency_other() {
        return emergency_other;
    }

    public void setEmergency_other(String emergency_other) {
        this.emergency_other = emergency_other;
    }

    @Override
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public int getRing_times() {
        return ring_times;
    }

    public void setRing_times(int ring_times) {
        this.ring_times = ring_times;
    }

    @Override
    public int getFavourite() {
        return favourite;
    }

    @Override
    public boolean isHospital() {
        return true;
    }

    public void setFavourite(int favourite) {
        this.favourite = favourite;
    }
}
