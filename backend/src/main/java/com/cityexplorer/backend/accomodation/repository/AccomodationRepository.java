package com.cityexplorer.backend.accomodation.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cityexplorer.backend.accomodation.entity.Accomodation;

public interface AccomodationRepository extends JpaRepository<Accomodation, Long> {

     Page<Accomodation>findByPlace_Id(Long placeId,Pageable pageable);

}
