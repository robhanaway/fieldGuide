package com.rh.fieldguide.data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rh.fieldguide.R;
import com.rh.fieldguide.data.primitives.Hospital;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class csvExtract {
    List<Hospital> hospitalList = new ArrayList<>();
    public void run(Context context) {
        int id = 0;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(context.getResources().openRawResource(R.raw.hospitalss)));
        String county = null;
        String line = null;

        do {

            try {
                line = bufferedReader.readLine();
                if (line != null) {
                    String[] fields = line.split(",");
                    if (fields.length >= 4) {
                        id++;
                        Hospital hospital = new Hospital();
                        hospital.set_id(id);
                        hospital.setCounty(county);
                        int offset = fields.length - 4;
                        String name = offset == 0 ? fields[0] : fields[0] + fields[1];
                        hospital.setName(name.replace("_",""));
                        hospital.setMain(fields[1 + offset].replace("_",""));
                        hospital.setEmergency(fields[2 + offset].replace("_",""));
                        hospital.setCode(fields[3 + offset].replace("_",""));
                        hospitalList.add(hospital);
                    } else if (fields.length == 1) {
                        county = fields[0].replace(",","").replace("_","");
                        System.out.println("COUNTY " +county);
                    } else {
                        System.out.println("Error " + line);
                    }
                }
            } catch (IOException e) {

            }
        } while (line != null);
        System.out.print(id);
        store();
    }

    void store() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        for (Hospital hospital : hospitalList) {
            DatabaseReference reference = firebaseDatabase.getReference("hospitals").child(String.valueOf(hospital.get_id()));

            Map<String,Object> values = new HashMap<>();
            values.put("id", hospital.get_id());
            values.put("county", hospital.getCounty());
            values.put("name", hospital.getName());
            values.put("main", hospital.getMain());
            values.put("er", hospital.getEmergency());
            values.put("er_other", "");
            values.put("code", hospital.getCode());
            reference.updateChildren(values, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                    databaseReference.push();
                }
            });

        }

    }
}
