package ru.gigaden.userservice.dto;

public record UserResponseDto(Long id,
                              String firstName,
                              String lastName,
                              String phoneNumber,
                              CompanyResponseDto company) {
}
