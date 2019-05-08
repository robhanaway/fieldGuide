package com.rh.fieldguide.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.rh.fieldguide.R;
import com.rh.fieldguide.activities.MedicineInfoActivity;
import com.rh.fieldguide.adapters.MedicineAdapter;
import com.rh.fieldguide.data.DataProvider;
import com.rh.fieldguide.data.primitives.MedicineDetails;

public class MedicineFragment extends BaseFragment implements MedicineAdapter.OnItemClickListener{
    RecyclerView recyclerView;
    MedicineAdapter medicineAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_medicines, container, false);
        recyclerView = view.findViewById(R.id.medicines);
        load();
        return view;
    }

    void load() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getParent().getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getParent(), LinearLayoutManager.VERTICAL));
        medicineAdapter = new MedicineAdapter(getParent(), DataProvider.getDB(getApp()).medicineDetailsDao().getAll(), this);

        recyclerView.setAdapter(medicineAdapter);



    }


    @Override
    public void onItemClicked(MedicineDetails medicineDetails) {
        Intent intent = new Intent(getApp(), MedicineInfoActivity.class);
        intent.putExtra(MedicineInfoActivity.EXTRA_ID, medicineDetails.get_id());
        getParent().startActivity(intent);
    }
}