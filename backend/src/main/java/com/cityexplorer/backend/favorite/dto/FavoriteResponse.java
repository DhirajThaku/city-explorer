package com.cityexplorer.backend.favorite.dto;

import com.cityexplorer.backend.favorite.entity.Favorite;

public class FavoriteResponse {
    private final Long favoriteId;
    private final Long placeId;
    private final String placeName;
    private final String state;
    private final String country;
    private final String description;
    private final String imageUrl;
    private final String type;

    public FavoriteResponse(Favorite favorite) {
        this.favoriteId = favorite.getId();
        this.placeId = favorite.getPlace().getId();
        this.placeName = favorite.getPlace().getName();
        this.state = favorite.getPlace().getState();
        this.country = favorite.getPlace().getCountry();
        this.description = favorite.getPlace().getDescription();
        this.imageUrl = favorite.getPlace().getImageUrl();
        this.type = favorite.getPlace().getType();

    }

    public Long getFavoriteId() {
        return favoriteId;
    }

    public Long getPlaceId() {
        return placeId;
    }

    public String getPlaceName() {
        return placeName;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getType() {
        return type;
    }

}
