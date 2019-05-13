package com.rh.fieldguide.data.primitives;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "medicineclinic")
public class MedicineClinic {
    @PrimaryKey(autoGenerate = true)
    private
    int _id;

    private int clinicallevelid;

    private int medclinid;

    private int medicinedetailid;


    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getClinicallevelid() {
        return clinicallevelid;
    }

    public void setClinicallevelid(int clinicallevelid) {
        this.clinicallevelid = clinicallevelid;
    }

    public int getMedclinid() {
        return medclinid;
    }

    public void setMedclinid(int medclinid) {
        this.medclinid = medclinid;
    }

    public int getMedicinedetailid() {
        return medicinedetailid;
    }

    public void setMedicinedetailid(int medicinedetailid) {
        this.medicinedetailid = medicinedetailid;
    }
}
