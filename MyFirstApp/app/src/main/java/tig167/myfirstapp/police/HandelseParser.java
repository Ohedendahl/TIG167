package tig167.myfirstapp.police;

import java.util.List;
import java.util.ArrayList;
import org.json.*;
import org.json.JSONArray;
import org.json.JSONObject;

public class HandelseParser {

  public static List<Handelser> parse(String json) throws JSONException {

    JSONArray jsonArray = new JSONArray(json);
    List<Handelser> handelser = new ArrayList<>();

    for(int i = 0; i < jsonArray.length(); i++) {

      JSONObject jsonObject = jsonArray.getJSONObject(i);
      JSONObject nestedlocation = jsonObject.getJSONObject("location");

      String locationName = nestedlocation.getString("name");
      String datetime = jsonObject.getString("datetime");
      String summary = jsonObject.getString("summary");
      String url = jsonObject.getString("url");


      /*JSONArray foodsJson = (JSONArray)jsonObject.get("food_pairing");
      ArrayList<String> foodStrings = new ArrayList<>();

      for(int j = 0; j < foodsJson.length(); j++) {
        foodStrings.add(foodsJson.getString(j));
      }*/

      handelser.add(new Handelser(datetime, summary, url, locationName));

    }
    return handelser;
  }

}
