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

import com.cityexplorer.backend.itinerary.dto.AddItineraryStopRequest;
import com.cityexplorer.backend.itinerary.dto.CreateItineraryRequest;
import com.cityexplorer.backend.itinerary.dto.ItineraryDayResponse;
import com.cityexplorer.backend.itinerary.dto.ItineraryResponse;
import com.cityexplorer.backend.itinerary.dto.ItineraryStopResponse;
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

    @GetMapping("/{itineraryId}/days")
    public ResponseEntity<List<ItineraryDayResponse>> getItineraryDays(@PathVariable Long itineraryId,
            Authentication authentication) {

        List<ItineraryDayResponse> days = itineraryService.getItineraryDays(authentication.getName(), itineraryId);

        return ResponseEntity.ok(days);
    }

    @PostMapping("/{itineraryId}/days/{dayId}/stops")
    public ResponseEntity<ItineraryStopResponse> addStop(@PathVariable Long itineraryId, @PathVariable Long dayId,
            @RequestBody AddItineraryStopRequest request, Authentication authentication) {

        // verify that the day belongs to the itinerary in the URL
        // (we'll add this validation in the service in the next step)

        ItineraryStopResponse response = itineraryService.addStop(authentication.getName(), itineraryId, dayId,
                request.attractionId());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{itineraryId}/days/{dayId}/stops")
    public ResponseEntity<List<ItineraryStopResponse>> getStops(@PathVariable Long itineraryId,
            @PathVariable Long dayId, Authentication authentication) {

        List<ItineraryStopResponse> stops = itineraryService.getStops(authentication.getName(), itineraryId, dayId);

        return ResponseEntity.ok(stops);
    }

    @DeleteMapping("/{itineraryId}/days/{dayId}/stops/{stopId}")
    public ResponseEntity<Void> deleteStop(@PathVariable Long itineraryId, @PathVariable Long dayId,
            @PathVariable Long stopId, Authentication authentication) {

        itineraryService.deleteStop(authentication.getName(), itineraryId, dayId, stopId);

        return ResponseEntity.noContent().build();
    }

}
