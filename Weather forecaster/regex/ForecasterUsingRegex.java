

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForecasterUsingRegex implements Forecaster {
    public String forecast(String locationKey) throws IOException {
        String weatherInformation = "";
        URL urlForForecast = new URL(LINK_FOR_FORECAST + locationKey + "?" + "apikey=" + WeatherForecastGiver.API_KEY + "&metric=true");
        try (BufferedReader in = new BufferedReader(new InputStreamReader(urlForForecast.openStream(), "UTF-8"))) {
            String line = in.readLine();
            weatherInformation = findWeather(line);
        } catch (IOException e) {
            throw new IOException("Error in obtaining the forecast");
        }
        String currentConditions = "";
        URL urlForCurrent = new URL(LINK_FOR_CURRENT_CONDITIONS + locationKey + "?apikey=" + WeatherForecastGiver.API_KEY);
        try (BufferedReader in = new BufferedReader(new InputStreamReader(urlForCurrent.openStream(), "UTF-8"))) {
            String line = in.readLine();
            currentConditions = findCurrentConditionals(line);
        } catch (IOException e) {
            throw new IOException("Error in obtaining the forecast");
        }
        return weatherInformation + currentConditions;
    }

    private String findCurrentConditionals(String line) {
        String currentMetric = findWord("\"Metric\":\\{.+?}", line);
        String currentTemp = findWord("\"Value\":[0-9\\.\\-]+", currentMetric);
        String currentPrecipitation = findWord("\"HasPrecipitation\":[A-Za-z]+", line);
        String typeOfcurrentPrecipitation = findWord("\"PrecipitationType\":[A-Za-z]+", line);
        return "Current weather conditions:\n" + currentTemp.replaceFirst("\"Value\"", "Temperature") + "\n" +
                currentPrecipitation.replaceAll("\"", "") + "\n" + typeOfcurrentPrecipitation.replaceAll("\"", "");
    }

    protected String findWeather(String line) {
        String regexForDescription = "\"Text\":\"[A-Za-z\\s]+\"";
        String description = findWord(regexForDescription, line).split(":")[1];
        String day = findWord("\"Day\":\\{.+?}", line);
        String[] dayArray = day.substring(day.indexOf("{") + 1, day.length()-1).split(",");
        String night = findWord("\"Night\":\\{.+?}", line);
        String[] nightArray = night.substring(night.indexOf("{") + 1, night.length()-1).split(",");
        String maximum = findWord("\"Maximum\":\\{.+?}", line);
        String[] maxTempArray = maximum.substring(maximum.indexOf("{") + 1, maximum.length()-1).split(",");
        String minimum = findWord("\"Minimum\":\\{.+?}", line);
        String[] minTempArray = minimum.substring(minimum.indexOf("{") + 1, minimum.length()-1).split(",");
        Map<String, String> dayMap = new HashMap<>();
        Map<String, String> nightMap = new HashMap<>();
        Map<String, String> maxTempMap = new HashMap<>();
        Map<String, String> minTempMap = new HashMap<>();
        for(String param: dayArray) {
            String[] pair = param.split(":");
            dayMap.put(pair[0], pair[1]);
        }

        for(String param: nightArray) {
            String[] pair = param.split(":");
            nightMap.put(pair[0], pair[1]);
        }

        for(String param: maxTempArray) {
            String[] pair = param.split(":");
            maxTempMap.put(pair[0], pair[1]);
        }

        for(String param: minTempArray) {
            String[] pair = param.split(":");
            minTempMap.put(pair[0], pair[1]);
        }

        String temperature = "Description:\n" + description + "\n Maximum temperature: " + maxTempMap.get("\"Value\"") + maxTempMap.get("\"Unit\"")
                + "\n Minimum temperature: " + minTempMap.get("\"Value\"") + minTempMap.get("\"Unit\"") + "\n";
        String [] precipitation = {"\"HasPrecipitation\"","\"PrecipitationType\"", "\"PrecipitationIntensity\""};
        String precipitationOfDay = "Precipitations:\n\t\tDay:\n";
        for (String parametr: precipitation) {
            if (dayMap.get(parametr) != null) {
                precipitationOfDay = precipitationOfDay.concat(parametr + " : " + dayMap.get(parametr) + "\n");
            }
        }
        precipitationOfDay = precipitationOfDay.concat("\n\t\tNight: " + "\n");
        for (String parametr: precipitation) {
            if (nightMap.get(parametr) != null) {
                precipitationOfDay = precipitationOfDay.concat(parametr + " : " + nightMap.get(parametr) + "\n");
            }
        }
        return temperature + precipitationOfDay;
    }

    private String findWord(String regex, String line) {
        String params = "";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            params = line.substring(start, end);
        }
        return params;
    }
}
