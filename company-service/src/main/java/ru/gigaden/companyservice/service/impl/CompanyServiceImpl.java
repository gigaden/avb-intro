package ru.gigaden.companyservice.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.gigaden.companyservice.dto.CompanyCreateDto;
import ru.gigaden.companyservice.dto.CompanyResponseDto;
import ru.gigaden.companyservice.entity.Company;
import ru.gigaden.companyservice.exception.CompanyNotFoundException;
import ru.gigaden.companyservice.mapper.CompanyMapper;
import ru.gigaden.companyservice.repository.CompanyRepository;
import ru.gigaden.companyservice.service.CompanyService;

import java.util.Collection;

@Service
@AllArgsConstructor
@Slf4j
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    @Override
    public CompanyResponseDto createCompany(CompanyCreateDto companyCreateDto) {
        log.info("Trying to create the company: {}", companyCreateDto);
        Company company = companyRepository.save(companyMapper.mapCreateCompanyDtoToCompany(companyCreateDto));
        log.info("The Company with id {} has been created", company.getId());

        return companyMapper.mapCompanyToResponseDto(company);
    }

    @Override
    public CompanyResponseDto getCompanyDtoById(Long companyId) {
        return companyMapper.mapCompanyToResponseDto(getCompanyById(companyId));
    }

    @Override
    public Company getCompanyById(Long companyId) {
        log.info("Trying to get the company with an id {}", companyId);
        Company company = companyRepository.findById(companyId).orElseThrow(() -> {
            log.warn("The company with id {} does not exist", companyId);
            return new CompanyNotFoundException("The company doesn't exist");
        });
        log.info("The company with id {} has been received", companyId);

        return company;
    }

    @Override
    public Collection<CompanyResponseDto> getAllCompanys() {
        log.info("Trying to get all companies");
        Collection<Company> companies = companyRepository.findAll();
        log.info("All companies has been received");

        return companies.stream()
                .map(companyMapper::mapCompanyToResponseDto)
                .toList();
    }

    @Override
    public CompanyResponseDto updateCompany(Long companyId, CompanyCreateDto companyCreateDto) {
        log.info("Trying to update the company with id {}", companyId);
        Company company = getCompanyById(companyId);
        setNewCompaniesField(company, companyCreateDto);
        log.info("The company with id {} has been updated", companyId);

        return companyMapper.mapCompanyToResponseDto(company);
    }

    @Override
    public void deleteCompanyById(Long companyId) {
        log.info("Trying to delete the company with id {}", companyId);
        Company company = getCompanyById(companyId);
        companyRepository.delete(company);
        log.info("The company with id {} has been deleted", companyId);
    }

    @Override
    public boolean checkCompanyIsExist(Long companyId) {
        log.info("Trying to check the company by id {}", companyId);
        boolean isExist = companyRepository.existsById(companyId);
        log.info("The company with id {} is exist", companyId);
        return isExist;
    }

    public void setNewCompaniesField(Company company, CompanyCreateDto dto) {
        company.setCompanyName(dto.companyName());
        company.setBudget(dto.budget());
    }
}
