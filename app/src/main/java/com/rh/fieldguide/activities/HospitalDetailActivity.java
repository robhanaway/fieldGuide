package com.rh.fieldguide.activities;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.text.util.Linkify;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.rh.fieldguide.R;
import com.rh.fieldguide.data.primitives.Hospital;

import java.util.List;

public class HospitalDetailActivity extends BaseActivity implements View.OnClickListener {
    final static String TAG = HospitalDetailActivity.class.getSimpleName();

    Hospital hospital;
    ActionModeCallback actionModeCallback;
    ActionMode currentActionMode;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_detail);

        if (getIntent() != null) {
            int id = getIntent().getIntExtra(EXTRA_ID,-1);
            if (id != -1) {
                List<Hospital> list = dataProvider.hospitalsDao().getById(id);
                if (list != null && list.size() ==1) {
                    hospital = list.get(0);
                    populate();
                }
            }
        }

        actionModeCallback = new ActionModeCallback();
        currentActionMode = startActionMode(actionModeCallback);
    }


    TextView main;
    TextView er;
    void populate() {
        logging.d(TAG, "populate");
        main = findViewById(R.id.main);

        SpannableString content = new SpannableString(hospital.getMain());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        main.setText(content);
        main.setOnClickListener(this);

        er = findViewById(R.id.er);

        content = new SpannableString(hospital.getEmergency());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        er.setText(content);
        er.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String number = v instanceof TextView ? TextView.class.cast(v).getText().toString() : null;
        if (number != null) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse(number.replace("(","").replace(")","").replace(" ","")));
            callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            try {
                startActivity(callIntent);
            } catch (Exception e) {
                logging.e(TAG, e, "onClick");
            }
        }
    }


    private class ActionModeCallback implements ActionMode.Callback {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.setTitle(hospital.getName());

            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {

            return true;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

            return false;


        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            finish();

        }
    }
}
