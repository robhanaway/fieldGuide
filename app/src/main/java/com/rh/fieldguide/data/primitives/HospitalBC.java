package com.rh.fieldguide.data.primitives;

public interface HospitalBC {
    String getCounty();

    int get_id();

    String getName();

    String getMain();

    String getEmergency();

    String getEmergency_other();

    String getCode();

    int getRing_times();

    int getFavourite();
    boolean isHospital();
}
