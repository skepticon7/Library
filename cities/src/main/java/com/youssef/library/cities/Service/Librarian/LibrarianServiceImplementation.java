package com.youssef.library.cities.Service.Librarian;


import com.youssef.library.cities.DTOs.Librarian.LibrarianDTO;
import com.youssef.library.cities.DTOs.Librarian.LibrarianDtoMapper;
import com.youssef.library.cities.DTOs.Library.LibraryDTO;
import com.youssef.library.cities.Entities.Librarian;
import com.youssef.library.cities.Entities.Library;
import com.youssef.library.cities.ExceptionHandlers.NotFoundException;
import com.youssef.library.cities.ExceptionHandlers.ServerErrorException;
import com.youssef.library.cities.Repository.LibraryRepository;
import com.youssef.library.cities.Repository.PersonRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class LibrarianServiceImplementation implements LibrarianService {

    private LibraryRepository libraryRepository;
    private PersonRepository personRepository;

    @Override
    public Librarian saveLibrarian(Librarian librarian, String libraryId) {
        try {
            Library library = libraryRepository.findLibraryById(libraryId);
            if(library == null)
                throw new NotFoundException("Library");
            String hashedPw = BCrypt.hashpw(librarian.getPassword() , BCrypt.gensalt());
            librarian.setId(UUID.randomUUID().toString());
            librarian.setPassword(hashedPw);
            library.getLibrariansInLibrary().add(librarian);
            librarian.setLibraryForLibrarian(library);
            return personRepository.save(librarian);
        }catch (DataAccessException e){
            throw new ServerErrorException("internal server error , failed to save librarian to the DB");
        }
    }

    @Override
    public List<Librarian> getAllLibrariansInLibrary(String libraryId) {
        Library library = libraryRepository.findLibraryById(libraryId);
        if(library == null)
            throw new NotFoundException("Library");
        return personRepository.findAllLibrariansInLibrary(libraryId);
    }

    @Override
    public List<Librarian> searchForLibrarianContainingName(String name , String libraryId) {
        return personRepository.findLibrariansInLibraryContainingName(name , libraryId);
    }

    @Override
    public List<Librarian> getAllLibrarians() {
        return personRepository.findAllLibrarians();
    }



}
