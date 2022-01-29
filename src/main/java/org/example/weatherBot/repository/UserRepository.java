package org.example.weatherBot.repository;

import org.example.weatherBot.entities.UserEntity;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepositoryImplementation<UserEntity, Long> {
    //void updateMetricsByID(Long id, String metrics);
}
