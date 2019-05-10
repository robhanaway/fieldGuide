package com.rh.fieldguide.data.primitives;

public class HospitalHeader implements HospitalBC {
    final  String name;

    public HospitalHeader(String name) {
        this.name = name;
    }

    @Override
    public String getCounty() {
        return null;
    }

    @Override
    public int get_id() {
        return 0;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getMain() {
        return null;
    }

    @Override
    public String getEmergency() {
        return null;
    }

    @Override
    public String getEmergency_other() {
        return null;
    }

    @Override
    public String getCode() {
        return null;
    }

    @Override
    public int getRing_times() {
        return 0;
    }

    @Override
    public int getFavourite() {
        return 0;
    }

    @Override
    public boolean isHospital() {
        return false;
    }
}
