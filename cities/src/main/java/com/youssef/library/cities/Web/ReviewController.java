package com.youssef.library.cities.Web;

import com.youssef.library.cities.DTOs.Review.ReviewInsertion.ReviewInsertionDTO;
import com.youssef.library.cities.DTOs.Review.ReviewInsertion.ReviewInsertionDtoMapper;
import com.youssef.library.cities.DTOs.Review.ReviewRetrieval.ReviewRetrievalDTO;
import com.youssef.library.cities.DTOs.Review.ReviewRetrieval.ReviewRetrievalDtoMapper;
import com.youssef.library.cities.Entities.Review;
import com.youssef.library.cities.Service.Review.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@AllArgsConstructor
public class ReviewController {

    private ReviewService reviewService;

    @GetMapping("/getByBook")
    public ResponseEntity<List<ReviewRetrievalDTO>> getReviewsByBookId(
            @RequestParam("bookId") String bookId
    ){
        List<Review> reviews = reviewService.findAllReviewsByBook(bookId);
        List<ReviewRetrievalDTO> mappedReviews = reviews
                .stream()
                .map(ReviewRetrievalDtoMapper::toDto).toList();
        return new ResponseEntity<>(mappedReviews , HttpStatus.OK);
    }


    @GetMapping("/getByPerson")
    public ResponseEntity<List<ReviewRetrievalDTO>> getReviewsByPersonId(
            @RequestParam("personId") String personId
    ){
        List<Review> reviews = reviewService.findAllReviewsByPerson(personId);
        List<ReviewRetrievalDTO> mappedReviews = reviews
                .stream()
                .map(ReviewRetrievalDtoMapper::toDto).toList();
        return new ResponseEntity<>(mappedReviews , HttpStatus.OK);
    }

    @PostMapping("/addReview")
    public ResponseEntity<ReviewRetrievalDTO> addReview(
            @RequestBody ReviewInsertionDTO reviewInsertionDTO,
            @RequestParam("bookId") String bookId,
            @RequestParam("personId") String personId
    ){
        Review newReview = reviewService.saveReview(ReviewInsertionDtoMapper.toEntity(reviewInsertionDTO) , personId , bookId);
        return new ResponseEntity<>(ReviewRetrievalDtoMapper.toDto(newReview) , HttpStatus.CREATED);
    }

}
