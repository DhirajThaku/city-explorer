package com.cityexplorer.backend.favorite.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cityexplorer.backend.favorite.dto.FavoriteResponse;
import com.cityexplorer.backend.favorite.entity.Favorite;
import com.cityexplorer.backend.favorite.service.FavoriteService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {
    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @GetMapping
    @Operation(summary = "Get user's favorite places", description = "Retrieves all favorite places of the authenticated user.")
    public ResponseEntity<List<FavoriteResponse>> getUserFavorites(Authentication authentication) {
        String email = authentication.getName();
        List<FavoriteResponse> favorites = favoriteService.getUserFavorites(email);
        return ResponseEntity.ok(favorites);
    }

    @PostMapping("/{placeId}")
    @Operation(summary = "Add a place to favorites", description = "Adds a place to the authenticated user's favorites.")
    public ResponseEntity<FavoriteResponse> addFavorite(Authentication authentication, @PathVariable Long placeId) {
        String email = authentication.getName();
        Favorite favorite = favoriteService.addFavorite(email, placeId);
        FavoriteResponse response = new FavoriteResponse(favorite);
        return ResponseEntity.ok(response);

    }

    @DeleteMapping("/{placeId}")
    @Operation(summary = "Remove a place from favorites", description = "Removes a place from the authenticated user's favorites.")
    public ResponseEntity<Void> deleteFavorite(Authentication authentication, @PathVariable Long placeId) {
        String email = authentication.getName();
        favoriteService.removeFavorite(email, placeId);
        return ResponseEntity.noContent().build();

    }
}
