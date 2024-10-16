package com.youssef.library.cities.DTOs.Visitor;

import com.youssef.library.cities.DTOs.Book.BookDtoMapper;
import com.youssef.library.cities.Entities.Visitor;

import java.util.stream.Collectors;

public class VisitorDtoMapper {

    public static VisitorDTO toDto(Visitor visitor) {
        if(visitor == null) return null;
        VisitorDTO visitorDTO = new VisitorDTO();
        visitorDTO.setName(visitor.getName());
        visitorDTO.setEmail(visitor.getEmail());
        visitorDTO.setGender(visitor.getGender());
        visitorDTO.setBirthDate(visitor.getBirthDate());
        visitorDTO.setSurname(visitor.getSurname());
        visitorDTO.setBooks(visitor.getBooks().stream().map(BookDtoMapper::toDto).collect(Collectors.toList()));
        visitorDTO.setPassword(visitor.getPassword());
        return visitorDTO;
    }

    public static Visitor toEntity(VisitorDTO visitorDTO) {
        if(visitorDTO == null) return null;
        Visitor visitor = new Visitor();
        visitor.setName(visitorDTO.getName());
        visitor.setEmail(visitorDTO.getEmail());
        visitor.setGender(visitorDTO.getGender());
        visitor.setBirthDate(visitorDTO.getBirthDate());
        visitor.setSurname(visitorDTO.getSurname());
        visitor.setPassword(visitorDTO.getPassword());
        visitor.setBooks(visitorDTO.getBooks().stream().map(BookDtoMapper::toEntity).collect(Collectors.toList()));
        return visitor;
    }

}
