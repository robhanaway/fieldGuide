package com.rh.fieldguide.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.rh.fieldguide.R;

public class WeightSpinnerAdapter extends ArrayAdapter<String> {
    final LayoutInflater layoutInflater;
    public WeightSpinnerAdapter(@NonNull Context context, @NonNull String[] objects) {
        super(context, R.layout.weight_row, objects);
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View result = layoutInflater.inflate(R.layout.weight_row, parent,false);
        TextView.class.cast(result).setText(getItem(position));
        return result;
    }
}
