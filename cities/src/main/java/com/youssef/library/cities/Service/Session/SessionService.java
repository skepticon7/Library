package com.youssef.library.cities.Service.Session;

import com.youssef.library.cities.Entities.Session;

import java.util.List;

public interface SessionService {
    Session saveSession(Session session , String bookId , String visitorId);
    List<Session> getSessions(String visitorId);

    Session getSession(String sessionId);
}
