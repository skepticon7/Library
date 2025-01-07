package com.youssef.library.cities.Repository;

import com.youssef.library.cities.Entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review , String> {

    @Query("SELECT r FROM Review r WHERE r.book.id = :bookId")
    List<Review> findAllReviewsByBook(@Param("bookId") String bookId);

    @Query("SELECT r FROM Review r WHERE r.person.id = :personId")
    List<Review> findAllReviewsByPerson(@Param("personId") String personId);
}
