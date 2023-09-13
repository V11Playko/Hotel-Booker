package com.playko.hotelservice.service;


import com.playko.hotelservice.model.HotelModel;
import com.playko.hotelservice.model.RoomModel;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IHotelService {

    void saveHotel(HotelModel hotelModel);

    List<HotelModel> getHotelList(int page, int elementsXpage);
}
