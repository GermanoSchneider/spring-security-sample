package com.example.springsecuritysample.infrastructure;

import com.example.springsecuritysample.domain.User;
import com.example.springsecuritysample.domain.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
class DatabaseUserDetails implements UserDetailsService {

    private final UserRepository userRepository;

    DatabaseUserDetails(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User userFromDatabase = userRepository.findBy(username);

        if (userFromDatabase == null) {
            throw new UsernameNotFoundException(username);
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(userFromDatabase.getName())
                .password(userFromDatabase.getPassword())
                .roles(userFromDatabase.getRole().name())
                .build();
    }
}
