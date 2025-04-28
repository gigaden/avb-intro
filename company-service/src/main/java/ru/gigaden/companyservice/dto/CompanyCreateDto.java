package ru.gigaden.companyservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record CompanyCreateDto(@NotNull String companyName,
                               @PositiveOrZero BigDecimal budget) {
}
