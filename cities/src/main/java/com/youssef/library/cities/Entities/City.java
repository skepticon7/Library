package com.youssef.library.cities.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "CITY")

public class City {
    @Id
    private String id;
    @Column(unique = true)
    private String name;

    @NotEmpty
    private String photo;

    @OneToMany(mappedBy = "city" , fetch = FetchType.LAZY)
    private List<Library> libraries;

}



