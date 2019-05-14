package com.rh.fieldguide.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.rh.fieldguide.data.primitives.ClinicalAllLevel;

import java.util.List;
@Dao
public interface ClinicalAllLevelDao {
    @Insert
    long[] insertAll(ClinicalAllLevel... dosages);

    @Query("DELETE from clininalalllevel")
    void deleteAll();

    @Query("SELECT * from clininalalllevel WHERE levelid = :levelid")
    List<ClinicalAllLevel> getByLevelId(int levelid);
}
