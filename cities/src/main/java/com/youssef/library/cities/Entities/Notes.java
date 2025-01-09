package com.youssef.library.cities.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Notes {
    @Id
    private String id;

    @ManyToOne
    private Book book;

    @ManyToOne
    private Person person;

    private int pageNumber;

    @Length(max = 500)
    private String note;

}
