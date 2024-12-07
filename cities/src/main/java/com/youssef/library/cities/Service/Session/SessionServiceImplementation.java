package com.youssef.library.cities.Service.Session;

import com.youssef.library.cities.Entities.Book;
import com.youssef.library.cities.Entities.Session;
import com.youssef.library.cities.Entities.Visitor;
import com.youssef.library.cities.Enums.SessionStatus;
import com.youssef.library.cities.Enums.SessionType;
import com.youssef.library.cities.Repository.BookRepository;
import com.youssef.library.cities.Repository.PersonRepository;
import com.youssef.library.cities.Repository.SessionRepository;
import com.youssef.library.cities.Service.Visitor.VisitorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class SessionServiceImplementation implements SessionService {

    private final BookRepository bookRepository;
    private PersonRepository personRepository;
    private SessionRepository sessionRepository;
    @Override
    public Session saveSession(Session session, String bookId, String visitorId) {;
        session.setId(UUID.randomUUID().toString());
        Visitor visitor = personRepository.findVisitorById(visitorId);
        Book book = bookRepository.findBookById(bookId);
        session.setVisitor(visitor);
        session.setBook(book);
        session.setSessionStatus(SessionStatus.Active);
        return sessionRepository.save(session);
    }

    @Override
    public List<Session> getSessions(String visitorId) {
        return sessionRepository.findSessionsByUser(visitorId);
    }

    @Override
    public Session updateSession(Session session) {
        return sessionRepository.save(session);
    }

    @Override
    public Session getSession(String sessionId) {
        return sessionRepository.findSessionById(sessionId);
    }

    @Override
    public Session getSessionByBook(String bookId , String visitorId) {
        return sessionRepository.findSessionByBook(bookId , visitorId);
    }
}
