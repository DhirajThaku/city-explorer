package com.cityexplorer.backend.review.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cityexplorer.backend.review.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    boolean existsByUser_IdAndAttraction_Id(Long userId, Long attractionId);

    Optional<Review> findByUser_IdAndAttraction_Id(Long userId, Long attractionIc);

    // to get the attraction specific all reviews
    List<Review> findByAttraction_Id(Long attractionId);
}