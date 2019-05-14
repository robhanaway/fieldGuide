package com.rh.fieldguide.activities;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.rh.fieldguide.R;
import com.rh.fieldguide.SyncService;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashActivity extends BaseActivity {
    TextView sync;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        sync = findViewById(R.id.sync);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        createSyncReceiver();
        SyncService.startSyncDelta(getApp());
    }

    void handleSyncProgress(Intent intent) {
        boolean complete = intent.getBooleanExtra(SyncService.EXTRA_COMPLETE, false);
        if (!complete) {
            sync.setText("Syncing : " + intent.getStringExtra(SyncService.EXTRA_STAGE));
        } else {
            startActivity(new Intent(getApp(), MainActivity.class));
        }
    }

    void createSyncReceiver() {
        syncReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                handleSyncProgress(intent);
            }
        };
        LocalBroadcastManager.getInstance(getApp()).registerReceiver(syncReceiver,
                new IntentFilter(SyncService.ACTION));
    }

    BroadcastReceiver syncReceiver;
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getApp()).unregisterReceiver(syncReceiver);

    }
}
