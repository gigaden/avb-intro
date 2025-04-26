package ru.gigaden.userservice.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.gigaden.userservice.dto.UserCreateDto;
import ru.gigaden.userservice.dto.UserResponseDto;
import ru.gigaden.userservice.entity.User;
import ru.gigaden.userservice.exception.UserNotFoundException;
import ru.gigaden.userservice.mapper.UserMapper;
import ru.gigaden.userservice.repository.UserRepository;
import ru.gigaden.userservice.service.UserService;

import java.util.Collection;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto createUser(UserCreateDto userCreateDto) {
        log.info("Trying to create the user: {}", userCreateDto);
        User user = userRepository.save(userMapper.mapCreateUserDtoToUser(userCreateDto));
        log.info("The User with id {} has been created", user.getId());

        return userMapper.mapUserToResponseDto(user);
    }

    @Override
    public UserResponseDto getUserDtoById(Long userId) {
        return userMapper.mapUserToResponseDto(getUserById(userId));
    }

    @Override
    public User getUserById(Long userId) {
        log.info("Trying to get the user with an id {}", userId);
        User user = userRepository.findById(userId).orElseThrow(() -> {
            log.warn("The user with id {} does not exist", userId);
            return new UserNotFoundException("The user doesn't exist");
        });
        log.info("The user with id {} has been received", userId);

        return user;
    }

    @Override
    public Collection<UserResponseDto> getAllUsers() {
        log.info("Trying to get all users");
        Collection<User> users = userRepository.findAll();
        log.info("All users has been received");

        return users.stream()
                .map(userMapper::mapUserToResponseDto)
                .toList();
    }

    @Override
    public UserResponseDto updateUser(Long userId, UserCreateDto userCreateDto) {
        log.info("Trying to update the user with id {}", userId);
        User user = getUserById(userId);
        setNewUsersField(user, userCreateDto);
        log.info("The user with id {} has been updated", userId);

        return userMapper.mapUserToResponseDto(user);
    }

    @Override
    public void deleteUserById(Long userId) {
        log.info("Trying to delete the user with id {}", userId);
        User user = getUserById(userId);
        userRepository.delete(user);
        log.info("The user with id {} has been deleted", userId);
    }

    public void setNewUsersField(User user, UserCreateDto dto) {
        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());
        user.setPhoneNumber(dto.phoneNumber());
        user.setCompanyId(dto.companyId());
    }
}
