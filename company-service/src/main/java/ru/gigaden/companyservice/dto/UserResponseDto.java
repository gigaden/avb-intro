package ru.gigaden.companyservice.dto;

public record UserResponseDto(Long id,
                              String firstName,
                              String lastName,
                              String phoneNumber) {
}
