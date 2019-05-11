package com.rh.fieldguide.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.rh.fieldguide.data.primitives.Calculation;

@Dao
public interface CalculationDao {
    @Insert
    long[] insertAll(Calculation... calculations);

    @Query("DELETE from calculation")
    void deleteAll();
}
