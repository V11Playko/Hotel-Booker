package com.playko.userservice.repository;


import com.playko.userservice.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<UserModel, Long> {
    UserModel findByEmail(String email);
    Optional<UserModel> findByIdAndRoleId(Long idUser, Long idRole);
}
