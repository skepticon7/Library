package com.youssef.library.cities.Web;

import com.youssef.library.cities.DTOs.Session.SessionDTO;
import com.youssef.library.cities.DTOs.Session.SessionDtoMapper;
import com.youssef.library.cities.Entities.Session;
import com.youssef.library.cities.Service.Session.SessionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasAuthority('SCOPE_LIBRARIAN') or hasAuthority('SCOPE_VISITOR')")
    @GetMapping("/getVisitorSessions")
    public ResponseEntity<List<SessionDTO>> getVisitorSessions(@RequestParam("visitorId") String visitorId) {
        List<Session> allVisitorSessions = sessionService.getSessions(visitorId);
        List<SessionDTO> mappedVisitorSessions = allVisitorSessions.stream()
                .map(SessionDtoMapper::toDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(mappedVisitorSessions , HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('SCOPE_LIBRARIAN') or hasAuthority('SCOPE_VISITOR')")
    @GetMapping("/getSession")
    public ResponseEntity<SessionDTO> getSession(@RequestParam("sessionId") String sessionId){
        Session visitorSession = sessionService.getSession(sessionId);
        return new ResponseEntity<>(SessionDtoMapper.toDto(visitorSession), HttpStatus.OK);
    }
}
