package com.rezilux.auth_services.Mappers;

import com.rezilux.auth_services.Models.User;
import com.rezilux.auth_services.dtos.SignUpDto;
import com.rezilux.auth_services.dtos.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto (User u);

    @Mapping(target = "password" , ignore = true)
    User signUpToUser (SignUpDto signUpDto);
}
