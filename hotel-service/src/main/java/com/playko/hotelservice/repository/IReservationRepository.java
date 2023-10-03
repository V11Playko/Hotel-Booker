package com.playko.hotelservice.repository;

import com.playko.hotelservice.model.ReservationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IReservationRepository extends JpaRepository<ReservationModel, Long> {
}
