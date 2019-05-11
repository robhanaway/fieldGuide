package com.rh.fieldguide.data;

import com.rh.fieldguide.data.primitives.Calculation;
import com.rh.fieldguide.data.primitives.Dosage;
import com.rh.fieldguide.data.primitives.MedicineDetails;

import java.util.List;

public class DosageCalculation {
    final MedicineDetails medicineDetails;
    final Dosage dosage;
    final List<Calculation> calculations;

    public DosageCalculation(MedicineDetails medicineDetails, Dosage dosage, List<Calculation> calculations) {
        this.medicineDetails = medicineDetails;
        this.dosage = dosage;
        this.calculations = calculations;
    }
}
