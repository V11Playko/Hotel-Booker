package com.playko.hotelservice.service;

import com.playko.hotelservice.model.HotelModel;
import com.playko.hotelservice.model.ReservationModel;

import java.io.IOException;
import java.util.List;

public interface IExcelReportService {
    void generateExcelReport(String fileName, List<ReservationModel> reservations, List<HotelModel> hotels) throws IOException;
}
