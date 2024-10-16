package com.youssef.library.cities.DTOs.Book;

import com.youssef.library.cities.Enums.Category;
import com.youssef.library.cities.Enums.Era;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor @NoArgsConstructor
public class BookDTO {
    private String id;
    @NotEmpty
    private String title;
    @NotEmpty
    private String author;
    @NotEmpty
    private String publisher;
    private Category category;
    @NotEmpty
    @Size(max = 255 , message = "the description must contains no more than 255 characters")
    private String description;
    private int pages;
    private Era era;
    private int year;
    @Min(1)
    private int copies;
    private String cover;
    private int halfPrice;
    private int onePrice;
    private int oneHalfPrice;
    private String pdfFile;
}
