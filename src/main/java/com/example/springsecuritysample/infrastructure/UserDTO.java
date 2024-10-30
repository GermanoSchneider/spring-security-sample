package com.example.springsecuritysample.infrastructure;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
class UserDTO {

    String name;

    String city;

    int age;
}
