package com.playko.hotelservice.service;

import com.playko.hotelservice.model.RoomModel;

import java.util.List;

public interface IRoomService {
    List<RoomModel> getRooms(Long hotelId, int page, int elementsXpage);
}
