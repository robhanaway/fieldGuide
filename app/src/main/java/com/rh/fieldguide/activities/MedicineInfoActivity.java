package com.rh.fieldguide.activities;

import android.os.Bundle;
import android.widget.TextView;

import com.rh.fieldguide.R;
import com.rh.fieldguide.data.DataProvider;
import com.rh.fieldguide.data.primitives.MedicineDetails;

import java.util.List;

public class MedicineInfoActivity extends BaseActivity {
    final static String TAG = MedicineInfoActivity.class.getSimpleName();
    public final static String EXTRA_ID = "id";
    MedicineDetails medicineDetails;
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
    }

    TextView indecations;
    TextView adultDose;
    TextView chidDose;
    TextView contra;
    TextView sideEffects;
    TextView additional;

    void populate() {
        logging.d(TAG, "populate");
        setActionBarTitle(medicineDetails.getMedicinename());
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
}
