package com.cityexplorer.backend.place.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cityexplorer.backend.place.dto.PlaceResponse;

import com.cityexplorer.backend.place.service.PlaceService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;

@RestController
@RequestMapping("/api/places")
public class PlaceController {

    private final PlaceService placeService;

    public PlaceController(PlaceService cityService) {
        this.placeService = cityService;
    }

    @Operation(summary = "Get all places", description = "Retrieves a paginated list of places. Supports optional filtering by name, state, and country.")
    @SecurityRequirements
    @GetMapping
    public ResponseEntity<Page<PlaceResponse>> getAllPlaces(@RequestParam(required = false) String name,
            @RequestParam(required = false) String state, @RequestParam(required = false) String country,
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(placeService.getAllPlaces(name, state, country, pageable));
    }

    @Operation(summary = "Get place by ID", description = "Retrieves the details of a specific place using its ID.")
    @SecurityRequirements
    @GetMapping("/{id}")
    public ResponseEntity<PlaceResponse> getPlaceById(@PathVariable Long id) {
        return placeService.getCityById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
