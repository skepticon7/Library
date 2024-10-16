package com.youssef.library.cities.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue(value = "LIBRARIAN")
@AllArgsConstructor @NoArgsConstructor
@Data
public class Librarian extends Person {



    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(name = "library_id",referencedColumnName = "id")
    private Library libraryForLibrarian;
}
