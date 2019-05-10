package com.rh.fieldguide.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rh.fieldguide.R;
import com.rh.fieldguide.activities.HospitalActivity;

public class HomeFragment extends BaseFragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        applyNavigation(view);
        return view;
    }

    void applyNavigation(View view) {
        view.findViewById(R.id.hospitals).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.hospitals:
                launchHospitals();
                break;
        }
    }


    void launchHospitals() {

        getParent().startActivity(new Intent(getParent(), HospitalActivity.class));
    }
}
