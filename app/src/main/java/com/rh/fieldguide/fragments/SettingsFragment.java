package com.rh.fieldguide.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v4.content.LocalBroadcastManager;

import com.rh.fieldguide.FieldGuideApplication;
import com.rh.fieldguide.R;
import com.rh.fieldguide.SyncService;
import com.rh.fieldguide.activities.DisclaimerActivity;
import com.rh.fieldguide.data.DataProvider;
import com.rh.fieldguide.data.primitives.ClinicalAllLevel;

import java.util.List;

public class SettingsFragment extends PreferenceFragment implements
        SharedPreferences.OnSharedPreferenceChangeListener{

    FieldGuideApplication fieldGuideApplication;
    boolean canSync = true;
    Preference sync;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fieldGuideApplication = FieldGuideApplication.class.cast(getActivity().getApplication());
        getPreferenceManager().setSharedPreferencesName("settingsPreference");
        getPreferenceManager().setSharedPreferencesMode(Context.MODE_PRIVATE);


        addPreferencesFromResource(R.xml.settings);
        sync = getPreferenceScreen().findPreference("sync");
        sync.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                runSync();
                return false;
            }
        });
        getPreferenceScreen().findPreference("disclaimer").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                DisclaimerActivity.launch(getActivity(), true);
                return false;
            }
        });
        applyClinicalLevel();
        createSyncReceiver();

    }

    void runSync() {
        sync.setEnabled(false);
        SyncService.startSync(fieldGuideApplication);
    }

    void createSyncReceiver() {
        syncReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                handleSyncProgress(intent);
            }
        };
        LocalBroadcastManager.getInstance(fieldGuideApplication).registerReceiver(syncReceiver,
                new IntentFilter(SyncService.ACTION));
    }

    void handleSyncProgress(Intent intent) {
        boolean complete = intent.getBooleanExtra(SyncService.EXTRA_COMPLETE, false);
        sync.setEnabled(complete);
        sync.setTitle(complete ? R.string.synchronize_title : R.string.synchronize_syncing);
        sync.setSummary(complete ? getString(R.string.synchronize_subtitle) :
                intent.getStringExtra(SyncService.EXTRA_STAGE));
    }

    void applyClinicalLevel() {
        List<ClinicalAllLevel> levels = fieldGuideApplication.getDataProvider().clinicalAllLevelDao()
                .getByLevelId(fieldGuideApplication.getSettingsProvider().getClinicalLevel());
        if (!levels.isEmpty()) {
            getPreferenceScreen().findPreference("clinicalLevel")
                    .setSummary(levels.get(0).getClinicallevelname());
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    BroadcastReceiver syncReceiver;
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(fieldGuideApplication).unregisterReceiver(syncReceiver);

    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        applyClinicalLevel();
    }
}
