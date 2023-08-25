package com.playko.hotelservice.service.impl;

import com.playko.hotelservice.model.HotelModel;
import com.playko.hotelservice.repository.IHotelRepository;
import com.playko.hotelservice.service.IHotelService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class HotelService implements IHotelService {

    private final IHotelRepository hotelRepository;

    public HotelService(IHotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @Override
    public void saveHotel(HotelModel hotelModel) {
        hotelRepository.save(hotelModel);
    }
}
