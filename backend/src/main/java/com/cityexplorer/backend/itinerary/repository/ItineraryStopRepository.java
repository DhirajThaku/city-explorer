package com.cityexplorer.backend.itinerary.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cityexplorer.backend.itinerary.entity.ItineraryStop;

public interface ItineraryStopRepository extends JpaRepository<ItineraryStop, Long> {

    List<ItineraryStop> findAllByItineraryDay_IdOrderByStopOrderAsc(Long itineraryDayId);

    boolean existsByItineraryDay_IdAndAttraction_Id(Long itineraryDayId, Long attractionId);

    @Query("""
                SELECT COALESCE(MAX(s.stopOrder),0) FROM ItineraryStop s WHERE s.itineraryDay.id = :itineraryDayId
            """)
    Integer findMaxStopOrderByItineraryDayId(@Param("itineraryDayId") Long itineraryDayId);

}
