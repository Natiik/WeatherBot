package org.example.weatherBot.service;

import lombok.RequiredArgsConstructor;
import org.example.weatherBot.entities.CityEntity;
import org.example.weatherBot.repository.CityRepository;
import org.example.weatherBot.web.dto.CityObject;
import org.springframework.stereotype.Service;

import javax.persistence.Lob;
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

    public List<CityObject> getAllCitiesByCountry(String country) {
        return cityRepository.findUniqueCitiesByCountry(country)
                .stream()
                .map(entity -> CityObject.builder()
                        .value(entity.getId())
                        .label(entity.getName())
                        .build())
                .collect(Collectors.toList());
    }

    public CityObject getCityObjectById(Integer id) {
        return CityObject.builder()
                .value(id)
                .label(cityRepository.getById(id).getName())
                .build();
    }

    public String getCountryNameById(Integer id) {
        return cityRepository.getById(id).getCountry();
    }


    public CityEntity findNearest(Double longitude, Double latitude) {
        String lon = (int) Math.floor(longitude) + "%";
        String lat = (int) Math.floor(latitude) + "%";
        List<CityEntity> list = cityRepository.findByLonAndLatIsContaining(lon, lat);
        CityEntity min = list.get(0);
        float min_lon = min.getLon();
        float min_lat = min.getLat();
        for (CityEntity entity : list) {
            if (Math.abs(longitude - min_lon) > Math.abs(longitude - entity.getLon())) {
                if (Math.abs(latitude - min_lat) > Math.abs(latitude - entity.getLat())) {
                    min = entity;
                    min_lon = min.getLon();
                    min_lat = min.getLat();
                }
            }
        }
        return min;
    }
}
