package com.youssef.library.cities.DTOs.Person;

import com.youssef.library.cities.Enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {
    private String id;
    private String name;
    private String surname;
    private String email;
    private Gender gender;
    private LocalDate birthDate;
    private String type;
    private String libraryId;
}
