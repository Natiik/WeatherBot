package org.example.weatherBot.web.services;

import lombok.RequiredArgsConstructor;
import org.example.weatherBot.web.dto.WeatherObject;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeatherService {
    private final ResponseService responseService;

    public WeatherObject getWeather(Long id) {
        return responseService.getWeather(id);
    }
}
