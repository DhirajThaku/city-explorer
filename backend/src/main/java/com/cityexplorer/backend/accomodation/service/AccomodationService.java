package com.cityexplorer.backend.accomodation.service;

import java.util.List;

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

    public List<AccomodationResponse> getAllAccomodatioins() {
        return accomodationRepository.findAll().stream().map(this::mapToResponse).toList();
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
