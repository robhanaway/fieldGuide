package com.rh.fieldguide.data;

import com.rh.fieldguide.data.primitives.Calculation;
import com.rh.fieldguide.data.primitives.Dosage;
import com.rh.fieldguide.data.primitives.MedicineDetails;

import java.util.List;

public class DosageCalculation {
    private final MedicineDetails medicineDetails;
    private final Dosage dosage;
    private final List<Calculation> calculations;

    public DosageCalculation(MedicineDetails medicineDetails, Dosage dosage, List<Calculation> calculations) {
        this.medicineDetails = medicineDetails;
        this.dosage = dosage;
        this.calculations = calculations;
    }

    public MedicineDetails getMedicineDetails() {
        return medicineDetails;
    }

    public Dosage getDosage() {
        return dosage;
    }

    public List<Calculation> getCalculations() {
        return calculations;
    }
}
