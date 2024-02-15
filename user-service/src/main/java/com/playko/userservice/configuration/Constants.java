package com.playko.userservice.configuration;

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
    public static final String USER_CREATED_MESSAGE = "User created successfully";
    public static final Long ADMIN_ROLE_ID = 1L;
    public static final Long OWNER_ROLE_ID = 2L;
    public static final Long EMPLOYEE_ROLE_ID = 3L;
    public static final Long CLIENT_ROLE_ID = 4L;
    public static final String USER_NOT_FOUND_EXCEPTION = "User not found.";
    public static final String OWNER_NOT_FOUND_EXCEPTION = "The owner cannot be found with this ID.";
    public static final String EMPLOYEE_NOT_FOUND_EXCEPTION = "The employee cannot be found with this ID.";
    public static final String CLIENT_NOT_FOUND_EXCEPTION = "The client cannot be found with this ID.";

}
