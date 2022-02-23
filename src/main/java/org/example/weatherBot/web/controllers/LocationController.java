package org.example.weatherBot.web.controllers;

import lombok.RequiredArgsConstructor;
import org.example.weatherBot.service.CityService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LocationController {
    private CityService cityService;

//    @GetMapping("/location/en")
//    public
}
