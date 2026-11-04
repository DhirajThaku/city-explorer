package com.cityexplorer.backend.itinerary.dto;

import java.time.LocalDate;

public record UpdateItineraryRequest(
        String title,
        LocalDate startDate,
        LocalDate endDate) {

}
