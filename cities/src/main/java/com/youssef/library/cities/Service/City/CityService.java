package com.youssef.library.cities.Service.City;

import com.youssef.library.cities.Entities.City;

import java.util.List;

public interface CityService {
    City getCityById(String id);
    City getCityByName(String name);
    List<City> filterCityByLibraries(String order , String subName);

    List<City> getAllCities(String subName);

    City saveCity(City city);
}
