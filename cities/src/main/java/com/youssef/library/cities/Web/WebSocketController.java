package com.youssef.library.cities.Web;


import com.youssef.library.cities.DTOs.Session.SessionDTO;
import com.youssef.library.cities.DTOs.Session.SessionDtoMapper;
import com.youssef.library.cities.DTOs.WebSocket.WebSocketDTO;
import com.youssef.library.cities.Entities.Session;
import com.youssef.library.cities.Enums.SessionStatus;
import com.youssef.library.cities.Repository.SessionRepository;
import com.youssef.library.cities.Service.Session.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.time.Duration;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class WebSocketController {

//    private ConcurrentHashMap<String, String> userConnectionMap = new ConcurrentHashMap<>();

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    SessionService sessionService;


    @MessageMapping("/start")
    public void startReadingSession(@Payload WebSocketDTO payload ,SimpMessageHeaderAccessor headerAccessor){

        String message = "Reading session started for session ID: " + payload.getSessionId();
        messagingTemplate.convertAndSend("/topic/session/" + headerAccessor.getSessionId() , message);
    }

    @MessageMapping("/stop")
    public void stopReadingSession(@Payload WebSocketDTO payload , SimpMessageHeaderAccessor headerAccessor){
        Session originalSession = sessionService.getSession(payload.getSessionId());
        originalSession.setRemainingTime(Duration.ofHours(0L).plusMinutes(0L).plusSeconds(0L));
        originalSession.setSessionStatus(SessionStatus.Finished);
        originalSession.setCurrentPage(payload.getCurrentPage());
        sessionService.updateSession(originalSession);
        String message = "Reading session stopped for session ID: " + payload.getSessionId();
        messagingTemplate.convertAndSend("/topic/session/" + headerAccessor.getSessionId()  , message);
    }

    @MessageMapping("/update")
    public void updateReadingSession(@Payload WebSocketDTO payload){
        Session originalSession = sessionService.getSession(payload.getSessionId());
        Duration duration = Duration.ofSeconds(payload.getRemainingTime());
        long hours = duration.toHours();
        long minutes = duration.toMinutes();
        long seconds = duration.getSeconds() % 60;
        originalSession.setCurrentPage(payload.getCurrentPage());
        originalSession.setRemainingTime(Duration.ofHours(hours).plusMinutes(minutes).plusSeconds(seconds));
        sessionService.updateSession(originalSession);
        String message = "Reading session updated for session ID: " + payload.getSessionId();
        messagingTemplate.convertAndSend("/topic/session/" + payload.getSessionId() , message);
    }

}
