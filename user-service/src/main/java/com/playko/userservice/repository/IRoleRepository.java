package com.playko.userservice.repository;

import com.playko.userservice.model.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRepository extends JpaRepository<RoleModel, Long> {
    RoleModel findByName(String name);
}