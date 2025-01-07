package com.youssef.library.cities.DTOs.Review.ReviewRetrieval;

import com.youssef.library.cities.DTOs.Book.BookDTO;
import com.youssef.library.cities.DTOs.Person.PersonDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRetrievalDTO {
    private String id;
    private BookDTO book;
    private PersonDTO person;
    private String review;
    private double stars;
    private LocalDateTime createdAt;
}


