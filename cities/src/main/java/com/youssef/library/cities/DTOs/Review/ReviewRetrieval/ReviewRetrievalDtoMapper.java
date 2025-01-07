package com.youssef.library.cities.DTOs.Review.ReviewRetrieval;

import com.youssef.library.cities.DTOs.Book.BookDtoMapper;
import com.youssef.library.cities.DTOs.Person.PersonDtoMapper;
import com.youssef.library.cities.Entities.Review;

public class ReviewRetrievalDtoMapper {
    static public ReviewRetrievalDTO toDto(Review review){
        if(review == null) return null;
        ReviewRetrievalDTO reviewRetrievalDTO = new ReviewRetrievalDTO();
        reviewRetrievalDTO.setReview(review.getReview());
        reviewRetrievalDTO.setBook(BookDtoMapper.toDto(review.getBook()));
        reviewRetrievalDTO.setPerson(PersonDtoMapper.toDto(review.getPerson()));
        reviewRetrievalDTO.setStars(review.getStars());
        reviewRetrievalDTO.setId(review.getId());
        reviewRetrievalDTO.setCreatedAt(review.getCreatedAt());
        return reviewRetrievalDTO;
    }
}
