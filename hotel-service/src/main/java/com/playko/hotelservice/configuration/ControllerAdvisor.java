package com.playko.hotelservice.configuration;

import com.playko.hotelservice.service.exception.DuplicateHotelAddressException;
import com.playko.hotelservice.service.exception.DuplicateHotelNameException;
import com.playko.hotelservice.service.exception.DuplicateHotelPhoneException;
import com.playko.hotelservice.service.exception.ExcelReportFileNotFoundException;
import com.playko.hotelservice.service.exception.ExcelReportGenerationException;
import com.playko.hotelservice.service.exception.HotelNotFoundException;
import com.playko.hotelservice.service.exception.HotelNotSaveException;
import com.playko.hotelservice.service.exception.InvalidLodgingTimeException;
import com.playko.hotelservice.service.exception.InvalidPageRequestException;
import com.playko.hotelservice.service.exception.InvalidStarsCategoryException;
import com.playko.hotelservice.service.exception.NoDataFoundException;
import com.playko.hotelservice.service.exception.NumberRoomsPositiveException;
import com.playko.hotelservice.service.exception.PdfReportGenerationException;
import com.playko.hotelservice.service.exception.RoomNotFoundException;
import com.playko.hotelservice.service.exception.RoomUnavailableException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.playko.hotelservice.configuration.Constants.DUPLICATE_HOTEL_ADDRESS_EXCEPTION;
import static com.playko.hotelservice.configuration.Constants.DUPLICATE_HOTEL_NAME_EXCEPTION;
import static com.playko.hotelservice.configuration.Constants.DUPLICATE_HOTEL_PHONE_EXCEPTION;
import static com.playko.hotelservice.configuration.Constants.EXCEL_REPORT_GENERATION_EXCEPTION;
import static com.playko.hotelservice.configuration.Constants.EXCEL_REPORT_NOT_FOUND_EXCEPTION;
import static com.playko.hotelservice.configuration.Constants.HOTEL_NOT_FOUND_EXCEPTION;
import static com.playko.hotelservice.configuration.Constants.HOTEL_NOT_SAVE_EXCEPTION;
import static com.playko.hotelservice.configuration.Constants.INVALID_LODGING_TIME_EXCEPTION;
import static com.playko.hotelservice.configuration.Constants.INVALID_PAGE_REQUEST_EXCEPTION;
import static com.playko.hotelservice.configuration.Constants.INVALID_STARS_CATEGORY_EXCEPTION;
import static com.playko.hotelservice.configuration.Constants.NO_DATA_FOUND_MESSAGE;
import static com.playko.hotelservice.configuration.Constants.NUMBER_ROOMS_POSITIVE_EXCEPTION;
import static com.playko.hotelservice.configuration.Constants.PDF_REPORT_GENERATION_EXCEPTION;
import static com.playko.hotelservice.configuration.Constants.RESPONSE_ERROR_MESSAGE_KEY;
import static com.playko.hotelservice.configuration.Constants.ROOM_NOT_FOUND_EXCEPTION;
import static com.playko.hotelservice.configuration.Constants.ROOM_UNVAILABLE_EXCEPTION;

@ControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();

        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            String fieldName = violation.getPropertyPath().toString();
            String errorMessage = violation.getMessage();
            errors.put(fieldName, errorMessage);
        }

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleBindExceptions(BindException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<Map<String, String>> handleNoDataFoundException(
            NoDataFoundException noDataFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, NO_DATA_FOUND_MESSAGE));
    }

    @ExceptionHandler(HotelNotSaveException.class)
    public ResponseEntity<Map<String, String>> handleHotelNotSaveException(
            HotelNotSaveException hotelNotSaveException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, HOTEL_NOT_SAVE_EXCEPTION));
    }

    @ExceptionHandler(HotelNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleHotelNotFoundException(
            HotelNotFoundException hotelNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY,HOTEL_NOT_FOUND_EXCEPTION));
    }

    @ExceptionHandler(RoomNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleRoomNotFoundException(
            RoomNotFoundException roomNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY,ROOM_NOT_FOUND_EXCEPTION));
    }

    @ExceptionHandler(RoomUnavailableException.class)
    public ResponseEntity<Map<String, String>> handleRoomUnavailableException(
            RoomUnavailableException roomUnavailableException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, ROOM_UNVAILABLE_EXCEPTION));
    }

    @ExceptionHandler(InvalidPageRequestException.class)
    public ResponseEntity<Map<String, String>> handleInvalidPageRequestException(
            InvalidPageRequestException invalidPageRequestException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, INVALID_PAGE_REQUEST_EXCEPTION));
    }

    @ExceptionHandler(NumberRoomsPositiveException.class)
    public ResponseEntity<Map<String, String>> handleNumberRoomsPositiveException(
            NumberRoomsPositiveException numberRoomsPositiveException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, NUMBER_ROOMS_POSITIVE_EXCEPTION));
    }

    @ExceptionHandler(InvalidStarsCategoryException.class)
    public ResponseEntity<Map<String, String>> handleInvalidStarsCategoryException(
            InvalidStarsCategoryException invalidStarsCategoryException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, INVALID_STARS_CATEGORY_EXCEPTION));
    }

    @ExceptionHandler(InvalidLodgingTimeException.class)
    public ResponseEntity<Map<String, String>> handleInvalidLodgingTimeException(
            InvalidLodgingTimeException invalidLodgingTimeException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, INVALID_LODGING_TIME_EXCEPTION));
    }

    @ExceptionHandler(ExcelReportFileNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleExcelReportFileNotFoundException(
            ExcelReportFileNotFoundException excelReportFileNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, EXCEL_REPORT_NOT_FOUND_EXCEPTION));
    }

    @ExceptionHandler(ExcelReportGenerationException.class)
    public ResponseEntity<Map<String, String>> handleExcelReportGenerationException(
            ExcelReportGenerationException excelReportGenerationException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, EXCEL_REPORT_GENERATION_EXCEPTION));
    }

    @ExceptionHandler(PdfReportGenerationException.class)
    public ResponseEntity<Map<String, String>> handlePdfReportGenerationException(
            PdfReportGenerationException pdfReportGenerationException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, PDF_REPORT_GENERATION_EXCEPTION));
    }

    @ExceptionHandler(DuplicateHotelNameException.class)
    public ResponseEntity<Map<String, String>> handleDuplicateHotelNameException(
            DuplicateHotelNameException duplicateHotelNameException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, DUPLICATE_HOTEL_NAME_EXCEPTION));
    }

    @ExceptionHandler(DuplicateHotelPhoneException.class)
    public ResponseEntity<Map<String, String>> handleDuplicateHotelPhoneException(
            DuplicateHotelPhoneException duplicateHotelPhoneException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, DUPLICATE_HOTEL_PHONE_EXCEPTION));
    }

    @ExceptionHandler(DuplicateHotelAddressException.class)
    public ResponseEntity<Map<String, String>> handleDuplicateHotelAddressException(
            DuplicateHotelAddressException duplicateHotelAddressException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, DUPLICATE_HOTEL_ADDRESS_EXCEPTION));
    }
}
