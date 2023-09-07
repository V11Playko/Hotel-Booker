package com.playko.hotelservice.service.impl;

import com.playko.hotelservice.model.HotelModel;
import com.playko.hotelservice.model.RoomModel;
import com.playko.hotelservice.repository.IHotelRepository;
import com.playko.hotelservice.repository.IRoomRepository;
import com.playko.hotelservice.service.ModelData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
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
    void testSaveHotel() {
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

    @Test
    void testGetHotelList() {
        // Crear una lista de hoteles ficticia para simular la respuesta de la base de datos
        List<HotelModel> fakeHotelList = List.of(
                ModelData.obtainHotel(),
                ModelData.obtainHotel(),
                ModelData.obtainHotel()
        );

        // Configurar el Mock del repositorio para devolver la lista ficticia cuando se llama a findAll
        when(hotelRepository.findAll(any(Sort.class))).thenReturn(fakeHotelList);

        // Llamar al método que queremos probar
        List<HotelModel> result = hotelService.getHotelList(0, 10);

        // Verificar que el método findAll del repositorio se haya llamado una vez con los argumentos correctos
        verify(hotelRepository, times(1)).findAll(any(Sort.class));

        // Verificar que el resultado coincida con la lista ficticia
        assertEquals(fakeHotelList, result);
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
        List<RoomModel> result = hotelService.getRooms(hotelId, page, elementsXpage);

        // Verifica que se llamó a los métodos correspondientes con los argumentos adecuados
        verify(hotelRepository, times(1)).findById(hotelId);
        verify(roomRepository, times(1)).findRoomsByHotelId(eq(hotelId), any(Pageable.class));

        // Realiza las aserciones necesarias para verificar el resultado
        // ...

        // Por ejemplo, puedes verificar el tamaño de la lista resultante
        assertEquals(roomList.size(), result.size());
    }
}