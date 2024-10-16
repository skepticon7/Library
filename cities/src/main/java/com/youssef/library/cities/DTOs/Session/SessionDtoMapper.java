package com.youssef.library.cities.DTOs.Session;

import com.youssef.library.cities.DTOs.Book.BookDtoMapper;
import com.youssef.library.cities.Entities.Session;

public class SessionDtoMapper {
    public static SessionDTO toDto(Session session) {
        if(session == null) return null;
        SessionDTO sessionDTO = new SessionDTO();
        sessionDTO.setId(session.getId());
        sessionDTO.setOriginalTime(session.getOriginalTime());
        sessionDTO.setRemainingTime(session.getRemainingTime());
        sessionDTO.setSessionStatus(session.getSessionStatus());
        sessionDTO.setSessionType(session.getSessionType());
        sessionDTO.setPrice(session.getPrice());
        sessionDTO.setBook(session.getBook());
        sessionDTO.setCurrentPage(session.getCurrentPage());
        return sessionDTO;
    }

    public static Session toEntity(SessionDTO sessionDTO) {
        if(sessionDTO == null) return null;
        Session session = new Session();
        session.setId(sessionDTO.getId());
        session.setOriginalTime(sessionDTO.getOriginalTime());
        session.setRemainingTime(sessionDTO.getRemainingTime());
        session.setSessionStatus(sessionDTO.getSessionStatus());
        session.setSessionType(sessionDTO.getSessionType());
        session.setPrice(sessionDTO.getPrice());
        session.setCurrentPage(sessionDTO.getCurrentPage());;
        return session;
    }
}
