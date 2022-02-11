package org.example.weatherBot.web.controllers;

import lombok.RequiredArgsConstructor;
import org.example.weatherBot.response.Response;
import org.example.weatherBot.web.services.WebService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WeatherController {
    private final WebService webService;

    @GetMapping("/weather")
    public Response getRequest(@RequestHeader Long id) {
        return webService.getWeather(id);
    }
}