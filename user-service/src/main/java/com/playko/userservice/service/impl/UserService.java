package com.playko.userservice.service.impl;



import com.playko.userservice.model.UserModel;
import com.playko.userservice.repository.IUserRepository;
import com.playko.userservice.service.IAuthPasswordEncoderPort;
import com.playko.userservice.service.IUserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.playko.userservice.configuration.Constants.CLIENT_ROLE_ID;
import static com.playko.userservice.configuration.Constants.EMPLOYEE_ROLE_ID;
import static com.playko.userservice.configuration.Constants.OWNER_ROLE_ID;
import static com.playko.userservice.configuration.Constants.USER_NOT_FOUND_EXCEPTION;

@Service
@Transactional
public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final IAuthPasswordEncoderPort authPasswordEncoderPort;

    public UserService(IUserRepository userRepository, IAuthPasswordEncoderPort authPasswordEncoderPort) {
        this.userRepository = userRepository;
        this.authPasswordEncoderPort = authPasswordEncoderPort;
    }

    @Override
    public void saveUser(UserModel userModel) {
        userModel.setPassword(authPasswordEncoderPort.encodePassword(userModel.getPassword()));
        userRepository.save(userModel);
    }

    @Override
    public Optional<UserModel> getUserByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email));
    }

    @Override
    public Optional<UserModel> getOwner(Long id) {
        return Optional.ofNullable(userRepository.findByIdAndRoleId(id, OWNER_ROLE_ID)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_EXCEPTION)));
    }

    @Override
    public Optional<UserModel> getEmployee(Long id) {
        return Optional.ofNullable(userRepository.findByIdAndRoleId(id, EMPLOYEE_ROLE_ID)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_EXCEPTION)));
    }

    @Override
    public Optional<UserModel> getClient(Long id) {
        return Optional.ofNullable(userRepository.findByIdAndRoleId(id, CLIENT_ROLE_ID)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_EXCEPTION)));
    }
}
