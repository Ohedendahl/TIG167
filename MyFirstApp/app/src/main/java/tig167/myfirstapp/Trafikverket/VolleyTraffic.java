package tig167.myfirstapp.Trafikverket;

import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tig167.myfirstapp.Settings;

public class VolleyTraffic {

    private static final String LOG_TAG = VolleyTraffic.class.getName();

    private static VolleyTraffic VolleyTraffic;
    private Context context;

    public static synchronized VolleyTraffic getInstance(Context context) {
        if (VolleyTraffic == null) {
            VolleyTraffic = new VolleyTraffic(context);
        }

        return VolleyTraffic;
    }

    private VolleyTraffic(Context context) {
        listeners = new ArrayList<>();
        this.context = context;
    }


    private List<TrafficEvents> jsonToTrafficEvents(JSONArray array) {
        List<TrafficEvents> TrafficEventsList = new ArrayList<>();

        for(int i = 0; i < array.length(); i++) {
            try {



                TrafficEvents t = new TrafficEvents(messagetype, summary, location);
                TrafficEventsList.add(t);

            } catch (JSONException e) {
                ;
            } catch (MalformedURLException m) {
                m.printStackTrace();
            }
        }
        return TrafficEventsList;
    }

    public void getTrafficEvents() {
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Settings.urlTrafficEvents,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                })

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.POST,
                Settings.urlTrafficEvents,
                null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray array) {
                        Log.d(LOG_TAG, "onResponse()    got some JSON");
                        List<TrafficEvents> TrafficEvents = jsonToTrafficEvents(array);
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
