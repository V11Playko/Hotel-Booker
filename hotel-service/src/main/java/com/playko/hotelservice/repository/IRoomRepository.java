package com.playko.hotelservice.repository;

import com.playko.hotelservice.model.RoomModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRoomRepository extends JpaRepository<RoomModel, Long> {
    Page<RoomModel> findRoomsByHotelId(Long hotelId, Pageable pageable);
}
