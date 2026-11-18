package com.cityexplorer.backend.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cityexplorer.backend.user.dto.UserProfileResponse;
import com.cityexplorer.backend.user.service.UserService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    @Operation(summary = "Get current user profile", description = "Retrieves the profile details of the currently authenticated user.")
    public UserProfileResponse getMyProfile(Authentication authentication) {
        String email = authentication.getName();

        return userService.getMyProfile(email);
    }
}
