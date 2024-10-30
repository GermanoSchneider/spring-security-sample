package com.example.springsecuritysample.infrastructure;

import com.example.springsecuritysample.domain.User;

class UserMapper {

    static UserEntity from(User user) {

        return new UserEntity(
                user.getId(),
                user.getName(),
                user.getPassword(),
                user.getCity(),
                user.getBirthday()
        );
    }

    static User from(UserEntity userEntity) {

        return User.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .password(userEntity.getPassword())
                .city(userEntity.getCity())
                .birthday(userEntity.getBirthday())
                .build();
    }
}
