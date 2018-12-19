package tig167.myfirstapp;

public class Settings {

    private String stad = "Göteborg";

    public void setStad (String newStad) {
        stad = newStad;
    }


    public String urlPolice = "https://polisen.se/api/events?locationname=" + stad;

    public static final String urlTrafficEvents = "http://api.trafikinfo.trafikverket.se/v1.3/data.json";
}