package com.example.springsecuritysample.infrastructure;

import com.example.springsecuritysample.domain.User;
import com.example.springsecuritysample.domain.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    private final PasswordEncoder passwordEncoder;

    UserRepositoryImpl(UserJpaRepository userJpaRepository, PasswordEncoder passwordEncoder) {
        this.userJpaRepository = userJpaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findBy(String username) {

       var entity = userJpaRepository.findByName(username);

       return UserMapper.from(entity);
    }

    @Override
    public Collection<User> findAll() {

        return ((List<UserEntity>) userJpaRepository
                .findAll())
                .stream()
                .map(UserMapper::from)
                .toList();
    }

    @Override
    public User save(User user) {

        var userWithEncodedPassword = user.toBuilder()
                .password(passwordEncoder.encode(user.getPassword()))
                .build();

        var entity = UserMapper.from(userWithEncodedPassword);

        var newUser = userJpaRepository.save(entity);

        return UserMapper.from(newUser);
    }
}
