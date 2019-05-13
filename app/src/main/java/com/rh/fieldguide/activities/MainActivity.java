package com.rh.fieldguide.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;


import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.rh.fieldguide.R;
import com.rh.fieldguide.fragments.BaseFragment;
import com.rh.fieldguide.fragments.HomeFragment;
import com.rh.fieldguide.fragments.MedicineFragment;

public class MainActivity extends BaseActivity implements HomeFragment.OnItemSelectedListener{

    BaseFragment currentFragment;
    View container;
    BottomNavigationView navigation;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

                case R.id.navigation_home:
                    loadHome();
                    return true;
                case R.id.navigation_medicication:
                    loadMedicine();
                    return true;
                case R.id.navigation_settings:
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
        navigation.setSelectedItemId(R.id.navigation_home);


    }





    @Override
    protected void onResume() {
        super.onResume();
        setActionBarTitle(R.string.app_name);
    }

    void loadMedicine() {
        loadFragment(new MedicineFragment());
    }

    void loadHome() {
        loadFragment(new HomeFragment());
    }

    void loadFragment(BaseFragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null).commit();
        currentFragment = fragment;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
