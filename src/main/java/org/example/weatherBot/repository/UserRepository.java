package org.example.weatherBot.repository;

import org.example.weatherBot.entities.Setting;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepositoryImplementation<Setting, Long> {
    //void updateMetricsByID(Long id, String metrics);
}
