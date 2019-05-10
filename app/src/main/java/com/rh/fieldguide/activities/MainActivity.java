package com.rh.fieldguide.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;


import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;

import com.rh.fieldguide.R;
import com.rh.fieldguide.fragments.BaseFragment;
import com.rh.fieldguide.fragments.HomeFragment;
import com.rh.fieldguide.fragments.MedicineFragment;

public class MainActivity extends BaseActivity{

    BaseFragment currentFragment;
    View container;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        container = findViewById(R.id.fragment_container);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
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


}
