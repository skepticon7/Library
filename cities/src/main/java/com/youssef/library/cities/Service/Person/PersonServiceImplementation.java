package com.youssef.library.cities.Service.Person;

import com.youssef.library.cities.DTOs.Person.PersonDTO;
import com.youssef.library.cities.Entities.Person;
import com.youssef.library.cities.ExceptionHandlers.UserNotFoundException;
import com.youssef.library.cities.Repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PersonServiceImplementation implements PersonService{

    private ModelMapper modelMapper;
    private PersonRepository personRepository;

    @Override
    public Person updateUser(String personId, PersonDTO personDto) {
        Person person = personRepository.findPersonById(personId);
        if(person == null)
            throw new UserNotFoundException("USER NOT FOUND");
        modelMapper.map(personDto , person);
        return personRepository.save(person);
    }
}
