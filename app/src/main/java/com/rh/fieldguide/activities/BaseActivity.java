package com.rh.fieldguide.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


import com.rh.fieldguide.FieldGuideApplication;

import com.rh.fieldguide.SettingsProvider;
import com.rh.fieldguide.analytics.AnalyticsProvider;
import com.rh.fieldguide.data.DataProvider;
import com.rh.fieldguide.utils.logging.Logging;

public abstract class BaseActivity extends AppCompatActivity {
    public final static String EXTRA_ID = "id";
    protected Logging logging;
    protected DataProvider dataProvider;
    protected SettingsProvider settingsProvider;
    protected AnalyticsProvider analyticsProvider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logging = getApp().getLogging();
        dataProvider = getApp().getDataProvider();
        settingsProvider = getApp().getSettingsProvider();
        analyticsProvider = getApp().getAnalyticsProvider();
    }

    public FieldGuideApplication getApp() {
        return (FieldGuideApplication) getApplication();
    }

    public Logging getLogging() {
        return logging;
    }

    public void setActionBarTitle(int resId) {
        setActionBarTitle(getString(resId));
    }

    public void setActionBarTitle(String text) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(text);

        }
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
