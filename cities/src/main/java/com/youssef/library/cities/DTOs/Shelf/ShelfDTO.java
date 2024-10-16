package com.youssef.library.cities.DTOs.Shelf;

import com.youssef.library.cities.Enums.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class ShelfDTO {
    private String id;
    private int shelfNumber;
    private Category category;
    private int booksCapacity;
    private int currentBooks;
}
