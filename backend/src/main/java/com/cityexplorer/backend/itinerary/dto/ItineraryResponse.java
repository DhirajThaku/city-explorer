package com.cityexplorer.backend.itinerary.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ItineraryResponse(
        Long id,
        String title,
        LocalDate startDate,
        LocalDate endDate,
        LocalDateTime createAt) {

}
