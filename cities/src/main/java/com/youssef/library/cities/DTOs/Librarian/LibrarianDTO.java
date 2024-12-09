package com.youssef.library.cities.DTOs.Librarian;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.youssef.library.cities.DTOs.Library.LibraryDTO;
import com.youssef.library.cities.Entities.Library;
import com.youssef.library.cities.Enums.Gender;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;


import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
public class LibrarianDTO {
    @NotEmpty
    private String name;
    @NotEmpty
    private String surname;

    private LocalDate birthDate;
    @NotNull
    private Gender gender;
    @NotEmpty
    private String email;
    @NotEmpty
    private String password;
    private LibraryDTO library;
}
