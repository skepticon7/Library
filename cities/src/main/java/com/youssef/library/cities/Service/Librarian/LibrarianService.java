package com.youssef.library.cities.Service.Librarian;

import com.youssef.library.cities.Entities.Librarian;

import java.util.List;

public interface LibrarianService {
    Librarian saveLibrarian(Librarian librarian , String libraryId);
    List<Librarian> getAllLibrariansInLibrary(String libraryId);
    List<Librarian> searchForLibrarianContainingName(String name , String libraryId);
    List<Librarian> getAllLibrarians();
}
