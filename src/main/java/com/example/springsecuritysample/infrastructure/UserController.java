package com.example.springsecuritysample.infrastructure;

import com.example.springsecuritysample.domain.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
class UserController {

    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/info")
    ResponseEntity<UserDTO> findUserBy(Principal principal) {

        var user = userService.findBy(principal.getName());

        var userDTO = new UserDTO(user.getName(), user.getCity(), user.getAge());

        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/users")
    ResponseEntity<List<UserDTO>> findAll() {

        var users = userService
                .findAll()
                .stream()
                .map(user -> new UserDTO(user.getName(), user.getCity(), user.getAge()))
                .toList();

        return ResponseEntity.ok(users);
    }
}
