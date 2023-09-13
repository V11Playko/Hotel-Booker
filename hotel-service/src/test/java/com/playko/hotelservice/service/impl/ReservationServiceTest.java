package com.playko.hotelservice.service.impl;

import com.playko.hotelservice.model.HotelModel;
import com.playko.hotelservice.model.ReservationModel;
import com.playko.hotelservice.model.RoomModel;
import com.playko.hotelservice.repository.IHotelRepository;
import com.playko.hotelservice.repository.IReservationRepository;
import com.playko.hotelservice.repository.IRoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ReservationServiceTest {
    @Mock
    private IRoomRepository roomRepository;
    @Mock
    private IReservationRepository reservationRepository;

    @InjectMocks
    private ReservationService reservationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void saveReservation()  {
        ReservationModel reservationModel = new ReservationModel();
        reservationModel.setHotel(new HotelModel());
        reservationModel.setReservedRooms(Collections.singletonList(new RoomModel()));

    // Simular el comportamiento de los repositorios
    when(roomRepository.findRoomsByHotelId(anyLong())).thenReturn(Collections.singletonList(new RoomModel()));
    when(reservationRepository.save(any(ReservationModel.class))).thenReturn(reservationModel);

    // Llamar al método que se va a probar
        reservationService.saveReservation(reservationModel);

    // Verificar que el estado de las habitaciones se haya actualizado
    verify(roomRepository, times(1)).saveAll(anyList());

    // Verificar que la reserva se haya guardado
    verify(reservationRepository, times(1)).save(any(ReservationModel.class));
}
}