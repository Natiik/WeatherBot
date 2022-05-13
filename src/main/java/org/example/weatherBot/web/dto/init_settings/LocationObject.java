package org.example.weatherBot.web.dto.init_settings;

import lombok.Builder;
import lombok.Value;
import org.example.weatherBot.web.dto.CityObject;
import org.example.weatherBot.web.dto.CountryObject;

@Value
@Builder
public class LocationObject {
    CountryObject country;
    CityObject city;
}
