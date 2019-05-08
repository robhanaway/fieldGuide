package com.rh.fieldguide.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.rh.fieldguide.data.primitives.MedicineDetails;


@Database(entities = {MedicineDetails.class}, version = 1)
@TypeConverters({DateTypeConverter.class})
public abstract class DataProvider extends RoomDatabase {
    private static DataProvider instance;
    public abstract MedicineDetailsDao medicineDetailsDao();

    public static DataProvider getDB(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    DataProvider.class,"dosages").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }
        return instance;
    }

}