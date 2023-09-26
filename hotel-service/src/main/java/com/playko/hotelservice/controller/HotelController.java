package com.playko.hotelservice.controller;

import com.playko.hotelservice.configuration.Constants;
import com.playko.hotelservice.model.HotelModel;
import com.playko.hotelservice.model.ReservationModel;
import com.playko.hotelservice.service.IHotelService;
import com.playko.hotelservice.service.IReservationService;
import com.playko.hotelservice.service.impl.ExcelReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.playko.hotelservice.configuration.Constants.INPUT_OUTPUT_EXCEPTION;
import static com.playko.hotelservice.configuration.Constants.RESPONSE_ERROR_MESSAGE_KEY;

@RestController
@RequestMapping("/hotel/v1")
public class HotelController {
    private final IHotelService hotelService;
    private final IReservationService reservationService;
    private final ExcelReportService excelReportService;


    public HotelController(IHotelService hotelService, IReservationService reservationService, ExcelReportService excelReportService) {
        this.hotelService = hotelService;
        this.reservationService = reservationService;
        this.excelReportService = excelReportService;
    }

    @Operation(summary = "Add a new hotel",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Hotel created",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "409", description = "Hotel already exists",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error"))),})
    @PostMapping("/save-hotel")
    public ResponseEntity<Map<String, String>> saveHotel(@Valid @RequestBody HotelModel hotel) {
        hotelService.saveHotel(hotel);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY, Constants.HOTEL_CREATED_MESSAGE));
    }

    @Operation(summary = "Get all the hotels")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Hotel list returned", content = @Content),
            @ApiResponse(responseCode = "409", description = "Hotel already exists", content = @Content)
    })
    @GetMapping("/list-hotels")
    public ResponseEntity<List<HotelModel>> listHotel(
            @Positive @RequestParam("page") int page,
            @Positive @RequestParam("elementsXPage") int elementsXPage
    ){
        return ResponseEntity.ok(hotelService.getHotelList(page, elementsXPage));
    }

    @Operation(summary = "Get excel report")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Excel report returned", content = @Content),
            @ApiResponse(responseCode = "409", description = "Excel report exists", content = @Content)
    })
    @GetMapping("/generate-excel")
    public ResponseEntity<Map<String, String>> generateExcelReport() {
        try {
            String fileName = "report.xlsx"; // Nombre del archivo de Excel
            List<ReservationModel> reservations = reservationService.findAllReservation(); // Reemplaza con tus datos reales
            List<HotelModel> hotels = hotelService.findAllHotel(); // Reemplaza con tus datos reales
            excelReportService.generateExcelReport(fileName, reservations, hotels);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY, Constants.EXCEL_REPORT_CREATE_SUCCESSFULLY));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, INPUT_OUTPUT_EXCEPTION));
        }
    }
}
