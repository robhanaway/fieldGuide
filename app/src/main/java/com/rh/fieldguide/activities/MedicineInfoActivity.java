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

public class MedicineInfoActivity extends BaseActivity {
    final static String TAG = MedicineInfoActivity.class.getSimpleName();

    MedicineDetails medicineDetails;
    ActionModeCallback actionModeCallback;
    ActionMode currentActionMode;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicineinfo);

        if (getIntent() != null) {
            int id = getIntent().getIntExtra(EXTRA_ID,-1);
            if (id != -1) {
                List<MedicineDetails> list = DataProvider.getDB(getApp()).medicineDetailsDao().getById(id);
                if (list != null && list.size() ==1) {
                    medicineDetails = list.get(0);
                    populate();
                }
            }
        }

        actionModeCallback = new ActionModeCallback();
        currentActionMode = startActionMode(actionModeCallback);
    }

    TextView indecations;
    TextView adultDose;
    TextView chidDose;
    TextView contra;
    TextView sideEffects;
    TextView additional;

    Calculator calculator;
    void populate() {
        logging.d(TAG, "populate");
        setActionBarTitle(medicineDetails.getMedicinename());
        calculator = new Calculator(dataProvider, medicineDetails);
        indecations = findViewById(R.id.indications);
        adultDose = findViewById(R.id.adult_dose);
        chidDose = findViewById(R.id.child_dose);
        contra = findViewById(R.id.contra);
        sideEffects = findViewById(R.id.side_effects);
        additional = findViewById(R.id.additional);

        indecations.setText(medicineDetails.getIndications());
        adultDose.setText(medicineDetails.getAdultdose());
        chidDose.setText(medicineDetails.getPaediatricdose());
        contra.setText(medicineDetails.getContraindications());
        sideEffects.setText(medicineDetails.getSideeffects());
        additional.setText(medicineDetails.getAdditionalinformations());


    }


    private class ActionModeCallback implements ActionMode.Callback {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.setTitle(medicineDetails.getMedicinename());


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
