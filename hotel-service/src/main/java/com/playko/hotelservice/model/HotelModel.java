package com.playko.hotelservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "hotel-tb")
@Getter
@Setter
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
    @JsonManagedReference // Evita la serialización infinita desde el lado "padre"
    @JsonIgnore
    private List<RoomModel> rooms;

    public HotelModel() {
    }
}
