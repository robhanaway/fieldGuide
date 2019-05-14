package com.rh.fieldguide;

import android.content.Context;

public class SettingsProvider {
    public static final String CLINICAL_LEVEL = "clinicalLevel";
    public static final String SETTINGS_PREFERENCE = "settingsPreference";
    public static final String DB_VERSION = "dbVersion";
    final Context context;


    public SettingsProvider(Context context) {
        this.context = context;
    }

    public int getDBVersion() {
        return Integer.valueOf(
                context.getSharedPreferences(SETTINGS_PREFERENCE, Context.MODE_PRIVATE)
                        .getString(DB_VERSION,"0"));
    }

    public void setDBVersion(int version) {
        context.getSharedPreferences(SETTINGS_PREFERENCE, Context.MODE_PRIVATE)
                .edit().putString(DB_VERSION,String.valueOf(version)).apply();
    }

    public int getClinicalLevel() {
        return Integer.valueOf(
         context.getSharedPreferences(SETTINGS_PREFERENCE, Context.MODE_PRIVATE)
                .getString(CLINICAL_LEVEL,"1"));
    }

    public void setClinicalLevel(int clinicalLevel) {
        context.getSharedPreferences(SETTINGS_PREFERENCE, Context.MODE_PRIVATE)
                .edit().putString(CLINICAL_LEVEL,String.valueOf(clinicalLevel)).apply();
    }
}
