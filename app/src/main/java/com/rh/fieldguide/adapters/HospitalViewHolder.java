package com.rh.fieldguide.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.rh.fieldguide.data.primitives.HospitalBC;

public class HospitalViewHolder extends RecyclerView.ViewHolder {
    HospitalAdapter.OnItemClickListener onItemClickListener;
    HospitalBC hospitalBC;
    TextView name;
    public HospitalViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public void bind(final HospitalBC hospitalBC, final HospitalAdapter.OnItemClickListener onItemClickListener) {
        this.hospitalBC = hospitalBC;
        this.onItemClickListener = onItemClickListener;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClicked(hospitalBC);
            }
        });

    }
}
