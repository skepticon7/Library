package com.youssef.library.cities.DTOs.Book;

import com.youssef.library.cities.Entities.Book;

public class BookDtoMapper {
    public static BookDTO toDto(Book book) {
        if(book == null) return null;
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(book.getId());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setAuthor(book.getAuthor());
        bookDTO.setYear(book.getYear());
        bookDTO.setCategory(book.getCategory());
        bookDTO.setCopies(book.getCopies());
        bookDTO.setDescription(book.getDescription());
        bookDTO.setPublisher(book.getPublisher());
        bookDTO.setPages(book.getPages());
        bookDTO.setCover(book.getCover());
        bookDTO.setEra(book.getEra());
        bookDTO.setHalfPrice(book.getHalfPrice());
        bookDTO.setOneHalfPrice(book.getOneHalfPrice());
        bookDTO.setOnePrice(book.getOnePrice());
        bookDTO.setPdfFile(book.getPdfFile());
        return bookDTO;
    }

    public static Book toEntity(BookDTO bookDTO) {
        if(bookDTO == null) return null;
        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setYear(bookDTO.getYear());
        book.setCategory(bookDTO.getCategory());
        book.setDescription(bookDTO.getDescription());
        book.setPublisher(bookDTO.getPublisher());
        book.setCopies(bookDTO.getCopies());
        book.setPages(bookDTO.getPages());
        book.setCover(bookDTO.getCover());
        book.setEra(bookDTO.getEra());
        book.setHalfPrice(bookDTO.getHalfPrice());
        book.setOneHalfPrice(bookDTO.getOneHalfPrice());
        book.setOnePrice(bookDTO.getOnePrice());
        book.setPdfFile(bookDTO.getPdfFile());
        return book;
    }
}
