package ru.gigaden.userservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.gigaden.userservice.dto.UserCreateDto;
import ru.gigaden.userservice.dto.UserResponseDto;
import ru.gigaden.userservice.service.UserService;

/**
 * Controller for working with users
 */
@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
@Slf4j
@Tag(name = "Users", description = "User management controller")
public class UserController {

    private final UserService userService;

    /**
     * Gets all users
     */
    @GetMapping
    @Operation(summary = "Get users", description = "Retrieves all users from the database")
    public ResponseEntity<Page<UserResponseDto>> getAllUsers(
            @RequestParam(defaultValue = "0") @PositiveOrZero(message = "Page value should be positive or zero") int page,
            @RequestParam(defaultValue = "10") @PositiveOrZero(message = "Size value should be positive or zero") int size
    ) {
        log.debug("Getting all users page = {}, size = {}", page, size);
        Page<UserResponseDto> users = userService.getAllUsers(page, size);

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Gets a user by id
     */
    @GetMapping("/{userId}")
    @Operation(summary = "Get user", description = "Retrieves a user by their id")
    public ResponseEntity<UserResponseDto> getUserById(
            @PathVariable @Positive(message = "User id should be positive") Long userId
    ) {
        log.debug("Getting user with an id {}", userId);
        UserResponseDto userResponseDto = userService.getUserDtoById(userId);

        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    /**
     * Gets all users
     */
    @GetMapping("/company/{companyId}")
    @Operation(summary = "Get users by company Id", description = "Retrieves all users from the database by company Id")
    public ResponseEntity<Page<UserResponseDto>> getAllUsersByCompanyId(
            @PathVariable @Positive(message = "Company id should be positive") Long companyId,
            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "10") @PositiveOrZero int size
    ) {
        log.debug("Getting all users by company id = {} page = {}, size = {}", companyId, page, size);
        Page<UserResponseDto> users = userService.getAllUsersByCompanyId(companyId, page, size);

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Adds a new user
     */
    @PostMapping
    @Operation(summary = "Create user", description = "Allows adding a new user to the database")
    public ResponseEntity<UserResponseDto> createNewUser(@Valid @RequestBody UserCreateDto userCreateDto) {
        log.debug("Creating user: {}", userCreateDto);
        UserResponseDto responseDto = userService.createUser(userCreateDto);

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    /**
     * Updates an existing user
     */
    @PutMapping("/{userId}")
    @Operation(summary = "Update user", description = "Allows updating user data in the database")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable @Positive(message = "User id should be positive") Long userId,
            @Valid @RequestBody UserCreateDto userCreateDto
    ) {
        log.debug("Updating user with id {}", userId);
        UserResponseDto responseDto = userService.updateUser(userId, userCreateDto);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    /**
     * Deletes a user by id
     */
    @DeleteMapping("/{userId}")
    @Operation(summary = "Delete user", description = "Allows deleting a user by their id")
    public ResponseEntity<String> deleteUser(
            @PathVariable @Positive(message = "User id should be positive") Long userId
    ) {
        log.debug("Deleting user with id {}", userId);
        userService.deleteUserById(userId);

        return new ResponseEntity<>("User deleted", HttpStatus.OK);
    }
}