package com.playko.hotelservice.service.impl;

import com.playko.hotelservice.model.HotelModel;
import com.playko.hotelservice.repository.IHotelRepository;
import com.playko.hotelservice.service.ModelData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class HotelServiceTest {

    @Mock
    private IHotelRepository hotelRepository;

    @InjectMocks
    private HotelService hotelService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void saveHotel() {
        HotelModel hotelModel = ModelData.obtainHotel();

        when(hotelRepository.save(any(HotelModel.class))).thenReturn(hotelModel);

        hotelService.saveHotel(hotelModel);

        verify(hotelRepository, times(1)).save(hotelModel);
    }
}