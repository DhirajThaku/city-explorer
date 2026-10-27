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

@RestController
@RequestMapping("/api/accommodations")
public class AccomodationController {

    private final AccomodationService accomodationService;

    public AccomodationController(AccomodationService accomodationService) {
        this.accomodationService = accomodationService;

    }

    @GetMapping
    public ResponseEntity<Page<AccomodationResponse>> getAllAccomodations(
            @RequestParam(required = false) Long placeId,
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(accomodationService.getAllAccomodatioins(placeId, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccomodationResponse> getAccomodationById(@PathVariable Long id) {

        return ResponseEntity.ok(accomodationService.getAccomodationById(id));
    }
}
