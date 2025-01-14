package com.youssef.library.cities.Service.Notes;

import com.youssef.library.cities.Entities.Notes;
import org.springframework.stereotype.Service;

import java.util.List;


public interface NotesService {
    List<Notes> getAllNotesByUser(String bookId , String personId , int pageNumber);
    Notes saveNotes(Notes note , String bookId ,String personId);
}
