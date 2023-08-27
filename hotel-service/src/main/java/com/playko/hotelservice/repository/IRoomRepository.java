package com.playko.hotelservice.repository;

import com.playko.hotelservice.model.RoomModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoomRepository extends JpaRepository<RoomModel, Long> {
}
