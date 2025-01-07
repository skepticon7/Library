package com.youssef.library.cities.Repository;

import ch.qos.logback.core.net.QueueFactory;
import com.youssef.library.cities.Entities.Librarian;
import com.youssef.library.cities.Entities.Library;
import com.youssef.library.cities.Entities.Person;
import com.youssef.library.cities.Entities.Visitor;
import com.youssef.library.cities.Service.Visitor.VisitorService;
import org.hibernate.internal.util.StringHelper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, String> {

    @Query("SELECT v FROM Visitor v WHERE v.id = :id")
    Visitor findVisitorById(@Param("id") String id);

    @Query("SELECT p FROM Person p WHERE p.surname = :username")
    Person findPersonByUsername(@Param("username") String username);

    Person findPersonByEmail(@Param("email") String email);


    @Query("SELECT l FROM Librarian l WHERE l.libraryForLibrarian.id = :libraryId")
    List<Librarian> findAllLibrariansInLibrary(@Param("libraryId") String libraryId);

    @Query("SELECT v FROM Visitor v")
    List<Visitor> findAllVisitors();

    @Query("SELECT l FROM Librarian l")
    List<Librarian> findAllLibrarians();

    @Query("SELECT l FROM Librarian l WHERE LOWER(l.name) LIKE LOWER(CONCAT('%',:name,'%')) AND l.libraryForLibrarian.id = :libraryId")
    List<Librarian> findLibrariansInLibraryContainingName(@Param("name") String name , @Param("libraryId") String libraryId);

    @Query("SELECT p FROM Person p WHERE p.id = :personId")
    Person findPersonById(@Param("personId") String personId);
}
