package com.vic8b.usermanager.mapper;

import com.vic8b.usermanager.command.UserRegistrationCommand;
import com.vic8b.usermanager.dto.UserDto;
import com.vic8b.usermanager.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(UserRegistrationCommand command);
}
