package com.youssef.library.cities.Web;

import com.youssef.library.cities.DTOs.Book.BookDTO;
import com.youssef.library.cities.DTOs.Book.BookDtoMapper;
import com.youssef.library.cities.Entities.Book;
import com.youssef.library.cities.Entities.Library;
import com.youssef.library.cities.Service.Book.BookService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("book")
@CrossOrigin("*")
public class BookController {

    private BookService bookService;

    @PostMapping("/addBook")
    public ResponseEntity<Book> addBook(@RequestBody @Valid BookDTO book , @RequestParam("shelfId") String shelfId){
        Book newBook = bookService.saveBook(BookDtoMapper.toEntity(book) , shelfId);
        return new ResponseEntity<>(newBook, HttpStatus.CREATED);
    }

    //after

    @GetMapping("filteredBooks")
    public ResponseEntity<List<BookDTO>> getFilteredBooks(
            @RequestParam("shelfId") String shelfId,
            @RequestParam("order") String order,
            @RequestParam("orderType") String orderType,
            @RequestParam("minPages") String minPages,
            @RequestParam("maxPages") String maxPages,
            @RequestParam("minYear") String minYear,
            @RequestParam("maxYear") String maxYear
            ){
            List<Book> allBooks;
            if(orderType.isEmpty()){
                allBooks  = bookService.filterBooksInShelfWithoutOrder(shelfId,minPages,maxPages,minYear,maxYear);
            }else{
                allBooks = bookService.filterBooksInShelfByOrder(shelfId,order,orderType,minPages,maxPages,minYear,maxYear);
            }
            List<BookDTO> mappedBooks = allBooks
                    .stream()
                    .map(BookDtoMapper::toDto)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(mappedBooks, HttpStatus.OK);
    }

    @GetMapping("filteredLibrariesAll")
    public ResponseEntity<List<BookDTO>> getAllFilteredLibrariesAll(
        @RequestParam("order") String order,
        @RequestParam("orderType") String orderType,
        @RequestParam("minPages") String minPages,
        @RequestParam("maxPages") String maxPages,
        @RequestParam("minYear") String minYear,
        @RequestParam("maxYear") String maxYear,
        @RequestParam("subName") String subName
    ){
        List<Book> allBooks;
        if(!orderType.isEmpty()){
            if(orderType.equals("pages"))
                allBooks = bookService.filterBooksByPages(subName,order,minPages,maxPages,minYear,maxYear);
            else
                allBooks = bookService.filterBooksByYear(subName,order, minPages, maxPages, minYear, maxYear);
        }else
            allBooks = bookService.filterAllBooks(subName, minPages, maxPages, minYear, maxYear);
        List<BookDTO> mappedBooks = allBooks
                .stream()
                .map(BookDtoMapper::toDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(mappedBooks, HttpStatus.OK);

    }

    @GetMapping("getBook")
    public ResponseEntity<HashMap<String, Object>> getBook(
            @RequestParam("shelfId") String shelfId,
            @RequestParam("bookId") String bookId,
            @RequestParam("visitorId") String visitorId
    )
    {
        Book specificBook = bookService.getSpecificBook(shelfId,bookId);
        boolean sessionCheck = bookService.hasSameBookSession(visitorId,bookId);
        HashMap<String, Object> response = new HashMap<>();
        response.put("book", BookDtoMapper.toDto(specificBook));
        response.put("sessionCheck", sessionCheck);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
