package com.youssef.library.cities.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.security.PrivateKey;
import java.time.LocalDateTime;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    private String id;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "person_id" , referencedColumnName = "id")
    private Person person;

    @ManyToOne
    @JoinColumn(name = "book_id" , referencedColumnName = "id")
    private Book book;

    private String review;
    private double stars;
}
