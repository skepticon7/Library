package com.youssef.library.cities.Web;

import com.stripe.param.PersonCollectionCreateParams;
import com.youssef.library.cities.DTOs.Person.PersonDTO;
import com.youssef.library.cities.DTOs.Person.PersonDtoMapper;
import com.youssef.library.cities.Entities.Person;
import com.youssef.library.cities.Repository.PersonRepository;
import com.youssef.library.cities.Service.Person.PersonService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/person")
@AllArgsConstructor
public class PersonController {

    private PersonService personService;
    private ModelMapper modelMapper;

    @PatchMapping("/updatePersonInfo")
    public ResponseEntity<PersonDTO> updatePersonInfo(
            @RequestParam("personId") String personId,
            @RequestBody PersonDTO personDTO
    ){
        Person person = personService.updateUser(personId , personDTO);
        return new ResponseEntity<>(PersonDtoMapper.toDto(person) , HttpStatus.OK);
    }
}
