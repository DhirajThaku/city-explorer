package com.cityexplorer.backend.accomodation.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cityexplorer.backend.accomodation.dto.AccomodationRequest;
import com.cityexplorer.backend.accomodation.dto.AccomodationResponse;
import com.cityexplorer.backend.accomodation.entity.Accomodation;
import com.cityexplorer.backend.accomodation.repository.AccomodationRepository;
import com.cityexplorer.backend.common.exception.ResourceNotFoundException;
import com.cityexplorer.backend.place.entity.Place;
import com.cityexplorer.backend.place.repository.PlaceRepository;

@Service
@Transactional
public class AccomodationService {

    private final AccomodationRepository accomodationRepository;

    private final PlaceRepository placeRepository;

    public AccomodationService(AccomodationRepository accomodationRepository, PlaceRepository placeRepository) {
        this.accomodationRepository = accomodationRepository;
        this.placeRepository = placeRepository;
    }

    // added pagination below method
    public Page<AccomodationResponse> getAllAccomodatioins(Long placeId, Pageable pageable) {

        Page<Accomodation> accomodations;

        if (placeId != null) {
            accomodations = accomodationRepository.findByPlace_Id(placeId, pageable);
        } else {
            accomodations = accomodationRepository.findAll(pageable);
        }
        // return accomodationRepository.findAll(pageable).map(this::mapToResponse);
        return accomodations.map(this::mapToResponse);
    }

    @Transactional
    public AccomodationResponse createAccomodation(AccomodationRequest request) {
        Place place = placeRepository.findById(request.placeId())
                .orElseThrow(() -> new ResourceNotFoundException("Place not found"));

        Accomodation accomodation = new Accomodation(place, request.name(), request.type(), request.address(),
                request.pricePerNight());
        Accomodation savedAccomodation = accomodationRepository.save(accomodation);

        return mapToResponse(savedAccomodation);
    }

    public AccomodationResponse updateAccomodation(Long id, AccomodationRequest request) {

        // check accomodation present or not
        Accomodation accomodation = accomodationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Accomodation not found"));

        // check place present
        Place place = placeRepository.findById(request.placeId())
                .orElseThrow(() -> new ResourceNotFoundException("Place not found"));

        accomodation.updateDetails(place, request.name(), request.type(), request.address(), request.pricePerNight());

        return mapToResponse(accomodation);
    }

    public void deleteAccomodation(Long id) {
        // check id present
        Accomodation accomodation = accomodationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Accomodation not found"));

        accomodationRepository.delete(accomodation);
    }

    private AccomodationResponse mapToResponse(Accomodation accomodation) {
        return new AccomodationResponse(
                accomodation.getId(),
                accomodation.getPlace().getId(),
                accomodation.getPlace().getName(),
                accomodation.getName(),
                accomodation.getType(),
                accomodation.getAddress(),
                accomodation.getPricePerNight()

        );
    }

    public AccomodationResponse getAccomodationById(Long id) {
        return accomodationRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Accomodation not found"));
    }
}
