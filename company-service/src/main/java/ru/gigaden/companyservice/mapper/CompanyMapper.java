package ru.gigaden.companyservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.gigaden.companyservice.dto.CompanyCreateDto;
import ru.gigaden.companyservice.dto.CompanyResponseDto;
import ru.gigaden.companyservice.dto.UserResponseDto;
import ru.gigaden.companyservice.entity.Company;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CompanyMapper {

    @Mapping(target = "id", source = "company.id")
    @Mapping(target = "companyName", source = "company.companyName")
    @Mapping(target = "budget", source = "company.budget")
    @Mapping(target = "users", source = "users")
    CompanyResponseDto mapCompanyToResponseDto(Company company, List<UserResponseDto> users);

    Company mapCreateCompanyDtoToCompany(CompanyCreateDto dto);

    default CompanyResponseDto mapCompanyToResponseDto(Company company) {
        return mapCompanyToResponseDto(company, null);
    }
}
