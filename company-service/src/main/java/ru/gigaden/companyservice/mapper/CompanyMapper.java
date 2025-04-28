package ru.gigaden.companyservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.gigaden.companyservice.dto.CompanyCreateDto;
import ru.gigaden.companyservice.dto.CompanyResponseDto;
import ru.gigaden.companyservice.entity.Company;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CompanyMapper {

    @Mapping(target = "users", ignore = true)
    CompanyResponseDto mapCompanyToResponseDto(Company company);

    Company mapCreateCompanyDtoToCompany(CompanyCreateDto dto);
}
