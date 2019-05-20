package com.rh.fieldguide.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.rh.fieldguide.R;
import com.rh.fieldguide.data.Calculator;
import com.rh.fieldguide.data.DataProvider;
import com.rh.fieldguide.data.DosageCalculation;
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
//        currentActionMode = startActionMode(actionModeCallback);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    TextView indecations;
    TextView adultDose;
    TextView chidDose;
    TextView contra;
    TextView sideEffects;
    TextView additional;

    View calculation;
    Calculator calculator;
    void populate() {
        logging.d(TAG, "populate");
        setActionBarTitle(medicineDetails.getMedicinename());
        calculator = new Calculator(getDataProvider(), medicineDetails);
        calculation = findViewById(R.id.calculation);
        calculation.setVisibility(calculator.available() ? View.VISIBLE : View.GONE);

        calculation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchCalculation();
            }
        });

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


    void launchCalculation() {
        if (calculator.getDosageCalculations().size() == 1 ) {
            startCalculationIntent(medicineDetails.get_id(), 0);

        } else {
            showSelections();
        }
    }

    void startCalculationIntent(int medicineId, int dosageIndex) {
        Intent intent = new Intent(getApplicationContext(),CalculatorActivity.class) ;
        intent.putExtra(EXTRA_ID, medicineId);
        intent.putExtra(CalculatorActivity.EXTRA_DOSAGE_INDEX, dosageIndex);
        startActivity(intent);
    }

    void showSelections() {

        String[] routes = new String[calculator.getDosageCalculations().size()];
        int index = 0;
        for (DosageCalculation calculation : calculator.getDosageCalculations()) {
            routes[index++] = calculation.getDosage().getPaediatricdose();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select a Route");
        builder.setItems(routes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startCalculationIntent(medicineDetails.get_id(), which);
            }
        });
        builder.show();
    }

    private class ActionModeCallback implements ActionMode.Callback {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.setTitle(medicineDetails.getMedicinename());
            analyticsProvider.logMedicationViewed(medicineDetails.getMedicinename());

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
