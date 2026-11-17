package com.cityexplorer.backend.attraction.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cityexplorer.backend.attraction.entity.Attraction;

public interface AttractionRepository extends JpaRepository<Attraction, Long> {
    List<Attraction> findByPlaceId(Long placeId);

    @Query(value = """
                SELECT * FROM attractions a WHERE a.latitude IS NOT NULL AND a.longitude IS NOT NULL AND (6371 * ACOS(
                LEAST(1.0,GREATEST(-1.0,COS(RADIANS(:latitude))
                *COS(RADIANS(a.latitude))
                *COS(RADIANS(a.longitude)-RADIANS(:longitude))
                +SIN(RADIANS(:latitude))
                *SIN(RADIANS(a.latitude))
                ))
                ))<=:radius

                ORDER BY (6371 * ACOS( LEAST(1.0, GREATEST(-1.0,
                        COS(RADIANS(:latitude))
                        * COS(RADIANS(a.latitude))
                        * COS(RADIANS(a.longitude) - RADIANS(:longitude))
                        + SIN(RADIANS(:latitude))
                        * SIN(RADIANS(a.latitude))
                    ))
                        ))ASC
            """, nativeQuery = true)
    List<Attraction> findNearByAttractions(@Param("latitude") BigDecimal latitude,
            @Param("longitude") BigDecimal longitude, @Param("radius") Double radius);
}
