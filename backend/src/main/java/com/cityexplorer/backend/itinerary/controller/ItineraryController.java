package com.cityexplorer.backend.itinerary.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cityexplorer.backend.itinerary.dto.CreateItineraryRequest;
import com.cityexplorer.backend.itinerary.dto.ItineraryResponse;
import com.cityexplorer.backend.itinerary.dto.UpdateItineraryRequest;
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

    @GetMapping
    public ResponseEntity<List<ItineraryResponse>> getAllItineraries(Authentication authentication) {

        List<ItineraryResponse> itineraries = itineraryService.getAllItineraries(authentication.getName());

        return ResponseEntity.ok(itineraries);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItineraryResponse> getItineraryById(Authentication authentication, @PathVariable Long id) {
        ItineraryResponse itineraryResponse = itineraryService.getItineraryById(authentication.getName(), id);

        return ResponseEntity.ok(itineraryResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItineraryResponse> updateItinerary(@PathVariable Long id,
            @RequestBody UpdateItineraryRequest request, Authentication authentication) {
        ItineraryResponse itinerary = itineraryService.updateItinerary(authentication.getName(), id, request.title(),
                request.startDate(), request.endDate());

        return ResponseEntity.ok(itinerary);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItineary(@PathVariable Long id, Authentication authentication) {
        itineraryService.deleteItinerary(authentication.getName(), id);

        return ResponseEntity.noContent().build();

    }
}
