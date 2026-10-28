package com.cityexplorer.backend.accomodation.entity;

import java.math.BigDecimal;

import com.cityexplorer.backend.place.entity.Place;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "accomodations")
public class Accomodation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false, length = 30)
    private String type;

    @Column(columnDefinition = "TEXT")
    private String address;

    @Column(name = "price_per_night", precision = 10, scale = 2)
    private BigDecimal pricePerNight;

    protected Accomodation() {
        // required by JPA
    }

    // parameterized public constructor
    public Accomodation(Place place, String name, String type, String address, BigDecimal pricePerNight) {
        this.place = place;
        this.name = name;
        this.type = type;
        this.address = address;
        this.pricePerNight = pricePerNight;
    }

    public void updateDetails(Place place, String name, String type, String address, BigDecimal pricePerNight) {
        this.place = place;
        this.name = name;
        this.type = type;
        this.address = address;
        this.pricePerNight = pricePerNight;
    }

    public Long getId() {
        return id;
    }

    public Place getPlace() {
        return place;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getAddress() {
        return address;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPricePerNight(BigDecimal pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public BigDecimal getPricePerNight() {
        return pricePerNight;
    }
}
