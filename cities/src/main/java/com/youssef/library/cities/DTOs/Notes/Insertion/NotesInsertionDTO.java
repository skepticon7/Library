package com.youssef.library.cities.DTOs.Notes.Insertion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotesInsertionDTO {
    private String note;
    private int pageNumber;
}
