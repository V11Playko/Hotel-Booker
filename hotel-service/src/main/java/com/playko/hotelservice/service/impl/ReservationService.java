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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
}
