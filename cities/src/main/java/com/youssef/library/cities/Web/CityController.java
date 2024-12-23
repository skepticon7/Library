package com.youssef.library.cities.Web;


import com.youssef.library.cities.DTOs.City.CityDTO;
import com.youssef.library.cities.DTOs.City.CityDtoMapper;
import com.youssef.library.cities.Entities.City;
import com.youssef.library.cities.Service.City.CityService;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("cities")
@AllArgsConstructor
public class CityController {


    private CityService cityService;

    @GetMapping
        public List<CityDTO> getAllCities(@RequestParam("subName") String subName) {
            List<City> cities = cityService.getAllCities(subName);
            return cities.stream()
                    .map(CityDtoMapper::toDto)
                    .collect(Collectors.toList());
        }

        @GetMapping("/id")
        public CityDTO getCityById(@RequestParam("cityId") String cityId) {
            return CityDtoMapper.toDto(cityService.getCityById(cityId));
        }

        @GetMapping("/name")
        public CityDTO getCityByName(@RequestParam("cityName") String name) {
            return CityDtoMapper.toDto(cityService.getCityByName(name));
        }

        @GetMapping("/filterCityByLibraries")
        public List<CityDTO> getCityByLibrary(@RequestParam("order") String order,@RequestParam("subName") String subName){
            List<City> cities = cityService.filterCityByLibraries(order , subName);
            return cities
                    .stream()
                    .map(CityDtoMapper::toDto)
                    .collect(Collectors.toList());
        }


        @PreAuthorize("hasAuthority('SCOPE_ROLE_LIBRARIAN')")
        @PostMapping("/addCity")
        public City addCity(@RequestBody CityDTO city) {
            return cityService.saveCity(CityDtoMapper.toEntity(city));
        }

}
