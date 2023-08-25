package com.playko.hotelservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name = "hotel-table")
public class HotelModel implements Serializable {

    private static final long serialVersionUID = 1L;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String name;
    private String address;
    private String starsCategory;
    private Integer numberRooms;
    private String facilities;
    private Double averagePricePerNight;

    public HotelModel() {
    }

    public HotelModel(Long id, String name, String address, String starsCategory, Integer numberRooms, String facilities, Double averagePricePerNight) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.starsCategory = starsCategory;
        this.numberRooms = numberRooms;
        this.facilities = facilities;
        this.averagePricePerNight = averagePricePerNight;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStarsCategory() {
        return starsCategory;
    }

    public void setStarsCategory(String starsCategory) {
        this.starsCategory = starsCategory;
    }

    public Integer getNumberRooms() {
        return numberRooms;
    }

    public void setNumberRooms(Integer numberRooms) {
        this.numberRooms = numberRooms;
    }

    public String getFacilities() {
        return facilities;
    }

    public void setFacilities(String facilities) {
        this.facilities = facilities;
    }

    public Double getAveragePricePerNight() {
        return averagePricePerNight;
    }

    public void setAveragePricePerNight(Double averagePricePerNight) {
        this.averagePricePerNight = averagePricePerNight;
    }
}
