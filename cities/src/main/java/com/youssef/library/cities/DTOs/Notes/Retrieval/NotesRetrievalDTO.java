package com.youssef.library.cities.DTOs.Notes.Retrieval;

import com.youssef.library.cities.DTOs.Book.BookDTO;
import com.youssef.library.cities.DTOs.Person.PersonDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NotesRetrievalDTO {
    private String id;
    private String note;
    private int pageNumber;
}
