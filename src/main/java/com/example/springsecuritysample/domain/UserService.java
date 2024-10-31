package com.example.springsecuritysample.domain;

import java.util.Collection;

public interface UserService {

    User findBy(String username);

    Collection<User> findAll();
}

