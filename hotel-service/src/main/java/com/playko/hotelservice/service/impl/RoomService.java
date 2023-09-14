package com.playko.hotelservice.service.impl;

import com.playko.hotelservice.model.HotelModel;
import com.playko.hotelservice.model.RoomModel;
import com.playko.hotelservice.repository.IHotelRepository;
import com.playko.hotelservice.repository.IRoomRepository;
import com.playko.hotelservice.service.IRoomService;
import com.playko.hotelservice.service.exception.HotelNotFoundException;
import com.playko.hotelservice.service.exception.InvalidPageRequestException;
import com.playko.hotelservice.service.exception.RoomNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class RoomService implements IRoomService {
    private final IHotelRepository hotelRepository;
    private final IRoomRepository roomRepository;

    public RoomService(IHotelRepository hotelRepository, IRoomRepository roomRepository) {
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public List<RoomModel> getRooms(Long hotelId, int page, int elementsXpage) {
        // Crear un objeto Pageable para la paginación
        Pageable pageable = PageRequest.of(page, elementsXpage);

        // Buscar el hotel por su ID
        HotelModel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(HotelNotFoundException::new);

        // Validar si el número de página y elementos por página son válidos
        if (page < 0 || elementsXpage <= 0) {
            throw new InvalidPageRequestException();
        }

        // Obtener una página de habitaciones del hotel directamente
        List<RoomModel> roomsList = roomRepository.findRoomsByHotelId(hotelId, pageable).stream().toList();

        if (roomsList.isEmpty()) {
            throw new RoomNotFoundException();
        }

        return roomsList;
    }
}
