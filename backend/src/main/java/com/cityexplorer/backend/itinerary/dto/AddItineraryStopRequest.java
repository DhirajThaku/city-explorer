package com.cityexplorer.backend.itinerary.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AddItineraryStopRequest(

                @NotNull(message = "Attraction ID is required") @Positive(message = "Attraction ID must be greater than zero") Long attractionId) {

}
