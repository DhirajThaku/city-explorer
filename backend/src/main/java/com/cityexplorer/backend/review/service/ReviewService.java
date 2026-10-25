package com.cityexplorer.backend.review.service;

import java.util.List;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cityexplorer.backend.attraction.entity.Attraction;
import com.cityexplorer.backend.attraction.repository.AttractionRepository;
import com.cityexplorer.backend.common.exception.ResourceNotFoundException;
import com.cityexplorer.backend.review.dto.ReviewRequest;
import com.cityexplorer.backend.review.dto.ReviewResponse;
import com.cityexplorer.backend.review.entity.Review;
import com.cityexplorer.backend.review.repository.ReviewRepository;
import com.cityexplorer.backend.user.entity.User;
import com.cityexplorer.backend.user.repository.UserRepository;

import jakarta.persistence.EntityManager;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final AttractionRepository attractionRepository;

    // to reload the entity current db values with timestamps
    private final EntityManager entityManager;

    public ReviewService(ReviewRepository reviewRepository,
            UserRepository userRepository, AttractionRepository attractionRepository, EntityManager entityManager) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.attractionRepository = attractionRepository;
        this.entityManager = entityManager;
    }

    @Transactional
    public ReviewResponse addReview(String email, Long attractionId, ReviewRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Attraction attraction = attractionRepository.findById(attractionId)
                .orElseThrow(() -> new ResourceNotFoundException("Attraction not found"));

        // tocheck if user adding the duplicate reviews on the same attraction
        boolean alreadyReviewed = reviewRepository.existsByUser_IdAndAttraction_Id(user.getId(), attraction.getId());

        if (alreadyReviewed) {
            throw new IllegalStateException("You have already reviewed this attraction");
        }

        // now get the comment and rating from obj
        Integer rating = request.getRating();
        String comment = request.getComment().trim();

        if (comment.isEmpty() || comment.length() > 2000) {
            throw new IllegalArgumentException(
                    "Comment must contain between 1 and 2000 characters");
        }
        Review review = new Review(user, attraction, rating, comment);

        // if we have to stored the data then entity we need which is mapped with ex
        // review table to add the record
        Review savedReview = reviewRepository.saveAndFlush(review);

        entityManager.refresh(savedReview);

        return new ReviewResponse(savedReview);
    }

    @Transactional(readOnly = true)
    public List<ReviewResponse> getReviewsByAttraction(Long attractionId) {
        // check if attraction id is present in attraction table
        Attraction attraction = attractionRepository.findById(attractionId)
                .orElseThrow(() -> new ResourceNotFoundException("Attraction not found."));
        List<Review> reviews = reviewRepository.findByAttraction_Id(attractionId);

        return reviews.stream().map(ReviewResponse::new).toList();
    }

    @Transactional
    public ReviewResponse updateReview(String email, Long reviewId, ReviewRequest request) {
        // retreiving the review which we want to edit
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found"));

        // find authenticated user
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // check owner ship
        if (!review.getUser().getId().equals(user.getId())) {
            throw new org.springframework.security.access.AccessDeniedException(
                    "You cannot update another users review");

        }

        Integer rating = request.getRating();
        String comment = request.getComment();

        if (comment.isEmpty() || comment.length() > 2000) {
            throw new IllegalArgumentException("Comment must contain between 1 and 2000 characters");
        }

        review.updateReview(rating, comment);

        Review updatedReview = reviewRepository.saveAndFlush(review);

        entityManager.refresh(updatedReview);

        return new ReviewResponse(updatedReview);
    }

    @Transactional
    public void deleteReview(String email, Long reviewId) {
        // review exist
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found"));

        // ownership check

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!review.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("You cannot delete another user's review");
        }

        // delete review from db
        reviewRepository.delete(review);
    }

}
