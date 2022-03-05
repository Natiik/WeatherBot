package org.example.weatherBot.service;

import lombok.RequiredArgsConstructor;
import org.example.weatherBot.entities.CityEntity;
import org.example.weatherBot.repository.CityRepository;
import org.example.weatherBot.web.dto.CityObject;
import org.springframework.stereotype.Service;

import java.util.List;
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


    public List<String> getAlikeCity(String value) {
        return cityRepository.findByNameIsContaining(value)
                .stream()
                .map(CityEntity::getName)
                .collect(Collectors.toList());
    }

    public List<CityObject> getAllCitiesByCountry(String country) {
        return cityRepository.findUniqueCitiesByCountry(country)
                .stream()
                .map(entity->CityObject.builder()
                        .Id(entity.getId())
                        .name(entity.getName())
                        .build())
                .collect(Collectors.toList());
    }
}
