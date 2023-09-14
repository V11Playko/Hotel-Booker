package com.playko.hotelservice.configuration;

public class Constants {

    private Constants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String SWAGGER_TITLE_MESSAGE = "User API";
    public static final String SWAGGER_DESCRIPTION_MESSAGE = "User microservice";
    public static final String SWAGGER_VERSION_MESSAGE = "1.0.0";
    public static final String SWAGGER_LICENSE_NAME_MESSAGE = "Apache 2.0";
    public static final String SWAGGER_LICENSE_URL_MESSAGE = "http://springdoc.org";
    public static final String SWAGGER_TERMS_OF_SERVICE_MESSAGE = "http://swagger.io/terms/";
    public static final String RESPONSE_MESSAGE_KEY = "message";
    public static final String RESPONSE_ERROR_MESSAGE_KEY = "error";
    public static final String HOTEL_CREATED_MESSAGE = "Hotel created successfully";
    public static final String SAVED_RESERVATION_MESSAGE = "Reservation successfully saved";
    public static final String ERROR_SAVED_RESERVATION = "Error when saving the reservation, verify that the data is done correctly.";
    public static final String NO_DATA_FOUND_MESSAGE = "No data found.";
    public static final String HOTEL_NOT_SAVE_EXCEPTION = "Error saving hotel and rooms.";
    public static final String HOTEL_NOT_FOUND_EXCEPTION = "Hotel not found";
    public static final String ROOM_NOT_FOUND_EXCEPTION = "Room not found";
    public static final String ROOM_UNVAILABLE_EXCEPTION = "Room not available.";
    public static final String INVALID_PAGE_REQUEST_EXCEPTION = "the page number and elements per page are not valid.";


}
