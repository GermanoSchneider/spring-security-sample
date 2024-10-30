package com.example.springsecuritysample.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;

import static com.example.springsecuritysample.domain.ConstraintValidator.validate;

@Value
@Builder
public class User {

    @NotBlank(message = "should not be blank")
    String name;

    @NotBlank(message = "should not be blank")
    String password;

    public static class UserBuilder {

        public User build() {

            var user = new User(name, password);

            validate(user);

            return user;
        }

    }

}
