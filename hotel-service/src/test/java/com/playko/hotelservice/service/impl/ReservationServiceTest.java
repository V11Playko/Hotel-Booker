package com.playko.hotelservice.service.impl;

import com.playko.hotelservice.model.HotelModel;
import com.playko.hotelservice.model.ReservationModel;
import com.playko.hotelservice.model.RoomModel;
import com.playko.hotelservice.repository.IHotelRepository;
import com.playko.hotelservice.repository.IReservationRepository;
import com.playko.hotelservice.repository.IRoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {
    @Mock
    private IRoomRepository roomRepository;
    @Mock
    private IReservationRepository reservationRepository;
    @Mock
    private IHotelRepository hotelRepository;
    @InjectMocks
    private ReservationService reservationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void saveReservation()  {
    }
}