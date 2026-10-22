package com.cityexplorer.backend.favorite.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cityexplorer.backend.favorite.dto.FavoriteResponse;
import com.cityexplorer.backend.favorite.entity.Favorite;
import com.cityexplorer.backend.favorite.repository.FavoriteRepository;
import com.cityexplorer.backend.place.entity.Place;
import com.cityexplorer.backend.place.repository.PlaceRepository;
import com.cityexplorer.backend.user.entity.User;
import com.cityexplorer.backend.user.repository.UserRepository;

@Service
public class FavoriteService {
    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final PlaceRepository placeRepository;

    public FavoriteService(FavoriteRepository favoriteRepository,
            UserRepository userRepository,
            PlaceRepository placeRepository) {
        this.favoriteRepository = favoriteRepository;
        this.userRepository = userRepository;
        this.placeRepository = placeRepository;
    }

    public Favorite addFavorite(String email, Long placeId) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new RuntimeException("Place not found"));

        boolean alreadyExists = favoriteRepository.existsByUser_IdAndPlace_Id(user.getId(), place.getId());
        if (alreadyExists) {
            throw new IllegalStateException("Place is already in favorites");

        }
        Favorite favorite = new Favorite(user, place);
        return favoriteRepository.save(favorite);
    }

    // get all places user's favorites

    public List<FavoriteResponse> getUserFavorites(String email) {
        // the logged user only get there places saved as favorite
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return favoriteRepository.findByUser_Id(user.getId()).stream().map(FavoriteResponse::new)
                // gather converted dtos in to a list
                .collect(Collectors.toList());

    }
}
