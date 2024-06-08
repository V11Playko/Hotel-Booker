package com.playko.userservice.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserRequestDto {
    @NotBlank(message = "Field 'name' it's required")
    private String name;
    @NotBlank(message = "Field 'surname' it's required")
    private String surname;
    @Pattern(regexp = "^\\+?57\\s(3[0-2]|7[0-1])\\d{8}$", message = "Field 'numberPhone' must be a valid number phone. Enter the format +57 3...")
    @NotBlank(message = "Field 'phoneNumber' it's required")
    private String phone;
    @NotBlank(message = "Field 'email' it's required")
    @Email(message = "Field 'email' must be a valid email direction. Enter the format name@example.com")
    private String email;
    @NotBlank(message = "Field 'password' it's required")
    private String password;

    private Long role;
}