package com.playko.hotelservice.service.impl;

import com.playko.hotelservice.client.UserClient;
import com.playko.hotelservice.client.dto.User;
import com.playko.hotelservice.model.HotelModel;
import com.playko.hotelservice.model.ReservationModel;
import com.playko.hotelservice.service.IHotelService;
import com.playko.hotelservice.service.IPdfReportService;
import com.playko.hotelservice.service.exception.PdfReportGenerationException;
import com.playko.hotelservice.utils.ReportUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class PdfReportService implements IPdfReportService {

    private final UserClient userClient;
    private final IHotelService hotelService;
    private final ReportUtils reportUtils;

    public PdfReportService(UserClient userClient, IHotelService hotelService, ReportUtils reportUtils) {
        this.userClient = userClient;
        this.hotelService = hotelService;
        this.reportUtils = reportUtils;
    }

    @Override
    public void generatePdfReport(String fileName, List<ReservationModel> reservations, List<HotelModel> hotels) throws IOException {
        reservations.sort(Comparator.comparingLong(ReservationModel::getId));

        try (PDDocument document = new PDDocument()) {
            PDType1Font font = PDType1Font.HELVETICA_BOLD;
            int fontSize = 12;

            for (ReservationModel reservation : reservations) {
                PDPage page = new PDPage(PDRectangle.A4);
                document.addPage(page);

                try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                    Optional<User> user = userClient.getClientByAdmin(reservation.getUserId());
                    contentStream.setFont(font, fontSize);
                    contentStream.beginText();

                    // Definir la posición de inicio del texto
                    float yPosition = PDRectangle.A4.getHeight() - 50; // Empieza en la parte superior de la página y ajusta según necesites
                    contentStream.newLineAtOffset(50, yPosition);

                    // Agregar los datos de la Reservación
                    contentStream.showText("Reservation ID: " + reservation.getId());
                    yPosition -= 20; // Avanza a la siguiente línea

                    contentStream.newLineAtOffset(0, -20); // Ajusta solo la posición vertical, mantén la posición horizontal
                    contentStream.showText("Customer Name: " + user.get().getName());
                    yPosition -= 20;

                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("Check-in Date: " + reservation.getDateReservation().toString());
                    yPosition -= 20;

                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("Check-out Date: " + reservation.getCheckOutDate());
                    yPosition -= 20;

                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("Room Type: " + String.join(", ", reportUtils.getRoomTypesFromReservation(reservation)));
                    yPosition -= 20;

                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("Reservation Status: " + reservation.getStatus());
                    yPosition -= 20;

                    // Encontrar el hotel asociado a la reserva
                    HotelModel hotel = hotelService.findHotelById(hotels, reservation.getHotel().getId());

                    if (hotel != null) {
                        // Agregar los datos del Hotel
                        contentStream.newLineAtOffset(0, -20);
                        contentStream.showText("Hotel Name: " + hotel.getName());
                        yPosition -= 20;

                        contentStream.newLineAtOffset(0, -20);
                        contentStream.showText("Hotel Address: " + hotel.getAddress());
                        yPosition -= 20;

                        contentStream.newLineAtOffset(0, -20);
                        contentStream.showText("Hotel Phone: " + hotel.getPhoneNumber());
                        yPosition -= 20;

                        contentStream.newLineAtOffset(0, -20);
                        contentStream.showText("Hotel Stars: " + hotel.getStarsCategory());
                        yPosition -= 20;

                        contentStream.newLineAtOffset(0, -20);
                        contentStream.showText("Hotel Services: " + String.join(", ", hotel.getFacilities()));
                        yPosition -= 20;
                    }

                    contentStream.endText();
                }
            }

            document.save(fileName);
        } catch (IOException e) {
            throw new PdfReportGenerationException();
        }
    }
}
