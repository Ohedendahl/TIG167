package tig167.myfirstapp;

public class Settings {

    public static String stad;

    public void setStad (String stad) {
        this.stad = stad;
    }


    public static final String url = "https://polisen.se/api/events?locationname=" + stad;

}
