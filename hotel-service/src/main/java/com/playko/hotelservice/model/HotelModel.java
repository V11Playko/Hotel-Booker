package com.playko.hotelservice.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "hotel-table")
public class HotelModel implements Serializable {

    private static final long serialVersionUID = 1L;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String name;
    private String address;
    @Min(value = 1, message = "La categoría de estrellas debe ser un número entre 1 y 5.")
    @Max(value = 5, message = "La categoría de estrellas debe ser un número entre 1 y 5.")
    private Integer starsCategory;
    private Integer numberRooms;
    private String facilities;
    private Double averagePricePerNight;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<RoomModel> rooms;

    public HotelModel() {
    }

    public HotelModel(Long id, String name, String address, Integer starsCategory, Integer numberRooms, String facilities, Double averagePricePerNight, List<RoomModel> rooms) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.starsCategory = starsCategory;
        this.numberRooms = numberRooms;
        this.facilities = facilities;
        this.averagePricePerNight = averagePricePerNight;
        this.rooms = rooms;
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

    public Integer getStarsCategory() {
        return starsCategory;
    }

    public void setStarsCategory(Integer starsCategory) {
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

    public List<RoomModel> getRooms() {
        return rooms;
    }

    public void setRooms(List<RoomModel> rooms) {
        this.rooms = rooms;
    }
}
