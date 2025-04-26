package ru.gigaden.userservice.dto;

import java.math.BigDecimal;

public record CompanyResponseDto(Long id,
                                 String companyName,
                                 BigDecimal budget) {
}
