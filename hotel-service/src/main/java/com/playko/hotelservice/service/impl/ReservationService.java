package com.playko.hotelservice.service.impl;

import com.playko.hotelservice.model.ReservationModel;
import com.playko.hotelservice.model.RoomModel;
import com.playko.hotelservice.repository.IReservationRepository;
import com.playko.hotelservice.repository.IRoomRepository;
import com.playko.hotelservice.service.IReservationService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ReservationService implements IReservationService {
    private final IReservationRepository reservationRepository;
    private final IRoomRepository roomRepository;

    public ReservationService(IReservationRepository reservationRepository, IRoomRepository roomRepository) {
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public void saveReservation(ReservationModel reservationModel) {
        Long hotelId = reservationModel.getHotel().getId();
        List<RoomModel> roomListByHotelId = roomRepository.findRoomsByHotelId(hotelId);
        List<Long> reservedRoomIds = reservationModel.getReservedRooms().stream()
                .map(RoomModel::getId)
                .toList();

        // Actualiza el atributo 'available' de las habitaciones reservadas a "false"
        for (RoomModel room : roomListByHotelId) {
            if (reservedRoomIds.contains(room.getId())) {
                room.setAvailable(false);
                room.setReservation(reservationModel);
            }
        }

        // Guarda las habitaciones actualizadas en la base de datos
        roomRepository.saveAll(roomListByHotelId);

        // Guarda la reserva con las habitaciones actualizadas en la base de datos
        reservationRepository.save(reservationModel);
    }
}
