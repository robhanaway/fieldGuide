package com.rh.fieldguide.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.rh.fieldguide.data.primitives.Hospital;
import com.rh.fieldguide.data.primitives.MedicineDetails;

import java.util.List;

@Dao
public interface HospitalsDao {
    @Insert
    long[] insertAll(Hospital... hospitals);

    @Query("DELETE from hospitals")
    void deleteAll();

    @Query("SELECT * FROM hospitals ORDER by county ASC")
    List<Hospital> getAll();

    @Query("SELECT * FROM hospitals WHERE _id = :id")
    List<Hospital> getById(int id);
}
