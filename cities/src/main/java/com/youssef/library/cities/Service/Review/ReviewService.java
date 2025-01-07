package com.youssef.library.cities.Service.Review;

import com.youssef.library.cities.Entities.Review;

import java.util.List;

public interface ReviewService {
    List<Review> findAllReviewsByBook(String bookId);
    List<Review> findAllReviewsByPerson(String personId);
    Review saveReview(Review review , String personId , String bookId);
}
