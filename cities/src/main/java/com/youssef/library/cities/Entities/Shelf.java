package com.youssef.library.cities.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.youssef.library.cities.Enums.Category;
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
@Table(name = "SHELF", uniqueConstraints = @UniqueConstraint(columnNames = {"library_id", "shelfNumber"}))
//@IdClass(ShelfId.class)
public class Shelf {
    @Id
    private String id;
    private int shelfNumber;
    @Enumerated(EnumType.STRING)
    private Category category;
    private int booksCapacity;

    @OneToMany(mappedBy = "shelf",fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Book> booksInShelf = new ArrayList<>();



    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(name = "library_id",referencedColumnName = "id")
    private Library libraryForShelf;

}


//@NoArgsConstructor @AllArgsConstructor
//@Embeddable
//class ShelfId implements Serializable {
//    private int shelfNumber;
//    private String library_name;
//    private String city_name;
//}
