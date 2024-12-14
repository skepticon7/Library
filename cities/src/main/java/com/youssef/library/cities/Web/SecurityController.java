package com.youssef.library.cities.Web;

import com.youssef.library.cities.DTOs.Auth.LoginDTO;
import com.youssef.library.cities.DTOs.Auth.SignupDTO.SignupDTO;
import com.youssef.library.cities.DTOs.Auth.SignupDTO.SignupDtoMapper;
import com.youssef.library.cities.DTOs.Librarian.LibrarianDTO;
import com.youssef.library.cities.DTOs.Librarian.LibrarianDtoMapper;
import com.youssef.library.cities.DTOs.Person.PersonDTO;
import com.youssef.library.cities.DTOs.Person.PersonDtoMapper;
import com.youssef.library.cities.DTOs.Visitor.VisitorDTO;
import com.youssef.library.cities.DTOs.Visitor.VisitorDtoMapper;
import com.youssef.library.cities.Entities.Librarian;
import com.youssef.library.cities.Entities.Visitor;
import com.youssef.library.cities.Service.Librarian.LibrarianService;
import com.youssef.library.cities.Service.Visitor.VisitorService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class SecurityController {

    private final AuthenticationManager authenticationManager;
    private final JwtEncoder jwtEncoder;
    private VisitorService visitorService;
    private LibrarianService librarianService;

    @GetMapping("/profile")
    public Authentication authentication(Authentication authentication){
        return authentication;
    }

    @PostMapping("/login")
    public Map<String , String> login(@RequestBody LoginDTO loginDTO){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername() , loginDTO.getPassword())
        );
        String scope = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(" "));
        Instant instant = Instant.now();
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .issuedAt(instant)
                .expiresAt(instant.plus(10, ChronoUnit.MINUTES))
                .subject(loginDTO.getUsername())
                .claim("scope",scope)

                .build();
        JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(
                JwsHeader.with(MacAlgorithm.HS512).build(),
                jwtClaimsSet
        );
        String jwt = jwtEncoder.encode(jwtEncoderParameters).getTokenValue();
        Map<String , String> res = new HashMap<>();
        res.put("jwt-token",jwt);
        res.put("roles",scope);
        return res;
    }

//    @PostMapping("/signup/visitor")
//    public ResponseEntity<VisitorDTO> visitorSignup(@RequestBody @Valid VisitorDTO visitor) {
//        Visitor newVisitor = visitorService.saveVisitor(VisitorDtoMapper.toEntity(visitor));
//        return new ResponseEntity<>(VisitorDtoMapper.toDto(newVisitor), HttpStatus.CREATED);
//    }
//
//    @PostMapping("/signup/librarian")
//    public ResponseEntity<LibrarianDTO> librarianSignup(@RequestBody @Valid LibrarianDTO librarian , @RequestParam("libraryId") String libraryId) {
//        Librarian newlibrarian = librarianService.saveLibrarian(LibrarianDtoMapper.toEntity(librarian) , libraryId);
//        return new ResponseEntity<>(LibrarianDtoMapper.toDto(newlibrarian), HttpStatus.CREATED);
//    }

    @PostMapping("/signup")
    public ResponseEntity<PersonDTO> signup(@RequestBody @Valid SignupDTO signupDTO , @RequestParam("libraryId") String libraryId){
        if("visitor".equalsIgnoreCase(signupDTO.getRole())){
            Visitor newVisitor = visitorService.saveVisitor(SignupDtoMapper.toEntityVisitor(signupDTO));
            return new ResponseEntity<>(PersonDtoMapper.toDto(newVisitor), HttpStatus.CREATED);
        }

        Librarian newlibrarian = librarianService.saveLibrarian(SignupDtoMapper.toEntityLibrarian(signupDTO) , libraryId);
        return new ResponseEntity<>(PersonDtoMapper.toDto(newlibrarian), HttpStatus.CREATED);
    }


}
