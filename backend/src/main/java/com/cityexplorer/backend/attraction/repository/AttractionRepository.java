package com.cityexplorer.backend.attraction.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cityexplorer.backend.attraction.entity.Attraction;

public interface AttractionRepository extends JpaRepository<Attraction, Long> {
    List<Attraction> findByPlaceId(Long placeId);
}
