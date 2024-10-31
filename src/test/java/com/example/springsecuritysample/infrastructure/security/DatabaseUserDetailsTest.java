package com.example.springsecuritysample.infrastructure.security;

import com.example.springsecuritysample.domain.Role;
import com.example.springsecuritysample.domain.User;
import com.example.springsecuritysample.domain.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

@ExtendWith(SpringExtension.class)
class DatabaseUserDetailsTest {

    @InjectMocks
    private DatabaseUserDetails databaseUserDetails;

    @Mock
    private UserRepository userRepository;

    @Test
    void should_load_user_by_name_with_success() {

        User user = User.builder()
                .id(1L)
                .name("John")
                .password("1234")
                .city("Vancouver")
                .birthday(LocalDate.of(1998, 10, 8))
                .role(Role.USER)
                .build();

        Mockito
                .doReturn(user)
                .when(userRepository)
                .findBy(user.getName());

       UserDetails userDetails = databaseUserDetails.loadUserByUsername(user.getName());

        GrantedAuthority expectedRole = new SimpleGrantedAuthority(
                "ROLE_" + user.getRole().name()
        );

       Assertions.assertTrue(userDetails.isEnabled());
       Assertions.assertTrue(userDetails.getAuthorities().contains(expectedRole));
       Assertions.assertEquals(user.getName(), userDetails.getUsername());
       Assertions.assertEquals(user.getPassword(), userDetails.getPassword());
    }
}
