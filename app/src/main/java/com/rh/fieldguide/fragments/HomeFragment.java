package com.rh.fieldguide.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rh.fieldguide.R;
import com.rh.fieldguide.activities.HospitalActivity;

public class HomeFragment extends BaseFragment implements View.OnClickListener {

    public interface OnItemSelectedListener {
        void onHospitals();
        void onMedications();
    }

    OnItemSelectedListener onItemSelectedListener;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        applyNavigation(view);
        return view;
    }

    void applyNavigation(View view) {
        view.findViewById(R.id.hospitals).setOnClickListener(this);
        view.findViewById(R.id.medications).setOnClickListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onItemSelectedListener = (OnItemSelectedListener)context;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.hospitals:
                onItemSelectedListener.onHospitals();
                break;
            case R.id.medications:
                onItemSelectedListener.onMedications();
                break;
        }
    }

}
