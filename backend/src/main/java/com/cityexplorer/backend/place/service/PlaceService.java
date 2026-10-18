package com.cityexplorer.backend.place.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cityexplorer.backend.place.dto.PlaceResponse;
import com.cityexplorer.backend.place.entity.Place;
import com.cityexplorer.backend.place.repository.PlaceRepository;

@Service
public class PlaceService {

    private final PlaceRepository placeRepository;

    // this is more preffered than field injection
    public PlaceService(PlaceRepository cityRepository) {
        this.placeRepository = cityRepository;
    }

    // api for listing all places with pagination
    // api for searching places by name with pagination
    public Page<PlaceResponse> getAllPlaces(String name, String state, String country, Pageable pageable) {

        // convert blank filters to null so they are ignored by the query
        name = (name == null || name.isBlank()) ? null : name.trim();
        state = (state == null || state.isBlank()) ? null : state.trim();
        country = (country == null || country.isBlank()) ? null : country.trim();
        Page<Place> places = placeRepository.searchPlaces(name, state, country, pageable);

        // if (name == null || name.isBlank()) {
        // places = placeRepository.findAll(pageable);
        // } else {
        // places = placeRepository.findByNameContainingIgnoreCase(name.trim(),
        // pageable);

        // }

        return places.map(place -> new PlaceResponse(
                place.getId(),
                place.getName(),
                place.getState(),
                place.getCountry(),
                place.getDescription(),
                place.getImageUrl(),
                place.getType()));
    }

    public Optional<PlaceResponse> getCityById(Long id) {
        return placeRepository.findById(id)
                .map(place -> new PlaceResponse(
                        place.getId(),
                        place.getName(),
                        place.getState(),
                        place.getCountry(),
                        place.getDescription(),
                        place.getImageUrl(),
                        place.getType()));
    }

}
