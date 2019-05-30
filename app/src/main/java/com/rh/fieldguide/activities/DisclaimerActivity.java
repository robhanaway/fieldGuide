package com.rh.fieldguide.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.rh.fieldguide.R;

public class DisclaimerActivity extends BaseActivity{
    final static String LAUNCHED_FROM_SETTINGS = "launchedFromSettings";
    boolean launchedFromSettings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disclaimer);
        launchedFromSettings = getIntent() != null && getIntent().getBooleanExtra(LAUNCHED_FROM_SETTINGS, false);
        findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingsProvider.setDisclaimerShown(true);
                if (!launchedFromSettings) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
                finish();
            }
        });
    }

    public static void launch(Context context, boolean fromSettings) {
        Intent intent = new Intent(context, DisclaimerActivity.class);
        intent.putExtra(LAUNCHED_FROM_SETTINGS, fromSettings);
        context.startActivity(intent);

    }
}
