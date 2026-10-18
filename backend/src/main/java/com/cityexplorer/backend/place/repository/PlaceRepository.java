package com.cityexplorer.backend.place.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cityexplorer.backend.place.entity.Place;

public interface PlaceRepository extends JpaRepository<Place, Long> {

    // search places by name ,state and country with pagination

    @Query("""
            SELECT p FROM Place p
            WHERE (CAST(:name AS string) IS NULL
                   OR LOWER(p.name) LIKE LOWER(CONCAT('%', CAST(:name AS string), '%')))
              AND (CAST(:state AS string) IS NULL
                   OR LOWER(p.state) = LOWER(CAST(:state AS string)))
              AND (CAST(:country AS string) IS NULL
                   OR LOWER(p.country) = LOWER(CAST(:country AS string)))
            """)

    Page<Place> searchPlaces(
            @Param("name") String name,
            @Param("state") String state,
            @Param("country") String country,
            Pageable pageable);

    // add search method
    // Page<Place> findByNameContainingIgnoreCase(String name, Pageable pageable);

    // Page<Place> findByStateIgnoreCase(String state, Pageable pageable);

    // Page<Place> findByCountryIgnoreCase(String country, Pageable pageable);
}
