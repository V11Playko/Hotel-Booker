package com.playko.hotelservice.service.impl;

import com.playko.hotelservice.model.HotelModel;
import com.playko.hotelservice.model.RoomModel;
import com.playko.hotelservice.repository.IHotelRepository;
import com.playko.hotelservice.repository.IRoomRepository;
import com.playko.hotelservice.service.IHotelService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class HotelService implements IHotelService {

    private final IHotelRepository hotelRepository;
    private final IRoomRepository roomRepository;

    public HotelService(IHotelRepository hotelRepository, IRoomRepository roomRepository) {
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public void saveHotel(HotelModel hotelModel) {
        // Guarda el hotel en la base de datos
        hotelRepository.save(hotelModel);

        // Define las proporciones de habitaciones según la categoría de estrellas
        double[] roomProportions = getRoomProportions(hotelModel.getStarsCategory(), hotelModel.getNumberRooms());

        // Define las categorías de habitaciones y sus cantidades
        String[] roomCategories = {"Standard", "Superior", "Suite"};
        int[] roomCounts = new int[roomCategories.length];

        // Calcula la cantidad de habitaciones para cada categoría
        for (int i = 0; i < roomCategories.length; i++) {
            roomCounts[i] = (int) (roomProportions[i] * hotelModel.getNumberRooms());
        }

        // Crea y guarda las habitaciones en la base de datos
        for (int i = 0; i < roomCategories.length; i++) {
            for (int j = 0; j < roomCounts[i]; j++) {
                RoomModel room = new RoomModel();
                room.setType(roomCategories[i]);
                room.setAvailable(true);
                room.setHotel(hotelModel);
                roomRepository.save(room);
            }
        }
    }

    @Override
    public List<HotelModel> getHotelList(int page, int elementsXpage) {
        // Define la ordenación por ID de manera ascendente
        Sort sort = Sort.by(Sort.Order.asc("id"));

        // Crea una solicitud de página que incluya la ordenación
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE, sort);

        // Obtén todos los hoteles ordenados por ID
        List<HotelModel> hotels = hotelRepository.findAll(sort);

        return hotels;
    }

    private double[] getRoomProportions(Integer starsCategory, Integer numRooms) {
        double[] proportions = {0.0, 0.0, 0.0}; // Inicializa todas las proporciones a 0

        if (starsCategory != null && numRooms != null && numRooms > 0) {
            switch (starsCategory) {
                case 1:
                    proportions[0] = 1.0; // 100% Standard
                    break;
                case 2:
                    proportions[0] = 0.7; // 70% Standard
                    proportions[1] = 0.3; // 30% Superior
                    break;
                case 3:
                    proportions[0] = 0.6; // 60% Standard
                    proportions[1] = 0.3; // 30% Superior
                    proportions[2] = 0.1; // 10% Suite
                    break;
                case 4:
                    proportions[0] = 0.5; // 50% Standard
                    proportions[1] = 0.4; // 40% Superior
                    proportions[2] = 0.1; // 10% Suite
                    break;
                case 5:
                    proportions[0] = 0.4; // 40% Standard
                    proportions[1] = 0.4; // 40% Superior
                    proportions[2] = 0.2; // 20% Suite
                    break;
                default:
                    // Tratar otros valores de starsCategory si es necesario
                    break;
            }
        }

        return proportions;
    }

}
