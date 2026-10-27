package com.cityexplorer.backend.accomodation.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cityexplorer.backend.accomodation.dto.AccomodationResponse;
import com.cityexplorer.backend.accomodation.entity.Accomodation;
import com.cityexplorer.backend.accomodation.repository.AccomodationRepository;
import com.cityexplorer.backend.common.exception.ResourceNotFoundException;

@Service
@Transactional(readOnly = true)
public class AccomodationService {

    private final AccomodationRepository accomodationRepository;

    public AccomodationService(AccomodationRepository accomodationRepository) {
        this.accomodationRepository = accomodationRepository;
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
