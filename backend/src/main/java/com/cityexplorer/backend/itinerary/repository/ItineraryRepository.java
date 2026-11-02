package com.cityexplorer.backend.itinerary.repository;

import java.util.Optional;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cityexplorer.backend.itinerary.entity.Itinerary;

public interface ItineraryRepository extends JpaRepository<Itinerary, Long> {

    List<Itinerary> findAllByUserId(Long userId);

    Optional<Itinerary> findByIdAndUserId(Long id,Long userId);
    
}
