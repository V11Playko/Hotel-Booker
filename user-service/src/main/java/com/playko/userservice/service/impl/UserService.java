package com.playko.userservice.service.impl;



import com.playko.userservice.model.UserModel;
import com.playko.userservice.repository.IUserRepository;
import com.playko.userservice.service.IAuthPasswordEncoderPort;
import com.playko.userservice.service.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
}
