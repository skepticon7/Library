package com.youssef.library.cities.DTOs.Auth.SignupDTO;

import com.youssef.library.cities.Enums.Gender;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupDTO {
    private String id;
    private String name;
    private String surname;
    private String email;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String password;
    private LocalDate birthDate;
    private String role;
    private String libraryId;
}
