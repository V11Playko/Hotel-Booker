package com.playko.userservice.service;

import com.playko.userservice.model.UserModel;

import java.util.Optional;

public interface IUserService {

    void saveUser(UserModel userModel);
    Optional<UserModel> getUserByEmail(String email);
    Optional<UserModel> getOwner(Long id);
    Optional<UserModel> getEmployee(Long id);
    Optional<UserModel> getClient(Long id);
}
