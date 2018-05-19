package org.lwstudio.springtodo.web.controller;

import org.lwstudio.springtodo.service.UserService;
import org.lwstudio.springtodo.model.entity.User;
import org.lwstudio.springtodo.model.dto.RegistrationDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping
    public ResponseEntity<?> postUser(@RequestBody RegistrationDTO registrationDTO) {
        userService.saveUser(registrationDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("");

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putUser(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);

        userService.modifyUserById(user);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);

        return ResponseEntity
                .noContent()
                .build();
    }

}
