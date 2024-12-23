package com.youssef.library.cities.Web;

import com.youssef.library.cities.DTOs.Shelf.ShelfDTO;
import com.youssef.library.cities.DTOs.Shelf.ShelfDtoMapper;
import com.youssef.library.cities.Entities.Library;
import com.youssef.library.cities.Entities.Shelf;
import com.youssef.library.cities.Enums.Category;
import com.youssef.library.cities.Service.Shelf.ShelfService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("shelf")
@CrossOrigin("*")
public class ShelfController {

    private ShelfService shelfService;

    @PreAuthorize("hasAuthority('SCOPE_ROLE_LIBRARIAN')")
    @PostMapping("/addShelf")
    public ResponseEntity<Shelf> addShelf(@RequestBody ShelfDTO shelfDto , @RequestParam("libraryId") String libraryId) {
        Shelf newShelf = shelfService.saveShelf(ShelfDtoMapper.toEntity(shelfDto) , libraryId);
        return new ResponseEntity<>(newShelf , HttpStatus.CREATED);
    }

    @GetMapping("/id")
    public ResponseEntity<List<ShelfDTO>> getShelfinLibraryById(@RequestParam("libraryId") String libraryId) {
        List<Shelf> allShelves = shelfService.getShelvesInLibraryById(libraryId);
        List<ShelfDTO> mappedShelves = allShelves.stream()
                                                .map(ShelfDtoMapper::toDto)
                                                .collect(Collectors.toList());
        return new ResponseEntity<>(mappedShelves , HttpStatus.OK);
    }

    @GetMapping("/name")
    public ResponseEntity<List<ShelfDTO>> getShelfInLibraryByName(@RequestParam("libraryName") String libraryName) {
        List<Shelf> allShelves = shelfService.getShelvesInLibraryByName(libraryName);
        List<ShelfDTO> mappedShelves = allShelves.stream()
                .map(ShelfDtoMapper::toDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(mappedShelves , HttpStatus.OK);
    }

    @GetMapping("/categoryAndId")
    public ResponseEntity<List<ShelfDTO>> getShelfInLibraryCategoryAndId(
            @RequestParam("libraryId") String libraryId,
            @RequestParam("category") Category category) {
        List<Shelf> allShelves = shelfService.getShelvesInLibraryByIdAndCategory(libraryId,category);
        List<ShelfDTO> mappedShelves = allShelves
                .stream()
                .map(ShelfDtoMapper::toDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(mappedShelves , HttpStatus.OK);
    }

    @GetMapping("/filterAsc")
    public ResponseEntity<List<ShelfDTO>> filterShelvesInLibraryByAsc(@RequestParam("libraryId") String libraryId){
        List<Shelf> allShelves = shelfService.filterShelvesInLibraryByAsc(libraryId);
        List<ShelfDTO> mappedShelves = allShelves
                .stream()
                .map(ShelfDtoMapper::toDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(mappedShelves , HttpStatus.OK);
    }

    @GetMapping("/filterDsc")
    public ResponseEntity<List<ShelfDTO>> filterShelvesInLibraryByDesc(@RequestParam("libraryId") String libraryId){
        List<Shelf> allShelves = shelfService.filterShelvesInLibraryByDesc(libraryId);
        List<ShelfDTO> mappedShelves = allShelves
                .stream()
                .map(ShelfDtoMapper::toDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(mappedShelves , HttpStatus.OK);
    }

    @GetMapping("/filteredShelves")
    public ResponseEntity<List<ShelfDTO>> getAllFilteredShelves(
            @RequestParam("libraryId") String libraryId,
            @RequestParam("minOccupied") String minOccupied,
            @RequestParam("maxOccupied") String maxOccupied,
            @RequestParam("minCapacity") String minBooks,
            @RequestParam("maxCapacity") String maxBooks,
            @RequestParam("order") String order,
            @RequestParam("orderType") String orderType
    ){
            List<Shelf> allShelvesInLibrary;
            if(!orderType.isEmpty()){
                allShelvesInLibrary = shelfService.filterShelvesInLibraryByOrder(libraryId,order,orderType,minOccupied,maxOccupied,minBooks,maxBooks);
            }else
                allShelvesInLibrary = shelfService.filterShelvesInLibraryWithoutOrder(libraryId,minOccupied,maxOccupied,minBooks,maxBooks);
            List<ShelfDTO> mappedShelves = allShelvesInLibrary
                        .stream()
                        .map(ShelfDtoMapper::toDto)
                        .collect(Collectors.toList());
            return new ResponseEntity<>(mappedShelves , HttpStatus.OK);
    }

}
