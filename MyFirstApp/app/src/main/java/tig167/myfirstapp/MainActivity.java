package tig167.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.app.Activity;
import android.widget.ExpandableListView;

public class MainActivity extends Activity {


    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);
    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Pressmeddelanden");
        listDataHeader.add("Polisen");
        listDataHeader.add("Trafikinfo");

        // Adding child data
        List<String> pressmeddelanden = new ArrayList<String>();
        pressmeddelanden.add("Löfven somnar på jobbet");
        pressmeddelanden.add("Gratis bira i stadshuset");
        pressmeddelanden.add("Håkan Hellström utsedd till tronarvinge; 'fan va soft'");


        List<String> polisen = new ArrayList<String>();
        polisen.add("Inbrott Dalheimersgatan");
        polisen.add("Gangsterz på Hisingen (som vanligt)");


        List<String> trafikinfo = new ArrayList<String>();
        trafikinfo.add("Olycka på Älvsborgsbron");
        trafikinfo.add("Krock skapar köer på Avenyn");


        listDataChild.put(listDataHeader.get(0), pressmeddelanden); // Header, Child data
        listDataChild.put(listDataHeader.get(1), polisen);
        listDataChild.put(listDataHeader.get(2), trafikinfo);
    }

    public void gotoSettings(View view) {
        Intent intent = new Intent(this, Preferences.class);
      //  EditText editText = (EditText) findViewById(R.id.editText);
      //  String message = editText.getText().toString();
      //  intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

}
