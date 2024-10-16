package com.youssef.library.cities.Repository;

import com.youssef.library.cities.Entities.Shelf;
import com.youssef.library.cities.Enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface ShelfRepository extends JpaRepository<Shelf,String> {


    @Query("SELECT s FROM Shelf s WHERE s.id = :id")
    Shelf findShelfById(String id);

    @Query("SELECT s FROM Shelf s WHERE s.libraryForShelf.id = :libraryId ORDER BY s.shelfNumber")
    List<Shelf> findShelvesInLibraryById(@Param("libraryId") String libraryId);


    @Query("SELECT s FROM Shelf s WHERE s.libraryForShelf.name = :libraryName")
    List<Shelf> findShelvesInLibraryByName(String libraryName);

    @Query("SELECT s FROM Shelf s WHERE s.libraryForShelf.id = :libraryId AND s.category = :category")
    List<Shelf> findShelfInLibraryByIdAndCategory(@Param("libraryId") String libraryId, @Param("category") Category category);


    @Query("SELECT s FROM Shelf s WHERE s.libraryForShelf.id = :libraryId ORDER BY s.booksCapacity DESC")
    List<Shelf> filterByLibraryIdAndDescBooks(@Param("libraryId") String libraryId);

    @Query("SELECT s FROM Shelf s WHERE s.libraryForShelf.id = :libraryId ORDER BY s.booksCapacity ASC")
    List<Shelf> filterByLibraryIdAndAscBooks(@Param("libraryId") String libraryId);

    @Query("SELECT s FROM Shelf s WHERE s.libraryForShelf.id = :libraryId AND s.booksCapacity BETWEEN :minCapacity AND :maxCapacity AND SIZE(s.booksInShelf) BETWEEN :minOccupied AND :maxOccupied ORDER BY s.booksCapacity DESC")
    List<Shelf> filterShelvesInLibraryByCapacityDesc(
            @RequestParam("libraryId") String libraryId,
            @RequestParam("minOccupied") String minOccupied,
            @RequestParam("maxOccupied") String maxOccupied,
            @RequestParam("minCapacity") String minCapacity,
            @RequestParam("maxCapacity") String maxCapacity
    );

    @Query("SELECT s FROM Shelf s WHERE s.libraryForShelf.id = :libraryId AND s.booksCapacity BETWEEN :minCapacity AND :maxCapacity AND SIZE(s.booksInShelf) BETWEEN :minOccupied AND :maxOccupied ORDER BY s.booksCapacity")
    List<Shelf> filterShelvesInLibraryByCapacityAsc(
            @RequestParam("libraryId") String libraryId,
            @RequestParam("minOccupied") String minOccupied,
            @RequestParam("maxOccupied") String maxOccupied,
            @RequestParam("minCapacity") String minCapacity,
            @RequestParam("maxCapacity") String maxCapacity
    );

    @Query("SELECT s FROM Shelf s WHERE s.libraryForShelf.id = :libraryId AND s.booksCapacity BETWEEN :minCapacity AND :maxCapacity AND SIZE(s.booksInShelf) BETWEEN :minOccupied AND :maxOccupied ORDER BY SIZE(s.booksInShelf) DESC")
    List<Shelf> filterShelvesInLibraryByOccupiedDesc(
            @RequestParam("libraryId") String libraryId,
            @RequestParam("minOccupied") String minOccupied,
            @RequestParam("maxOccupied") String maxOccupied,
            @RequestParam("minCapacity") String minCapacity,
            @RequestParam("maxCapacity") String maxCapacity
    );

    @Query("SELECT s FROM Shelf s WHERE s.libraryForShelf.id = :libraryId AND s.booksCapacity BETWEEN :minCapacity AND :maxCapacity AND SIZE(s.booksInShelf) BETWEEN :minOccupied AND :maxOccupied ORDER BY SIZE(s.booksInShelf)")
    List<Shelf> filterShelvesInLibraryByOccupiedAsc(
            @RequestParam("libraryId") String libraryId,
            @RequestParam("minOccupied") String minOccupied,
            @RequestParam("maxOccupied") String maxOccupied,
            @RequestParam("minCapacity") String minCapacity,
            @RequestParam("maxCapacity") String maxCapacity
    );

    @Query("SELECT s FROM Shelf s WHERE s.libraryForShelf.id = :libraryId AND s.booksCapacity BETWEEN :minCapacity AND :maxCapacity AND SIZE(s.booksInShelf) BETWEEN :minOccupied AND :maxOccupied")
    List<Shelf> findShelvesInLibraryByFilters(
            @RequestParam("libraryId") String libraryId,
            @RequestParam("minOccupied") String minOccupied,
            @RequestParam("maxOccupied") String maxOccupied,
            @RequestParam("minCapacity") String minCapacity,
            @RequestParam("maxCapacity") String maxCapacity
    );
}
