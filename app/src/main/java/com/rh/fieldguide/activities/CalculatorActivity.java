package com.rh.fieldguide.activities;

import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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

public class CalculatorActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {
    public final static String EXTRA_DOSAGE_INDEX = "dosageindex";

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
                dosageIndex = getIntent().getIntExtra(EXTRA_DOSAGE_INDEX,0);
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
    TextView mg;
    TextView ml;
    void populate() {
        medicine = findViewById(R.id.medicine);
        medicine.setText(medicineDetails.getMedicinename() + getString(R.string.dosage_calculation));

        mg = findViewById(R.id.calculation_mg);
        ml = findViewById(R.id.calculation_ml);
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

        weightAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, weights);

        weightAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weight.setAdapter(weightAdapter);

        String[] ageArray = new String[ages.size()];
        ages.toArray(ageArray);
        ageAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, ageArray);

        ageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        age.setAdapter(ageAdapter);
        calculate();

        weight.setOnItemSelectedListener(this);
        age.setOnItemSelectedListener(this);

    }

    ArrayAdapter<String> weightAdapter;
    ArrayAdapter<String> ageAdapter;

    void calculate() {
        String weightText = weight.getSelectedItem().toString();

        Calculation calculation = calculator.find(dosageIndex,  weightText);
        if (calculation != null) {
            populateCalculation(calculation);
        }
    }


    void populateCalculation(Calculation calculation) {
        ml.setText(calculation.getMl());
        mg.setText(calculation.getMg());
    }




    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            if (parent.getId() == R.id.weight) {
                calculate();
//                moveAge();
            } else {
                moveWeight();
            }


    }

    void moveAge() {
        String weightText = weight.getSelectedItem().toString();

        for (Calculation calculation :calculator.getDosageCalculations().get(dosageIndex).getCalculations()) {
            if (calculation.getWeight().equals(weightText)) {
                int index = ageAdapter.getPosition(calculation.getAge());
                if (index != -1) {

                    age.setSelection(index);

                }
                break;
            }
        }
    }

    void moveWeight() {
        String ageText = age.getSelectedItem().toString();
        int index = 0;
        for (Calculation calculation :calculator.getDosageCalculations().get(dosageIndex).getCalculations()) {
            if (calculation.getAge().equals(ageText)) {

                weight.setSelection(index);

                break;
            }
            index ++;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
