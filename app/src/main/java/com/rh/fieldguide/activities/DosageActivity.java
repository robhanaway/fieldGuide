package com.rh.fieldguide.activities;

import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.rh.fieldguide.R;
import com.rh.fieldguide.data.Calculator;
import com.rh.fieldguide.data.DataProvider;
import com.rh.fieldguide.data.primitives.MedicineDetails;

import java.util.List;

public class DosageActivity extends BaseActivity {
    ActionModeCallback actionModeCallback;
    ActionMode currentActionMode;
    MedicineDetails medicineDetails;
    Calculator calculator;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dosage);

        actionModeCallback = new ActionModeCallback();
//        currentActionMode = startActionMode(actionModeCallback);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (getIntent() != null) {
            int id = getIntent().getIntExtra(EXTRA_ID,-1);
            if (id != -1) {
                List<MedicineDetails> list = DataProvider.getDB(getApp()).medicineDetailsDao().getById(id);
                if (list != null && list.size() ==1) {
                    medicineDetails = list.get(0);
                    calculator = new Calculator(getDataProvider(), medicineDetails);
                    populate();
                }
            }
        }
    }

    TextView medicine;
    void populate() {
        medicine = findViewById(R.id.medicine);
        medicine.setText(medicineDetails.getMedicinename() + getString(R.string.dosage_calculation));
    }

    private class ActionModeCallback implements ActionMode.Callback {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.setTitle(R.string.calculate_dosage);


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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
