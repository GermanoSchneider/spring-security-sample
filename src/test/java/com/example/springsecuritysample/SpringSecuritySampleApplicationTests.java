package com.example.springsecuritysample;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringSecuritySampleApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void should_get_user_info_with_success() {

        ResponseEntity<String> response = restTemplate.withBasicAuth("John", "1234")
                .getForEntity("/user/info", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void should_get_all_users_with_success() {

        ResponseEntity<String> response = restTemplate.withBasicAuth("Mary", "4321")
                .getForEntity("/user/info", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void should_fail_when_trying_to_get_user_info_with_invalid_user() {

        ResponseEntity<String> response = restTemplate.withBasicAuth("Max", "4567")
                .getForEntity("/user/info", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void should_fail_when_trying_to_get_all_users_with_invalid_user() {

        ResponseEntity<String> response = restTemplate.withBasicAuth("John", "1234")
                .getForEntity("/users", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }
}
