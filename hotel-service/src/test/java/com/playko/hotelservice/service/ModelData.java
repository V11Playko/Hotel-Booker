package com.playko.hotelservice.service;

import com.playko.hotelservice.model.HotelModel;

public class ModelData {

    public static HotelModel obtainHotel() {
        HotelModel hotel = new HotelModel();

        hotel.setName("Bolevar");
        hotel.setAddress("Cll 43 centro");
        hotel.setFacilities("Piscina, restaurante");
        hotel.setNumberRooms(43);
        hotel.setAveragePricePerNight(200.50);
        hotel.setStarsCategory("3");

        return hotel;
    }
}
