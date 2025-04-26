package ru.gigaden.userservice.dto;

public record UserCreateDto(String firstName,
                            String lastName,
                            String phoneNumber,
                            Long companyId) {
}
