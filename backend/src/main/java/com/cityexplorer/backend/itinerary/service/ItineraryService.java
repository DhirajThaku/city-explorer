package com.cityexplorer.backend.itinerary.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cityexplorer.backend.attraction.repository.AttractionRepository;
import com.cityexplorer.backend.common.exception.ResourceNotFoundException;
import com.cityexplorer.backend.itinerary.dto.ItineraryDayResponse;
import com.cityexplorer.backend.itinerary.dto.ItineraryResponse;
import com.cityexplorer.backend.itinerary.entity.Itinerary;
import com.cityexplorer.backend.itinerary.entity.ItineraryDay;
import com.cityexplorer.backend.itinerary.repository.ItineraryDayRepository;
import com.cityexplorer.backend.itinerary.repository.ItineraryRepository;
import com.cityexplorer.backend.itinerary.repository.ItineraryStopRepository;
import com.cityexplorer.backend.user.entity.User;
import com.cityexplorer.backend.user.repository.UserRepository;

@Service
public class ItineraryService {

    private final ItineraryRepository itineraryRepository;
    private final UserRepository userRepository;
    // added when working on the itineraryDay
    private final ItineraryDayRepository itineraryDayRepository;

    // to find attraction selected by user
    private final AttractionRepository attractionRepository;
    // to save the and retrieve itinerary stops
    private final ItineraryStopRepository itineraryStopRepository;

    public ItineraryService(ItineraryRepository itineraryRepository, UserRepository userRepository,
            ItineraryDayRepository itineraryDayRepository, AttractionRepository attractionRepository,
            ItineraryStopRepository itineraryStopRepository) {
        this.itineraryRepository = itineraryRepository;
        this.userRepository = userRepository;
        this.itineraryDayRepository = itineraryDayRepository;
        this.itineraryStopRepository = itineraryStopRepository;
        this.attractionRepository = attractionRepository;
    }

    @Transactional
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

        // addding this logic for the ItineraryDay logic
        List<ItineraryDay> days = new ArrayList<>();

        int dayNumber = 1;
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            ItineraryDay day = new ItineraryDay(savedItinerary, dayNumber);
            days.add(day);
            dayNumber++;

        }
        itineraryDayRepository.saveAll(days);

        // after adding the required days rows in the itineraryDay table with respect to
        // itinerary return the response of itinerary

        return new ItineraryResponse(savedItinerary.getId(), savedItinerary.getTitle(), savedItinerary.getStartDate(),
                savedItinerary.getEndDate(), savedItinerary.getCreatedAt());

    }

    public List<ItineraryResponse> getAllItineraries(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        List<Itinerary> itineraries = itineraryRepository.findAllByUserId(user.getId());

        return itineraries.stream()
                .map(itinerary -> new ItineraryResponse(itinerary.getId(), itinerary.getTitle(),
                        itinerary.getStartDate(), itinerary.getEndDate(), itinerary.getCreatedAt()))
                .collect(Collectors.toList());
    }

    public ItineraryResponse getItineraryById(String email, Long id) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Itinerary itinerary = itineraryRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Itinerary not found"));

        return new ItineraryResponse(itinerary.getId(), itinerary.getTitle(), itinerary.getStartDate(),
                itinerary.getEndDate(), itinerary.getCreatedAt());

    }

    @Transactional
    public ItineraryResponse updateItinerary(String email, Long id, String title, LocalDate startDate,
            LocalDate endDate) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // here will check the ownership
        Itinerary itinerary = itineraryRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Itinerary not found"));

        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Itinerary title is required");
        }

        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("startDate and endDate are required");
        }

        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date cannot be before start date");

        }

        itinerary.updateDetails(title.trim(), startDate, endDate);

        // Itinerary updatedItinerary=itineraryRepository.save(itinerary);

        return new ItineraryResponse(itinerary.getId(), itinerary.getTitle(), itinerary.getStartDate(),
                itinerary.getEndDate(), itinerary.getCreatedAt());

    }

    @Transactional
    public void deleteItinerary(String email, Long id) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Itinerary itinerary = itineraryRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Itinerary not found for this user"));

        itineraryRepository.delete(itinerary);
    }

    public List<ItineraryDayResponse> getItineraryDays(String email, Long itineraryId) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // verify that itinerary belongs to the authenticated user
        itineraryRepository.findByIdAndUserId(itineraryId, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Itinerary not found"));

        // Retrieve all days belonging to this itinerary
        return itineraryDayRepository
                .findAllByItinerary_IdOrderByDayNumberAsc(itineraryId)
                .stream()
                .map(day -> new ItineraryDayResponse(day.getId(), day.getDayNumber(), day.getCreatedAt()))
                .collect(Collectors.toList());
    }

}
