package com.rh.fieldguide.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rh.fieldguide.R;
import com.rh.fieldguide.data.primitives.Hospital;
import com.rh.fieldguide.data.primitives.HospitalBC;
import com.rh.fieldguide.data.primitives.HospitalHeader;
import com.rh.fieldguide.data.primitives.MedicineDetails;

import java.util.ArrayList;
import java.util.List;

public class HospitalAdapter extends RecyclerView.Adapter<HospitalViewHolder> {



    public interface OnItemClickListener {
        void onItemClicked(HospitalBC hospital);
    }
    final Context context;
    final List<HospitalBC> hospitals = new ArrayList<>();
    final LayoutInflater layoutInflater;
    final OnItemClickListener onItemClickListener;


    public HospitalAdapter(Context context, List<Hospital> hospitalList, OnItemClickListener onItemClickListener) {
        this.context = context;

        layoutInflater = LayoutInflater.from(context);
        this.onItemClickListener = onItemClickListener;
        populateList(hospitalList);
    }

    private final void populateList(List<Hospital> hospitalList) {
        String currentCounty = null;
        for (Hospital hospital : hospitalList) {
            if (currentCounty == null || !currentCounty.equals(hospital.getCounty())) {
                currentCounty = hospital.getCounty();
                hospitals.add(new HospitalHeader(currentCounty));
            }
            hospitals.add(hospital);
        }
    }

    @NonNull
    @Override
    public HospitalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.hospital_row,
                        parent, false);
        HospitalViewHolder hospitalViewHolder = new HospitalViewHolder(view);
        hospitalViewHolder.name = view.findViewById(R.id.name);
        return hospitalViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HospitalViewHolder hospitalViewHolder, int i) {
        HospitalBC hospitalBC = hospitals.get(i);
        hospitalViewHolder.name.setText(hospitalBC.getName());

        hospitalViewHolder.name.setTextAppearance(context, hospitalBC instanceof HospitalHeader ?
                R.style.HospitalHeaderStyle :  R.style.HospitalStyle);

        if (hospitalBC instanceof Hospital) {
            int shading  =i %2 == 0 ?  context.getResources().getColor(R.color.transparent):
                    context.getResources().getColor(R.color.light_tint);
            hospitalViewHolder.itemView.setBackgroundColor(shading);
        }
        hospitalViewHolder.bind(hospitalBC, onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return hospitals.size();
    }
}
