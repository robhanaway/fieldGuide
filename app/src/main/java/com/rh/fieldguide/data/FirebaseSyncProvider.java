package com.rh.fieldguide.data;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rh.fieldguide.data.primitives.Calculation;
import com.rh.fieldguide.data.primitives.ClinicalAllLevel;
import com.rh.fieldguide.data.primitives.Dosage;
import com.rh.fieldguide.data.primitives.Hospital;
import com.rh.fieldguide.data.primitives.MedicineClinic;
import com.rh.fieldguide.data.primitives.MedicineDetails;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class FirebaseSyncProvider implements SyncProvider {
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    ValueEventListener medicineEventListener;
    ValueEventListener hospitalEventListener;
    ValueEventListener dosageListener;
    ValueEventListener calculationListener;
    ValueEventListener medicineClinicListener;
    ValueEventListener clinicAllLevelListener;
    @Override
    public void sync(DataProvider dataProvider) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        medicine(dataProvider, firebaseDatabase);
        hospitals(dataProvider,firebaseDatabase);
        dosages(dataProvider,firebaseDatabase);
        calculation(dataProvider,firebaseDatabase);
        medicineClinic(dataProvider, firebaseDatabase);
        clinicAllLevel(dataProvider, firebaseDatabase);
    }

    void calculation(final DataProvider dataProvider, FirebaseDatabase firebaseDatabase) {
        dataProvider.calculationDao().deleteAll();
        final DatabaseReference reference = firebaseDatabase.getReference("calculation");
        calculationListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataSnapshot.getRef().removeEventListener(calculationListener);
                storeCalculation(dataSnapshot, dataProvider);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        reference.addValueEventListener(calculationListener);
    }


    void clinicAllLevel(final DataProvider dataProvider, FirebaseDatabase firebaseDatabase) {
        dataProvider.clinicalAllLevelDao().deleteAll();
        final DatabaseReference reference = firebaseDatabase.getReference("clinicalalllevel");
        clinicAllLevelListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataSnapshot.getRef().removeEventListener(clinicAllLevelListener);
                storeClinicAllLevel(dataSnapshot, dataProvider);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        reference.addValueEventListener(clinicAllLevelListener);
    }

    void storeClinicAllLevel(DataSnapshot dataSnapshot, DataProvider dataProvider) {
        int count = 0;
        ClinicalAllLevel[] dosages = new ClinicalAllLevel[(int)dataSnapshot.getChildrenCount()];

        for (DataSnapshot child : dataSnapshot.getChildren()) {
            dosages[count++] = clinicalAllLevelFromSnapshot(child);

        }
        dataProvider.clinicalAllLevelDao().insertAll(dosages);
        System.out.println(count);
    }

    ClinicalAllLevel clinicalAllLevelFromSnapshot(DataSnapshot dataSnapshot) {
        ClinicalAllLevel result = null;

        try {
            result = new ClinicalAllLevel();
            for (DataSnapshot valueIterator : dataSnapshot.getChildren()) {
                switch (valueIterator.getKey()) {
                    case "_id":
                        result.set_id(Integer.parseInt(valueIterator.getValue(String.class)));
                        break;
                    case "levelid":
                        result.setLevelid(Integer.parseInt(valueIterator.getValue(String.class)));
                        break;
                    case "clinicallevelname":
                        result.setClinicallevelname(valueIterator.getValue(String.class));
                        break;


                }
            }
        } catch (Exception e) {
            result = null;
        }
        return result;
    }

    void medicineClinic(final DataProvider dataProvider, FirebaseDatabase firebaseDatabase) {
        dataProvider.medicineClinicDao().deleteAll();
        final DatabaseReference reference = firebaseDatabase.getReference("medicineclinic");
        medicineClinicListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataSnapshot.getRef().removeEventListener(medicineClinicListener);
                storeMedicineClinic(dataSnapshot, dataProvider);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        reference.addValueEventListener(medicineClinicListener);
    }

    void storeMedicineClinic(DataSnapshot dataSnapshot, DataProvider dataProvider) {
        int count = 0;
        MedicineClinic[] dosages = new MedicineClinic[(int)dataSnapshot.getChildrenCount()];

        for (DataSnapshot child : dataSnapshot.getChildren()) {
            dosages[count++] = medicineClinicFromSnapshot(child);

        }
        dataProvider.medicineClinicDao().insertAll(dosages);
        System.out.println(count);
    }

    MedicineClinic medicineClinicFromSnapshot(DataSnapshot dataSnapshot) {
        MedicineClinic result = null;

        try {
            result = new MedicineClinic();
            for (DataSnapshot valueIterator : dataSnapshot.getChildren()) {
                switch (valueIterator.getKey()) {
                    case "_id":
                        result.set_id(Integer.parseInt(valueIterator.getValue(String.class)));
                        break;
                    case "clinicallevelid":
                        result.setClinicallevelid(Integer.parseInt(valueIterator.getValue(String.class)));
                        break;
                    case "medclinid":
                        result.setMedclinid(Integer.parseInt(valueIterator.getValue(String.class)));
                        break;
                    case "medicinedetailid":
                        result.setMedicinedetailid(Integer.parseInt(valueIterator.getValue(String.class)));
                        break;

                }
            }
        } catch (Exception e) {
            result = null;
        }
        return result;
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
        Hospital[] hospitals = new Hospital[(int) dataSnapshot.getChildrenCount()];
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

    Calculation calculationFromSnapshot(DataSnapshot dataSnapshot) {
        Calculation result = null;

        try {
            result = new Calculation();
            for (DataSnapshot valueIterator : dataSnapshot.getChildren()) {
                switch (valueIterator.getKey()) {
                    case "_id":
                        result.set_id(Integer.parseInt(valueIterator.getValue(String.class)));
                        break;
                    case "ct_calid":
                        result.setCt_calid(Integer.parseInt(valueIterator.getValue(String.class)));
                        break;
                    case "ct_dosageid":
                        result.setCt_dosageid(Integer.parseInt(valueIterator.getValue(String.class)));
                        break;
                    case "age":
                        result.setAge(valueIterator.getValue(String.class));
                        break;
                    case "mg":
                        result.setMg(valueIterator.getValue(String.class));
                        break;
                    case "weight":
                        result.setWeight(valueIterator.getValue(String.class));
                        break;
                    case "ml":
                        result.setMl(valueIterator.getValue(String.class));
                        break;
                    case "ct_modifieddate":
                        result.setCt_modifieddate(formatter.parse(valueIterator.getValue(String.class)));
                        break;
                    case "ct_createdate":
                        result.setCt_createdate(formatter.parse(valueIterator.getValue(String.class)));
                        break;
                }
            }
        } catch (Exception e) {
            result = null;
        }
        return result;
    }

    void storeCalculation(DataSnapshot dataSnapshot, DataProvider dataProvider) {
        dataProvider.calculationDao().deleteAll();
        Calculation[] calculations = new Calculation[(int)dataSnapshot.getChildrenCount()];
        int count = 0;


        for (DataSnapshot child : dataSnapshot.getChildren()) {
            calculations[count++] = calculationFromSnapshot(child);

        }

        System.out.println(count);
        dataProvider.calculationDao().insertAll(calculations);
    }
}
