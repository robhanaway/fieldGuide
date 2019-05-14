package com.rh.fieldguide;

import android.app.Application;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rh.fieldguide.data.DataProvider;
import com.rh.fieldguide.data.FirebaseSyncProvider;
import com.rh.fieldguide.data.LocalSyncProvider;
import com.rh.fieldguide.data.csvExtract;
import com.rh.fieldguide.utils.logging.LogToCat;
import com.rh.fieldguide.utils.logging.Logging;

public class FieldGuideApplication extends Application {
    final static String TAG = FieldGuideApplication.class.getSimpleName();
    private Logging logging;
    private SettingsProvider settingsProvider;
    private DataProvider dataProvider;
    @Override
    public void onCreate() {
        super.onCreate();
        createObjects();
        logging.d(TAG, "onCreate");
        settingsProvider = new SettingsProvider(this);
        dataProvider = DataProvider.getDB(this);


    }

    void createObjects() {
        logging = new LogToCat(true);
    }

    public Logging getLogging() {
        return logging;
    }


    public SettingsProvider getSettingsProvider() {
        return settingsProvider;
    }

    public DataProvider getDataProvider() {
        return dataProvider;
    }
}
