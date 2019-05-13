package com.rh.fieldguide.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.rh.fieldguide.FieldGuideApplication;
import com.rh.fieldguide.SettingsProvider;
import com.rh.fieldguide.activities.BaseActivity;
import com.rh.fieldguide.data.DataProvider;
import com.rh.fieldguide.utils.logging.Logging;

public abstract class BaseFragment extends Fragment {
    Logging logging;
    protected SettingsProvider settingsProvider;
    protected DataProvider dataProvider;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logging = getParent().getLogging();
        settingsProvider = getParent().getSettingsProvider();
        dataProvider = getParent().getDataProvider();
    }

    protected BaseActivity getParent() {
        return (BaseActivity) getActivity();
    }

    protected FieldGuideApplication getApp() {
        return getParent().getApp();
    }
}
