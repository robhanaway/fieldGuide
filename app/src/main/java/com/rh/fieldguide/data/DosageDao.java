package com.rh.fieldguide.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.rh.fieldguide.data.primitives.Dosage;

import java.util.List;


@Dao
public interface DosageDao {
        @Insert
        long[] insertAll(Dosage... dosages);

        @Query("DELETE from dosage")
        void deleteAll();

        @Query("SELECT * from dosage WHERE medicineid = :medicineid")
        List<Dosage> getByMedicineId(int medicineid);
}
