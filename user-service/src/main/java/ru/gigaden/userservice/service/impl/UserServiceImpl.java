package ru.gigaden.userservice.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gigaden.userservice.client.CompanyClient;
import ru.gigaden.userservice.dto.CompanyResponseDto;
import ru.gigaden.userservice.dto.UserCreateDto;
import ru.gigaden.userservice.dto.UserResponseDto;
import ru.gigaden.userservice.entity.User;
import ru.gigaden.userservice.exception.CompanyNotFoundException;
import ru.gigaden.userservice.exception.UserNotFoundException;
import ru.gigaden.userservice.mapper.UserMapper;
import ru.gigaden.userservice.repository.UserRepository;
import ru.gigaden.userservice.service.UserService;

import java.util.Collection;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final CompanyClient companyClient;

    @Override
    public UserResponseDto createUser(UserCreateDto userCreateDto) {
        log.info("Trying to create the user: {}", userCreateDto);
        if (!companyClient.checkCompanyIsExist(userCreateDto.companyId())) {
            throw new CompanyNotFoundException("Company is not exist");
        }
        User user = userRepository.save(userMapper.mapCreateUserDtoToUser(userCreateDto));
        log.info("The User with id {} has been created", user.getId());

        return userMapper.mapUserToResponseDto(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDto getUserDtoById(Long userId) {
        User user = getUserById(userId);
        CompanyResponseDto companyResponseDto = companyClient.getCompanyById(user.getCompanyId())
                .orElse(null);

        return userMapper.mapUserToResponseDto(user, companyResponseDto);
    }

    @Override
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
    public Collection<UserResponseDto> getAllUsers() {
        log.info("Trying to get all users");
        Collection<User> users = userRepository.findAll();
        log.info("All users has been received");

        return users.stream()
                .map(el -> userMapper
                        .mapUserToResponseDto(el, companyClient.getCompanyById(el.getCompanyId()).orElse(null)))
                .toList();
    }

    @Override
    public Collection<UserResponseDto> getAllUsersByCompanyId(Long companyId) {
        log.info("Trying to get all users by company id {}", companyId);
        Collection<User> users = userRepository.findAllByCompanyId(companyId);
        log.info("All users with company id {} has been received", companyId);

        return users.stream().map(userMapper::mapUserToResponseDto).toList();
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
