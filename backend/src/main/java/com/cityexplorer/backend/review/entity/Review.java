package com.cityexplorer.backend.review.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.UpdateTimestamp;

import com.cityexplorer.backend.attraction.entity.Attraction;
import com.cityexplorer.backend.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "attraction_id", nullable = false)
    private Attraction attraction;

    @Column(nullable = false)
    private Integer rating;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String comment;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    // @Column(name = "updated_at", insertable = false, updatable = false)
    // private LocalDateTime updatedAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    protected Review() {
        // required by JPA
    }

    public Review(User user, Attraction attraction, Integer rating, String comment) {
        this.user = user;
        this.attraction = attraction;
        this.rating = rating;
        this.comment = comment;
    }

    public void updateReview(Integer rating, String comment) {
        this.rating = rating;
        this.comment = comment;

    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Attraction getAttraction() {
        return attraction;
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
