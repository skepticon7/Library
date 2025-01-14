package com.youssef.library.cities.Web;

import com.youssef.library.cities.DTOs.Notes.Retrieval.NotesRetrievalDTO;
import com.youssef.library.cities.DTOs.Notes.Retrieval.NotesRetrievalDtoMapper;
import com.youssef.library.cities.Entities.Notes;
import com.youssef.library.cities.Service.Notes.NotesService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/notes")
@AllArgsConstructor
public class NotesController {

    private NotesService notesService;

    @PreAuthorize("hasAuthority('SCOPE_ROLE_VISITOR') or hasAuthority('SCOPE_ROLE_LIBRARIAN')")
    @GetMapping("/getNotes")
    public ResponseEntity<List<NotesRetrievalDTO>> getNotes(
            @RequestParam("bookId") String bookId,
            @RequestParam("personId") String personId,
            @RequestParam("pageNumber") int pageNumber
    ){
        List<Notes> allNotes = notesService.getAllNotesByUser(bookId , personId , pageNumber);
        List<NotesRetrievalDTO> mappedNotes = allNotes.stream()
                .map(NotesRetrievalDtoMapper::toDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(mappedNotes , HttpStatus.OK);
    }
}
