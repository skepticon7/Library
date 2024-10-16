package com.youssef.library.cities.Repository;

import com.youssef.library.cities.Entities.City;
import com.youssef.library.cities.Entities.Library;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibraryRepository extends JpaRepository<Library,String> {
    Library findLibraryById(String id);
    Library findLibraryByName(String libraryName);
    List<Library> findLibrariesByNameContaining(String name);
    @Query("SELECT l FROM Library l WHERE l.visitorsCapacity > :capacity")
    List<Library> findLibrariesWithCapacityBigger(@Param("capacity") int capacity);
    @Query("SELECT l FROM Library l WHERE l.visitorsCapacity < :capacity")
    List<Library> findLibrariesWithCapacitySmaller(@Param("capacity") int capacity);

    @Query("SELECT l FROM Library l WHERE l.visitorsCapacity < :capacity AND l.city.id = :cityId")
    List<Library> findLibrariesWithCapacitySmallerByCity(@Param("capacity") int capacity , @Param("cityId") String cityId);

    @Query("SELECT l FROM Library l WHERE l.visitorsCapacity > :capacity AND l.city.id = :cityId")
    List<Library> findLibrariesWithCapacityBiggerByCity(@Param("capacity") int capacity , @Param("cityId") String cityId);

    @Query("SELECT c FROM Library c WHERE c.city.id = :cityId")
    List<Library> findLibrariesByCity(@Param("cityId") String cityId);

    @Query("SELECT c FROM Library c WHERE c.city.name = :cityName")
    List<Library> findLibrariesByCityName(@Param("cityName") String cityName);

    @Query(value = "SELECT l FROM Library l WHERE l.city.id = :cityId AND l.shelvesCapacity BETWEEN :minShelves AND :maxShelves AND l.visitorsCapacity BETWEEN :minCapacity AND :maxCapacity ORDER BY l.shelvesCapacity ASC")
    List<Library> filterLibrariesInCityByShelvesAsc(
            @Param("cityId") String cityId,
            @Param("minShelves") String minShelves,
            @Param("maxShelves") String maxShelves,
            @Param("minCapacity") String minCapacity,
            @Param("maxCapacity") String maxCapacity
    );

    @Query("SELECT l FROM Library l WHERE l.city.id = :cityId AND l.shelvesCapacity BETWEEN :minShelves AND :maxShelves AND l.visitorsCapacity BETWEEN :minCapacity AND :maxCapacity ORDER BY l.shelvesCapacity DESC")
    List<Library> filterLibrariesInCityByShelvesDesc(
            @Param("cityId") String cityId,
            @Param("minShelves") String minShelves,
            @Param("maxShelves") String maxShelves,
            @Param("minCapacity") String minCapacity,
            @Param("maxCapacity") String maxCapacity
    );


    @Query("SELECT l FROM Library l WHERE  l.city.id = :cityId AND l.shelvesCapacity BETWEEN :minShelves AND :maxShelves AND l.visitorsCapacity BETWEEN :minCapacity AND :maxCapacity  ORDER BY l.visitorsCapacity ASC")
    List<Library> filterLibrariesInCityByVisitorsAsc(
            @Param("cityId") String cityId,
            @Param("minShelves") String minShelves,
            @Param("maxShelves") String maxShelves,
            @Param("minCapacity") String minCapacity,
            @Param("maxCapacity") String maxCapacity
    );

    @Query("SELECT l FROM Library l WHERE  l.city.id = :cityId AND l.shelvesCapacity BETWEEN :minShelves AND :maxShelves AND l.visitorsCapacity BETWEEN :minCapacity AND :maxCapacity ORDER BY l.visitorsCapacity DESC")
    List<Library> filterLibrariesInCityByVisitorsDesc(
            @Param("cityId") String cityId,
            @Param("minShelves") String minShelves,
            @Param("maxShelves") String maxShelves,
            @Param("minCapacity") String minCapacity,
            @Param("maxCapacity") String maxCapacity
            );


    @Query("SELECT l FROM Library l WHERE l.city.name = :cityName AND l.shelvesCapacity BETWEEN :minShelves AND :maxShelves AND l.visitorsCapacity BETWEEN :minCapacity AND :maxCapacity")
    List<Library> findLibrariesByAllFilters(
            @Param("cityName") String cityName ,
            @Param("minShelves") String minShelves,
            @Param("maxShelves") String maxShelves,
            @Param("minCapacity") String minCapacity,
            @Param("maxCapacity") String maxCapacity
            );


    @Query("SELECT l FROM Library l WHERE LOWER(l.name) LIKE LOWER(CONCAT('%',:subName,'%')) AND l.shelvesCapacity BETWEEN :minShelves AND :maxShelves AND l.visitorsCapacity BETWEEN :minCapacity AND :maxCapacity ORDER BY l.shelvesCapacity DESC")
    List<Library> findAllFilteredLibrariesByShelvesDesc(String subName, String minShelves, String maxShelves, String minCapacity, String maxCapacity);

    @Query("SELECT l FROM Library l WHERE LOWER(l.name) LIKE LOWER(CONCAT('%',:subName,'%')) AND l.shelvesCapacity BETWEEN :minShelves AND :maxShelves AND l.visitorsCapacity BETWEEN :minCapacity AND :maxCapacity ORDER BY l.shelvesCapacity ")
    List<Library> findAllFilteredLibrariesByShelvesAsc(String subName, String minShelves, String maxShelves, String minCapacity, String maxCapacity);

    @Query("SELECT l FROM Library l WHERE LOWER(l.name) LIKE LOWER(CONCAT('%',:subName,'%')) AND l.shelvesCapacity BETWEEN :minShelves AND :maxShelves AND l.visitorsCapacity BETWEEN :minCapacity AND :maxCapacity ORDER BY l.visitorsCapacity DESC")
    List<Library> findAllFilteredLibrariesByVisitorsDesc(String subName, String minShelves, String maxShelves, String minCapacity, String maxCapacity);

    @Query("SELECT l FROM Library l WHERE LOWER(l.name) LIKE LOWER(CONCAT('%',:subName,'%')) AND l.shelvesCapacity BETWEEN :minShelves AND :maxShelves AND l.visitorsCapacity BETWEEN :minCapacity AND :maxCapacity ORDER BY l.visitorsCapacity")
    List<Library> findAllFilteredLibrariesByVisitorsAsc(String subName, String minShelves, String maxShelves, String minCapacity, String maxCapacity);

    @Query("SELECT l FROM Library l WHERE LOWER(l.name) LIKE LOWER(CONCAT('%',:subName,'%')) AND l.shelvesCapacity BETWEEN :minShelves AND :maxShelves AND l.visitorsCapacity BETWEEN :minCapacity AND :maxCapacity")
    List<Library> findAllLibrariesByFilters(String subName, String minShelves, String maxShelves, String minCapacity, String maxCapacity);
}
