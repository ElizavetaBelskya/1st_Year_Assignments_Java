

import java.io.IOException;

public interface Forecaster {
    String LINK_FOR_FORECAST = "http://dataservice.accuweather.com/forecasts/v1/daily/1day/";
    String LINK_FOR_CURRENT_CONDITIONS = "http://dataservice.accuweather.com/currentconditions/v1/";
    String forecast(String lk) throws IOException;
}
