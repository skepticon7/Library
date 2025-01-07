package com.youssef.library.cities.DTOs.Review.ReviewInsertion;

import com.youssef.library.cities.Entities.Review;

import java.util.UUID;

public class ReviewInsertionDtoMapper {
    public static Review toEntity(ReviewInsertionDTO reviewInsertionDTO){
        if(reviewInsertionDTO == null) return null;
        Review review = new Review();
        review.setId(UUID.randomUUID().toString());
        review.setReview(reviewInsertionDTO.getReview());
        review.setStars(reviewInsertionDTO.getStars());
        return review;
    }
}
