package com.playko.userservice.service.impl;



import com.playko.userservice.model.UserModel;
import com.playko.userservice.repository.IUserRepository;
import com.playko.userservice.service.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService implements IUserService {

    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void saveUser(UserModel userModel) {
        userRepository.save(userModel);
    }
}
