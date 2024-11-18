package com.youssef.library.cities.Web;


import com.youssef.library.cities.DTOs.Session.SessionDTO;
import com.youssef.library.cities.DTOs.Session.SessionDtoMapper;
import com.youssef.library.cities.Repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class WebSocketController {

    private ConcurrentHashMap<String, String> userConnectionMap = new ConcurrentHashMap<>();

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    SessionRepository sessionRepository;


    @MessageMapping("/start")
    public void startReadingSession(String sessionId , SimpMessageHeaderAccessor headerAccessor){
        System.out.println("session started");
        System.out.println("session id : " + headerAccessor.getSessionId());
        String message = "Reading session started for session ID: " + sessionId;
        messagingTemplate.convertAndSend("/topic/session/" + sessionId , message);
    }

    @MessageMapping("/stop")
    public void stopReadingSession(String sessionId){
        System.out.println("session stopped");
        System.out.println("session id : " + sessionId);
        String message = "Reading session stopped for session ID: " + sessionId;
        messagingTemplate.convertAndSend("/topic/session/" + sessionId , message);
    }

    @MessageMapping("/update")
    public void updateReadingSession(String sessionId){
        System.out.println("session updated");
        System.out.println("session id : " + sessionId);
        String message = "Reading session updated for session ID: " + sessionId;
        messagingTemplate.convertAndSend("/topic/session/" + sessionId , message);
    }

}
