package com.youssef.library.cities.DTOs.Person;

import com.youssef.library.cities.Entities.Librarian;
import com.youssef.library.cities.Entities.Person;
import com.youssef.library.cities.Entities.Visitor;

public class PersonDtoMapper {

    public static PersonDTO toDto(Person person){
        if(person == null) return null;
        PersonDTO personDTO = new PersonDTO();
        personDTO.setId(person.getId());
        personDTO.setName(person.getName());
        personDTO.setGender(person.getGender());
        personDTO.setSurname(person.getSurname());
        personDTO.setEmail(person.getEmail());
        personDTO.setBirthDate(person.getBirthDate());
        if(person instanceof Visitor){
            personDTO.setType("Visitor");
            personDTO.setLibraryId(" ");
        }
        else if(person instanceof Librarian){
            personDTO.setType("Librarian");
            personDTO.setLibraryId(((Librarian) person).getLibraryForLibrarian().getId());
        }
        return personDTO;
    }

}
