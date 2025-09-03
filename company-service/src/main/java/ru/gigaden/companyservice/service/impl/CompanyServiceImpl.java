package ru.gigaden.companyservice.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gigaden.companyservice.client.UserClient;
import ru.gigaden.companyservice.dto.CompanyCreateDto;
import ru.gigaden.companyservice.dto.CompanyResponseDto;
import ru.gigaden.companyservice.dto.UserResponseDto;
import ru.gigaden.companyservice.entity.Company;
import ru.gigaden.companyservice.exception.CompanyNotFoundException;
import ru.gigaden.companyservice.mapper.CompanyMapper;
import ru.gigaden.companyservice.repository.CompanyRepository;
import ru.gigaden.companyservice.service.CompanyService;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;
    private final UserClient userClient;

    @Override
    public CompanyResponseDto createCompany(CompanyCreateDto companyCreateDto) {
        Company company = companyRepository.save(companyMapper.mapCreateCompanyDtoToCompany(companyCreateDto));
        CompanyResponseDto response = companyMapper.mapCompanyToResponseDto(company);
        log.debug("The Company with id {} has been created", company.getId());

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public CompanyResponseDto getCompanyDtoById(Long companyId) {
        Company company = getCompanyById(companyId);
        List<UserResponseDto> users = userClient.getAllUsersByCompanyId(companyId);
        CompanyResponseDto response = companyMapper.mapCompanyToResponseDto(company, users);

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public Company getCompanyById(Long companyId) {
        Company company = companyRepository.findById(companyId).orElseThrow(() -> {
            log.warn("The company with id {} does not exist", companyId);
            return new CompanyNotFoundException("The company doesn't exist");
        });
        log.debug("The company with id {} has been received", companyId);

        return company;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CompanyResponseDto> getAllCompanies(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Company> companiesPage = companyRepository.findAll(pageable);

        List<CompanyResponseDto> result = companiesPage.getContent().stream()
                .map(c -> companyMapper
                        .mapCompanyToResponseDto(c, userClient.getAllUsersByCompanyId(c.getId())))
                .toList();

        Page<CompanyResponseDto> companies = new PageImpl<>(
                result,
                companiesPage.getPageable(),
                companiesPage.getTotalElements()
        );
        log.debug("{} companies has been received", companies.getNumberOfElements());

        return companies;
    }

    @Override
    public CompanyResponseDto updateCompany(Long companyId, CompanyCreateDto companyCreateDto) {
        Company company = getCompanyById(companyId);
        setNewCompaniesField(company, companyCreateDto);
        log.debug("The company with id {} has been updated", companyId);

        return companyMapper.mapCompanyToResponseDto(company);
    }

    @Override
    public void deleteCompanyById(Long companyId) {
        Company company = getCompanyById(companyId);
        companyRepository.delete(company);
        log.debug("The company with id {} has been deleted", companyId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean checkCompanyIsExist(Long companyId) {
        boolean isExist = companyRepository.existsById(companyId);
        log.debug("The company with id {} is exist", companyId);

        return isExist;
    }

    public void setNewCompaniesField(Company company, CompanyCreateDto dto) {
        company.setCompanyName(dto.companyName());
        company.setBudget(dto.budget());
    }
}
