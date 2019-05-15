package com.rh.fieldguide.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;


import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.rh.fieldguide.R;
import com.rh.fieldguide.SyncService;
import com.rh.fieldguide.fragments.BaseFragment;
import com.rh.fieldguide.fragments.HomeFragment;
import com.rh.fieldguide.fragments.HospitalFragment;
import com.rh.fieldguide.fragments.MedicineFragment;

import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;

public class MainActivity extends BaseActivity implements HomeFragment.OnItemSelectedListener{

    BaseFragment currentFragment;
    View container;
    BottomNavigationView navigation;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

                case R.id.navigation_hospitals:
                    loadHospitals();
                    return true;
                case R.id.navigation_medicication:
                    loadMedicine();
                    return true;
            }


            return false;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        container = findViewById(R.id.fragment_container);

        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_medicication);


    }

    Handler handler = new Handler();
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        if (!settingsProvider.isMainHintShown()) {
            settingsProvider.setMainHintShown(true);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    showTap();
                }
            }, 250);
        }

    }


    void showTap() {

        new MaterialTapTargetPrompt.Builder(this)
                .setTarget(findViewById(R.id.nav_settings))
                .setPrimaryText(R.string.start_hint_header)
                .setSecondaryText(R.string.start_hint_content)
                .setBackgroundColourFromRes(R.color.start_hint_header)
                .show();
    }




    @Override
    protected void onResume() {
        super.onResume();
        setActionBarTitle(R.string.app_name);
    }

    void loadMedicine() {
        loadFragment(new MedicineFragment());
    }

    void loadHospitals() {
        loadFragment(new HospitalFragment());
    }

    void loadFragment(BaseFragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction. addToBackStack(null).commit();
        currentFragment = fragment;
    }

    @Override
    public void onBackPressed() {

    }


    @Override
    public void onHospitals() {
        startActivity(new Intent(this, HospitalActivity.class));
    }

    @Override
    public void onMedications() {
        navigation.setSelectedItemId(R.id.navigation_medicication);
    }
}
