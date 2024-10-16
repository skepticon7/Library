package com.youssef.library.cities.DTOs.Librarian;

import com.youssef.library.cities.DTOs.Library.LibraryDtoMapper;
import com.youssef.library.cities.Entities.Librarian;

public class LibrarianDtoMapper {

    public static LibrarianDTO toDto(Librarian librarian) {
        if(librarian == null) {
            return null;
        }
        LibrarianDTO librarianDTO = new LibrarianDTO();
        librarianDTO.setName(librarian.getName());
        librarianDTO.setEmail(librarian.getEmail());
        librarianDTO.setSurname(librarian.getSurname());
        librarianDTO.setGender(librarian.getGender());
        librarianDTO.setBirthDate(librarian.getBirthDate());
        librarianDTO.setLibrary(LibraryDtoMapper.toDto(librarian.getLibraryForLibrarian()));
        librarianDTO.setPassword(librarian.getPassword());
        return librarianDTO;
    }

    public static Librarian toEntity(LibrarianDTO librarianDTO) {
        if(librarianDTO == null)
            return null;
        Librarian librarian = new Librarian();
        librarian.setName(librarianDTO.getName());
        librarian.setSurname(librarianDTO.getSurname());
        librarian.setEmail(librarianDTO.getEmail());
        librarian.setGender(librarianDTO.getGender());
        librarian.setBirthDate(librarianDTO.getBirthDate());
        librarian.setLibraryForLibrarian(LibraryDtoMapper.toEntity(librarianDTO.getLibrary()));
        librarian.setPassword(librarianDTO.getPassword());
        return librarian;
    }

}
