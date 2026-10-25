package com.cityexplorer.backend.review.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cityexplorer.backend.review.dto.ReviewRequest;
import com.cityexplorer.backend.review.dto.ReviewResponse;
import com.cityexplorer.backend.review.service.ReviewService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/reviews")
public class ReviewManagementController {
    private final ReviewService reviewService;

    public ReviewManagementController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PatchMapping("/{reviewId}")
    public ResponseEntity<ReviewResponse> updateReview(Authentication authentication, @PathVariable Long reviewId,
            @Valid @RequestBody ReviewRequest request) {

        String email = authentication.getName();

        ReviewResponse response = reviewService.updateReview(email, reviewId, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(Authentication authentication, @PathVariable Long reviewId) {

        String email = authentication.getName();

        reviewService.deleteReview(email, reviewId);
        return ResponseEntity.noContent().build();
    }
}
