package org.lwstudio.springtodo.web.controller;

import org.lwstudio.springtodo.security.JwtAuthenticationRequest;

import org.lwstudio.springtodo.model.entity.User;

import org.lwstudio.springtodo.service.AuthService;

import org.lwstudio.springtodo.exception.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Value("Authorization")
    private String tokenHeader;

    @Autowired
    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest) throws ValidationException {
        final String token = authService.login(authenticationRequest);

        return ResponseEntity.ok().body(token);
    }

    @PostMapping("/register")
    public User register(@RequestBody JwtAuthenticationRequest authenticationRequest) throws ValidationException{
        return authService.register(authenticationRequest);
    }
}
