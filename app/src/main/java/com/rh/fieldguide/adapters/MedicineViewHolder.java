package com.rh.fieldguide.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.rh.fieldguide.data.primitives.MedicineDetails;

public class MedicineViewHolder extends RecyclerView.ViewHolder {
    public TextView name;
    public TextView indications;
    MedicineDetails medicineDetails;
    MedicineAdapter.OnItemClickListener onItemClickListener;
    public MedicineViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public void bind(MedicineDetails details, MedicineAdapter.OnItemClickListener clickListener) {
        this.medicineDetails = details;
        this.onItemClickListener = clickListener;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClicked(medicineDetails);
            }
        });

    }
}
