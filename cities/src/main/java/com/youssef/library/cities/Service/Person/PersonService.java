package com.youssef.library.cities.Service.Person;

import com.youssef.library.cities.DTOs.Person.PersonDTO;
import com.youssef.library.cities.Entities.Person;

public interface PersonService {
    Person updateUser(String personId , PersonDTO person);
}
