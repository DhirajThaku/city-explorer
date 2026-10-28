package com.cityexplorer.backend.accomodation.dto;

import java.math.BigDecimal;

public record AccomodationRequest(
        Long placeId,
        String name,
        String type,
        String address,
        BigDecimal pricePerNight) {
}