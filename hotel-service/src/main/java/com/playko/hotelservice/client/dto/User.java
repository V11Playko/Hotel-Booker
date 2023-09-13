package com.playko.hotelservice.client.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class User {
    private Long id;
    private String name;
    private String surname;
    private String dniNumber;
    private String phone;
    private LocalDate birthdate;
    private String email;
    private String password;
    private Long idRole;

    public User() {

    }
}
