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

import com.rh.fieldguide.R;
import com.rh.fieldguide.activities.HospitalDetailActivity;
import com.rh.fieldguide.activities.MedicineInfoActivity;
import com.rh.fieldguide.adapters.HospitalAdapter;
import com.rh.fieldguide.data.primitives.Hospital;
import com.rh.fieldguide.data.primitives.HospitalBC;

public class HospitalFragment extends BaseFragment  implements HospitalAdapter.OnItemClickListener{
    HospitalAdapter hospitalAdapter;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hospitals, container, false);
        recyclerView = view.findViewById(R.id.hospitals);
        load();
        return view;
    }

    void load() {
        hospitalAdapter = new HospitalAdapter(getApp(), dataProvider.hospitalsDao().getAll(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApp()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getApp(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(hospitalAdapter);
    }

    @Override
    public void onItemClicked(HospitalBC hospital) {
        if (hospital instanceof Hospital) {
            Intent intent = new Intent(getApp(), HospitalDetailActivity.class);
            intent.putExtra(MedicineInfoActivity.EXTRA_ID, hospital.get_id());
            startActivity(intent);
        }
    }
}
