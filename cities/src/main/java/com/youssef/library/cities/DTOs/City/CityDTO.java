package com.youssef.library.cities.DTOs.City;

import com.youssef.library.cities.DTOs.Library.LibraryDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityDTO {
    private String id;
    private String name;
    private String photo;
    private int librarySize;
}
