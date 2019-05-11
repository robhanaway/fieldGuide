package com.rh.fieldguide.data;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rh.fieldguide.data.primitives.Dosage;
import com.rh.fieldguide.data.primitives.Hospital;
import com.rh.fieldguide.data.primitives.MedicineDetails;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class FirebaseSyncProvider implements SyncProvider {
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    ValueEventListener medicineEventListener;
    ValueEventListener hospitalEventListener;
    ValueEventListener dosageListener;
    @Override
    public void sync(DataProvider dataProvider) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        medicine(dataProvider, firebaseDatabase);
        hospitals(dataProvider,firebaseDatabase);
        dosages(dataProvider,firebaseDatabase);
    }

    void medicine(final DataProvider dataProvider, FirebaseDatabase firebaseDatabase) {
        dataProvider.medicineDetailsDao().deleteAll();

        final DatabaseReference reference = firebaseDatabase.getReference("medicinedetails");
        medicineEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                dataSnapshot.getRef().removeEventListener(medicineEventListener);
                storeMedicine(dataSnapshot,dataProvider);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        reference.addValueEventListener(medicineEventListener);
    }

    void storeMedicine(DataSnapshot dataSnapshot, DataProvider dataProvider) {
        int count = 0;
        for (DataSnapshot child : dataSnapshot.getChildren()) {
            MedicineDetails medicineDetails = medicineFromDataSnapshot(child);
            if (medicineDetails != null) {
                dataProvider.medicineDetailsDao().insertAll(medicineDetails);
                count++;
            }
        }
        System.out.println(count);
    }

    MedicineDetails medicineFromDataSnapshot(DataSnapshot dataSnapshot) {
        MedicineDetails result = null;

        try {
            result = new MedicineDetails();
            for (DataSnapshot valueIterator : dataSnapshot.getChildren()) {
                switch (valueIterator.getKey()) {
                    case "_id":
                        result.set_id(Integer.parseInt(valueIterator.getValue(String.class)));
                        break;
                    case "additionalinformations":
                        result.setAdditionalinformations(valueIterator.getValue(String.class));
                        break;
                    case "adultdose":
                        result.setAdultdose(valueIterator.getValue(String.class));
                        break;
                    case "contraindications":
                        result.setContraindications(valueIterator.getValue(String.class));
                        break;
                    case "indications":
                        result.setIndications(valueIterator.getValue(String.class));
                        break;
                    case "mdccreatedate":
                        result.setMdccreatedate(formatter.parse(valueIterator.getValue(String.class)));
                        break;
                    case "mdcid":
                        result.setMdcid(Integer.parseInt(valueIterator.getValue(String.class)));
                        break;
                    case "mdcmodifieddate":
                        result.setMdcmodifieddate(formatter.parse(valueIterator.getValue(String.class)));
                        break;
                    case "medicinename":
                        result.setMedicinename(valueIterator.getValue(String.class));
                        break;
                    case "paediatricdose":
                        result.setPaediatricdose(valueIterator.getValue(String.class));
                        break;
                    case "sideeffects":
                        result.setSideeffects(valueIterator.getValue(String.class));
                        break;
                }
            }
        } catch (Exception e) {
            result = null;
        }
        return result;
    }

    void hospitals(final DataProvider dataProvider, FirebaseDatabase firebaseDatabase) {
        dataProvider.hospitalsDao().deleteAll();
        final DatabaseReference reference = firebaseDatabase.getReference("hospitals");
        hospitalEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                dataSnapshot.getRef().removeEventListener(hospitalEventListener);
                storeHospitals(dataSnapshot,dataProvider);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        reference.addValueEventListener(hospitalEventListener);
    }

    Hospital hospitalFromSnapshot(DataSnapshot dataSnapshot) {
        Hospital result = null;
        try {
            result = new Hospital();
            for (DataSnapshot valueIterator : dataSnapshot.getChildren()) {
                switch (valueIterator.getKey()) {
                    case "id":
                        result.set_id(valueIterator.getValue(Integer.class));
                        break;
                    case "name":
                        result.setName(valueIterator.getValue(String.class));
                        break;
                    case "main":
                        result.setMain(valueIterator.getValue(String.class));
                        break;
                    case "code":
                        result.setCode(valueIterator.getValue(String.class));
                        break;
                    case "er":
                        result.setEmergency(valueIterator.getValue(String.class));
                        break;
                    case "er_other":
                        result.setEmergency_other(valueIterator.getValue(String.class));
                        break;
                    case "county":
                        result.setCounty(valueIterator.getValue(String.class));
                        break;
                }
            }
        } catch (Exception e) {
            result = null;
        }
        return result;
    }

    void storeHospitals(DataSnapshot dataSnapshot, DataProvider dataProvider) {
        int count = 0;
        for (DataSnapshot child : dataSnapshot.getChildren()) {
            Hospital hospital = hospitalFromSnapshot(child);
            if (hospital != null) {
                dataProvider.hospitalsDao().insertAll(hospital);
                count++;
            }
        }
        System.out.println(count);
    }

    void dosages(final DataProvider dataProvider, FirebaseDatabase firebaseDatabase) {
        dataProvider.dosageDao().deleteAll();
        final DatabaseReference reference = firebaseDatabase.getReference("dosage");
        dosageListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                dataSnapshot.getRef().removeEventListener(dosageListener);
                storeDosage(dataSnapshot,dataProvider);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        reference.addValueEventListener(dosageListener);
    }

    Dosage dosageFromDataSnapshot(DataSnapshot dataSnapshot) {
        Dosage result = null;

        try {
            result = new Dosage();
            for (DataSnapshot valueIterator : dataSnapshot.getChildren()) {
                switch (valueIterator.getKey()) {
                    case "_id":
                        result.set_id(Integer.parseInt(valueIterator.getValue(String.class)));
                        break;
                    case "dosageid":
                        result.setDosageid(Integer.parseInt(valueIterator.getValue(String.class)));
                        break;
                    case "medicineid":
                        result.setMedicineid(Integer.parseInt(valueIterator.getValue(String.class)));
                        break;
                    case "concentration":
                        result.setConcentration(valueIterator.getValue(String.class));
                        break;
                    case "paediatricdose":
                        result.setPaediatricdose(valueIterator.getValue(String.class));
                        break;
                    case "dosagecreatedate":
                        result.setDosagecreatedate(formatter.parse(valueIterator.getValue(String.class)));
                        break;
                    case "dosagemodifieddate":
                        result.setDosagemodifieddate(formatter.parse(valueIterator.getValue(String.class)));
                        break;
                }
            }
        } catch (Exception e) {
            result = null;
        }
        return result;
    }

    void storeDosage(DataSnapshot dataSnapshot, DataProvider dataProvider) {
        int count = 0;
        Dosage[] dosages = new Dosage[(int)dataSnapshot.getChildrenCount()];

        for (DataSnapshot child : dataSnapshot.getChildren()) {
            dosages[count++] = dosageFromDataSnapshot(child);

        }
        dataProvider.dosageDao().insertAll(dosages);
        System.out.println(count);
    }
}
