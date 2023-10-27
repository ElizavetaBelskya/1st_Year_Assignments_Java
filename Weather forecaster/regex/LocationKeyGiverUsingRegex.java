
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LocationKeyGiverUsingRegex implements LocationKeyGiver {
    public String giveLocationKey(String city) throws IOException {
        URL urlForKey = null;
        try {
            urlForKey = new URL(linkForSearchingKey + "?" + "apikey=" + WeatherForecastGiver.API_KEY + "&q=" + city);
        } catch (MalformedURLException e) {
            throw new IOException("It is impossible to form a request by this city");
        }
        String locationKey = "";
        try (BufferedReader in = new BufferedReader(new InputStreamReader(urlForKey.openStream(), "UTF-8"))) {
            String line = in.readLine();
            locationKey = findLocationKey(line);
            if (locationKey.isEmpty()) {
                throw new IOException();
            }
        } catch (IOException e) {
            throw new IOException("City search error");
        }
        return locationKey;
    }
    private String findLocationKey(String line) {
        String regex = "\"Key\":\"[0-9]+\"";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);
        String cityKey = "";
        if (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            cityKey = line.substring(start, end).split(":")[1].replaceAll("\"", "");
        }
        return cityKey;
    }
}
