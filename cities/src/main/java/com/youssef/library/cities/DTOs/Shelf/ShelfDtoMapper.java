package com.youssef.library.cities.DTOs.Shelf;

import com.youssef.library.cities.Entities.Book;
import com.youssef.library.cities.Entities.Shelf;

public class ShelfDtoMapper {
    public static ShelfDTO toDto(Shelf shelf) {
        if(shelf == null) return null;
        ShelfDTO shelfDTO = new ShelfDTO();
        shelfDTO.setId(shelf.getId());
        shelfDTO.setCategory(shelf.getCategory());
        shelfDTO.setShelfNumber(shelf.getShelfNumber());
        shelfDTO.setBooksCapacity(shelf.getBooksCapacity());
        int totalBooks = shelf.getBooksInShelf().stream().mapToInt(Book::getCopies).sum();
        shelfDTO.setCurrentBooks(totalBooks);
        return shelfDTO;
    }

    public static Shelf toEntity(ShelfDTO shelfDTO) {
        if(shelfDTO == null) return null;
        Shelf shelf = new Shelf();
        shelf.setCategory(shelfDTO.getCategory());
        shelf.setShelfNumber(shelfDTO.getShelfNumber());
        shelf.setBooksCapacity(shelfDTO.getBooksCapacity());
        return shelf;
    }
}
