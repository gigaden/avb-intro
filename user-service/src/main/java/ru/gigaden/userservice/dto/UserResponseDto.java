package ru.gigaden.userservice.dto;

public record UserResponseDto(Long Id,
                              Long firstName,
                              Long lastName,
                              String phoneNumber,
                              CompanyResponseDto company) {
}
