package com.playko.userservice.controller;

import com.playko.userservice.controller.dto.UserRequestDto;
import com.playko.userservice.controller.mapper.IUserRequestMapper;
import com.playko.userservice.model.UserModel;
import com.playko.userservice.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/users/v1/employee")
public class EmployeeRestController {

    private final IUserService userService;
    private final IUserRequestMapper userRequestMapper;

    public EmployeeRestController(IUserService userService, IUserRequestMapper userRequestMapper) {
        this.userService = userService;
        this.userRequestMapper = userRequestMapper;
    }

    @Operation(summary = "Get a user with role CLIENT",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User returned",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserRequestDto.class))),
                    @ApiResponse(responseCode = "404", description = "User not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))})
    @GetMapping("/getClient/{id}")
    public ResponseEntity<Optional<UserModel>> getClient(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getClient(id));
    }
}
