package com.cityexplorer.backend.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cityexplorer.backend.user.dto.LoginRequest;
import com.cityexplorer.backend.user.dto.LoginResponse;
import com.cityexplorer.backend.user.dto.RegisterRequest;
import com.cityexplorer.backend.user.dto.RegisterResponse;
import com.cityexplorer.backend.user.entity.User;
import com.cityexplorer.backend.user.service.JwtService;
import com.cityexplorer.backend.user.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final JwtService jwtService;

    public AuthController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {

        User user = userService.register(request);

        RegisterResponse response = new RegisterResponse(user.getId(), user.getName(), user.getEmail());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        User user = userService.login(request);

        String token = jwtService.generateToken(user.getEmail());
        LoginResponse response = new LoginResponse(user.getId(), user.getName(), user.getEmail(), token);
        return ResponseEntity.ok(response);
    }

}
