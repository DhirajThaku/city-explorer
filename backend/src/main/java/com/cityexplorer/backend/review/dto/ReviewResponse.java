package com.cityexplorer.backend.review.dto;

import java.time.LocalDateTime;

import com.cityexplorer.backend.review.entity.Review;

public class ReviewResponse {

    private Long id;
    private Long userId;
    private Long attractionId;
    private Integer rating;
    private String comment;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ReviewResponse(Review review) {
        this.id = review.getId();
        this.userId = review.getUser().getId();
        this.attractionId = review.getAttraction().getId();
        this.rating = review.getRating();
        this.comment = review.getComment();
        this.createdAt = review.getCreatedAt();
        this.updatedAt = review.getUpdatedAt();
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getAttractionId() {
        return attractionId;
    }

    public Integer getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

}
