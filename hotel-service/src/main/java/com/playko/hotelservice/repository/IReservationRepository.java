package com.playko.hotelservice.repository;

import com.playko.hotelservice.model.ReservationModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IReservationRepository extends JpaRepository<ReservationModel, Long> {
}
