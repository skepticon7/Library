package com.youssef.library.cities.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.youssef.library.cities.Enums.Category;
import com.youssef.library.cities.Enums.Era;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "BOOK", uniqueConstraints = @UniqueConstraint(columnNames = {"shelf_id", "title"}))

//@IdClass(BookId.class)
public class Book {
    @Id
    private String id;
    @Column(unique = true)
    private String title;
    private String author;
    private String publisher;
    @Enumerated(EnumType.STRING)
    private Category category;
    private String description;
    private int year;
    private int pages;
    private String cover;
    @Enumerated(EnumType.STRING)
    private Era era;
    private int halfPrice;
    private int onePrice;
    private int oneHalfPrice;
    private String pdfFile;


    @ManyToOne
    @JoinColumn(name = "shelf_id",referencedColumnName = "id")
    private Shelf shelf;

    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Visitor visitor;

    @OneToMany(mappedBy = "book")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Session> sessions = new ArrayList<>();


    @OneToMany(mappedBy = "book")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Notes> notes = new ArrayList<>();

    @OneToMany(mappedBy = "book")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Review> reviews = new ArrayList<>();
}





