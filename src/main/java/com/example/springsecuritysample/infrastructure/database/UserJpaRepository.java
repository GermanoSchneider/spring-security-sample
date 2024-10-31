package com.example.springsecuritysample.infrastructure.database;

import org.springframework.data.repository.CrudRepository;

interface UserJpaRepository extends CrudRepository<UserEntity, Long> {
    UserEntity findByName(String name);
}
