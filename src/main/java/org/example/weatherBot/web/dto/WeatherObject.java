package org.example.weatherBot.web.dto;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class WeatherObject {
    String name;
    String weather;
    Integer temperature;
    Integer feelsLike;
    Integer humidity;
    Integer pressure;
    Integer windSpeed;
    String sunrise;
    String sunset;
    String update;
}
