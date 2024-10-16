package com.youssef.library.cities.DTOs.Library;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class LibraryDTO {
    private String id;
    @NotEmpty
    private String name;
    @Min(50) @Max(200)
    private int visitorsCapacity;
    @Min(5)
    private int shelvesCapacity;
}
