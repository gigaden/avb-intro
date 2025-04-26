package ru.gigaden.userservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.gigaden.userservice.dto.UserCreateDto;
import ru.gigaden.userservice.dto.UserResponseDto;
import ru.gigaden.userservice.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "companyId", ignore = true)
    UserResponseDto mapUserToResponseDto(User user);

    User mapCreateUserDtoToUser(UserCreateDto userCreateDto);
}
