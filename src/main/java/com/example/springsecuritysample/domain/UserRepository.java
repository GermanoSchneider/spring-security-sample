package com.example.springsecuritysample.domain;

public interface UserRepository {

    User findBy(String username);

    User save(User user);
}
