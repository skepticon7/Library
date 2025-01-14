package com.youssef.library.cities.Repository;

import com.youssef.library.cities.Entities.Notes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotesRepository extends JpaRepository<Notes , String> {

    @Query("SELECT n FROM Notes n WHERE n.book.id = :bookId AND n.person.id = :personId AND n.pageNumber BETWEEN :pageNumber - 5 AND :pageNumber + 5")
    List<Notes> findAllNotesByPerson(
            @Param("bookId") String bookId,
            @Param("personId") String personId,
            @Param("pageNumber") int pageNumber
            );

}
