package org.example.weatherBot.service;

import org.example.weatherBot.json.JsonMapper;
import org.example.weatherBot.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityService {

    @Autowired
    private final CityRepository cityRepository;
    private JsonMapper jsonMapper = new JsonMapper();

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

}
