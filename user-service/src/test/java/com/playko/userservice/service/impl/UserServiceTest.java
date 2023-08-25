package com.playko.userservice.service.impl;

import com.playko.userservice.model.UserModel;
import com.playko.userservice.repository.IUserRepository;
import com.playko.userservice.service.ModelData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @Mock
    private IUserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void saveUser() {
        UserModel userModel = ModelData.obtainUser();

        // Configurar el comportamiento simulado del repositorio
        when(userRepository.save(any(UserModel.class))).thenReturn(userModel);

        userService.saveUser(userModel);

        verify(userRepository, times(1)).save(userModel);
    }
}