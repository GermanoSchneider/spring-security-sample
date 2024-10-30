package com.example.springsecuritysample.infrastructure;

import com.example.springsecuritysample.domain.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/user")
class UserController {

    private final UserRepository userRepository;

    UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/info")
    ResponseEntity<UserDTO> findUserBy(Principal principal) {

        var user = userRepository.findBy(principal.getName());

        var userDTO = new UserDTO(user.getName(), user.getCity(), user.getAge());

        return ResponseEntity.ok(userDTO);
    }
}
