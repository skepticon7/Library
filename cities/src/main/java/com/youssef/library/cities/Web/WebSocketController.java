package com.youssef.library.cities.Web;


import com.youssef.library.cities.DTOs.Session.SessionDTO;
import com.youssef.library.cities.DTOs.Session.SessionDtoMapper;
import com.youssef.library.cities.Repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.Duration;

@Controller
public class WebSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    SessionRepository sessionRepository;

    @MessageMapping("/start")
    public void startReadingSession(String sessionId){
        System.out.println("session started");
        System.out.println("session id : " + sessionId);
        String message = "Reading session started for session ID: " + sessionId;
        messagingTemplate.convertAndSend("/topic/session/" + sessionId , message);
    }

    @MessageMapping("/stop")
    public void stopReadingSession(SessionDTO session){

    }

    @MessageMapping("/update")
    public void updateReadingSession(SessionDTO session){

    }

}
