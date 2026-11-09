package com.cityexplorer.backend.itinerary.entity;

import java.time.LocalDateTime;

import com.cityexplorer.backend.attraction.entity.Attraction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "itinerary_stops")
public class ItineraryStop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "itinerary_day_id", nullable = false)
    private ItineraryDay itineraryDay;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "attraction_id", nullable = false)
    private Attraction attraction;

    @Column(name = "stop_order", nullable = false)
    private Integer stopOrder;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    protected ItineraryStop() {

        // this for the jpa
    }

    public ItineraryStop(ItineraryDay itineraryDay, Attraction attraction, Integer stopOrder) {
        this.itineraryDay = itineraryDay;
        this.attraction = attraction;
        this.stopOrder = stopOrder;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public ItineraryDay getItineraryDay() {
        return itineraryDay;
    }

    public Attraction getAttraction() {
        return attraction;
    }

    public Integer getStopOrder() {
        return stopOrder;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
