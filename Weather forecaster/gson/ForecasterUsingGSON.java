

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class ForecasterUsingGSON implements Forecaster {
    public String forecast(String locationKey) throws IOException {
        String weatherInformation = "";
        String currentConditionals = "";
        URL urlForForecast = new URL(LINK_FOR_FORECAST + locationKey + "?" + "apikey=" + WeatherForecastGiver.API_KEY + "&metric=true");
        try (BufferedReader in = new BufferedReader(new InputStreamReader(urlForForecast.openStream(), "UTF-8"))){
            JsonElement element = new JsonParser().parse(in);
            JsonObject root = element.getAsJsonObject();
            JsonObject headline = root.get("Headline").getAsJsonObject();
            weatherInformation = headline.get("Text").getAsString();
        } catch (IOException e) {
            throw new IOException("Error in obtaining the forecast");
        }
        URL urlForCurrent = new URL(LINK_FOR_CURRENT_CONDITIONS + locationKey + "?apikey=" + WeatherForecastGiver.API_KEY + "&metric=true");
        try (BufferedReader in = new BufferedReader(new InputStreamReader(urlForCurrent.openStream(), "UTF-8"))) {
            JsonElement element = new JsonParser().parse(in);
            JsonArray current = element.getAsJsonArray();
            JsonObject obj = current.get(0).getAsJsonObject();
            JsonObject tempObj = obj.get("Temperature").getAsJsonObject();
            JsonObject metric = tempObj.get("Metric").getAsJsonObject();
            float temperature = metric.get("Value").getAsFloat();
            String unit = metric.get("Unit").getAsString();
            String text = obj.get("WeatherText").getAsString();
            boolean hasPrecipitation = obj.get("HasPrecipitation").getAsBoolean();
            String precipitationType = "";
            if (hasPrecipitation) {
                precipitationType = obj.get("PrecipitationType").getAsString();
            }
            weatherInformation = "Weather:\n" + text + "\nTemperature is " + temperature + unit + "\nPrecipitation: " +
                    hasPrecipitation + (hasPrecipitation? "," + precipitationType : "");
        } catch (IOException e) {
            throw new IOException("Error in obtaining the forecast");
        }
        return weatherInformation;
    }

}
