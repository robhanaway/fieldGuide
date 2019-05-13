package com.rh.fieldguide.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rh.fieldguide.R;
import com.rh.fieldguide.data.primitives.MedicineDetails;

import java.util.List;

public class MedicineAdapter extends RecyclerView.Adapter<MedicineViewHolder> {

    public interface OnItemClickListener {
        void onItemClicked(MedicineDetails medicineDetails);
    }
    final Context context;
    List<MedicineDetails> medicineDetails;
    final LayoutInflater layoutInflater;
    final OnItemClickListener onItemClickListener;
    public MedicineAdapter(Context context, List<MedicineDetails> medicineDetails, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.medicineDetails = medicineDetails;
        layoutInflater = LayoutInflater.from(context);
        this.onItemClickListener = onItemClickListener;
    }


    public void setMedicineList(List<MedicineDetails> medicineDetails) {
        this.medicineDetails = medicineDetails;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MedicineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.medicine_row,
                        parent, false);
        MedicineViewHolder medicineViewHolder = new MedicineViewHolder(view);
        medicineViewHolder.name = view.findViewById(R.id.name);
        medicineViewHolder.indications = view.findViewById(R.id.indications);

        return medicineViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MedicineViewHolder medicineViewHolder, int i) {
        MedicineDetails details = medicineDetails.get(i);
        medicineViewHolder.bind(details, onItemClickListener);
        int shading  =i %2 == 0 ?  context.getResources().getColor(R.color.transparent):
                context.getResources().getColor(R.color.light_tint);
        medicineViewHolder.itemView.setBackgroundColor(shading);
        medicineViewHolder.name.setText(details.getMedicinename());
        medicineViewHolder.indications.setText(details.getIndications());
    }

    @Override
    public int getItemCount() {
        return medicineDetails.size();
    }

    public MedicineDetails getByIndex(int index) {
        return medicineDetails.get(index);
    }
}
