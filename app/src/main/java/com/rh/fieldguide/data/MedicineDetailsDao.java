package com.rh.fieldguide.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.rh.fieldguide.data.primitives.MedicineDetails;

import java.util.List;

@Dao
public interface MedicineDetailsDao {
    @Insert
    long[] insertAll(MedicineDetails... medicineDetails);

    @Query("DELETE from medicinedetails")
    void deleteAll();

    @Query("SELECT * FROM medicinedetails ORDER by medicinename ASC")
    List<MedicineDetails> getAll();

    @Query("SELECT * FROM medicinedetails WHERE _id = :id")
    List<MedicineDetails> getById(int id);
}
