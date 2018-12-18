package tig167.myfirstapp;

public class Settings {

    public static String stad = "Göteborg";

    public void setStad (String stad) {
        this.stad = stad;
    }


    public static final String urlPolice = "https://polisen.se/api/events?locationname=" + stad;

    public static final String urlTrafficEvents = "http://api.trafikinfo.trafikverket.se/v1.3/data.json";
}
