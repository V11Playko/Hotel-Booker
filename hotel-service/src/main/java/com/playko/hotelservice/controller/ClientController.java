package com.playko.hotelservice.controller;

import com.playko.hotelservice.configuration.Constants;
import com.playko.hotelservice.model.ReservationModel;
import com.playko.hotelservice.service.IReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/hotel/v1/client")
public class ClientController {
    private final IReservationService reservationService;

    public ClientController(IReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @Operation(summary = "Save reservation",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Save reservation",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "409", description = "Reservation already exists",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error"))),})
    @PostMapping("/saveReservation")
    public ResponseEntity<Map<String, String>> saveReservation(@Valid @RequestBody ReservationModel reservation) {
            reservationService.saveReservation(reservation);
            return  ResponseEntity.status(HttpStatus.CREATED)
                    .body(Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY, Constants.SAVED_RESERVATION_MESSAGE));

    }
}
