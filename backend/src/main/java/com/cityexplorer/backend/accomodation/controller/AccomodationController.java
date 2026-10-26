package com.cityexplorer.backend.accomodation.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cityexplorer.backend.accomodation.dto.AccomodationResponse;
import com.cityexplorer.backend.accomodation.service.AccomodationService;

@RestController
@RequestMapping("/api/accommodations")
public class AccomodationController {

    private final AccomodationService accomodationService;

    public AccomodationController(AccomodationService accomodationService) {
        this.accomodationService = accomodationService;

    }

    @GetMapping
    public List<AccomodationResponse> getAllAccomodations() {
        return accomodationService.getAllAccomodatioins();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccomodationResponse> getAccomodationById(@PathVariable Long id) {

        return ResponseEntity.ok(accomodationService.getAccomodationById(id));
    }
}
