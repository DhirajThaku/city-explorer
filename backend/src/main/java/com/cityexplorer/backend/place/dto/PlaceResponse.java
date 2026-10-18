package com.cityexplorer.backend.place.dto;

//record java feature gives us a concise way to define a data carrier
public record PlaceResponse(
        Long id,
        String name,
        String state,
        String country,
        String description,
        String imageUrl,
        String type) {
}
