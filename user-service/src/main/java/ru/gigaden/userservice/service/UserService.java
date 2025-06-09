package ru.gigaden.userservice.service;

import org.springframework.data.domain.Page;
import ru.gigaden.userservice.dto.UserCreateDto;
import ru.gigaden.userservice.dto.UserResponseDto;
import ru.gigaden.userservice.entity.User;

/**
 * Contains the core logic for working with users
 */
public interface UserService {

    /**
     * Creates a user
     *
     * @param userCreateDto object with fields of the new user
     */
    UserResponseDto createUser(UserCreateDto userCreateDto);

    /**
     * Gets a user by id
     *
     * @param userId user id
     * @throws ru.gigaden.userservice.exception.UserNotFoundException if user is not found
     */
    User getUserById(Long userId);

    /**
     * Gets a user DTO by id
     *
     * @param userId user id
     * @throws ru.gigaden.userservice.exception.UserNotFoundException if user is not found
     */
    UserResponseDto getUserDtoById(Long userId);

    /**
     * Gets a list of all users
     *
     * @return returns an empty list if there are no users
     */
    Page<UserResponseDto> getAllUsers(int page, int size);

    /**
     * Gets a list of all users by companyId
     *
     * @return returns an empty list if there are no users
     */
    Page<UserResponseDto> getAllUsersByCompanyId(Long companyId, int page, int size);

    /**
     * Updates a user
     *
     * @param userId        user id
     * @param userCreateDto entity with new user fields
     * @throws ru.gigaden.userservice.exception.UserNotFoundException if user is not found
     */
    UserResponseDto updateUser(Long userId, UserCreateDto userCreateDto);

    /**
     * Deletes a user by id
     *
     * @param userId user id
     * @throws ru.gigaden.userservice.exception.UserNotFoundException if user is not found
     */
    void deleteUserById(Long userId);
}