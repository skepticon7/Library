package com.youssef.library.cities.Service.Librarian;


import com.youssef.library.cities.Entities.Librarian;
import com.youssef.library.cities.Entities.Library;
import com.youssef.library.cities.Entities.Person;
import com.youssef.library.cities.ExceptionHandlers.NotFoundException;
import com.youssef.library.cities.ExceptionHandlers.ServerErrorException;
import com.youssef.library.cities.ExceptionHandlers.UserAlreadyExistsException;
import com.youssef.library.cities.Repository.LibraryRepository;
import com.youssef.library.cities.Repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class LibrarianServiceImplementation implements LibrarianService {

    private final PasswordEncoder passwordEncoder;
    private LibraryRepository libraryRepository;
    private PersonRepository personRepository;

    @Override
    public Librarian saveLibrarian(Librarian librarian , String libraryId) {
        try {
            Library library = libraryRepository.findLibraryById(libraryId);
            if(library == null)
                throw new NotFoundException("Library");
            Person checkingLibrarian = personRepository.findPersonByEmail(librarian.getEmail());
            if(checkingLibrarian != null)
                throw new UserAlreadyExistsException("user already exists");
            String hashedPw = passwordEncoder.encode(librarian.getPassword());
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
