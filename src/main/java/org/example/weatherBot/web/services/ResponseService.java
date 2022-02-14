package org.example.weatherBot.web.services;

import lombok.RequiredArgsConstructor;
import org.example.weatherBot.requesters.WeatherRequester;
import org.example.weatherBot.response.Response;
import org.example.weatherBot.response.structure.Weather;
import org.example.weatherBot.utility.DateUtil;
import org.example.weatherBot.web.dto.WeatherObject;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResponseService {
    private final WeatherRequester weatherRequester;


    public WeatherObject getWeather(Long id){
        Response response= weatherRequester.sendRequest(id);
        return WeatherObject.builder()
                .state(getWeatherString(response))
                .feelsLike((int)response.getMain().getFeels_like())
                .name(response.getName())
                .humidity(response.getMain().getHumidity())
                .sunrise(DateUtil.toNormalTime(response.getSys().getSunrise()))
                .sunset(DateUtil.toNormalTime(response.getSys().getSunset()))
                .temperature((int)response.getMain().getTemp())
                .pressure(response.getMain().getPressure())
                .update(DateUtil.toNormal(response.getDt()))
                .windSpeed((int)response.getWind().getSpeed())
                .build();
    }

    private String getWeatherString(Response response) {
        List<String> strings = response.getWeather()
                .stream()
                .map(Weather::getMain)
                .toList();
        StringBuilder weather= new StringBuilder();
        for (String string : strings) {
            weather.append(string+" ");
        }
        return weather.toString();
    }


}
