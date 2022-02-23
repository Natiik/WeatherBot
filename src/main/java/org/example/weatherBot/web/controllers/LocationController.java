package org.example.weatherBot.web.controllers;

import lombok.RequiredArgsConstructor;
import org.example.weatherBot.service.CityService;
import org.example.weatherBot.service.CountryService;
import org.example.weatherBot.web.dto.CountryObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LocationController {
    private final CityService cityService;
    private final CountryService countryService;

    @GetMapping("/location/en")
    public List<CountryObject> getAllCountries(){
        return countryService.getAllCountries();
    }

    @GetMapping("/loction/en/{country}")
    public List<String> getAllCitiesByCountry(@PathVariable String country){
        return cityService.getAllCitiesByCountry(country);
    }
}
