package ru.kpfu.itis.belskaya;

import java.io.IOException;

public interface LocationKeyGiver {
    String linkForSearchingKey = "http://dataservice.accuweather.com/locations/v1/cities/search";
    String giveLocationKey(String city) throws IOException;
}
