package com.youssef.library.cities.Service.Book;

import com.youssef.library.cities.Entities.Book;

import java.util.List;

public interface BookService {
    Book saveBook(Book book , String shelfId);



    //AFTER FRONT
   Book getSpecificBook(String shelfId,String bookId);

   List<Book> filterBooksInShelfByOrder(String shelfId,String order , String orderType , String minPages,String maxPages,String minYear,String maxYear);
   List<Book> filterBooksInShelfWithoutOrder(String shelfId,String minPages,String maxPages,String minYear,String maxYear);

   List<Book> filterBooksByPages(String subName , String order , String minPages,String maxPages,String minYear,String maxYear);
   List<Book> filterBooksByYear(String subName , String order  , String minPages,String maxPages,String minYear,String maxYear);

   List<Book> filterAllBooks(String subName , String minPages,String maxPages,String minYear,String maxYear);

   boolean hasSameBookSession(String visitorId,String bookId);
}
