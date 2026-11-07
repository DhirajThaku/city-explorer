package com.cityexplorer.backend.itinerary.dto;

import java.time.LocalDateTime;

public record ItineraryDayResponse(
        Long id,
        Integer dayNumber,
        LocalDateTime createdAt) {
}