package com.youssef.library.cities.Repository;

import com.youssef.library.cities.Entities.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<Session , String> {

    @Query("SELECT s FROM Session s WHERE s.visitor.id = :visitorId ORDER BY CASE WHEN s.sessionStatus = 'Active' THEN 1 ELSE 2 END")
    List<Session> findSessionsByUser(@Param("visitorId") String visitorId);

    Session findSessionById(String sessionId);
}
