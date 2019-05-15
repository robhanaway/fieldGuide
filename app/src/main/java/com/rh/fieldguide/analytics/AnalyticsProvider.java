package com.rh.fieldguide.analytics;


import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;

public class AnalyticsProvider {
    final static String MEDICATION_VIEWED = "medicationViewed";
    final static String NAME = "name";
    public void logMedicationViewed(String name) {
        Answers.getInstance().logCustom(new CustomEvent(MEDICATION_VIEWED).putCustomAttribute(NAME,name));
    }
}
