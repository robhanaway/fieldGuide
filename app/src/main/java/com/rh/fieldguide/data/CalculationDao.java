package com.rh.fieldguide.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.rh.fieldguide.data.primitives.Calculation;

import java.util.List;

@Dao
public interface CalculationDao {
    @Insert
    long[] insertAll(Calculation... calculations);

    @Query("DELETE from calculation")
    void deleteAll();

    @Query("SELECT * from calculation where ct_dosageid = :dosageId")
    List<Calculation> getByDosageId(int dosageId);
}
