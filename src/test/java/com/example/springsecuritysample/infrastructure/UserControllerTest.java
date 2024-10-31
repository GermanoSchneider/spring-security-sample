package com.example.springsecuritysample.infrastructure;

import com.example.springsecuritysample.domain.Role;
import com.example.springsecuritysample.domain.User;
import com.example.springsecuritysample.domain.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(controllers = UserController.class)
@ContextConfiguration(classes = SecurityTestConfig.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    private final ObjectMapper objectMapper =
            new ObjectMapper()
                    .registerModule(new JavaTimeModule())
                    .registerModule(new Jdk8Module());

    @Test
    @WithMockUser(username = "John", roles = {"USER"})
    void should_get_user_info_with_success() throws Exception {

        User user = User.builder()
                .id(1L)
                .name("John")
                .password("1234")
                .city("Vancouver")
                .birthday(LocalDate.of(1998, 10, 8))
                .role(Role.USER)
                .build();

        Mockito.doReturn(user)
                .when(userRepository)
                .findBy(user.getName());

        var expectedResponse = objectMapper.writeValueAsString(
                new UserDTO(user.getName(), user.getCity(), user.getAge())
        );

        mockMvc.perform(get("/user/info"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.content().json(expectedResponse));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void should_get_all_users_with_success() throws Exception {

        User john = User.builder()
                .id(1L)
                .name("John")
                .password("1234")
                .city("Vancouver")
                .birthday(LocalDate.of(1998, 10, 8))
                .role(Role.USER)
                .build();

        User mary = User.builder()
                .id(2L)
                .name("Mary")
                .password("4321")
                .city("Seattle")
                .birthday(LocalDate.of(1995, 5, 10))
                .role(Role.ADMIN)
                .build();

        Mockito.doReturn(List.of(john, mary))
                .when(userRepository)
                .findAll();

        var expectedResponse = objectMapper.writeValueAsString(
                List.of(
                        new UserDTO(john.getName(), john.getCity(), john.getAge()),
                        new UserDTO(mary.getName(), mary.getCity(), mary.getAge())
                )
        );

        mockMvc.perform(get("/users"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.content().json(expectedResponse));
    }
}
