package org.example.weatherBot.repository;

import org.example.weatherBot.entities.CountryEntity;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

public interface CountryRepository extends JpaRepositoryImplementation<CountryEntity, Integer> {
}
