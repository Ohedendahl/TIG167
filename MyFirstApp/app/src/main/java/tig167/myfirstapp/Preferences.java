package tig167.myfirstapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Preferences extends AppCompatActivity {

    private Preferences me;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        final CheckBox checkBoxPress = (CheckBox) findViewById(R.id.pressCheck);
        final CheckBox checkBoxPolice = (CheckBox) findViewById(R.id.polisenCheck);
        final CheckBox checkBoxTraffic = (CheckBox) findViewById(R.id.trafikinfoCheck);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("Preferences", 0);
        SharedPreferences.Editor prefEditor = pref.edit();

        Spinner s = (Spinner) findViewById(R.id.spinner2);
        int positionSpinner = s.getSelectedItemPosition();
        Intent intent = getIntent();
        TextView textView = findViewById(R.id.info_settings);

        List<String> targetList = new ArrayList<>();
        targetList.add(0, "Göteborg");
        targetList.add(1, "Stockholm");
        targetList.add(2, "Malmö");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, targetList);
        s.setAdapter(adapter);
        s.setSelection(pref.getInt("valSpinner",0));


        String stadSpinner = s.getSelectedItem().toString();
        Log.d("Här är stad från Spinner", stadSpinner);


        prefEditor.putString("stad", stadSpinner); //kom ihåg spinner
        prefEditor.putString("policeURL", "https://polisen.se/api/events?locationname=" + stadSpinner);

  //      prefEditor.putBoolean("polisen", true);
//        prefEditor.putBoolean("trafik", true);



        prefEditor.apply();


        checkBoxPress.setChecked(pref.getBoolean("pressbox", false));
        checkBoxPolice.setChecked(pref.getBoolean("polisbox", false));
        checkBoxTraffic.setChecked(pref.getBoolean("trafikbox", false));




    }

    public void saveSettings(View view){


        final CheckBox checkBoxPress = (CheckBox) findViewById(R.id.pressCheck);
        final CheckBox checkBoxPolice = (CheckBox) findViewById(R.id.polisenCheck);
        final CheckBox checkBoxTraffic = (CheckBox) findViewById(R.id.trafikinfoCheck);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("Preferences", 0);
        SharedPreferences.Editor prefEditor = pref.edit();
        Spinner s = (Spinner) findViewById(R.id.spinner2);
        int positionSpinner = s.getSelectedItemPosition();
        prefEditor.putInt("valSpinner",positionSpinner);
        String stadSpinner = s.getSelectedItem().toString();
        prefEditor.putString("stad", stadSpinner);

        if (checkBoxPress.isChecked()) {
            prefEditor.putBoolean("pressbox", true);
        }
        else {
            prefEditor.putBoolean("pressbox", false);
        }

        if (checkBoxPolice.isChecked()) {
            prefEditor.putBoolean("polisbox", true);
        }
        else {
            prefEditor.putBoolean("polisbox", false);
        }

        if (checkBoxTraffic.isChecked()) {
            prefEditor.putBoolean("trafikbox", true);
        }
        else {
            prefEditor.putBoolean("trafikbox", false);
        }

        prefEditor.apply();

        me = this;
        Toast.makeText(me, "Inställningar sparade", Toast.LENGTH_SHORT).show();
   }

}

