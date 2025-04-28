package ru.gigaden.userservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.gigaden.userservice.dto.CompanyResponseDto;
import ru.gigaden.userservice.dto.UserCreateDto;
import ru.gigaden.userservice.dto.UserResponseDto;
import ru.gigaden.userservice.entity.User;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(target = "id", source = "user.id")
    @Mapping(target = "firstName", source = "user.firstName")
    @Mapping(target = "lastName", source = "user.lastName")
    @Mapping(target = "phoneNumber", source = "user.phoneNumber")
    @Mapping(target = "company", source = "company")
    UserResponseDto mapUserToResponseDto(User user, CompanyResponseDto company);

    User mapCreateUserDtoToUser(UserCreateDto userCreateDto);

    default UserResponseDto mapUserToResponseDto(User user) {
        return mapUserToResponseDto(user, null);
    }
}
