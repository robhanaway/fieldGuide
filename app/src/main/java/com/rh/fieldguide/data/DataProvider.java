package com.rh.fieldguide.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.rh.fieldguide.data.primitives.Calculation;
import com.rh.fieldguide.data.primitives.ClinicalAllLevel;
import com.rh.fieldguide.data.primitives.Dosage;
import com.rh.fieldguide.data.primitives.Hospital;
import com.rh.fieldguide.data.primitives.MedicineClinic;
import com.rh.fieldguide.data.primitives.MedicineDetails;


@Database(entities = {MedicineDetails.class, Hospital.class, Dosage.class, Calculation.class, MedicineClinic.class, ClinicalAllLevel.class}, version = 1)
@TypeConverters({DateTypeConverter.class})
public abstract class DataProvider extends RoomDatabase {
    private static DataProvider instance;
    public abstract MedicineDetailsDao medicineDetailsDao();
    public abstract HospitalsDao hospitalsDao();
    public abstract DosageDao dosageDao();
    public abstract CalculationDao calculationDao();
    public abstract MedicineClinicDao medicineClinicDao();
    public abstract ClinicalAllLevelDao clinicalAllLevelDao();
    public static DataProvider getDB(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    DataProvider.class,"dosages").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }
        return instance;
    }

}