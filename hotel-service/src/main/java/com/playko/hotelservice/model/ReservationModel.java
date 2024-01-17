package com.playko.hotelservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "reservation-tb")
@Getter
@Setter
public class ReservationModel implements Serializable {

    private static final long serialVersionUID = 1L;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @NotNull
    private Long userId;

    private LocalDateTime dateReservation;

    private String checkOutDate;

    private int lodgingTime;

    private String status;

    @NotNull(message = "El hotel no puede estar en blanco.")
    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private HotelModel hotel;

    @NotEmpty(message = "La lista de habitaciones reservadas no puede estar vacía.")
    @Size(min = 1, message = "Debe haber al menos una habitación reservada.")
    @OneToMany(mappedBy = "reservation")
    private List<RoomModel> reservedRooms;
}
