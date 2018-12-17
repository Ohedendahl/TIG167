package tig167.myfirstapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.os.Bundle;

public class Preferences extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        Intent intent = getIntent();
        TextView textView = findViewById(R.id.info_settings);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("Preferences", 0);
        SharedPreferences.Editor editor = pref.edit();

        //exmpel nedan
        editor.putString("Stad", "Göteborg");
        editor.putBoolean("Pressmeddelanden", true);

        editor.commit();

        String[] arraySpinner = new String[] {
                "Göteborg", "Stockholm", "Malmö"
        };
        Spinner s = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.browser_link_context_header, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.browser_link_context_header);
        s.setAdapter(adapter);
    }

    }


