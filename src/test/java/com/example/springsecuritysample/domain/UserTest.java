package com.example.springsecuritysample.domain;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void should_build_user_with_success() {

        assertDoesNotThrow(user()::build);
    }

    @Test
    void should_fail_when_trying_to_build_user_without_name() {

        var exception = assertThrows(ConstraintViolationException.class, () -> user().name("").build());

        assertEquals("name: should not be blank", exception.getMessage());
    }

    @Test
    void should_fail_when_trying_to_build_user_without_password() {

        var exception = assertThrows(ConstraintViolationException.class, () -> user().password("").build());

        assertEquals("password: should not be blank", exception.getMessage());
    }

    @Test
    void should_fail_when_trying_to_build_user_without_city() {

        var exception = assertThrows(ConstraintViolationException.class, () -> user().city("").build());

        assertEquals("city: should not be blank", exception.getMessage());
    }

    @Test
    void should_fail_when_trying_to_build_user_without_birthday() {

        var exception = assertThrows(ConstraintViolationException.class, () -> user().birthday(null).build());

        assertEquals("birthday: should not be null", exception.getMessage());
    }

    @Test
    void should_get_the_users_age() {

        assertEquals(47, user().build().getAge());
    }

    private User.UserBuilder user() {

        return User.builder().name("John")
                .password("1234")
                .city("Vancouver")
                .birthday(LocalDate.of(1977, 10, 24));
    }

}
