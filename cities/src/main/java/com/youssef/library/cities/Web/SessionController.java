package com.youssef.library.cities.Web;

import com.youssef.library.cities.DTOs.Session.SessionDTO;
import com.youssef.library.cities.DTOs.Session.SessionDtoMapper;
import com.youssef.library.cities.Entities.Session;
import com.youssef.library.cities.Service.Session.SessionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpClient;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("session")
@CrossOrigin("*")
public class SessionController {

    private SessionService sessionService;

    @GetMapping("/getVisitorSessions")
    public ResponseEntity<List<SessionDTO>> getVisitorSessions(@RequestParam("visitorId") String visitorId) {
        List<Session> allVisitorSessions = sessionService.getSessions(visitorId);
        List<SessionDTO> mappedVisitorSessions = allVisitorSessions.stream()
                .map(SessionDtoMapper::toDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(mappedVisitorSessions , HttpStatus.OK);
    }
}
