package com.youssef.library.cities.DTOs.Auth.SignupDTO;

import com.youssef.library.cities.Entities.Librarian;
import com.youssef.library.cities.Entities.Visitor;

public class SignupDtoMapper {

    public static Visitor toEntityVisitor(SignupDTO signupDTO){
        if(signupDTO == null) return null;
        Visitor visitor = new Visitor();
        visitor.setName(signupDTO.getName());
        visitor.setSurname(signupDTO.getSurname());
        visitor.setEmail(signupDTO.getEmail());
        visitor.setGender(signupDTO.getGender());
        visitor.setPassword(signupDTO.getPassword());
        visitor.setBirthDate(signupDTO.getBirthDate());
        return visitor;
    }

    public static Librarian toEntityLibrarian(SignupDTO signupDTO){
        if(signupDTO == null) return null;
        Librarian librarian = new Librarian();
        librarian.setName(signupDTO.getName());
        librarian.setSurname(signupDTO.getSurname());
        librarian.setEmail(signupDTO.getEmail());
        librarian.setGender(signupDTO.getGender());
        librarian.setPassword(signupDTO.getPassword());
        librarian.setBirthDate(signupDTO.getBirthDate());
        return librarian;
    }

}
