package com.cityexplorer.backend.place.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cityexplorer.backend.place.dto.PlaceResponse;

import com.cityexplorer.backend.place.repository.PlaceRepository;

@Service
public class PlaceService {

    private final PlaceRepository placeRepository;

    // this is more preffered than field injection
    public PlaceService(PlaceRepository cityRepository) {
        this.placeRepository = cityRepository;
    }

    public Page<PlaceResponse> getAllPlaces(Pageable pageable) {
        return placeRepository.findAll(pageable)
                .map(place -> new PlaceResponse(place.getId(), place.getName(), place.getState(), place.getCountry(),
                        place.getDescription(), place.getImageUrl(), place.getType()));
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
