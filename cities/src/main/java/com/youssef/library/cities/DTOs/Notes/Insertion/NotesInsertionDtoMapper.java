package com.youssef.library.cities.DTOs.Notes.Insertion;

import com.youssef.library.cities.Entities.Notes;

import java.util.UUID;

public class NotesInsertionDtoMapper {
    static public Notes toEntity(NotesInsertionDTO notesInsertionDTO){
        Notes notes = new Notes();
        notes.setId(UUID.randomUUID().toString());
        notes.setPageNumber(notesInsertionDTO.getPageNumber());
        notes.setNote(notesInsertionDTO.getNote());
        return notes;
    }
}
