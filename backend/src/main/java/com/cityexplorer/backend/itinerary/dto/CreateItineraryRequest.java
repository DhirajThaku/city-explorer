package com.cityexplorer.backend.itinerary.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateItineraryRequest(
                @NotBlank(message = "Title is required") @Size(max = 150, message = "Title cannot exceed 150 characters") String title,

                @NotNull(message = "Start date is required") LocalDate startDate,

                @NotNull(message = "End date is required") LocalDate endDate) {

}
