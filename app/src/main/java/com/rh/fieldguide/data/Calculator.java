package com.rh.fieldguide.data;

import android.text.TextUtils;

import com.rh.fieldguide.data.primitives.Calculation;
import com.rh.fieldguide.data.primitives.Dosage;
import com.rh.fieldguide.data.primitives.MedicineDetails;

import java.util.ArrayList;
import java.util.List;

public class Calculator {
    final DataProvider dataProvider;
    final MedicineDetails medicineDetails;

    private List<DosageCalculation> dosageCalculations;
    public Calculator(DataProvider dataProvider, MedicineDetails medicineDetails) {
        this.dataProvider = dataProvider;
        this.medicineDetails = medicineDetails;
        loadCalculations();
    }

    private void loadCalculations() {
        List<Dosage> dosages = dataProvider.dosageDao().getByMedicineId(medicineDetails.getMdcid());

        if (dosages != null && !dosages.isEmpty()) {
            dosageCalculations = new ArrayList<>();

            for (Dosage dosage : dosages) {
                List<Calculation> calculations = dataProvider.calculationDao().getByDosageId(dosage.getDosageid());
                if (calculations != null && !calculations.isEmpty()) {
                    dosageCalculations.add(new DosageCalculation(medicineDetails, dosage, calculations));
                }
            }
        }
    }

    public boolean available() {
        return  dosageCalculations != null;
    }

    public List<DosageCalculation> getDosageCalculations() {
        return dosageCalculations;
    }

    public Calculation find(int dosageIndex,  String weight) {
        if (!TextUtils.isEmpty(weight)) {
            for (Calculation calculation : dosageCalculations.get(dosageIndex).getCalculations()) {
                if (calculation.getWeight().equals(weight)) {
                    return calculation;
                }
            }
        }
        return null;
    }
}
