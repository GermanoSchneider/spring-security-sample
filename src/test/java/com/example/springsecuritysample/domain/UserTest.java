package com.example.springsecuritysample.domain;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class UserTest {

    @Test
    void should_build_user_with_success() {

        Assertions.assertDoesNotThrow(user()::build);
    }

    @Test
    void should_fail_when_trying_to_build_user_without_name() {

        var exception = Assertions.assertThrows(ConstraintViolationException.class, () -> user().name("").build());

        Assertions.assertEquals("name: should not be blank", exception.getMessage());
    }

    @Test
    void should_fail_when_trying_to_build_user_without_password() {

        var exception = Assertions.assertThrows(ConstraintViolationException.class, () -> user().password("").build());

        Assertions.assertEquals("password: should not be blank", exception.getMessage());
    }

    @Test
    void should_fail_when_trying_to_build_user_without_city() {

        var exception = Assertions.assertThrows(ConstraintViolationException.class, () -> user().city("").build());

        Assertions.assertEquals("city: should not be blank", exception.getMessage());
    }

    @Test
    void should_fail_when_trying_to_build_user_without_birthday() {

        var exception = Assertions.assertThrows(ConstraintViolationException.class, () -> user().birthday(null).build());

        Assertions.assertEquals("birthday: should not be null", exception.getMessage());
    }

    private User.UserBuilder user() {

        return User.builder().name("John")
                .password("1234")
                .city("Vancouver")
                .birthday(LocalDate.of(1977, 10, 24));
    }

}
