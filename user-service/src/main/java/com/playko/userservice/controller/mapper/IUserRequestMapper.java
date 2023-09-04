package com.playko.userservice.controller.mapper;

import com.playko.userservice.controller.dto.UserRequestDto;
import com.playko.userservice.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IUserRequestMapper {
    @Mapping(target = "role.id", source = "role")
    UserModel toUser(UserRequestDto userRequestDto);
}
