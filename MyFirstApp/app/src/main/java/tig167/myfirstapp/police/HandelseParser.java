package tig167.myfirstapp.police;

import java.util.List;
import java.util.ArrayList;
import org.json.*;
import org.json.JSONArray;
import org.json.JSONObject;

public class HandelseParser {

  public static List<Handelser> jsonToHandelser(String json) throws JSONException {

    JSONArray jsonArray = new JSONArray(json);
    List<Handelser> handelser = new ArrayList<>();

    for(int i = 0; i < jsonArray.length(); i++) {

      JSONObject jsonObject = jsonArray.getJSONObject(i);
      JSONObject nestedlocation = jsonObject.getJSONObject("location");

      String locationName = nestedlocation.getString("name");
      String datetime = jsonObject.getString("datetime");
      String summary = jsonObject.getString("summary");
      String url = jsonObject.getString("url");

      handelser.add(new Handelser(datetime, summary, url, locationName));

    }
    return handelser;
  }

}
