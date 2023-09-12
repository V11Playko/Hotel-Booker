package com.playko.userservice.controller;

import com.playko.userservice.configuration.Constants;
import com.playko.userservice.controller.dto.UserRequestDto;
import com.playko.userservice.controller.mapper.IUserRequestMapper;
import com.playko.userservice.model.UserModel;
import com.playko.userservice.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users/v1/owner")
public class OwnerRestController {

    private final IUserService userService;
    private final IUserRequestMapper userRequestMapper;

    public OwnerRestController(IUserService userService, IUserRequestMapper userRequestMapper) {
        this.userService = userService;
        this.userRequestMapper = userRequestMapper;
    }

    @Operation(summary = "Add a new user",
            responses = {
                    @ApiResponse(responseCode = "201", description = "User created",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "409", description = "User already exists",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error"))),
                    @ApiResponse(responseCode = "403", description = "Role not allowed for user creation",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))})
    @PostMapping("/save-employee")
    public ResponseEntity<Map<String, String>> saveEmployee(@Valid @RequestBody UserRequestDto user) {
        user.setRole(3L);
        userService.saveUser(userRequestMapper.toUser(user));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY, Constants.USER_CREATED_MESSAGE));
    }

    @Operation(summary = "Get a user with role EMPLOYEE",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User returned",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserRequestDto.class))),
                    @ApiResponse(responseCode = "404", description = "User not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))})
    @GetMapping("/getEmployee/{id}")
    public ResponseEntity<Optional<UserModel>> getEmployee(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getEmployee(id));
    }
}
