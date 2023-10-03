package com.playko.hotelservice.service.impl;

import com.playko.hotelservice.model.HotelModel;
import com.playko.hotelservice.model.RoomModel;
import com.playko.hotelservice.repository.IHotelRepository;
import com.playko.hotelservice.repository.IRoomRepository;
import com.playko.hotelservice.service.IHotelService;
import com.playko.hotelservice.service.exception.HotelNotSaveException;
import com.playko.hotelservice.service.exception.InvalidPageRequestException;
import com.playko.hotelservice.service.exception.InvalidStarsCategoryException;
import com.playko.hotelservice.service.exception.NoDataFoundException;
import com.playko.hotelservice.service.exception.NumberRoomsPositiveException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class HotelService implements IHotelService {

    private final IHotelRepository hotelRepository;

    private final IRoomRepository roomRepository;

    public HotelService(IHotelRepository hotelRepository, IRoomRepository roomRepository) {
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
    }

    /**
     * Save a hotel next to the specified rooms
     *
     * @param hotelModel - Model with which the hotel will be saved
     * @throws  NumberRoomsPositiveException - The number of rooms must be positive
     * @throws InvalidStarsCategoryException - The number of stars must be between 1 and 5
     */

    @Override
    public void saveHotel(HotelModel hotelModel) {
        // Validar si el número de habitaciones es positivo
        if (hotelModel.getNumberRooms() <= 0) {
            throw new NumberRoomsPositiveException();
        }

        // Validar si la categoría de estrellas está en el rango de 1 a 5
        int starsCategory = hotelModel.getStarsCategory();
        if (starsCategory < 1 || starsCategory > 5) {
            throw new InvalidStarsCategoryException();
        }

        // Define las proporciones de habitaciones según la categoría de estrellas
        double[] roomProportions = getRoomProportions(hotelModel.getStarsCategory(), hotelModel.getNumberRooms());

        // Define las categorías de habitaciones y sus cantidades
        String[] roomCategories = {"Standard", "Superior", "Suite"};

        // Crea y guarda las habitaciones en la base de datos
        for (String roomCategory : roomCategories) {
            int roomCount = (int) (roomProportions
                    [Arrays.asList(roomCategories).indexOf(roomCategory)] * hotelModel.getNumberRooms());
            for (int j = 0; j < roomCount; j++) {
                RoomModel room = new RoomModel();
                room.setType(roomCategory);
                room.setAvailable(true);
                room.setHotel(hotelModel);
                roomRepository.save(room);
            }
        }

        // Guarda el hotel en la base de datos después de crear las habitaciones
        hotelRepository.save(hotelModel);
    }

    /**
     *  List the hotels with the indicated pagination
     *
     * @param page - Page number
     * @param elementsXpage - Elements that will be per page
     * @throws InvalidPageRequestException - They are doing pagination incorrectly
     * @throws NoDataFoundException - No data found
     * @return hotels
     */
    @Override
    public List<HotelModel> getHotelList(int page, int elementsXpage) {
        // Define la ordenación por ID de manera ascendente
        Sort sort = Sort.by(Sort.Order.asc("id"));

        // Crea una solicitud de página que incluya la ordenación
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE, sort);

        if (page < 0 || elementsXpage <= 0) {
            throw new InvalidPageRequestException();
        }

        // Obtén todos los hoteles ordenados por ID
        List<HotelModel> hotels = hotelRepository.findAll(sort);

        if (hotels.isEmpty()) {
            throw new NoDataFoundException();
        }

        return hotels;
    }

    /**
     * Find all hotels
     *
     * @return
     */
    @Override
    public List<HotelModel> findAllHotel() {
        return hotelRepository.findAll();
    }

    /**
     * Find all hotels using an id
     *
     * @param hotels - list of hotels
     * @param hotelId - id of hotel
     * @return hotels
     */
    @Override
    public HotelModel findHotelById(List<HotelModel> hotels, Long hotelId) {
        return hotels.stream()
                .filter(hotel -> hotel.getId().equals(hotelId))
                .findFirst()
                .orElse(new HotelModel()); // Tratar la situación en la que no se encuentra el hotel
    }

    /**
     * Method that returns the proportions of the rooms using the number of rooms and the number of stars
     *
     * @param starsCategory - Star category of the hotel
     * @param numRooms - Number of hotel rooms
     * @return proportions
     */
    private double[] getRoomProportions(Integer starsCategory, Integer numRooms) {
        double[] proportions = {0.0, 0.0, 0.0}; // Inicializa todas las proporciones a 0

        if (starsCategory != null && numRooms != null && numRooms > 0) {
            switch (starsCategory) {
                case 1 -> proportions[0] = 1.0; // 100% Standard
                case 2 -> {
                    proportions[0] = 0.7; // 70% Standard
                    proportions[1] = 0.3; // 30% Superior
                }
                case 3 -> {
                    proportions[0] = 0.6; // 60% Standard
                    proportions[1] = 0.3; // 30% Superior
                    proportions[2] = 0.1; // 10% Suite
                }
                case 4 -> {
                    proportions[0] = 0.5; // 50% Standard
                    proportions[1] = 0.4; // 40% Superior
                    proportions[2] = 0.1; // 10% Suite
                }
                case 5 -> {
                    proportions[0] = 0.4; // 40% Standard
                    proportions[1] = 0.4; // 40% Superior
                    proportions[2] = 0.2; // 20% Suite
                }
                default -> {
                }
                // Tratar otros valores de starsCategory si es necesario
            }
        }

        return proportions;
    }

}
