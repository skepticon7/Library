package com.youssef.library.cities.Repository;

import com.youssef.library.cities.Entities.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City , String> {
    @Query("SELECT c FROM City c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%',:subName,'%'))")
    List<City> findAllCities(@Param("subName") String subName);
    City findCityByName(String name);
    City findCityById(String id);

    @Query("SELECT c FROM City c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%',:subName,'%')) ORDER BY SIZE(c.libraries) DESC")
    List<City> filterCityByLibrariesDesc(@Param("subName") String subName);

    @Query("SELECT c FROM City c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%',:subName,'%')) ORDER BY SIZE(c.libraries) ASC")
    List<City> filterCityByLibrariesAsc(@Param("subName") String subName);
}
