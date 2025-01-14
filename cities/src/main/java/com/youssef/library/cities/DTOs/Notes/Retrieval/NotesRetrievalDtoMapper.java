package com.youssef.library.cities.DTOs.Notes.Retrieval;

import com.youssef.library.cities.Entities.Notes;


public class NotesRetrievalDtoMapper {
    static public NotesRetrievalDTO toDto(Notes notes){
        if(notes == null) return null;
        NotesRetrievalDTO notesRetrievalDTO = new NotesRetrievalDTO();
        notesRetrievalDTO.setId(notes.getId());
        notesRetrievalDTO.setNote(notes.getNote());
        notesRetrievalDTO.setPageNumber(notes.getPageNumber());
        return notesRetrievalDTO;
    }
}
