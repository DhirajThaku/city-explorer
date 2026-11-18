package com.cityexplorer.backend.accomodation.controller;

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

import com.cityexplorer.backend.accomodation.dto.AccomodationResponse;
import com.cityexplorer.backend.accomodation.service.AccomodationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;

@RestController
@RequestMapping("/api/accommodations")
public class AccomodationController {

    private final AccomodationService accomodationService;

    public AccomodationController(AccomodationService accomodationService) {
        this.accomodationService = accomodationService;

    }

    @Operation(summary = "Get all accommodations", description = "Retrieves a paginated list of accommodations, optionally filtered by place ID.")
    @GetMapping
    public ResponseEntity<Page<AccomodationResponse>> getAllAccomodations(
            @RequestParam(required = false) Long placeId,
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(accomodationService.getAllAccomodatioins(placeId, pageable));
    }

    @Operation(summary = "Get accommodation by ID", description = "Retrieves the details of a specific accommodation using its ID.")
    @GetMapping("/{id}")
    public ResponseEntity<AccomodationResponse> getAccomodationById(@PathVariable Long id) {

        return ResponseEntity.ok(accomodationService.getAccomodationById(id));
    }
}
