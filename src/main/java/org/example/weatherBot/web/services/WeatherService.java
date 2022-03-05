package org.example.weatherBot.web.services;

import lombok.RequiredArgsConstructor;
import org.example.weatherBot.requesters.OpenWeatherRequester;
import org.example.weatherBot.response.OpenWeatherResponse;
import org.example.weatherBot.response.structure.Weather;
import org.example.weatherBot.utility.DateUtil;
import org.example.weatherBot.web.dto.WeatherObject;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WeatherService {
    private final OpenWeatherRequester openWeatherRequester;


    public WeatherObject getWeather(Long id) {
        OpenWeatherResponse openWeatherResponse = openWeatherRequester.sendRequest(id);
        return WeatherObject.builder()
                .state(getWeatherString(openWeatherResponse))
                .feelsLike((int) openWeatherResponse.getMain().getFeels_like())
                .name(openWeatherResponse.getName())
                .humidity(openWeatherResponse.getMain().getHumidity())
                .sunrise(DateUtil.toNormalTime(openWeatherResponse.getSys().getSunrise()))
                .sunset(DateUtil.toNormalTime(openWeatherResponse.getSys().getSunset()))
                .temperature((int) openWeatherResponse.getMain().getTemp())
                .pressure(openWeatherResponse.getMain().getPressure())
                .update(DateUtil.toNormal(openWeatherResponse.getDt()))
                .windSpeed((int) openWeatherResponse.getWind().getSpeed())
                .build();
    }

    private String getWeatherString(OpenWeatherResponse openWeatherResponse) {
        return openWeatherResponse.getWeather()
                .stream()
                .map(Weather::getMain)
                .collect(Collectors.joining(" "));
    }


}
