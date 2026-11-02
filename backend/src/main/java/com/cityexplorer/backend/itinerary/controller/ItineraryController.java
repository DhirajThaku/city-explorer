package com.cityexplorer.backend.itinerary.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cityexplorer.backend.itinerary.dto.CreateItineraryRequest;
import com.cityexplorer.backend.itinerary.dto.ItineraryResponse;
import com.cityexplorer.backend.itinerary.entity.Itinerary;
import com.cityexplorer.backend.itinerary.repository.ItineraryRepository;
import com.cityexplorer.backend.itinerary.service.ItineraryService;

@RestController
@RequestMapping("/api/itineraries")
public class ItineraryController {

    private final ItineraryService itineraryService;

    public ItineraryController(ItineraryService itineraryService) {

        this.itineraryService = itineraryService;
    }

    @PostMapping
    public ResponseEntity<ItineraryResponse> createItinerary(@RequestBody CreateItineraryRequest request,
            Authentication authentication) {

        ItineraryResponse itinerary = itineraryService.createItinerary(authentication.getName(), request.title(),
                request.startDate(), request.endDate());

        return ResponseEntity.status(HttpStatus.CREATED).body(itinerary);
    }
}
