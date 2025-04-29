package ru.gigaden.userservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
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

import java.util.Collection;

/**
 * Controller for working with users
 */
@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
@Tag(name = "Users", description = "User management controller")
public class UserController {

    private final UserService userService;

    /**
     * Gets all users
     */
    @GetMapping
    @Operation(summary = "Get users", description = "Retrieves all users from the database")
    public ResponseEntity<Collection<UserResponseDto>> getAllUsers() {
        Collection<UserResponseDto> users = userService.getAllUsers();

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Gets a user by id
     */
    @GetMapping("/{userId}")
    @Operation(summary = "Get user", description = "Retrieves a user by their id")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long userId) {
        UserResponseDto userResponseDto = userService.getUserDtoById(userId);

        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    /**
     * Gets all users
     */
    @GetMapping("/company/{companyId}")
    @Operation(summary = "Get users by company Id", description = "Retrieves all users from the database by company Id")
    public ResponseEntity<Collection<UserResponseDto>> getAllUsersByCompanyId(@PathVariable Long companyId) {
        Collection<UserResponseDto> users = userService.getAllUsersByCompanyId(companyId);

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Adds a new user
     */
    @PostMapping
    @Operation(summary = "Create user", description = "Allows adding a new user to the database")
    public ResponseEntity<UserResponseDto> createNewUser(@Valid @RequestBody UserCreateDto userCreateDto) {
        UserResponseDto responseDto = userService.createUser(userCreateDto);

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    /**
     * Updates an existing user
     */
    @PutMapping("/{userId}")
    @Operation(summary = "Update user", description = "Allows updating user data in the database")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long userId, @Valid @RequestBody UserCreateDto userCreateDto) {
        UserResponseDto responseDto = userService.updateUser(userId, userCreateDto);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    /**
     * Deletes a user by id
     */
    @DeleteMapping("/{userId}")
    @Operation(summary = "Delete user", description = "Allows deleting a user by their id")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        userService.deleteUserById(userId);

        return new ResponseEntity<>("User deleted", HttpStatus.OK);
    }
}