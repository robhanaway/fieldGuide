package com.rh.fieldguide.data;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rh.fieldguide.R;
import com.rh.fieldguide.data.primitives.MedicineDetails;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LocalSyncProvider implements SyncProvider {
    final DataProvider dataProvider;
    final Context context;
    public LocalSyncProvider(Context context) {
        this.context = context;
        dataProvider = DataProvider.getDB(context);
    }

    @Override
    public void sync(DataProvider dataProvider) {
        storeMedicineDetails(dataProvider);
    }

    void storeMedicineDetails(DataProvider dataProvider) {
        dataProvider.medicineDetailsDao().deleteAll();
        Gson gson = new GsonBuilder()

                .registerTypeAdapter(Date.class, new DateSerializer())
                .setLenient()
                .create();
        MedicineDetails[] medicineDetails =  gson.fromJson(new InputStreamReader(context.getResources().openRawResource(R.raw.medicinedetails)), MedicineDetails[].class);
        if (medicineDetails != null) {
            dataProvider.medicineDetailsDao().insertAll(medicineDetails);
        }
    }

}
