package com.rh.fieldguide.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.rh.fieldguide.FieldGuideApplication;
import com.rh.fieldguide.utils.logging.Logging;

public abstract class BaseActivity extends AppCompatActivity {
    protected Logging logging;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logging = getApp().getLogging();
    }

    protected FieldGuideApplication getApp() {
        return (FieldGuideApplication) getApplication();
    }
}
