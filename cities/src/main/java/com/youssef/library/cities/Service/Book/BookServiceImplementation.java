package com.youssef.library.cities.Service.Book;

import com.youssef.library.cities.Entities.Book;
import com.youssef.library.cities.Entities.Library;
import com.youssef.library.cities.Entities.Shelf;
import com.youssef.library.cities.ExceptionHandlers.LimitExceededException;
import com.youssef.library.cities.ExceptionHandlers.NotCompatibleException;
import com.youssef.library.cities.ExceptionHandlers.NotFoundException;
import com.youssef.library.cities.ExceptionHandlers.ServerErrorException;
import com.youssef.library.cities.Repository.BookRepository;
import com.youssef.library.cities.Repository.LibraryRepository;
import com.youssef.library.cities.Repository.ShelfRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class BookServiceImplementation implements BookService {

    private BookRepository bookRepository;
    private ShelfRepository shelfRepository;
    private LibraryRepository libraryRepository;

    @Override
    public Book saveBook(Book book, String shelfId) {
        try {
            Shelf shelf = shelfRepository.findShelfById(shelfId);
            if(shelf == null){
                throw new NotFoundException("Shelf");
            }
            book.setCategory(shelf.getCategory());
            if(shelf.getCategory() != book.getCategory()){
                String message = String.format("Shelf with category %s is not compatible with book with category %s", shelf.getCategory(), book.getCategory());
                throw new NotCompatibleException(message);
            }
            book.setId(UUID.randomUUID().toString());
            book.setShelf(shelf);
            shelf.getBooksInShelf().add(book);
            return bookRepository.save(book);
        }catch(DataAccessException e){
            throw new ServerErrorException("Internal Server Error : failed to save the book in the database");
        }

    }

    @Override
    public Book getSpecificBook(String shelfId, String bookId) {
        Shelf shelf = shelfRepository.findShelfById(shelfId);
        if(shelf == null){
            throw new NotFoundException("Shelf");
        }
        if(bookRepository.findSpecificBook(shelfId,bookId) == null)
            throw new NotFoundException("Book");
        return bookRepository.findSpecificBook(shelfId,bookId);
    }


    @Override
    public List<Book> filterBooksInShelfByOrder(String shelfId,String order, String orderType ,String minPages, String maxPages, String minYear, String maxYear) {
        Shelf shelf = shelfRepository.findShelfById(shelfId);
        if (shelf == null)
            throw new NotFoundException("Shelf");
        if (orderType.equals("pages")) {
            if ("DESC".equalsIgnoreCase(order))
                return bookRepository.filterBooksInShelfByPagesDesc(shelfId, minPages, maxPages, minYear, maxYear);
            return bookRepository.filterBooksInShelfByPagesAsc(shelfId, minPages, maxPages, minYear, maxYear );
        } else {
            if ("DESC".equalsIgnoreCase(order))
                return bookRepository.filterBooksInShelfByYearDesc(shelfId, minPages, maxPages, minYear, maxYear );
            return bookRepository.filterBooksInShelfByYearAsc(shelfId, minPages, maxPages, minYear, maxYear);
        }
    }

    @Override
    public List<Book> filterBooksInShelfWithoutOrder(String shelfId, String minPages, String maxPages, String minYear, String maxYear) {
        Shelf shelf = shelfRepository.findShelfById(shelfId);
        if(shelf == null)
            throw new NotFoundException("Shelf");
        return bookRepository.filterBooksInShelf(shelfId,minPages,maxPages,minYear,maxYear);
    }

    @Override
    public List<Book> filterBooksByPages(String subName, String order, String minPages, String maxPages, String minYear, String maxYear) {
        if("DESC".equalsIgnoreCase(order))
            return bookRepository.filterBooksByPageDesc(subName,minPages,maxPages,minYear,maxYear);
        return bookRepository.filterBooksByPageAsc(subName,minPages,maxPages,minYear,maxYear);
    }

    @Override
    public List<Book> filterBooksByYear(String subName, String order, String minPages, String maxPages, String minYear, String maxYear) {
        if("DESC".equalsIgnoreCase(order))
            return bookRepository.filterBooksByYearDesc(subName,minPages,maxPages,minYear,maxYear);
        return bookRepository.filterBooksByYearAsc(subName,minPages,maxPages,minYear,maxYear);
    }

    @Override
    public List<Book> filterAllBooks(String subName, String minPages, String maxPages, String minYear, String maxYear) {
        return bookRepository.filterAllBooks(subName,minPages,maxPages,minYear,maxYear);
    }

    @Override
    public boolean hasSameBookSession(String visitorId, String bookId) {
        return bookRepository.hasSameBookSession(visitorId,bookId);
    }

}
