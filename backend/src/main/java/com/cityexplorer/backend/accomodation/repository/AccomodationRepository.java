package com.cityexplorer.backend.accomodation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cityexplorer.backend.accomodation.entity.Accomodation;

public interface AccomodationRepository extends JpaRepository<Accomodation, Long> {

}
