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
    SharedPreferences pref;
    SharedPreferences.Editor prefEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        final CheckBox checkBoxPolice = findViewById(R.id.polisenCheck);
        final CheckBox checkBoxTraffic = findViewById(R.id.trafikinfoCheck);

        pref = getApplicationContext().getSharedPreferences("Preferences", 0);

        Spinner s = findViewById(R.id.spinner2);

        List<String> targetList = new ArrayList<>();
        targetList.add(0, getString(R.string.goteborg_header));
        targetList.add(1, getString(R.string.stockholm_header));
        targetList.add(2, getString(R.string.malmo_header));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, targetList);

        s.setAdapter(adapter);
        s.setSelection(pref.getInt("valSpinner",0));

        checkBoxPolice.setChecked(pref.getBoolean("polisbox", false));
        checkBoxTraffic.setChecked(pref.getBoolean("trafikbox", false));

    }

    public void saveSettings(View view){

        pref = getApplicationContext().getSharedPreferences("Preferences", 0);
        prefEditor = pref.edit();

        final CheckBox checkBoxPolice = findViewById(R.id.polisenCheck);
        final CheckBox checkBoxTraffic = findViewById(R.id.trafikinfoCheck);
        String stadURL = "Göteborg";

        Spinner s = findViewById(R.id.spinner2);
        int positionSpinner = s.getSelectedItemPosition();

        prefEditor.putInt("valSpinner",positionSpinner);

        if (positionSpinner == 0) {
            stadURL = "Göteborg";
        }

        if (positionSpinner == 1) {
            stadURL = "Stockholm";
        }

        if (positionSpinner == 2) {
            stadURL = "Malmö";
        }

        prefEditor.putString("policeURL", "https://polisen.se/api/events?locationname=" + stadURL);

        String rubrikStad = s.getSelectedItem().toString();
        prefEditor.putString("stad", rubrikStad);


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
        Toast.makeText(me, me.getResources().getString(R.string.toast_settings_saved), Toast.LENGTH_SHORT).show();
   }


}

