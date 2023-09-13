package com.playko.hotelservice.controller;

import com.playko.hotelservice.configuration.Constants;
import com.playko.hotelservice.model.ReservationModel;
import com.playko.hotelservice.model.RoomModel;
import com.playko.hotelservice.service.IReservationService;
import com.playko.hotelservice.service.IRoomService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/hotel/v1/client")
public class ClientController {
    private final IRoomService roomService;
    private final IReservationService reservationService;

    public ClientController(IRoomService roomService, IReservationService reservationService) {
        this.roomService = roomService;
        this.reservationService = reservationService;
    }

    @Operation(summary = "Get Rooms")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Rooms list returned", content = @Content),
            @ApiResponse(responseCode = "409", description = "Rooms already exists", content = @Content)
    })
    @GetMapping("/list-rooms/{hotelId}")
    public ResponseEntity<List<RoomModel>> listRooms(
            @PathVariable("hotelId") Long hotelId,
            @Positive @RequestParam("page") int page,
            @Positive @RequestParam("elementsXPage") int elementsXPage
    ){
        return ResponseEntity.ok(roomService.getRooms(hotelId, page, elementsXPage));
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
