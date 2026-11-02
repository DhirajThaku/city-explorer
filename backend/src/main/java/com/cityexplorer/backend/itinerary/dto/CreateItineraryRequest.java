package com.cityexplorer.backend.itinerary.dto;

import java.time.LocalDate;

public record CreateItineraryRequest(
        String title,
        LocalDate startDate,
        LocalDate endDate) {

}
