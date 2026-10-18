package com.cityexplorer.backend.attraction.dto;

import java.math.BigDecimal;

public record AttractionResponse(
        Long id,
        Long placeId,
        String name,
        String description,
        String category,
        String address,
        BigDecimal latitude,
        BigDecimal longitude,
        BigDecimal entryFee) {
}
