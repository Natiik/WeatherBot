package org.example.weatherBot.repository;

import org.example.weatherBot.entities.CityEntity;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepositoryImplementation<CityEntity, Integer> {
}
