package com.youssef.library.cities.Service.Review;

import com.youssef.library.cities.Entities.Book;
import com.youssef.library.cities.Entities.Person;
import com.youssef.library.cities.Entities.Review;
import com.youssef.library.cities.Repository.BookRepository;
import com.youssef.library.cities.Repository.PersonRepository;
import com.youssef.library.cities.Repository.ReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class ReviewServiceImplementation implements ReviewService{

    private BookRepository bookRepository;
    private PersonRepository personRepository;
    private ReviewRepository reviewRepository;

    @Override
    public List<Review> findAllReviewsByBook(String bookId) {
        return reviewRepository.findAllReviewsByBook(bookId);
    }

    @Override
    public List<Review> findAllReviewsByPerson(String personId) {
        return reviewRepository.findAllReviewsByPerson(personId);
    }

    @Override
    public Review saveReview(Review review , String personId, String bookId) {
        Book book = bookRepository.findBookById(bookId);
        Person person = personRepository.findPersonById(personId);
        review.setBook(book);
        review.setPerson(person);
        review.setCreatedAt(LocalDateTime.now());
        return reviewRepository.save(review);
    }
}
