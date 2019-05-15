package com.rh.fieldguide;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rh.fieldguide.data.DataProvider;
import com.rh.fieldguide.data.primitives.Calculation;
import com.rh.fieldguide.data.primitives.ClinicalAllLevel;
import com.rh.fieldguide.data.primitives.Dosage;
import com.rh.fieldguide.data.primitives.Hospital;
import com.rh.fieldguide.data.primitives.MedicineClinic;
import com.rh.fieldguide.data.primitives.MedicineDetails;
import com.rh.fieldguide.utils.logging.Logging;

import java.text.SimpleDateFormat;


public class SyncService extends IntentService {
    final static String TAG = SyncService.class.getSimpleName();
    final static String START = TAG + ".start";
    final static String DELTA = TAG + ".delta";

    public final static String ACTION = TAG + "action";
    public final static String EXTRA_STAGE = TAG + "stage";
    public final static String EXTRA_COMPLETE = TAG + "complete";

    Logging logging;
    DataProvider dataProvider;
    SettingsProvider settingsProvider;

    LocalBroadcastManager localBroadcastManager;

    public SyncService() {
        super("SyncService");
    }
    private boolean syncing;

    private void broadcast(String stage, boolean complete) {
        Intent intent = new Intent(ACTION);
        intent.putExtra(EXTRA_COMPLETE, complete);
        if (!complete) {
            intent.putExtra(EXTRA_STAGE, stage);
        }
        localBroadcastManager.sendBroadcast(intent);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        FieldGuideApplication fieldGuideApplication = (FieldGuideApplication) getApplication();
        logging = fieldGuideApplication.getLogging();
        dataProvider = fieldGuideApplication.getDataProvider();
        settingsProvider = fieldGuideApplication.getSettingsProvider();
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
    }

    public static void startSync(Context context) {
        Intent intent = new Intent(context, SyncService.class);
        intent.setAction(START);
        context.startService(intent);
    }

