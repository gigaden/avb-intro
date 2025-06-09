package ru.gigaden.userservice.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

import java.util.List;

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
        validateCompanyIsExist(userCreateDto.companyId());

        User user = userMapper.mapCreateUserDtoToUser(userCreateDto);
        User savedUser = userRepository.save(user);
        UserResponseDto response = userMapper.mapUserToResponseDto(savedUser);
        log.debug("The User with id {} has been created", savedUser.getId());

        return response;
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
        User user = userRepository.findById(userId).orElseThrow(() -> {
            log.warn("The user with id {} does not exist", userId);
            return new UserNotFoundException("The user doesn't exist");
        });
        log.debug("The user with id {} has been received", userId);

        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserResponseDto> getAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> usersPage = userRepository.findAll(pageable);
        List<UserResponseDto> result = usersPage.getContent().stream()
                .map(el -> userMapper
                        .mapUserToResponseDto(el, companyClient.getCompanyById(el.getCompanyId()).orElse(null)))
                .toList();

        Page<UserResponseDto> users = new PageImpl<>(
                result,
                usersPage.getPageable(),
                usersPage.getTotalElements()
        );
        log.debug("{} users has been received", users.getNumberOfElements());

        return users;
    }

    @Override
    public Page<UserResponseDto> getAllUsersByCompanyId(Long companyId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> usersPage = userRepository.findAllByCompanyId(companyId, pageable);
        List<UserResponseDto> result = usersPage.getContent().stream().map(userMapper::mapUserToResponseDto).toList();

        Page<UserResponseDto> users = new PageImpl<>(
                result,
                usersPage.getPageable(),
                usersPage.getTotalElements()
        );
        log.debug("Users with company id {} has been received", companyId);

        return users;
    }

    @Override
    public UserResponseDto updateUser(Long userId, UserCreateDto userCreateDto) {
        User user = getUserById(userId);
        setNewUsersField(user, userCreateDto);
        log.debug("The user with id {} has been updated", userId);

        return userMapper.mapUserToResponseDto(user);
    }

    @Override
    public void deleteUserById(Long userId) {
        User user = getUserById(userId);
        userRepository.delete(user);
        log.debug("The user with id {} has been deleted", userId);
    }

    public void setNewUsersField(User user, UserCreateDto dto) {
        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());
        user.setPhoneNumber(dto.phoneNumber());
        user.setCompanyId(dto.companyId());
    }

    private void validateCompanyIsExist(Long companyId) {
        if (!companyClient.checkCompanyIsExist(companyId)) {
            throw new CompanyNotFoundException("Company is not exist");
        }
    }
}
