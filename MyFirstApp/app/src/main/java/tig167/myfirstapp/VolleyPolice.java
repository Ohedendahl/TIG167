package tig167.myfirstapp;

import tig167.myfirstapp.police.Handelser;
import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class VolleyPolice {

    private static final String LOG_TAG = VolleyPolice.class.getName();

    private static VolleyPolice VolleyPolice;
    private Context context;

    public static synchronized VolleyPolice getInstance(Context context) {
        if (VolleyPolice == null) {
            VolleyPolice = new VolleyPolice(context);
        }

        return VolleyPolice;
    }

    private VolleyPolice(Context context) {
        listeners = new ArrayList<>();
        this.context = context;
    }


    private List<Handelser> jsonToHandelser(JSONArray array) {
        List<Handelser> handelserList = new ArrayList<>();
        for(int i = 0; i < array.length(); i++) {
            try {
            JSONObject row = array.getJSONObject(i);
            JSONObject nestedLocation = row.getJSONObject("location");

            String locationName = nestedLocation.getString("name");
            String datetime = row.getString("datetime");
            String summary = row.getString("summary");
            //String url = row.getString("url");
            URL policeURL = new URL(row.getString("url"));


            Handelser h = new Handelser(datetime, summary, policeURL, locationName);
            handelserList.add(h);

            } catch (JSONException e) {
                ;
           } catch (MalformedURLException m) {
                m.printStackTrace();
            }
        }
        return handelserList;
    }
    // The code below is "slightly" (nudge nudge) based on:
    //   https://developer.android.com/training/volley/request.html

    public void getHandelser() {
        RequestQueue queue = Volley.newRequestQueue(context);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                Settings.url,
                null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray array) {
                        Log.d(LOG_TAG, "onResponse()    got some JSON");
                        List<Handelser> handelser = jsonToHandelser(array);
                        for (HandelserChangeListener m : listeners) {
                            Log.d(LOG_TAG, "onResponse()    call any vegetable");
                            m.onHandelserChangeList(handelser);
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(LOG_TAG, " cause: " + error.getCause());
                for (HandelserChangeListener m : listeners) {
                    Log.d(LOG_TAG, "onResponse()    call any vegetable with NULL");
                    m.onHandelserChangeList(null);
                }
            }
        });

        // Add the request to the RequestQueue.
        queue.add(jsonArrayRequest);
    }

    /******************************************
     HandelserChangeListener
     ******************************************/

    private List<HandelserChangeListener> listeners;

    public interface HandelserChangeListener {
        void onHandelserChangeList(List<Handelser> handelser);
    }

    public void addHandelserChangeListener(HandelserChangeListener l) {
        Log.d(LOG_TAG, "onResponse()    got some JSON");
        listeners.add(l);
    }



































}
