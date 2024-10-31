package com.example.springsecuritysample.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

import static com.example.springsecuritysample.domain.ConstraintValidator.validate;
import static lombok.AccessLevel.PRIVATE;

@Value
@Builder(toBuilder = true)
@AllArgsConstructor(access = PRIVATE)
public class User {

    Long id;

    @NotBlank(message = "should not be blank")
    String name;

    @NotBlank(message = "should not be blank")
    String password;

    @NotBlank(message = "should not be blank")
    String city;

    @NotNull(message = "should not be null")
    LocalDate birthday;

    @NotNull(message = "should not be null")
    Role role;

    public int getAge() {
        return LocalDate.now().getYear() - birthday.getYear();
    }

    public static class UserBuilder {

        public User build() {

            var user = new User(id, name, password, city, birthday, role);

            validate(user);

            return user;
        }

    }

}
