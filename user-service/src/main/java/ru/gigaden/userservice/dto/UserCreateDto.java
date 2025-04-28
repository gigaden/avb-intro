package ru.gigaden.userservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record UserCreateDto(@NotNull String firstName,
                            @NotNull String lastName,
                            @NotNull String phoneNumber,
                            @NotNull @Positive Long companyId) {
}
