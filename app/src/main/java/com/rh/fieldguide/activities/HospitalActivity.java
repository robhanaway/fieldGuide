package com.rh.fieldguide.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

import com.rh.fieldguide.R;
import com.rh.fieldguide.adapters.HospitalAdapter;
import com.rh.fieldguide.data.primitives.Hospital;
import com.rh.fieldguide.data.primitives.HospitalBC;

public class HospitalActivity extends BaseActivity implements HospitalAdapter.OnItemClickListener{
    ActionModeCallback actionModeCallback;
    ActionMode currentActionMode;
    HospitalAdapter hospitalAdapter;
    RecyclerView recyclerView;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospitals);

        actionModeCallback = new ActionModeCallback();
        currentActionMode = startActionMode(actionModeCallback);

        recyclerView = findViewById(R.id.hospitals);
        load();
    }

    void load() {
        hospitalAdapter = new HospitalAdapter(this, getDataProvider().hospitalsDao().getAll(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(hospitalAdapter);
    }
    @Override
    public void onItemClicked(HospitalBC hospital) {
        if (hospital instanceof Hospital) {
            Intent intent = new Intent(getApp(), HospitalDetailActivity.class);
            intent.putExtra(MedicineInfoActivity.EXTRA_ID, hospital.get_id());
            startActivity(intent);
        }
    }

    private class ActionModeCallback implements ActionMode.Callback {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.setTitle(R.string.title_hospitals);


            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {

            return true;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

            return false;


        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            finish();

        }
    }
}
