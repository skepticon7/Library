package com.youssef.library.cities.Service.City;

import com.youssef.library.cities.Entities.City;
import com.youssef.library.cities.ExceptionHandlers.NotFoundException;
import com.youssef.library.cities.Repository.CityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CityServiceImplementation implements CityService {

    private CityRepository cityRepository;

    @Override
    public City getCityById(String id){
        City city = cityRepository.findCityById(id);
        if(city == null){
            throw new NotFoundException("City");
        }
        return city;
    }

    @Override
    public City getCityByName(String name){
        City city = cityRepository.findCityByName(name);
        if(city == null){
            throw new NotFoundException("City");
        }
        return city;
    }

    @Override
    public List<City> filterCityByLibraries(String order , String subName) {
        if("DESC".equalsIgnoreCase(order))
            return cityRepository.filterCityByLibrariesDesc(subName);
        return cityRepository.filterCityByLibrariesAsc(subName);
    }


    @Override
    public List<City> getAllCities(String subName) {
        return cityRepository.findAllCities(subName);
    }

    @Override
    public City saveCity(City city) {
        city.setId(UUID.randomUUID().toString());
        return cityRepository.save(city);
    }
}
