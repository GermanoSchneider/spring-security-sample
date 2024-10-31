package com.example.springsecuritysample.infrastructure.database;

import com.example.springsecuritysample.domain.Role;
import com.example.springsecuritysample.domain.User;
import com.example.springsecuritysample.domain.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.MethodMode;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Collection;

import static org.mockito.Mockito.verify;

@DataJpaTest
@ActiveProfiles("test")
@Import({BCryptPasswordEncoder.class, UserRepositoryImpl.class})
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    @SpyBean
    private PasswordEncoder passwordEncoder;

    @SpyBean
    private UserJpaRepository userJpaRepository;

    @Test
    @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
    void should_find_user_with_success() {

        UserEntity entity = new UserEntity(
                null,
                "John",
                passwordEncoder.encode("1234"),
                "Vancouver",
                LocalDate.of(1998, 10, 8),
                Role.USER
        );

        UserEntity newUser = entityManager.persist(entity);

        User user = userRepository.findBy(newUser.getName());

        Assertions.assertEquals(newUser.getId(), user.getId());
        Assertions.assertEquals(newUser.getName(), user.getName());
        Assertions.assertEquals(newUser.getPassword(), user.getPassword());
        Assertions.assertEquals(newUser.getCity(), user.getCity());
        Assertions.assertEquals(newUser.getBirthday(), user.getBirthday());

        verify(userJpaRepository).findByName(newUser.getName());
    }

    @Test
    @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
    void should_find_all_users_with_success() {

        UserEntity firstUserEntity = new UserEntity(
                null,
                "Steve",
                passwordEncoder.encode("1234"),
                "Vancouver",
                LocalDate.of(1998, 10, 8),
                Role.USER
        );

        UserEntity secondUserEntity = new UserEntity(
                null,
                "Mary",
                passwordEncoder.encode("4321"),
                "Seattle",
                LocalDate.of(1995, 5, 10),
                Role.ADMIN
        );

        entityManager.persist(firstUserEntity);
        entityManager.persist(secondUserEntity);

        Collection<User> users = userRepository.findAll();

        org.assertj.core.api.Assertions.assertThat(users).hasSize(2);

        verify(userJpaRepository).findAll();
    }

    @Test
    @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
    void should_save_user_with_success() {

        User user = User.builder()
                .id(null)
                .name("Max")
                .password("1234")
                .city("Vancouver")
                .birthday(LocalDate.of(1998, 10, 8))
                .role(Role.USER)
                .build();

        User newUser = userRepository.save(user);

        UserEntity findUser = entityManager.find(UserEntity.class, newUser.getId());

        Assertions.assertEquals(newUser.getId(), findUser.getId());
        Assertions.assertEquals(newUser.getName(), findUser.getName());
        Assertions.assertEquals(newUser.getPassword(), findUser.getPassword());
        Assertions.assertEquals(newUser.getCity(), findUser.getCity());
        Assertions.assertEquals(newUser.getBirthday(), findUser.getBirthday());

        verify(passwordEncoder).encode(user.getPassword());
        verify(userJpaRepository).save(findUser);
    }
}
