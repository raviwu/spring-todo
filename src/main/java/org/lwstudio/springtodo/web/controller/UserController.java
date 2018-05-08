package org.lwstudio.springtodo.web.controller;

import org.lwstudio.springtodo.service.UserService;
import org.lwstudio.springtodo.model.entity.User;

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

    @PostMapping
    public ResponseEntity<?> postUser(@RequestBody User user) {
        userService.saveUser(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}/todos")
                .buildAndExpand(user.getId())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(user);

    }

}
