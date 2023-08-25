package com.playko.userservice.service;


import com.playko.userservice.model.UserModel;

public class ModelData {

    public static UserModel obtainUser() {
        UserModel user = new UserModel();

        user.setName("Heinner");
        user.setSurname("Vega");
        user.setPhone("+57 3134647020");
        user.setEmail("johndoe@example.com");
        user.setPassword("password");

        return user;
    }
}
