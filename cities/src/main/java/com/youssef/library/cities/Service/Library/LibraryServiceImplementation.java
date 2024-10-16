package com.youssef.library.cities.Service.Library;

import com.youssef.library.cities.Entities.City;
import com.youssef.library.cities.Entities.Library;
import com.youssef.library.cities.ExceptionHandlers.NotFoundException;
import com.youssef.library.cities.Repository.CityRepository;
import com.youssef.library.cities.Repository.LibraryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.channels.NotYetBoundException;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class LibraryServiceImplementation implements LibraryService{

    private LibraryRepository libraryRepository;
    private CityRepository cityRepository;

    @Override
    public Library saveLibrary(Library library, String CityId ) {
        City city = cityRepository.findCityById(CityId);
        if(city == null) {
            throw new NotFoundException("City");
        }
        library.setCity(city);
        city.getLibraries().add(library);
        library.setId(UUID.randomUUID().toString());
        return libraryRepository.save(library);
    }



    @Override
    public Library getLibraryById(String id){
        Library library = libraryRepository.findLibraryById(id);
        if(library == null) {
            throw new NotFoundException("Library");
        }
        return libraryRepository.findLibraryById(id);
    }

    @Override
    public List<Library> getLibrariesByCity(String cityId) {
        City city = cityRepository.findCityById(cityId);
        if(city == null) {
            throw new NotFoundException("City");
        }
        return libraryRepository.findLibrariesByCity(cityId);
    }






    @Override
    public List<Library> filterLibrariesInCityByShelves(String cityId , String order,String minShelves,String maxShelves,String minCapacity,String maxCapacity) {
        City city = cityRepository.findCityById(cityId);
        if(city == null) {
            throw new NotFoundException("City");
        }
        if("DESC".equalsIgnoreCase(order))
            return libraryRepository.filterLibrariesInCityByShelvesDesc(cityId , minShelves, maxShelves, minCapacity, maxCapacity);
        return libraryRepository.filterLibrariesInCityByShelvesAsc(cityId , minShelves,maxShelves, minCapacity, maxCapacity);
    }


    @Override
    public List<Library> filterLibrariesInCityByVisitors(String cityId , String order,String minShelves,String maxShelves,String minCapacity,String maxCapacity) {
        City city = cityRepository.findCityById(cityId);
        if(city == null) {
            throw new NotFoundException("City");
        }
        if("DESC".equalsIgnoreCase(order))
            return libraryRepository.filterLibrariesInCityByVisitorsDesc(cityId , minShelves, maxShelves, minCapacity, maxCapacity);
        return libraryRepository.filterLibrariesInCityByVisitorsAsc(cityId , minShelves, maxShelves, minCapacity, maxCapacity);
    }

    @Override
    public List<Library> getAllFilteredLibrariesByShelves(String order, String subName, String minShelves, String maxShelves, String minCapacity, String maxCapacity) {
        if("DESC".equalsIgnoreCase(order))
            return libraryRepository.findAllFilteredLibrariesByShelvesDesc(subName,minShelves,maxShelves,minCapacity,maxCapacity);
        return libraryRepository.findAllFilteredLibrariesByShelvesAsc(subName,minShelves,maxShelves,minCapacity,maxCapacity);
    }

    @Override
    public List<Library> getAllFilteredLibrariesByVisitors(String order, String subName, String minShelves, String maxShelves, String minCapacity, String maxCapacity) {
        if("DESC".equalsIgnoreCase(order))
            return libraryRepository.findAllFilteredLibrariesByVisitorsDesc(subName,minShelves,maxShelves,minCapacity,maxCapacity);
        return libraryRepository.findAllFilteredLibrariesByVisitorsAsc(subName,minShelves,maxShelves,minCapacity,maxCapacity);
    }

    @Override
    public List<Library> getAllLibrariesWithFilter(String subName, String minShelves, String maxShelves, String minCapacity, String maxCapacity) {
        return libraryRepository.findAllLibrariesByFilters(subName,minShelves,maxShelves,minCapacity,maxCapacity);
    }


}
