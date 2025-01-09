package com.youssef.library.cities.Service.User;

import com.youssef.library.cities.Entities.Librarian;
import com.youssef.library.cities.Entities.Person;
import com.youssef.library.cities.Repository.PersonRepository;
import com.youssef.library.cities.Service.Librarian.LibrarianService;
import com.youssef.library.cities.Service.Visitor.VisitorService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final PersonRepository personRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person user = personRepository.findPersonByUsername(username);
        if(user == null) throw new UsernameNotFoundException(username);
        boolean librarianOrNot = user instanceof Librarian;
        return User.builder()
                .username(user.getSurname())
                .password(user.getPassword())
                .roles(librarianOrNot ? new String[]{"VISITOR", "LIBRARIAN"} : new String[]{"VISITOR"})// assuming you have roles defined in the Person entity
                .build();
    }
}
