package tig167.myfirstapp.police;


import java.net.URL;

public class Handelser {
  private String datetime;
  private String summary;
  private URL url;
  private String location;

  public Handelser(String datetime, String summary, URL url, String location) {

    this.datetime = datetime;
    this.summary = summary;
    this.url = url;
    this.location = location;
  }

  public String datetime() {
    return datetime;
  }
  public String summary() {
    return summary;
  }
  public URL url() {
    return url;
  }
  public String location() {
    return location;
  }

  @Override
  public String toString() {
  return new StringBuilder()
    .append("Tidpunkt: " + datetime)
    .append("\n")
    .append("Beskrivning: " + summary + "\n")
    .append("\n")
    .append("Läs mer här: " + url)
    .append("\n")
    .toString();
  }



}
