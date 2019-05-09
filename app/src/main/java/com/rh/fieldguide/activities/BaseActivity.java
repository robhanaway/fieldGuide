package com.rh.fieldguide.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

import com.rh.fieldguide.FieldGuideApplication;
import com.rh.fieldguide.R;
import com.rh.fieldguide.utils.logging.Logging;

public abstract class BaseActivity extends AppCompatActivity {
    protected Logging logging;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logging = getApp().getLogging();

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

}
