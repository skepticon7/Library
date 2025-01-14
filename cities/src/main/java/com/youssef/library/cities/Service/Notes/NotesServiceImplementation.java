package com.youssef.library.cities.Service.Notes;


import com.youssef.library.cities.Entities.Book;
import com.youssef.library.cities.Entities.Notes;
import com.youssef.library.cities.Entities.Person;
import com.youssef.library.cities.Repository.BookRepository;
import com.youssef.library.cities.Repository.NotesRepository;
import com.youssef.library.cities.Repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class NotesServiceImplementation implements NotesService{

    private NotesRepository notesRepository;
    private BookRepository bookRepository;
    private PersonRepository personRepository;

    @Override
    public List<Notes> getAllNotesByUser(String bookId, String personId , int pageNumber) {
        Book book = bookRepository.findBookById(bookId);
        Person person = personRepository.findPersonById(personId);
        if(book == null || person == null)
            return new ArrayList<>();
        return notesRepository.findAllNotesByPerson(bookId ,personId , pageNumber);
    }

    @Override
    public Notes saveNotes(Notes note, String bookId, String personId) {
        Book book = bookRepository.findBookById(bookId);
        Person person = personRepository.findPersonById(personId);
        if(book == null || person == null)
            return null;
        note.setBook(book);
        note.setPerson(person);
        return notesRepository.save(note);
    }
}
