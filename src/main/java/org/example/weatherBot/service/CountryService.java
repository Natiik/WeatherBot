package org.example.weatherBot.service;

import lombok.RequiredArgsConstructor;
import org.example.weatherBot.repository.CountryRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CountryService {
    private final CountryRepository countryRepository;

}
