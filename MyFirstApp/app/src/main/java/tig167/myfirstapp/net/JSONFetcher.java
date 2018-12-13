package tig167.myfirstapp.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.stream.Collectors;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class JSONFetcher {

	private static final String END_POINT = "https://polisen.se/api/";
	private static final String DEFAULT_QUERY = "events?locationname=Göteborg&DateTime=";

	LocalDate date = LocalDate.now();
	DateTimeFormatter currentDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public String fetch(String query) {
		if (query == null || query.equals("")) {
			query = DEFAULT_QUERY + currentDate.format(date);
		}
		String result = "";
		try {
			System.out.println("Using this query: " + query);
			System.out.println("Current date: " + currentDate.format(date));
			System.out.println("Connecting to POLISENS api");
			System.out.println("Using this URL: " + END_POINT + query);
			URL url = new URL(END_POINT + query);
			StringBuilder response = new StringBuilder();
			BufferedReader reader =
			new BufferedReader(new InputStreamReader
										 (url.openStream()));


			for(Object o : reader.lines().collect(Collectors.toList())) {
				String line = (String) o;
				response.append(line);
				}
				result = response.toString();
				} catch (IOException e) {
					throw new RuntimeException("Problem fetching JSON: " + e.getMessage(), e);
				}
				return result;
	}
}
