package com.cityexplorer.backend.attraction.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cityexplorer.backend.attraction.dto.AttractionResponse;

import com.cityexplorer.backend.attraction.service.AttractionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

@Validated
@RestController
@RequestMapping("/api/places/{placeId}/attractions")
public class AttractionController {

    private final AttractionService attractionService;

    public AttractionController(AttractionService attractionService) {
        this.attractionService = attractionService;
    }

    @Operation(summary = "Get attractions by place ID", description = "Retrieves all attractions associated with a specific place. The place ID must be a positive number.")
    @SecurityRequirements
    @GetMapping
    public ResponseEntity<List<AttractionResponse>> getAttractionsByPlaceId(
            @Positive @PathVariable Long placeId) {

        return ResponseEntity.ok(
                attractionService.getAttractionsByPlaceId(placeId));
    }
}
