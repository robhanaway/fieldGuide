package com.rh.fieldguide.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.rh.fieldguide.data.primitives.Calculation;
import com.rh.fieldguide.data.primitives.Dosage;
import com.rh.fieldguide.data.primitives.Hospital;
import com.rh.fieldguide.data.primitives.MedicineDetails;


@Database(entities = {MedicineDetails.class, Hospital.class, Dosage.class, Calculation.class}, version = 5)
@TypeConverters({DateTypeConverter.class})
public abstract class DataProvider extends RoomDatabase {
    private static DataProvider instance;
    public abstract MedicineDetailsDao medicineDetailsDao();
    public abstract HospitalsDao hospitalsDao();
    public abstract DosageDao dosageDao();
    public abstract CalculationDao calculationDao();
    public static DataProvider getDB(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    DataProvider.class,"dosages").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }
        return instance;
    }

}