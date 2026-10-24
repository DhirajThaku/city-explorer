package com.cityexplorer.backend.user.dto;

import com.cityexplorer.backend.user.entity.User;

public record UserProfileResponse(Long loginId, String email, String name) {

    public UserProfileResponse(User user) {
        this(user.getId(), user.getEmail(), user.getName());
    }
}
