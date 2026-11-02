package com.cityexplorer.backend.itinerary.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.cityexplorer.backend.common.exception.ResourceNotFoundException;
import com.cityexplorer.backend.itinerary.dto.ItineraryResponse;
import com.cityexplorer.backend.itinerary.entity.Itinerary;
import com.cityexplorer.backend.itinerary.repository.ItineraryRepository;
import com.cityexplorer.backend.user.entity.User;
import com.cityexplorer.backend.user.repository.UserRepository;

@Service
public class ItineraryService {

    private final ItineraryRepository itineraryRepository;
    private final UserRepository userRepository;

    public ItineraryService(ItineraryRepository itineraryRepository, UserRepository userRepository) {
        this.itineraryRepository = itineraryRepository;
        this.userRepository = userRepository;

    }

    public ItineraryResponse createItinerary(String email, String title, LocalDate startDate, LocalDate endDate) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("startDate and endDate required");

        }

        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date cannot be before start date");
        }

        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Itinerary title is required");
        }

        Itinerary itinerary = new Itinerary(user.getId(), title.trim(), startDate, endDate);

        Itinerary savedItinerary = itineraryRepository.save(itinerary);

        return new ItineraryResponse(savedItinerary.getId(), savedItinerary.getTitle(), savedItinerary.getStartDate(),
                savedItinerary.getEndDate(), savedItinerary.getCreatedAt());

    }

}
