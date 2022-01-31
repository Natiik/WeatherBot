package org.example.weatherBot.service;

import lombok.RequiredArgsConstructor;
import org.example.weatherBot.entities.CityEntity;
import org.example.weatherBot.repository.CityRepository;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CityService {
    private final CityRepository cityRepository;

    public Map<String, String> findSimilarCities(String value) {
        return cityRepository.findByNameIsContaining(value)
                .stream()
                .collect(Collectors.toMap(
                        e -> "/ci_" + e.getId(),
                        CityEntity::getName)
                );
    }

    public String getCityNameById(Integer id) {
        return cityRepository.getById(id).getName();
    }
}
