package com.playko.hotelservice.service.impl;

import com.playko.hotelservice.model.HotelModel;
import com.playko.hotelservice.repository.IHotelRepository;
import com.playko.hotelservice.repository.IRoomRepository;
import com.playko.hotelservice.service.ModelData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class HotelServiceTest {

    @Mock
    private IRoomRepository roomRepository;
    @Mock
    private IHotelRepository hotelRepository;

    @InjectMocks
    private HotelService hotelService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveHotel() {
        // Crea un objeto HotelModel de ejemplo
        HotelModel hotelModel = ModelData.obtainHotel();

        // Define las proporciones esperadas para el test
        double[] expectedProportions = {0.6, 0.3, 0.1}; // 60% Standard, 30% Superior, 10% Suite

        // Configura el comportamiento esperado para el hotelRepository
        when(hotelRepository.save(hotelModel)).thenReturn(hotelModel);

        // Llama al método saveHotel
        hotelService.saveHotel(hotelModel);

        // Verifica que se haya llamado a hotelRepository.save con el hotelModel
        verify(hotelRepository, times(1)).save(hotelModel);

        // Verifica que se hayan creado las habitaciones con las proporciones esperadas
        for (int i = 0; i < 3; i++) {
            String roomType = i == 0 ? "Standard" : (i == 1 ? "Superior" : "Suite");
            int expectedCount = (int) (expectedProportions[i] * hotelModel.getNumberRooms());
            verify(roomRepository, times(expectedCount)).save(argThat(room -> room.getType().equals(roomType)));
        }
    }
}