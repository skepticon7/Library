package com.youssef.library.cities.Web;

import com.youssef.library.cities.DTOs.Library.LibraryDTO;
import com.youssef.library.cities.DTOs.Library.LibraryDtoMapper;
import com.youssef.library.cities.Entities.Library;
import com.youssef.library.cities.Service.Library.LibraryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("libraries")
@AllArgsConstructor
@CrossOrigin("*")
public class LibraryController {

    private LibraryService libraryService;

    @GetMapping("/id")
    public ResponseEntity<List<LibraryDTO>> getAllLibrariesInCityById(@RequestParam("cityId") String cityId){
        List<Library> AllLibrariesInCity = libraryService.getLibrariesByCity(cityId);
        List<LibraryDTO> MappedLibraries = AllLibrariesInCity
                .stream()
                    .map(LibraryDtoMapper::toDto)
                        .collect(Collectors.toList());
        return new ResponseEntity<>(MappedLibraries, HttpStatus.OK);
    }



    @GetMapping("/filteredLibraries")
    public ResponseEntity<List<LibraryDTO>> getAllLibrariesByFilter(
            @RequestParam("subName") String subName,
            @RequestParam("order") String order,
            @RequestParam("orderType") String orderType,
            @RequestParam("minShelves") String minShelves,
            @RequestParam("maxShelves") String maxShelves,
            @RequestParam("minCapacity") String minCapacity,
            @RequestParam("maxCapacity") String maxCapacity
    ){
        List<Library> allLibraries;
        if(!orderType.isEmpty()){
            if(orderType.equals("shelves"))
                allLibraries = libraryService.getAllFilteredLibrariesByShelves(order,subName,minShelves,maxShelves,minCapacity,maxCapacity);
            else
                allLibraries = libraryService.getAllFilteredLibrariesByVisitors(order,subName,minShelves,maxShelves,minCapacity,maxCapacity);
        }else
            allLibraries = libraryService.getAllLibrariesWithFilter(subName,minShelves,maxShelves,minCapacity,maxCapacity);
        List<LibraryDTO> MappedLibraries = allLibraries
                .stream()
                .map(LibraryDtoMapper::toDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(MappedLibraries, HttpStatus.OK);
    }




    @GetMapping("/filterLibrariesByCity")
    public ResponseEntity<List<LibraryDTO>> getLibrariesInCityByVisitors(
            @RequestParam("cityId") String cityId ,
            @Param("order") String order,
            @Param("orderType") String orderType,
            @RequestParam("minShelves") String minShelves,
            @RequestParam("maxShelves") String maxShelves,
            @RequestParam("minCapacity") String minCapacity,
            @RequestParam("maxCapacity") String maxCapacity
            ){
        List<Library> allLibraries;
        if(orderType.equals("visitors"))
            allLibraries = libraryService.filterLibrariesInCityByVisitors(cityId,order,minShelves,maxShelves,minCapacity,maxCapacity);
        else
            allLibraries = libraryService.filterLibrariesInCityByShelves(cityId,order,minShelves,maxShelves,minCapacity,maxCapacity);
        List<LibraryDTO> mappedLibraries = allLibraries
                .stream()
                .map(LibraryDtoMapper::toDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(mappedLibraries, HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('SCOPE_ROLE_LIBRARIAN')")
    @PostMapping("/addLibrary")
    public ResponseEntity<Library> addLibrary(@RequestBody @Valid LibraryDTO libraryDTO , @RequestParam("cityId") String cityId){
            Library newLibrary = libraryService.saveLibrary(LibraryDtoMapper.toEntity(libraryDTO) , cityId);
            return new ResponseEntity<>(newLibrary , HttpStatus.CREATED);
    }

}
