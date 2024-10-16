package com.youssef.library.cities.DTOs.City;

import com.youssef.library.cities.Entities.City;

public class CityDtoMapper {
    public static CityDTO toDto(City city) {
        if(city == null) return null;
        CityDTO cityDTO = new CityDTO();
        cityDTO.setId(city.getId());
        cityDTO.setName(city.getName());
        cityDTO.setPhoto(city.getPhoto());
        cityDTO.setLibrarySize(city.getLibraries().size());
        return cityDTO;
    }

    public static City toEntity(CityDTO cityDTO) {
        if(cityDTO == null) return null;
        City city = new City();
        city.setId(cityDTO.getId());
        city.setName(cityDTO.getName());
        city.setPhoto(cityDTO.getPhoto());
        return city;
    }

}
