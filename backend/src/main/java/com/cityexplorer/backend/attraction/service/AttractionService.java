package com.cityexplorer.backend.attraction.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import com.cityexplorer.backend.attraction.dto.AttractionResponse;
import com.cityexplorer.backend.attraction.entity.Attraction;
import com.cityexplorer.backend.attraction.repository.AttractionRepository;
import com.cityexplorer.backend.place.repository.PlaceRepository;

@Service
public class AttractionService {

    private final AttractionRepository attractionRepository;
    private final PlaceRepository placeRepository;

    public AttractionService(
            AttractionRepository attractionRepository,
            PlaceRepository placeRepository) {
        this.attractionRepository = attractionRepository;
        this.placeRepository = placeRepository;
    }

    public List<AttractionResponse> getAttractionsByPlaceId(Long placeId) {

        if (!placeRepository.existsById(placeId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Place not found with id: " + placeId);
        }

        List<Attraction> attractions = attractionRepository.findByPlaceId(placeId);

        return attractions.stream()
                .map(attraction -> new AttractionResponse(
                        attraction.getId(),
                        attraction.getPlaceId(),
                        attraction.getName(),
                        attraction.getDescription(),
                        attraction.getCategory(),
                        attraction.getAddress(),
                        attraction.getLatitude(),
                        attraction.getLongitude(),
                        attraction.getEntryFee()))
                .toList();
    }
}
