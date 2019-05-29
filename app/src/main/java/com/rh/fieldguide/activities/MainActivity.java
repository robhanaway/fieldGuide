package com.rh.fieldguide.activities;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.SearchRecentSuggestions;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;


import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SearchView;

import com.rh.fieldguide.R;
import com.rh.fieldguide.fragments.BaseFragment;
import com.rh.fieldguide.fragments.HomeFragment;
import com.rh.fieldguide.fragments.HospitalFragment;
import com.rh.fieldguide.fragments.MedicineFragment;
import com.rh.fieldguide.search.SearchSuggestionProvider;

import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;

import static android.content.Intent.ACTION_SEARCH;

public class MainActivity extends BaseActivity implements HomeFragment.OnItemSelectedListener, MaterialTapTargetPrompt.OnHidePromptListener{
    SearchView searchView;
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
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (ACTION_SEARCH.equals(intent.getAction())) {
            String searchText = intent.getStringExtra(SearchManager.QUERY);
            SearchRecentSuggestions searchRecentSuggestions = new SearchRecentSuggestions(this, SearchSuggestionProvider.AUTHORITY, SearchSuggestionProvider.MODE);
            searchRecentSuggestions.saveRecentQuery(searchText, null);
            search(searchText);
        }
    }


    void search(String searchText) {
        if (currentFragment instanceof MedicineFragment) {
            MedicineFragment.class.cast(currentFragment).search(searchText);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();


        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                search(null);
                return false;
            }
        });


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
                .setOnHidePromptListener(this)
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

    @Override
    public void onHidePrompt(MotionEvent event, boolean tappedTarget) {

    }

    @Override
    public void onHidePromptComplete() {

//        new MaterialTapTargetPrompt.Builder(this)
//                .setTarget(findViewById(R.id.nav_settings))
//                .setPrimaryText(R.string.start_hint_header)
//                .setSecondaryText(R.string.start_hint_search_content)
//                .setBackgroundColourFromRes(R.color.start_hint_header)
//                .show();
    }
}
