package com.youssef.library.cities.DTOs.Library;

import com.youssef.library.cities.Entities.Library;

public class LibraryDtoMapper {
    public static LibraryDTO toDto(Library library) {
        if(library == null) return null;
        LibraryDTO libraryDTO = new LibraryDTO();
        libraryDTO.setId(library.getId());
        libraryDTO.setName(library.getName());
        libraryDTO.setShelvesCapacity(library.getShelvesCapacity());
        libraryDTO.setVisitorsCapacity(library.getVisitorsCapacity());
        return libraryDTO;
    }

    public static Library toEntity(LibraryDTO libraryDTO) {
        if(libraryDTO == null) return null;
        Library library = new Library();
        library.setId(libraryDTO.getId());
        library.setName(libraryDTO.getName());
        library.setShelvesCapacity(libraryDTO.getShelvesCapacity());
        library.setVisitorsCapacity(libraryDTO.getVisitorsCapacity());
        return library;
    }
}
