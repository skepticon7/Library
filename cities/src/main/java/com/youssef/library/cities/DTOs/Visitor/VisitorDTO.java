package com.youssef.library.cities.DTOs.Visitor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.youssef.library.cities.DTOs.Book.BookDTO;
import com.youssef.library.cities.DTOs.Session.SessionDTO;
import com.youssef.library.cities.Entities.Book;
import com.youssef.library.cities.Entities.Library;
import com.youssef.library.cities.Enums.Gender;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class VisitorDTO {
    private String id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String surname;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate birthDate;
    @NotNull
    private Gender gender;
    @NotEmpty
    private String email;
    @NotEmpty
    private String password;
    private List<BookDTO> books = new ArrayList<>();
    private List<SessionDTO> sessions = new ArrayList<>();
}
