package com.youssef.library.cities.Service.Visitor;

import com.youssef.library.cities.DTOs.Visitor.VisitorDTO;
import com.youssef.library.cities.DTOs.Visitor.VisitorDtoMapper;
import com.youssef.library.cities.Entities.Person;
import com.youssef.library.cities.Entities.Visitor;
import com.youssef.library.cities.ExceptionHandlers.ServerErrorException;
import com.youssef.library.cities.ExceptionHandlers.UserAlreadyExistsException;
import com.youssef.library.cities.Repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class VisitorServiceImplementation implements VisitorService {

    private final PasswordEncoder passwordEncoder;
    private PersonRepository personRepository;

    @Override
    public Visitor saveVisitor(Visitor visitor) {
        try {
            Person checkingVisitor = personRepository.findPersonByEmail(visitor.getEmail());
            if(checkingVisitor != null) throw new UserAlreadyExistsException("user already exists");
            String hashedPw = passwordEncoder.encode(visitor.getPassword());
            visitor.setId(UUID.randomUUID().toString());
            visitor.setPassword(hashedPw);
            return personRepository.save(visitor);
        }catch (DataAccessException e){
            throw new ServerErrorException("Internal server error , failed to save the visitor to the DB");
        }

    }



    @Override
    public List<Visitor> getAllVisitors() {
        return personRepository.findAllVisitors();
    }

}
