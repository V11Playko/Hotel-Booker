package com.playko.hotelservice.service.impl;

import com.playko.hotelservice.model.HotelModel;
import com.playko.hotelservice.model.RoomModel;
import com.playko.hotelservice.repository.IHotelRepository;
import com.playko.hotelservice.repository.IRoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RoomServiceTest {

    @Mock
    private IRoomRepository roomRepository;
    @Mock
    private IHotelRepository hotelRepository;

    @InjectMocks
    private RoomService roomService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetRooms() {
        // Datos de prueba
        Long hotelId = 1L;
        int page = 0;
        int elementsXpage = 10;

        // Mock del hotel encontrado por ID
        HotelModel hotel = new HotelModel();
        hotel.setId(hotelId);
        when(hotelRepository.findById(hotelId)).thenReturn(Optional.of(hotel));

        // Mock de la página de habitaciones
        List<RoomModel> roomList = new ArrayList<>();
        // Agrega habitaciones de prueba a la lista
        // ...

        Page<RoomModel> roomPage = new PageImpl<>(roomList, Pageable.unpaged(), roomList.size());
        when(roomRepository.findRoomsByHotelId(eq(hotelId), any(Pageable.class))).thenReturn(roomPage);

        // Llama al método que se va a probar
        List<RoomModel> result = roomService.getRooms(hotelId, page, elementsXpage);

        // Verifica que se llamó a los métodos correspondientes con los argumentos adecuados
        verify(hotelRepository, times(1)).findById(hotelId);
        verify(roomRepository, times(1)).findRoomsByHotelId(eq(hotelId), any(Pageable.class));

        // Realiza las aserciones necesarias para verificar el resultado
        // ...

        // Por ejemplo, puedes verificar el tamaño de la lista resultante
        assertEquals(roomList.size(), result.size());
    }
}