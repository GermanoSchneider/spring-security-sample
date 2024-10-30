package com.example.springsecuritysample.domain;

import java.util.Collection;

public interface UserRepository {

    User findBy(String username);

    Collection<User> findAll();

    User save(User user);
}
