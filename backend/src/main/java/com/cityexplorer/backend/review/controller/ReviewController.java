package com.cityexplorer.backend.review.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cityexplorer.backend.review.dto.ReviewRequest;
import com.cityexplorer.backend.review.dto.ReviewResponse;
import com.cityexplorer.backend.review.service.ReviewService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/attractions")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/{attractionId}/reviews")
    public ResponseEntity<ReviewResponse> addReview(Authentication authentication, @PathVariable Long attractionId,
            @Valid @RequestBody ReviewRequest request) {

        String email = authentication.getName();

        ReviewResponse response = reviewService.addReview(email, attractionId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{attractionId}/reviews")
    public ResponseEntity<List<ReviewResponse>> getReviewsByAttraction(@PathVariable Long attractionId) {

        List<ReviewResponse> reviews = reviewService.getReviewsByAttraction(attractionId);
        return ResponseEntity.ok(reviews);
    }

}