    public static void startSyncDelta(Context context) {
        Intent intent = new Intent(context, SyncService.class);
        intent.setAction(DELTA);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (START.equals(action)) {
                startSync();
            } else if (DELTA.equals(action)) {
                deltaSync();
            }
        }
    }

    void startSync() {
        if (checkConnection()) {
            logging.d(TAG, "startSync");
            if (!isSyncing()) {
                setSyncing(true);
                sync(dataProvider);
            }
        }
    }

    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    ValueEventListener medicineEventListener;
    ValueEventListener hospitalEventListener;
    ValueEventListener dosageListener;
    ValueEventListener calculationListener;
    ValueEventListener medicineClinicListener;
    ValueEventListener clinicAllLevelListener;

    private void sync(DataProvider dataProvider) {
        broadcast("Starting", false);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        medicine(dataProvider, firebaseDatabase);

    }

    void calculation(final DataProvider dataProvider, final FirebaseDatabase firebaseDatabase) {
        broadcast("Calculations", false);
        dataProvider.calculationDao().deleteAll();
        final DatabaseReference reference = firebaseDatabase.getReference("calculation");
        calculationListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataSnapshot.getRef().removeEventListener(calculationListener);
                storeCalculation(dataSnapshot, dataProvider);

                medicineClinic(dataProvider, firebaseDatabase);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        reference.addValueEventListener(calculationListener);
    }


    void clinicAllLevel(final DataProvider dataProvider, FirebaseDatabase firebaseDatabase) {
        broadcast("Clinical Level", false);
        dataProvider.clinicalAllLevelDao().deleteAll();
        final DatabaseReference reference = firebaseDatabase.getReference("clinicalalllevel");

        clinicAllLevelListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataSnapshot.getRef().removeEventListener(clinicAllLevelListener);
                storeClinicAllLevel(dataSnapshot, dataProvider);
                setSyncing(false);
                broadcast(null, true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        reference.addValueEventListener(clinicAllLevelListener);
    }

    void storeClinicAllLevel(DataSnapshot dataSnapshot, DataProvider dataProvider) {
        int count = 0;
        ClinicalAllLevel[] clinicalAllLevels = new ClinicalAllLevel[(int)dataSnapshot.getChildrenCount()];

        for (DataSnapshot child : dataSnapshot.getChildren()) {
            clinicalAllLevels[count++] = clinicalAllLevelFromSnapshot(child);

        }
        dataProvider.clinicalAllLevelDao().insertAll(clinicalAllLevels);
        logging.d(TAG, "clinincal levels stored : %d", clinicalAllLevels.length);

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

    void medicineClinic(final DataProvider dataProvider, final FirebaseDatabase firebaseDatabase) {
        broadcast("Medicine By Clinical Level", false);
        dataProvider.medicineClinicDao().deleteAll();
        final DatabaseReference reference = firebaseDatabase.getReference("medicineclinic");
        medicineClinicListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataSnapshot.getRef().removeEventListener(medicineClinicListener);
                storeMedicineClinic(dataSnapshot, dataProvider);
                clinicAllLevel(dataProvider, firebaseDatabase);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        reference.addValueEventListener(medicineClinicListener);
    }

    void storeMedicineClinic(DataSnapshot dataSnapshot, DataProvider dataProvider) {
        int count = 0;
        MedicineClinic[] medicineClinics = new MedicineClinic[(int)dataSnapshot.getChildrenCount()];

        for (DataSnapshot child : dataSnapshot.getChildren()) {
            medicineClinics[count++] = medicineClinicFromSnapshot(child);

        }
        dataProvider.medicineClinicDao().insertAll(medicineClinics);
        logging.d(TAG, "MedicineClinic stored : %d", medicineClinics.length);

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

    void medicine(final DataProvider dataProvider, final FirebaseDatabase firebaseDatabase) {
        broadcast("Medicines", false);
        dataProvider.medicineDetailsDao().deleteAll();

        final DatabaseReference reference = firebaseDatabase.getReference("medicinedetails");
        medicineEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                dataSnapshot.getRef().removeEventListener(medicineEventListener);
                storeMedicine(dataSnapshot,dataProvider);
                hospitals(dataProvider,firebaseDatabase);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        reference.addValueEventListener(medicineEventListener);
    }

    void storeMedicine(DataSnapshot dataSnapshot, DataProvider dataProvider) {
        int count = 0;
        MedicineDetails[] medicineDetails = new MedicineDetails[(int)dataSnapshot.getChildrenCount()];
        for (DataSnapshot child : dataSnapshot.getChildren()) {
            medicineDetails[count++] = medicineFromDataSnapshot(child);
        }
        dataProvider.medicineDetailsDao().insertAll(medicineDetails);
        logging.d(TAG, "medicine stored : %d", medicineDetails.length);

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

    void hospitals(final DataProvider dataProvider, final FirebaseDatabase firebaseDatabase) {
        broadcast("Hospitals", false);
        dataProvider.hospitalsDao().deleteAll();
        final DatabaseReference reference = firebaseDatabase.getReference("hospitals");
        hospitalEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataSnapshot.getRef().removeEventListener(hospitalEventListener);
                storeHospitals(dataSnapshot,dataProvider);
                dosages(dataProvider,firebaseDatabase);

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
            hospitals[count++] = hospitalFromSnapshot(child);
        }
        dataProvider.hospitalsDao().insertAll(hospitals);
        logging.d(TAG, "Inserted hospitals %d", hospitals.length);
    }

    void dosages(final DataProvider dataProvider, final FirebaseDatabase firebaseDatabase) {
        broadcast("Dosages", false);
        dataProvider.dosageDao().deleteAll();
        final DatabaseReference reference = firebaseDatabase.getReference("dosage");
        dosageListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataSnapshot.getRef().removeEventListener(dosageListener);
                storeDosage(dataSnapshot,dataProvider);
                calculation(dataProvider,firebaseDatabase);

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
        logging.d(TAG, "Inserted dosages %d", dosages.length);

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

        dataProvider.calculationDao().insertAll(calculations);
        logging.d(TAG, "Inserted calculations %d", calculations.length);

    }

    private synchronized boolean isSyncing() {
        return syncing;
    }

    private synchronized void setSyncing(boolean syncing) {
        this.syncing = syncing;
    }

    ValueEventListener versionListener;
    void deltaSync() {
        if (checkConnection()) {
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

            final DatabaseReference reference = firebaseDatabase.getReference("version");
            versionListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    checkAgainstConfig(dataSnapshot);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };
            reference.addValueEventListener(versionListener);
        }
    }

    boolean checkConnection() {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager == null ? null : connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null) {
            connected = networkInfo.isConnected();
        }

        if (!connected) {
            broadcast(null, true);
        }
        return connected;
    }
    void checkAgainstConfig(DataSnapshot dataSnapshot) {
        DataSnapshot lastChild = null;
        for (DataSnapshot child : dataSnapshot.getChildren()) {
            lastChild = child;
        }

        if (lastChild != null) {
            for (DataSnapshot valueIterator : lastChild.getChildren()) {
                switch (valueIterator.getKey()) {
                    case "version":
                        checkAgainstVersion(Integer.parseInt(valueIterator.getValue().toString()));
                        break;
                }
            }
        }
    }

    void checkAgainstVersion(int remoteVersion) {
        if (remoteVersion > settingsProvider.getDBVersion()) {
            settingsProvider.setDBVersion(remoteVersion);
            //Run the sync
            startSync();
        } else {
            broadcast(null, true);
        }
    }
}
