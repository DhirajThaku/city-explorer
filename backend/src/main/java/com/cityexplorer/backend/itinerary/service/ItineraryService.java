package com.cityexplorer.backend.itinerary.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cityexplorer.backend.attraction.entity.Attraction;
import com.cityexplorer.backend.attraction.repository.AttractionRepository;
import com.cityexplorer.backend.common.exception.ResourceNotFoundException;
import com.cityexplorer.backend.itinerary.dto.ItineraryDayResponse;
import com.cityexplorer.backend.itinerary.dto.ItineraryResponse;
import com.cityexplorer.backend.itinerary.dto.ItineraryStopResponse;
import com.cityexplorer.backend.itinerary.entity.Itinerary;
import com.cityexplorer.backend.itinerary.entity.ItineraryDay;
import com.cityexplorer.backend.itinerary.entity.ItineraryStop;
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

                return new ItineraryResponse(savedItinerary.getId(), savedItinerary.getTitle(),
                                savedItinerary.getStartDate(),
                                savedItinerary.getEndDate(), savedItinerary.getCreatedAt());

        }

        public List<ItineraryResponse> getAllItineraries(String email) {
                User user = userRepository.findByEmail(email)
                                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

                List<Itinerary> itineraries = itineraryRepository.findAllByUserId(user.getId());

                return itineraries.stream()
                                .map(itinerary -> new ItineraryResponse(itinerary.getId(), itinerary.getTitle(),
                                                itinerary.getStartDate(), itinerary.getEndDate(),
                                                itinerary.getCreatedAt()))
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
                                .map(day -> new ItineraryDayResponse(day.getId(), day.getDayNumber(),
                                                day.getCreatedAt()))
                                .collect(Collectors.toList());
        }

        @Transactional
        public ItineraryStopResponse addStop(String email, Long itineraryId, Long dayId, Long attractionId) {

                // 1.find authenticated user
                User user = userRepository.findByEmail(email)
                                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

                // 2.verify that the itinerary day belongs to this user
                ItineraryDay day = itineraryDayRepository.findByIdAndItinerary_UserId(dayId, user.getId())
                                .orElseThrow(() -> new ResourceNotFoundException("Itinerary day not found"));

                // checking that dayId is belongs to itineraryId or not
                if (!day.getItinerary().getId().equals(itineraryId)) {
                        throw new ResourceNotFoundException("Itinerary day not found");
                }
                // 3.find the requested attraction
                Attraction attraction = attractionRepository.findById(attractionId)
                                .orElseThrow(() -> new ResourceNotFoundException("Attraction  not found"));

                // 4.prevent duplicate attractions on the same day
                if (itineraryStopRepository.existsByItineraryDay_IdAndAttraction_Id(dayId, attractionId)) {
                        throw new IllegalStateException("Attraction already added to this day");
                }

                // 5.calculate the next stop order
                Integer maxOrder = itineraryStopRepository.findMaxStopOrderByItineraryDayId(dayId);

                Integer nextOrder = maxOrder + 1;

                // 6. create and save the itinerary stop
                ItineraryStop stop = new ItineraryStop(day, attraction, nextOrder);

                ItineraryStop savedStop = itineraryStopRepository.save(stop);

                // 7. Return the response
                return new ItineraryStopResponse(
                                savedStop.getId(),
                                attraction.getId(),
                                attraction.getName(),
                                savedStop.getStopOrder(),
                                savedStop.getCreatedAt());
        }

        @Transactional
        public List<ItineraryStopResponse> getStops(String email, Long itineraryId, Long dayId) {
                User user = userRepository.findByEmail(email)
                                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

                // verify that the day belongs to the authenticated user
                ItineraryDay day = itineraryDayRepository.findByIdAndItinerary_UserId(dayId, user.getId())
                                .orElseThrow(() -> new ResourceNotFoundException("Itinerary day not found"));

                // verify that the day belongs to the requested itinerary
                if (!day.getItinerary().getId().equals(itineraryId)) {
                        throw new ResourceNotFoundException("Itinerary day not found");
                }

                return itineraryStopRepository.findAllByItineraryDay_IdOrderByStopOrderAsc(dayId)
                                .stream().map(stop -> new ItineraryStopResponse(stop.getId(),
                                                stop.getAttraction().getId(),
                                                stop.getAttraction().getName(),
                                                stop.getStopOrder(),
                                                stop.getCreatedAt()))
                                .collect(Collectors.toList());
        }

        @Transactional
        public void deleteStop(String email, Long itineraryId, Long dayId, Long stopId) {
                // 1. Verify authenticated user
                User user = userRepository.findByEmail(email)
                                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

                // 2. Verify that the day belong to this user
                ItineraryDay day = itineraryDayRepository.findByIdAndItinerary_UserId(dayId, user.getId())
                                .orElseThrow(() -> new ResourceNotFoundException("Itinerary day not found"));

                // 3. Verify that the day belongs to the requested itinerary

                if (!day.getItinerary().getId().equals(itineraryId)) {

                        throw new ResourceNotFoundException("Itinerary day not found");
                }

                // 4. find the stop and verify that it belongs to this day
                ItineraryStop stop = itineraryStopRepository.findByIdAndItineraryDay_Id(stopId, dayId)
                                .orElseThrow(() -> new ResourceNotFoundException("Itinerary stop not found"));

                // 5. Delete the stop
                itineraryStopRepository.delete(stop);
        }

        @Transactional
        public List<ItineraryStopResponse> reorderStops(String email, Long itineraryId, Long dayId,
                        List<Long> stopIds) {

                // 1. find authenticated user
                User user = userRepository.findByEmail(email)
                                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

                // 2.verify day ownership
                ItineraryDay day = itineraryDayRepository.findByIdAndItinerary_UserId(dayId, user.getId())
                                .orElseThrow(() -> new ResourceNotFoundException("Itinerary day not found"));

                // 3.verify day belongs to requested itinerary
                if (!day.getItinerary().getId().equals(itineraryId)) {
                        throw new ResourceNotFoundException("Itinerary day not found");

                }

                // 4. validate request
                if (stopIds == null) {
                        throw new IllegalArgumentException("Stop IDs are required");
                }

                if (new HashSet<>(stopIds).size() != stopIds.size()) {
                        throw new IllegalStateException("Duplicate stop IDs are not allowed");
                }

                // 5.retrieve all stops beloging to this day
                List<ItineraryStop> stops = itineraryStopRepository.findAllByItineraryDay_IdOrderByStopOrderAsc(dayId);

                // 6.verify that the request contains exactly these stops

                Map<Long, ItineraryStop> stopsById = new HashMap<>();

                for (ItineraryStop stop : stops) {
                        stopsById.put(stop.getId(), stop);
                }

                // 7. Assign temporary orders to avoid unique constraint conflicts
                int maxOrder = stops.stream().mapToInt(ItineraryStop::getStopOrder)
                                .max().orElse(0);

                int temporaryOrder = maxOrder + 1;

                for (ItineraryStop stop : stops) {
                        stop.updateStopOrder(temporaryOrder++);
                }

                itineraryStopRepository.flush();

                // 8.assign the final orders
                for (int i = 0; i < stopIds.size(); i++) {
                        ItineraryStop stop = stopsById.get(stopIds.get(i));
                        stop.updateStopOrder(i + 1);
                }

                // 9.return updated stops
                return stopIds.stream().map(stopsById::get)
                                .map(stop -> new ItineraryStopResponse(stop.getId(), stop.getAttraction().getId(),
                                                stop.getAttraction().getName(), stop.getStopOrder(),
                                                stop.getCreatedAt()))
                                .collect(Collectors.toList());

        }

}
