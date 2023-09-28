package com.playko.hotelservice.service.impl;

import com.playko.hotelservice.client.UserClient;
import com.playko.hotelservice.client.dto.User;
import com.playko.hotelservice.model.HotelModel;
import com.playko.hotelservice.model.ReservationModel;
import com.playko.hotelservice.service.IExcelReportService;
import com.playko.hotelservice.service.IHotelService;
import com.playko.hotelservice.service.exception.ExcelReportFileNotFoundException;
import com.playko.hotelservice.service.exception.ExcelReportGenerationException;
import com.playko.hotelservice.utils.ReportUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class ExcelReportService implements IExcelReportService {
    private final UserClient userClient;
    private final IHotelService hotelService;
    private final ReportUtils reportUtils;

    public ExcelReportService(UserClient userClient, IHotelService hotelService, ReportUtils reportUtils) {
        this.userClient = userClient;
        this.hotelService = hotelService;
        this.reportUtils = reportUtils;
    }

    @Override
    public void generateExcelReport(String fileName, List<ReservationModel> reservations, List<HotelModel> hotels) throws IOException {
        // Ordenar las reservas por ID
        reservations.sort(Comparator.comparingLong(ReservationModel::getId));


        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Report");

            // Crear fila de encabezado
            Row headerRow = sheet.createRow(0);
            String[] columns = {
                    "Reservation ID",
                    "Customer Name",
                    "Check-in Date",
                    "Check-out Date",
                    "Room Type",
                    "Reservation Status",
                    "Hotel Name",
                    "Hotel Address",
                    "Hotel Phone",
                    "Hotel Stars",
                    "Hotel Services"
            };

            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }

            // Agregar datos de reservaciones y hoteles
            int rowNum = 1;
            for (ReservationModel reservation : reservations) {
                // Obtener los tipos de habitaciones de una reserva
                List<String> roomTypes = reportUtils.getRoomTypesFromReservation(reservation);
                Optional<User> user = userClient.getClientByAdmin(reservation.getUserId());

                Row row = sheet.createRow(rowNum++);
                int colNum = 0;

                // Datos de la Reservación
                row.createCell(colNum++).setCellValue(reservation.getId());
                row.createCell(colNum++).setCellValue(user.get().getName());
                row.createCell(colNum++).setCellValue(reservation.getDateReservation().toString());
                row.createCell(colNum++).setCellValue(reservation.getCheckOutDate());
                row.createCell(colNum++).setCellValue(String.join(", ", roomTypes));
                row.createCell(colNum++).setCellValue(reservation.getStatus());

                // Datos del Hotel (asumiendo que cada reserva está asociada a un hotel)
                HotelModel hotel = hotelService.findHotelById(hotels, reservation.getHotel().getId());
                row.createCell(colNum++).setCellValue(hotel.getName());
                row.createCell(colNum++).setCellValue(hotel.getAddress());
                row.createCell(colNum++).setCellValue(hotel.getPhoneNumber());
                row.createCell(colNum++).setCellValue(hotel.getStarsCategory());
                row.createCell(colNum++).setCellValue(String.join(", ", hotel.getFacilities())); // Lista de servicios separados por comas
            }

            try (FileOutputStream outputStream = new FileOutputStream(fileName)) {
                workbook.write(outputStream);
            } catch (FileNotFoundException e) {
                throw new ExcelReportFileNotFoundException();
            } catch (IOException e) {
                throw new ExcelReportGenerationException();
            }
        }
    }

}
