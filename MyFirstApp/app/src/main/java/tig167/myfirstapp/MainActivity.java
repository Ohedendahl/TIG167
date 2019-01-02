package tig167.myfirstapp;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tig167.myfirstapp.Trafikverket.VolleyTraffic;
import tig167.myfirstapp.police.Handelser;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observer;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import tig167.myfirstapp.VolleyPolice.HandelserChangeListener;
import tig167.myfirstapp.VolleyPolice;



public class MainActivity extends Activity {


    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private ArrayAdapter<Handelser> adapter;
    private List<Handelser> handelser;
    private MainActivity me;

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("Preferences", 0);
        SharedPreferences.Editor prefEditor = pref.edit();

        Boolean poliusenOnOff = pref.getBoolean("polisen", false);
        Boolean trafikOnOff = pref.getBoolean("trafik", true);


        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        expListView.setAdapter(listAdapter);

        handelser = new ArrayList<>();

        me = this;

        // register to listen to member updates in VolleyMember
        VolleyPolice.getInstance(this).addHandelserChangeListener(new HandelserChangeListener() {
            @Override
            public void onHandelserChangeList(List<Handelser> handelser) {
                if (handelser==null) {
                    Log.d(LOG_TAG, "   Failed to fetch JSON");
                    Toast.makeText(me, "Inget svar hos Polisen, försök igen.", Toast.LENGTH_LONG).show();
                    return;
                }

                List <String> polisgrejer = new ArrayList<>();
                for (Handelser h : handelser) {
                    polisgrejer.add(h.toString());
                }
                //Log.d(LOG_TAG, "onHandelserChangeList()   Wowie Zowie:  " + handelser);
               try {
                    listDataChild.put(listDataHeader.get(0), polisgrejer);
                } catch ( IndexOutOfBoundsException e ) {
                   Log.d(LOG_TAG, "något var fel med index: " + e);
                }

                listAdapter = new ExpandableListAdapter(MainActivity.this, listDataHeader, listDataChild);
                expListView.setAdapter(listAdapter);

                ActivitySwitcher.showToast(me, "Händelser uppdaterade");
            }
        });
        TextView headerText = (TextView) findViewById(R.id.text_header);
        headerText.setText(pref.getString("stad", "ingen stad hittades"));



        if (!pref.getBoolean("polisbox", false)) {
            listDataHeader.remove("Polisen");
        }

        if (!pref.getBoolean("trafikbox", false)) {
            listDataHeader.remove("Trafikinfo");
        }

    }


    private void prepareListData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("Preferences", 0);
        SharedPreferences.Editor prefEditor = pref.edit();

        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        listDataHeader.add("Polisen");
        listDataHeader.add("Trafikinfo");

        List<String> polisen = new ArrayList<String>();
        polisen.add("Ingen data att visa");

        List<String> trafikinfo = new ArrayList<String>();
        trafikinfo.add("Olycka på Älvsborgsbron");
        trafikinfo.add("Krock skapar köer på Avenyn");


        listDataChild.put(listDataHeader.get(0), polisen);
        listDataChild.put(listDataHeader.get(1), trafikinfo);

    }

    public void gotoSettings(View view) {
        Intent intent = new Intent(this, Preferences.class);
        startActivity(intent);
    }


    public void refreshList(View view) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("Preferences", 0);
        SharedPreferences.Editor prefEditor = pref.edit();

       if (pref.getBoolean("polisbox", false)) {
            ActivitySwitcher.showToast(this, "Uppdaterar händelser, var god vänta");
            VolleyPolice.getInstance(this).getHandelser();
        }   else {
            ActivitySwitcher.showToast(this, "Inget att uppdatera");
        }

    }
}
