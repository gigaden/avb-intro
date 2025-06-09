package ru.gigaden.companyservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record CompanyCreateDto(
        @NotBlank @Size(min = 5, max = 512,
                message = "Company name must be between 5 and 512 characters") String companyName,
        @PositiveOrZero(message = "Budget must be positive or zero") BigDecimal budget) {
}
