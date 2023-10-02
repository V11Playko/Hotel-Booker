package com.playko.hotelservice.utils;

import com.playko.hotelservice.model.ReservationModel;
import com.playko.hotelservice.model.RoomModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReportUtils {

    public List<String> getRoomTypesFromReservation(ReservationModel reservation) {
        List<String> roomTypes = new ArrayList<>();

        // Verificar si la reserva tiene habitaciones
        if (reservation.getReservedRooms() != null && !reservation.getReservedRooms().isEmpty()) {
            // Iterar sobre las habitaciones reservadas
            for (RoomModel room : reservation.getReservedRooms()) {
                // Obtener el tipo de habitación y agregarlo a la lista
                String roomType = room.getType();
                roomTypes.add(roomType);
            }
        }

        return roomTypes;
    }
}
