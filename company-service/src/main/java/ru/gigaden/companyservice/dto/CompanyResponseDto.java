package ru.gigaden.companyservice.dto;

import java.math.BigDecimal;
import java.util.ArrayList;

public record CompanyResponseDto(Long id,
                                 String companyName,
                                 BigDecimal budget,
                                 ArrayList<UserResponseDto> users) {
}
