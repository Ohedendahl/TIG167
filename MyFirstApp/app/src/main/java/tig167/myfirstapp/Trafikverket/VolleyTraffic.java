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

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
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
        //listeners = new ArrayList<>();
        this.context = context;
    }






    public void getTrafficEvents() {

        RequestQueue queue = Volley.newRequestQueue(context);

        try
        {
            String requestXml = new String(Files.readAllBytes(Paths.get("TrafficEventsRequests.xml")));

            URL url = new URL(Settings.urlTrafficEvents);
            URLConnection con = url.openConnection();
            // specify that we will send output and accept input
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setConnectTimeout(20000);
            con.setReadTimeout(20000);
            con.setUseCaches (false);
            con.setDefaultUseCaches (false);
            // tell the web server what we are sending
            con.setRequestProperty ("Content-Type", "text/xml");
            OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
            writer.write(requestXml);
            writer.flush();
            writer.close();
            // reading the response
            InputStreamReader reader = new InputStreamReader(con.getInputStream());
            StringBuilder buf = new StringBuilder();
            char[] cbuf = new char[2048];
            int num;
            while ( -1 != (num=reader.read(cbuf)))
            {
                buf.append( cbuf, 0, num );
            }
            String result = buf.toString();
            //System.err.println( "\nResponse from server after POST:\n" + result);

            JSONObject obj = new JSONObject(result);
            JSONArray arr = obj.getJSONObject("RESPONSE").getJSONArray("RESULT");


            for(int i=0; i<arr.length();i++) {

                JSONObject obj2 = arr.getJSONObject(i);
                JSONArray situation = obj2.getJSONArray("Situation");

                for(int j=0; j<situation.length();j++) {

                    JSONObject obj3 = situation.getJSONObject(j);
                    JSONArray deviation = obj3.getJSONArray("Deviation");

                    for(int k=0; k<deviation.length();k++) {

                        JSONObject objects = deviation.getJSONObject(k);

                        //System.out.println(objects);
                        String messagetype = objects.optString("MessageType");
                        String message = objects.optString("Message");
                        String locationdescriptor = objects.optString("LocationDescriptor");
                        Log.d(LOG_TAG, "Beskrivning: " + message);
                        Log.d(LOG_TAG, "Typ: " + messagetype);
                        Log.d(LOG_TAG, "Plats: " + locationdescriptor);


                    }
                }
            }
        }
        catch(Throwable t)
        {
            t.printStackTrace(System.out);
        }



        // Add the request to the RequestQueue.
       // queue.add(stringRequest);
    }



    /******************************************
     HandelserChangeListener
     ******************************************/
    /*

    private List<HandelserChangeListener> listeners;

    public interface HandelserChangeListener {
        void onHandelserChangeList(List<Handelser> handelser);
    }

    public void addHandelserChangeListener(HandelserChangeListener l) {
        Log.d(LOG_TAG, "onResponse()    got some JSON");
        listeners.add(l);
    }

    */

}
