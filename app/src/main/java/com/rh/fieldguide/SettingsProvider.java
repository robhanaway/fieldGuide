package com.rh.fieldguide;

import android.content.Context;

public class SettingsProvider {
    final Context context;


    public SettingsProvider(Context context) {
        this.context = context;
    }

    public int getClinicalLevel() {
        return Integer.valueOf(
         context.getSharedPreferences("settingsPreference", Context.MODE_PRIVATE)
                .getString("clinicalLevel","1"));
    }

    public void setClinicalLevel(int clinicalLevel) {
        context.getSharedPreferences("settingsPreference", Context.MODE_PRIVATE)
                .edit().putString("clinicalLevel",String.valueOf(clinicalLevel)).apply();
    }
}
