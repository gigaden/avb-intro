package ru.gigaden.userservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record UserCreateDto(
        @NotBlank @Size(min = 5, max = 512, message = "First name must be between 5 and 512 characters") String firstName,
        @NotBlank @Size(min = 5, max = 512, message = "Last name must be between 5 and 512 characters") String lastName,
        @NotBlank @Size(min = 3, max = 128, message = "Phone number must be between 3 and 128 characters") String phoneNumber,
        @NotNull @Positive Long companyId) {
}
