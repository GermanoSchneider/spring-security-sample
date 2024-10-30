package com.example.springsecuritysample.infrastructure;

import org.springframework.data.repository.CrudRepository;

interface UserJpaRepository extends CrudRepository<UserEntity, Long> {
    UserEntity findByName(String name);
}
