

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String application = "";
        while (!application.equals("stop")) {
            System.out.println("Choose an application: type \"regex\" or \"GSON\", or print \"stop\" to finish work");
            Scanner sc = new Scanner(System.in);
            application = sc.nextLine();
            WeatherForecastGiver weatherForecastGiver = new WeatherForecastGiver();
            switch (application) {
                case "regex":
                    try {
                        System.out.println(weatherForecastGiver.giveForecast(application));
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                    break;
                case "GSON":
                    try {
                        System.out.println(weatherForecastGiver.giveForecast(application));
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                    break;
                case "stop":
                    break;
                default:
                    System.err.println("The command has not been identified, please, choose again");
                    break;
            }
        }

    }
}
