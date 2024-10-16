package com.youssef.library.cities.Service.Library;

import com.youssef.library.cities.Entities.City;
import com.youssef.library.cities.Entities.Library;

import java.util.List;

public interface LibraryService {

    Library saveLibrary(Library library , String cityId);
    Library getLibraryById(String id);
    List<Library> getLibrariesByCity(String cityId);
    List<Library> filterLibrariesInCityByShelves(String cityId , String order,String minShelves,String maxShelves,String minCapacity,String maxCapacity);
    List<Library> filterLibrariesInCityByVisitors(String cityId , String order,String minShelves,String maxShelves,String minCapacity,String maxCapacity);


    List<Library> getAllFilteredLibrariesByShelves(String order, String subName, String minShelves, String maxShelves, String minCapacity, String maxCapacity);

    List<Library> getAllFilteredLibrariesByVisitors(String order, String subName, String minShelves, String maxShelves, String minCapacity, String maxCapacity);

    List<Library> getAllLibrariesWithFilter(String subName, String minShelves, String maxShelves, String minCapacity, String maxCapacity);





}
