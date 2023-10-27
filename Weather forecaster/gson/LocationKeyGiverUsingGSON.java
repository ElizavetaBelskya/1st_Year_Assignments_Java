package ru.kpfu.itis.belskaya;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class LocationKeyGiverUsingGSON implements LocationKeyGiver {
    public String giveLocationKey(String city) throws IOException {
        URL urlForKey = null;
        try {
            urlForKey = new URL(linkForSearchingKey + "?" + "apikey=" + WeatherForecastGiver.API_KEY + "&q=" + city);
        } catch (MalformedURLException e) {
            throw new IOException("It is impossible to form a request by this city");
        }
        String locationKey = "";
        try (BufferedReader in = new BufferedReader(new InputStreamReader(urlForKey.openStream(), "UTF-8"))) {
            JsonElement element = (new JsonParser()).parse(in);
            JsonArray current = element.getAsJsonArray();
            JsonObject obj = current.get(0).getAsJsonObject();
            locationKey = obj.get("Key").getAsString();
        } catch (IOException e) {
            throw new IOException("City search error");
        } catch (IndexOutOfBoundsException e) {
            throw new IOException("There is no such city");
        }
        return locationKey;
    }
}
