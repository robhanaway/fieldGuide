package com.rh.fieldguide.activities;

import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.rh.fieldguide.R;
import com.rh.fieldguide.data.Calculator;
import com.rh.fieldguide.data.DataProvider;
import com.rh.fieldguide.data.primitives.Calculation;
import com.rh.fieldguide.data.primitives.MedicineDetails;

import java.util.ArrayList;
import java.util.List;

public class CalculatorActivity extends BaseActivity {
    ActionModeCallback actionModeCallback;
    ActionMode currentActionMode;
    MedicineDetails medicineDetails;
    Calculator calculator;
    int dosageIndex = 0;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        actionModeCallback = new ActionModeCallback();
        currentActionMode = startActionMode(actionModeCallback);
        //Todo: get dosage index
        if (getIntent() != null) {
            int id = getIntent().getIntExtra(EXTRA_ID,-1);
            if (id != -1) {
                List<MedicineDetails> list = DataProvider.getDB(getApp()).medicineDetailsDao().getById(id);
                if (list != null && list.size() ==1) {
                    medicineDetails = list.get(0);
                    calculator = new Calculator(dataProvider, medicineDetails);
                    populate();
                }
            }
        }
    }


    TextView medicine;
    TextView route;
    TextView concentation;
    Spinner weight;
    Spinner age;
    void populate() {
        medicine = findViewById(R.id.medicine);
        medicine.setText(medicineDetails.getMedicinename() + getString(R.string.dosage_calculation));

        route = findViewById(R.id.route);
        route.setText(calculator.getDosageCalculations().get(dosageIndex).getDosage().getPaediatricdose());
        concentation = findViewById(R.id.concentration);
        concentation.setText(calculator.getDosageCalculations().get(dosageIndex).getDosage().getConcentration());

        weight = findViewById(R.id.weight);
        age = findViewById(R.id.age);

        List<Calculation> calculations = calculator.getDosageCalculations().get(dosageIndex).getCalculations();
        String[] weights = new String[calculations.size()];
        int index = 0;
        List<String> ages = new ArrayList<>();
        String currentAge = null;
        for (Calculation calculation : calculations) {
            weights[index++] = calculation.getWeight();
            if (currentAge == null || !currentAge.equals(calculation.getAge())) {
                currentAge = calculation.getAge();
                ages.add(currentAge);
            }
        }

        ArrayAdapter<String> weightAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, weights);

        weightAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weight.setAdapter(weightAdapter);

        String[] ageArray = new String[ages.size()];
        ages.toArray(ageArray);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, ageArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        age.setAdapter(adapter);

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
}
