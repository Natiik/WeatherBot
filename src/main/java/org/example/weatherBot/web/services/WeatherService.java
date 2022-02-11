package org.example.weatherBot.web.services;

import lombok.RequiredArgsConstructor;
import org.example.weatherBot.requesters.WeatherRequester;
import org.example.weatherBot.response.Response;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeatherService {
    private final WeatherRequester weatherRequester;

    public Response getWeather(Long id) {
        return weatherRequester.sendRequest(id);
    }
}
