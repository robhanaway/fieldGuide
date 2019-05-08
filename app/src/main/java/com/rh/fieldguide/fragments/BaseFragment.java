package com.rh.fieldguide.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.rh.fieldguide.FieldGuideApplication;
import com.rh.fieldguide.activities.BaseActivity;
import com.rh.fieldguide.utils.logging.Logging;

public abstract class BaseFragment extends Fragment {
    Logging logging;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logging = getParent().getLogging();
    }

    protected BaseActivity getParent() {
        return (BaseActivity) getActivity();
    }

    protected FieldGuideApplication getApp() {
        return (FieldGuideApplication) getParent().getApp();
    }
}
