package com.playko.hotelservice.service;


import com.playko.hotelservice.model.HotelModel;

import java.util.List;

public interface IHotelService {

    void saveHotel(HotelModel hotelModel);

    List<HotelModel> getHotelList(int page, int elementsXpage);
}
