package com.example.springsecuritysample.application;

import com.example.springsecuritysample.domain.User;
import com.example.springsecuritysample.domain.UserRepository;
import com.example.springsecuritysample.domain.UserService;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
class UserApplicationService implements UserService {

    private final UserRepository userRepository;

    UserApplicationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findBy(String username) {

        return userRepository.findBy(username);
    }

    @Override
    public Collection<User> findAll() {

        return userRepository.findAll();
    }
}
