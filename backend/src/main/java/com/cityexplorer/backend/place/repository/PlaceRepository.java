package com.cityexplorer.backend.place.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cityexplorer.backend.place.entity.Place;

public interface PlaceRepository extends JpaRepository<Place, Long> {

}
