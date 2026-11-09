package com.cityexplorer.backend.itinerary.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cityexplorer.backend.itinerary.entity.ItineraryDay;

public interface ItineraryDayRepository extends JpaRepository<ItineraryDay, Long> {

    List<ItineraryDay> findAllByItinerary_IdOrderByDayNumberAsc(Long itineraryId);

    Optional<ItineraryDay> findByIdAndItinerary_UserId(Long id, Long userId);
}
