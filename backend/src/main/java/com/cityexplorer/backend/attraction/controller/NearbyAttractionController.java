package com.cityexplorer.backend.attraction.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cityexplorer.backend.attraction.dto.AttractionResponse;
import com.cityexplorer.backend.attraction.service.AttractionService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;

@Validated
@RestController
@RequestMapping("/api/attractions")
public class NearbyAttractionController {

    private final AttractionService attractionService;

    public NearbyAttractionController(AttractionService attractionService) {
        this.attractionService = attractionService;
    }

    @GetMapping("/nearby")
    @Operation(summary = "Find nearby attractions", description = "Retrieves attractions within the specified radius of the given latitude and longitude.")
    public ResponseEntity<List<AttractionResponse>> getNearbyAttractions(
            @RequestParam @DecimalMin("-90.0") @DecimalMax("90.0") BigDecimal latitude,

            @RequestParam @DecimalMin("-180.0") @DecimalMax("180.0") BigDecimal longitude,

            @RequestParam @DecimalMin(value = "0.0", inclusive = false) Double radius) {

        return ResponseEntity.ok(attractionService.getNearbyAttractions(latitude, longitude, radius));
    }
}
