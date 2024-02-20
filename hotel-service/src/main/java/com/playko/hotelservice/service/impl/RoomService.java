package com.playko.hotelservice.service.impl;

import com.playko.hotelservice.model.HotelModel;
import com.playko.hotelservice.model.RoomModel;
import com.playko.hotelservice.repository.IHotelRepository;
import com.playko.hotelservice.repository.IRoomRepository;
import com.playko.hotelservice.service.IRoomService;
import com.playko.hotelservice.service.exception.HotelNotFoundException;
import com.playko.hotelservice.service.exception.InvalidPageRequestException;
import com.playko.hotelservice.service.exception.RoomNotFoundException;
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

    /**
     * Get a list of rooms using hotel id and pagination
     *
     * @param hotelId - hotel id
     * @param page - Page number
     * @param elementsXpage - Elements that will be per page
     * @throws HotelNotFoundException - Hotel not found
     * @throws InvalidPageRequestException - They are doing pagination incorrectly
     * @throws RoomNotFoundException - Room not found
     * @return roomList
     */
    @Override
    public List<RoomModel> getRooms(Long hotelId, int page, int elementsXpage) {
        Pageable pageable = PageRequest.of(page, elementsXpage);

        HotelModel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(HotelNotFoundException::new);

        if (page < 0 || elementsXpage <= 0) {
            throw new InvalidPageRequestException();
        }

        List<RoomModel> roomsList = roomRepository.findRoomsByHotelId(hotelId, pageable).stream().toList();

        if (roomsList.isEmpty()) {
            throw new RoomNotFoundException();
        }

        return roomsList;
    }
}
