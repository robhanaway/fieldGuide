package com.rh.fieldguide.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.rh.fieldguide.data.primitives.MedicineClinic;

@Dao
public interface MedicineClinicDao {
    @Insert
    long[] insertAll(MedicineClinic... medicineDetails);

    @Query("DELETE from medicineclinic")
    void deleteAll();
}
