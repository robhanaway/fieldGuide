package com.rh.fieldguide;

import android.app.Application;


import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.perf.FirebasePerformance;
import com.rh.fieldguide.analytics.AnalyticsProvider;
import com.rh.fieldguide.data.DataProvider;
import com.rh.fieldguide.data.FirebaseSyncProvider;
import com.rh.fieldguide.data.LocalSyncProvider;
import com.rh.fieldguide.data.csvExtract;
import com.rh.fieldguide.utils.logging.LogToCat;
import com.rh.fieldguide.utils.logging.Logging;
import io.fabric.sdk.android.Fabric;


public class FieldGuideApplication extends Application {
    final static String TAG = FieldGuideApplication.class.getSimpleName();
    private Logging logging;
    private SettingsProvider settingsProvider;
    private DataProvider dataProvider;
    private AnalyticsProvider analyticsProvider;
    @Override
    public void onCreate() {
        super.onCreate();

        Fabric.with(this, new Crashlytics(), new Answers());


        createObjects();
        analyticsProvider = new AnalyticsProvider();

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

    public AnalyticsProvider getAnalyticsProvider() {
        return analyticsProvider;
    }
}
