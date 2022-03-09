package org.example.weatherBot.service;

import lombok.RequiredArgsConstructor;
import org.example.weatherBot.repository.CountryRepository;
import org.example.weatherBot.web.dto.CountryObject;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CountryService {
    private final CountryRepository countryRepository;

    public List<CountryObject> getAllCountries() {
        return countryRepository.findAll().stream()
                .map(e -> CountryObject
                        .builder()
                        .value(e.getShortName())
                        .label(e.getFullName())
                        .build())
                .collect(Collectors.toList());
    }

    public CountryObject getCountryObjectByShortName(String shortName){
        return CountryObject.builder()
                .value(shortName)
                .label(countryRepository.getCountryEntityByShortName(shortName).getFullName())
                .build();
    }

}
