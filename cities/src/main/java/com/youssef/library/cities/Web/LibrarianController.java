package com.youssef.library.cities.Web;

import com.youssef.library.cities.DTOs.Librarian.LibrarianDTO;
import com.youssef.library.cities.DTOs.Librarian.LibrarianDtoMapper;
import com.youssef.library.cities.DTOs.Library.LibraryDtoMapper;
import com.youssef.library.cities.Entities.Librarian;
import com.youssef.library.cities.Service.Librarian.LibrarianService;
import com.youssef.library.cities.Service.Library.LibraryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("librarian")
@AllArgsConstructor
public class LibrarianController {

    private LibrarianService librarianService;

    @PostMapping("/addLibrarian")
    public ResponseEntity<LibrarianDTO> addLibrarian(@RequestBody @Valid LibrarianDTO librarian ,
                                                    @RequestParam("libraryId") String libraryId) {
        Librarian newlibrarian = librarianService.saveLibrarian(LibrarianDtoMapper.toEntity(librarian) , libraryId);
        return new ResponseEntity<>(LibrarianDtoMapper.toDto(newlibrarian), HttpStatus.CREATED);
    }

    @GetMapping("/allLibrarians")
    public ResponseEntity<List<LibrarianDTO>> getAllLibrarians() {
        List<Librarian> allLibrarians = librarianService.getAllLibrarians();
        List<LibrarianDTO> mappedLibrarians = allLibrarians
                .stream()
                .map(LibrarianDtoMapper::toDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(mappedLibrarians, HttpStatus.OK);
    }

    @GetMapping("/allLibrariansInLibrary")
    public ResponseEntity<List<LibrarianDTO>> getAllLibrariansInLibrary(@RequestParam("libraryId") String libraryId){
        List<Librarian> allLibrarians = librarianService.getAllLibrariansInLibrary(libraryId);
        List<LibrarianDTO> mappedLibrarians = allLibrarians
                .stream()
                .map(LibrarianDtoMapper::toDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(mappedLibrarians, HttpStatus.OK);
    }

    @GetMapping("/librarianName")
    public ResponseEntity<List<LibrarianDTO>> getLibrarianContainingNameInLibrary(
            @RequestParam("name") String name,
            @RequestParam("libraryId") String libraryId) {
        List<Librarian> allLibrarians = librarianService.searchForLibrarianContainingName(name,libraryId);
        List<LibrarianDTO> mappedLibrarians = allLibrarians
                .stream()
                .map(LibrarianDtoMapper::toDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(mappedLibrarians, HttpStatus.OK);
    }

}
