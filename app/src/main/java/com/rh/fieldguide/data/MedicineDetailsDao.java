package com.rh.fieldguide.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.rh.fieldguide.data.primitives.MedicineDetails;

@Dao
public interface MedicineDetailsDao {
    @Insert
    long[] insertAll(MedicineDetails... medicineDetails);

    @Query("DELETE from medicinedetails")
    void deleteAll();
}
