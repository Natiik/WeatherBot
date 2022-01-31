package org.example.weatherBot.repository;

import org.example.weatherBot.entities.CityEntity;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepositoryImplementation<CityEntity, Integer> {
    List<CityEntity> findByNameIsContaining(String value);
}
