package com.playko.hotelservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "room-tb")
@Getter
@Setter
public class RoomModel implements Serializable {

    private static final long serialVersionUID = 1L;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String type;
    private boolean available;

    @ManyToOne
    @JoinColumn(name = "hotel_id") // Nombre de la columna en la tabla de habitaciones que hace referencia al hotel
    @JsonBackReference // Evita la serialización infinita desde el lado "hijo"
    private HotelModel hotel;

    @ManyToOne
    @JoinColumn(name = "reservation_id") // Nombre de la columna en la tabla de habitaciones que hace referencia a la reserva
    private ReservationModel reservation;


    public RoomModel() {
    }

}
