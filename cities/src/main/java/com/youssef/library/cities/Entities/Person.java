package com.youssef.library.cities.Entities;

import com.youssef.library.cities.Enums.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE",length = 10)
@Data
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "PERSON", uniqueConstraints = @UniqueConstraint(columnNames = {"name", "surname","email"}))
public class Person {
    @Id
    private String id;
    private String name;
    private String surname;
    private LocalDate birthDate;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String email;
    private String password;
}


