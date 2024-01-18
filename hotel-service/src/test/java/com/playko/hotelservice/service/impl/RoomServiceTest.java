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

    }
}