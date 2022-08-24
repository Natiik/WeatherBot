package org.example.weatherBot.repository;

import org.example.weatherBot.entities.CityEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepositoryImplementation<CityEntity, Integer> {
    List<CityEntity> findByNameIsContaining(String value);

    @Query("select c from CityEntity c where c.id in (SELECT min(c1.id) from CityEntity c1 where c1.country=?1 group by c1.name)")
    List<CityEntity> findUniqueCitiesByCountry(String country);

   @Query( "SELECT c FROM CityEntity c WHERE concat(c.lon, '') LIKE ?1 AND concat(c.lat,'') LIKE ?2 ")
    List<CityEntity> findByLonAndLatIsContaining(String longitude, String latitude);
}
