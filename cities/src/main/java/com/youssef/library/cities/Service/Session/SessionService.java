package com.youssef.library.cities.Service.Session;

import com.youssef.library.cities.Entities.Session;

import java.util.List;

public interface SessionService {
    Session saveSession(Session session , String bookId , String visitorId);
    List<Session> getSessions(String visitorId);
    Session updateSession(Session session);
    Session getSession(String sessionId);
    Session getSessionByBook(String bookId , String visitorId);
}
