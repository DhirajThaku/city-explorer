package com.cityexplorer.backend.accomodation.dto;

import java.math.BigDecimal;

public record AccomodationResponse(
        Long id,
        Long placeId,
        String placeName,
        String name,
        String type,
        String address,
        BigDecimal pricePerNight

) {

}
