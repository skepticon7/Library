package com.youssef.library.cities.Repository;

import com.youssef.library.cities.Entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT SUM(b.copies) FROM Book b WHERE b.shelf.id = :shelfId")
    Integer findOccupiedBooksNumber(@Param("shelfId") String shelfId);

    @Query("SELECT b FROM Book b WHERE b.id = :id")
    Book findBookById(@Param("id") String id);


    @Query("SELECT b FROM Book b WHERE b.shelf.id = :shelfId   AND b.pages BETWEEN :minPages AND :maxPages AND b.year BETWEEN :minYear AND :maxYear ORDER BY b.pages")
    List<Book> filterBooksInShelfByPagesAsc(
            @Param("shelfId") String shelfId,
            @Param("minPages") String minPages,
            @Param("maxPages") String maxPages,
            @Param("minYear") String minYear,
            @Param("maxYear") String maxYear
            );

    @Query("SELECT b FROM Book b WHERE b.shelf.id = :shelfId  AND b.pages BETWEEN :minPages AND :maxPages AND b.year BETWEEN :minYear AND :maxYear ORDER BY b.pages DESC")
    List<Book> filterBooksInShelfByPagesDesc(
            @Param("shelfId") String shelfId,
            @Param("minPages") String minPages,
            @Param("maxPages") String maxPages,
            @Param("minYear") String minYear,
            @Param("maxYear") String maxYear
    );

    @Query("SELECT b FROM Book b WHERE b.shelf.id = :shelfId AND b.pages BETWEEN :minPages AND :maxPages AND b.year BETWEEN :minYear AND :maxYear ORDER BY b.year")
    List<Book> filterBooksInShelfByYearAsc(
            @Param("shelfId") String shelfId,
            @Param("minPages") String minPages,
            @Param("maxPages") String maxPages,
            @Param("minYear") String minYear,
            @Param("maxYear") String maxYear
    );

    @Query("SELECT b FROM Book b WHERE b.shelf.id = :shelfId  AND  b.pages BETWEEN :minPages AND :maxPages AND b.year BETWEEN :minYear AND :maxYear ORDER BY b.year DESC")
    List<Book> filterBooksInShelfByYearDesc(
            @Param("shelfId") String shelfId,
            @Param("minPages") String minPages,
            @Param("maxPages") String maxPages,
            @Param("minYear") String minYear,
            @Param("maxYear") String maxYear
    );

    @Query("SELECT b FROM Book b WHERE b.shelf.id = :shelfId  AND b.pages BETWEEN :minPages AND :maxPages AND b.year BETWEEN :minYear AND :maxYear")
    List<Book> filterBooksInShelf(
            @Param("shelfId") String shelfId,
            @Param("minPages") String minPages,
            @Param("maxPages") String maxPages,
            @Param("minYear") String minYear,
            @Param("maxYear") String maxYear
    );


    @Query("SELECT b FROM Book b WHERE (LOWER(b.title) LIKE LOWER(CONCAT('%',:subName,'%')) OR LOWER(b.author) LIKE LOWER(CONCAT('%',:subName,'%'))) AND b.pages BETWEEN :minPages AND :maxPages AND b.year BETWEEN :minYear AND :maxYear ORDER BY b.pages DESC")
    List<Book> filterBooksByPageDesc(String subName, String minPages, String maxPages, String minYear, String maxYear);

    @Query("SELECT b FROM Book b WHERE (LOWER(b.title) LIKE LOWER(CONCAT('%',:subName,'%')) OR LOWER(b.author) LIKE LOWER(CONCAT('%',:subName,'%'))) AND b.pages BETWEEN :minPages AND :maxPages AND b.year BETWEEN :minYear AND :maxYear ORDER BY b.pages")
    List<Book> filterBooksByPageAsc(String subName, String minPages, String maxPages, String minYear, String maxYear);

    @Query("SELECT b FROM Book b WHERE (LOWER(b.title) LIKE LOWER(CONCAT('%',:subName,'%')) OR LOWER(b.author) LIKE LOWER(CONCAT('%',:subName,'%'))) AND b.pages BETWEEN :minPages AND :maxPages AND b.year BETWEEN :minYear AND :maxYear ORDER BY b.year DESC")
    List<Book> filterBooksByYearDesc(String subName, String minPages, String maxPages, String minYear, String maxYear);

    @Query("SELECT b FROM Book b WHERE (LOWER(b.title) LIKE LOWER(CONCAT('%',:subName,'%')) OR LOWER(b.author) LIKE LOWER(CONCAT('%',:subName,'%'))) AND b.pages BETWEEN :minPages AND :maxPages AND b.year BETWEEN :minYear AND :maxYear ORDER BY b.pages")
    List<Book> filterBooksByYearAsc(String subName, String minPages, String maxPages, String minYear, String maxYear);

    @Query("SELECT b FROM Book b WHERE (LOWER(b.title) LIKE LOWER(CONCAT('%',:subName,'%')) OR LOWER(b.author) LIKE LOWER(CONCAT('%',:subName,'%'))) AND b.pages BETWEEN :minPages AND :maxPages AND b.year BETWEEN :minYear AND :maxYear")
    List<Book> filterAllBooks(String subName, String minPages, String maxPages, String minYear, String maxYear);

    @Query("SELECT b FROM Book b WHERE b.id = :bookId AND b.shelf.id = :shelfId")
    Book findSpecificBook(String shelfId, String bookId);

    @Query("SELECT CASE WHEN Count(s) > 0 THEN TRUE ELSE FALSE END FROM Session s WHERE s.visitor.id = :visitorId AND s.book.id = :bookId")
    boolean hasSameBookSession(@Param("visitorId") String visitorId , @Param("bookId") String bookId);
}
