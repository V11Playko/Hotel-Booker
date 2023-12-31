package com.playko.hotelservice.repository;

import com.playko.hotelservice.model.HotelModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IHotelRepository extends JpaRepository<HotelModel, Long> {
}
