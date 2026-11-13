package com.cityexplorer.backend.itinerary.dto;

import java.util.List;

public record ReorderItineraryStopsRequest(
        List<Long> stopIds) {

}
