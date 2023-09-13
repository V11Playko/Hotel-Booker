package com.playko.hotelservice.controller;

import com.playko.hotelservice.configuration.Constants;
import com.playko.hotelservice.model.HotelModel;
import com.playko.hotelservice.model.RoomModel;
import com.playko.hotelservice.service.IHotelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.data.domain.Page;
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
@RequestMapping("/hotel/v1")
public class HotelController {
    private final IHotelService hotelService;

    public HotelController(IHotelService hotelService) {
        this.hotelService = hotelService;
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

}
