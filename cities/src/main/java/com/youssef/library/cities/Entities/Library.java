package com.youssef.library.cities.Entities;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "LIBRARY", uniqueConstraints = @UniqueConstraint(columnNames = {"city_id", "name"}))
//@IdClass(LibraryId.class)
public class Library {
    @Id
    private String id;
    private String name;
    private int visitorsCapacity;
    private int shelvesCapacity;



    @ManyToOne
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private City city;

    @OneToMany(mappedBy = "libraryForShelf")
    private List<Shelf> shelvesInLibrary = new ArrayList<>();

    @OneToMany(mappedBy = "libraryForLibrarian")
    private List<Librarian> librariansInLibrary = new ArrayList<>();
}

//@NoArgsConstructor @AllArgsConstructor
//@Embeddable
//class LibraryId implements Serializable {
//    private String name;
//    private String city_name;
//}
