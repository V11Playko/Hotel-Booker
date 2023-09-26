package com.playko.hotelservice.service;

import com.playko.hotelservice.model.ReservationModel;

import java.util.List;

public interface IReservationService {
    void saveReservation(ReservationModel reservationModel);

    List<ReservationModel> findAllReservation();
}
