package com.rh.fieldguide.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.rh.fieldguide.data.Calculator;

public class DosageAdapter extends RecyclerView.Adapter<DosageViewHolder> {
    final Context context;
    final Calculator calculator;

    public DosageAdapter(Context context, Calculator calculator) {
        this.context = context;
        this.calculator = calculator;
    }

    @NonNull
    @Override
    public DosageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull DosageViewHolder dosageViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return calculator.getDosageCalculations().size();
    }
}
