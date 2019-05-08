package com.rh.fieldguide;

import android.app.Application;

import com.rh.fieldguide.utils.logging.LogToCat;
import com.rh.fieldguide.utils.logging.Logging;

public class FieldGuideApplication extends Application {
    final static String TAG = FieldGuideApplication.class.getSimpleName();
    private Logging logging;
    @Override
    public void onCreate() {
        super.onCreate();
        createObjects();
        logging.d(TAG, "onCreate");
    }

    void createObjects() {
        logging = new LogToCat(true);
    }

    public Logging getLogging() {
        return logging;
    }
}
