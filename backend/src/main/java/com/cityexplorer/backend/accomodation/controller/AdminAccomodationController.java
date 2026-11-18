package com.cityexplorer.backend.accomodation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.PutExchange;

import com.cityexplorer.backend.accomodation.dto.AccomodationRequest;
import com.cityexplorer.backend.accomodation.dto.AccomodationResponse;
import com.cityexplorer.backend.accomodation.service.AccomodationService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/admin/accommodations")
public class AdminAccomodationController {
    private final AccomodationService accomodationService;

    public AdminAccomodationController(AccomodationService accomodationService) {
        this.accomodationService = accomodationService;

    }

    @Operation(summary = "Create an accommodation", description = "Allows an admin to create a new accommodation.")
    @PostMapping
    public ResponseEntity<AccomodationResponse> createAccomodation(@RequestBody AccomodationRequest request) {
        AccomodationResponse response = accomodationService.createAccomodation(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Update an accommodation", description = "Allows an admin to update an existing accommodation using its ID.")
    @PutMapping("/{id}")
    public ResponseEntity<AccomodationResponse> updateAccomodation(@PathVariable Long id,
            @RequestBody AccomodationRequest request) {
        AccomodationResponse response = accomodationService.updateAccomodation(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete an accommodation", description = "Allows an admin to delete an existing accommodation using its ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccomodation(@PathVariable Long id) {
        accomodationService.deleteAccomodation(id);
        return ResponseEntity.noContent().build();
    }
}
