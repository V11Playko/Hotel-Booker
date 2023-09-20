package com.playko.hotelservice.service.impl;

import com.playko.hotelservice.model.ReservationModel;
import com.playko.hotelservice.model.RoomModel;
import com.playko.hotelservice.repository.IHotelRepository;
import com.playko.hotelservice.repository.IReservationRepository;
import com.playko.hotelservice.repository.IRoomRepository;
import com.playko.hotelservice.service.IReservationService;
import com.playko.hotelservice.service.exception.HotelNotFoundException;
import com.playko.hotelservice.service.exception.RoomNotFoundException;
import com.playko.hotelservice.service.exception.RoomUnavailableException;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@Transactional
public class ReservationService implements IReservationService {
    private final IReservationRepository reservationRepository;

    private final IRoomRepository roomRepository;

    private final IHotelRepository hotelRepository;

    public ReservationService(IReservationRepository reservationRepository, IRoomRepository roomRepository, IHotelRepository hotelRepository) {
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
        this.hotelRepository = hotelRepository;
    }

    @Override
    public void saveReservation(ReservationModel reservationModel) {
        Long hotelId = reservationModel.getHotel().getId();

        if (!hotelRepository.existsById(hotelId)) {
            throw new HotelNotFoundException();
        }

        // Validar que lodgingTime no sea nulo ni negativo
        if (reservationModel.getLodgingTime() <= 0) {
            throw new IllegalArgumentException();
        }

        reservationModel.setDateReservation(LocalDateTime.now());
        reservationModel.setStatus("Reservada");

        List<RoomModel> roomListByHotelId = roomRepository.findRoomsByHotelId(hotelId);
        List<Long> reservedRoomIds = reservationModel.getReservedRooms().stream()
                .map(RoomModel::getId)
                .toList();

        // Actualiza las habitaciones reservadas
        for (Long roomId : reservedRoomIds) {
            updateRoomAvailability(roomId, false, reservationModel);
        }

        // Guarda las habitaciones actualizadas en la base de datos
        roomRepository.saveAll(roomListByHotelId);

        // Guarda la reserva con las habitaciones actualizadas en la base de datos
        reservationRepository.save(reservationModel);
    }

    private void updateRoomAvailability(Long roomId, boolean availability, ReservationModel reservationModel) {
        RoomModel room = roomRepository.findById(roomId)
                .orElseThrow(RoomNotFoundException::new);

        if (!room.isAvailable()) {
            throw new RoomUnavailableException();
        }

        room.setAvailable(availability);
        room.setReservation(reservationModel);
    }

    @Scheduled(cron = "0 */2 * * * *") // Ejecutar cada 2 minutos
    public void checkExpiredReservations() {
        LocalDateTime currentDateTime = LocalDateTime.now();

        List<ReservationModel> reservations = reservationRepository.findAll();

        for (ReservationModel reservation : reservations) {
            LocalDate reservationEndDate = LocalDate.from(reservation.getDateReservation()
                    .plusDays(reservation.getLodgingTime())); // Suponiendo que la fecha de finalización se calcula sumando días a la fecha de reserva

            LocalDateTime reservationEndDateTime = reservationEndDate.atTime(LocalTime.MAX);

            if (currentDateTime.isAfter(reservationEndDateTime)) {
                // La reserva ha vencido, restaura la disponibilidad de las habitaciones
                restoreRoomAvailability(reservation);
                // Actualiza el estado de la reserva
                reservation.setStatus("Completada");
                // Actualiza la reserva en la base de datos
                reservationRepository.save(reservation);
            }
        }
    }

    private void restoreRoomAvailability(ReservationModel reservation) {
        List<RoomModel> reservedRooms = reservation.getReservedRooms();
        for (RoomModel room : reservedRooms) {
            // Restaurar la disponibilidad de las habitaciones
            room.setAvailable(true);
            room.setReservation(null); // Elimina la referencia a la reserva
            roomRepository.save(room);
        }
    }
}

