package com.youssef.library.cities.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DiscriminatorValue(value = "VISITOR")
@AllArgsConstructor
@NoArgsConstructor
public class Visitor extends Person{

    @OneToMany(mappedBy = "visitor")
    private List<Book> books = new ArrayList<>();

    @OneToMany(mappedBy = "visitor")
    private List<Session> sessions = new ArrayList<>();
}
