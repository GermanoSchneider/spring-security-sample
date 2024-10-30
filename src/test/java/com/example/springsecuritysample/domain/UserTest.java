package com.example.springsecuritysample.domain;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UserTest {

    @Test
    void should_build_user_with_success() {

        Assertions.assertDoesNotThrow(() -> User.builder()
                .name("John")
                .password("1234")
                .build()
        );
    }

    @Test
    void should_fail_when_trying_to_build_user_without_name() {

        var exception = Assertions.assertThrows(ConstraintViolationException.class, () -> User.builder()
                .password("1234")
                .build()
        );

        Assertions.assertEquals("name: should not be blank", exception.getMessage());
    }

    @Test
    void should_fail_when_trying_to_build_user_without_password() {

        var exception = Assertions.assertThrows(ConstraintViolationException.class, () -> User.builder()
                .name("John")
                .build()
        );

        Assertions.assertEquals("password: should not be blank", exception.getMessage());
    }

}
