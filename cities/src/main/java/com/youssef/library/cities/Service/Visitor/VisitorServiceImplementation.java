package com.youssef.library.cities.Service.Visitor;

import com.youssef.library.cities.DTOs.Visitor.VisitorDTO;
import com.youssef.library.cities.DTOs.Visitor.VisitorDtoMapper;
import com.youssef.library.cities.Entities.Visitor;
import com.youssef.library.cities.ExceptionHandlers.ServerErrorException;
import com.youssef.library.cities.Repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class VisitorServiceImplementation implements VisitorService {

    private PersonRepository personRepository;

    @Override
    public Visitor saveVisitor(Visitor visitor) {
        try {
            String hashedPsw = BCrypt.hashpw(visitor.getPassword(), BCrypt.gensalt());
            visitor.setId(UUID.randomUUID().toString());
            visitor.setPassword(hashedPsw);
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
