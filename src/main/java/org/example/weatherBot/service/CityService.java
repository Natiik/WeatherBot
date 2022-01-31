package org.example.weatherBot.service;

import lombok.RequiredArgsConstructor;
import org.example.weatherBot.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CityService {

    @Autowired
    private final CityRepository cityRepository;
}
