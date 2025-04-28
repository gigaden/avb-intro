package ru.gigaden.companyservice.service;

import ru.gigaden.companyservice.dto.CompanyCreateDto;
import ru.gigaden.companyservice.dto.CompanyResponseDto;
import ru.gigaden.companyservice.entity.Company;

import java.util.Collection;

/**
 * Contains the core logic for working with companys
 */
public interface CompanyService {

    /**
     * Creates a company
     *
     * @param companyCreateDto object with fields of the new company
     */
    CompanyResponseDto createCompany(CompanyCreateDto companyCreateDto);

    /**
     * Gets a company by id
     *
     * @param companyId company id
     * @throws ru.gigaden.companyservice.exception.CompanyNotFoundException if company is not found
     */
    Company getCompanyById(Long companyId);

    /**
     * Gets a company DTO by id
     *
     * @param companyId company id
     * @throws ru.gigaden.companyservice.exception.CompanyNotFoundException if company is not found
     */
    CompanyResponseDto getCompanyDtoById(Long companyId);

    /**
     * Gets a list of all companys
     *
     * @return returns an empty list if there are no companys
     */
    Collection<CompanyResponseDto> getAllCompanys();

    /**
     * Updates a company
     *
     * @param companyId        company id
     * @param companyCreateDto entity with new company fields
     * @throws ru.gigaden.companyservice.exception.CompanyNotFoundException if company is not found
     */
    CompanyResponseDto updateCompany(Long companyId, CompanyCreateDto companyCreateDto);

    /**
     * Deletes a company by id
     *
     * @param companyId company id
     * @throws ru.gigaden.companyservice.exception.CompanyNotFoundException if company is not found
     */
    void deleteCompanyById(Long companyId);

    /**
     * Check a company by id
     *
     * @param companyId company id
     * @return true if company is exist
     */
    boolean checkCompanyIsExist(Long companyId);
}