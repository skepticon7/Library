package com.youssef.library.cities.DTOs.Review.ReviewInsertion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewInsertionDTO {
    private String review;
    private int stars;
}
