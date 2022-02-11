package org.example.weatherBot.web.services;

import lombok.RequiredArgsConstructor;
import org.example.weatherBot.requesters.WeatherRequester;
import org.example.weatherBot.response.Response;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@Service
@RequiredArgsConstructor
public class WeatherService {
    private final WeatherRequester weatherRequester;

    @GetMapping("/weather")
    public Response getWeather(Long id) {
        return weatherRequester.sendRequest(id);
    }
}
