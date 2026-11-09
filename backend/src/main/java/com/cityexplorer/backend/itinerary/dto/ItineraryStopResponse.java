package com.cityexplorer.backend.itinerary.dto;

import java.time.LocalDateTime;

public record ItineraryStopResponse(
        Long id,
        Long attractionId,
        String attractionName,
        Integer stopOrder,
        LocalDateTime createdAt) {
}
