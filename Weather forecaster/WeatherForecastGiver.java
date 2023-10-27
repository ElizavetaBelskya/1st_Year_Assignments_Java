package ru.kpfu.itis.belskaya;

import java.io.IOException;
import java.util.Scanner;

public class WeatherForecastGiver {
    public static final String API_KEY = "LnAtz7vbhKeeBA3FDuXroRYqorc6kCWy";

    protected String giveForecast(String parsingMethod) throws IOException {
        LocationKeyGiver lk = null;
        Forecaster forecaster = null;
        if (parsingMethod.equals("regex")) {
            lk = new LocationKeyGiverUsingRegex();
            forecaster = new ForecasterUsingRegex();
        } else if (parsingMethod.equals("GSON")) {
            lk = new LocationKeyGiverUsingGSON();
            forecaster = new ForecasterUsingGSON();
        }
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the city where the weather forecast is interesting to you: ");
        String city = sc.nextLine().trim();
        String locationKey = lk.giveLocationKey(city);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        return forecaster.forecast(locationKey);
    }
}
