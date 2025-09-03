package ru.gigaden.companyservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.gigaden.companyservice.dto.CompanyCreateDto;
import ru.gigaden.companyservice.dto.CompanyResponseDto;
import ru.gigaden.companyservice.service.CompanyService;

/**
 * Controller for working with companies
 */
@RestController
@RequestMapping("/api/companies")
@AllArgsConstructor
@Tag(name = "Companies", description = "Company management controller")
@Slf4j
public class CompanyController {

    private final CompanyService companyService;

    /**
     * Gets all companies
     */
    @GetMapping
    @Operation(summary = "Get companies", description = "Retrieves all companies from the database")
    public ResponseEntity<Page<CompanyResponseDto>> getAllCompanies(
            @RequestParam(defaultValue = "0") @PositiveOrZero(message = "Page value should be positive or zero") int page,
            @RequestParam(defaultValue = "10") @PositiveOrZero(message = "Size value should be positive or zero") int size
    ) {
        log.debug("Getting all companies page = {}, size = {}", page, size);
        Page<CompanyResponseDto> companies = companyService.getAllCompanies(page, size);

        return new ResponseEntity<>(companies, HttpStatus.OK);
    }

    /**
     * Gets a company by id
     */
    @GetMapping("/{companyId}")
    @Operation(summary = "Get company", description = "Retrieves a company by their id")
    public ResponseEntity<CompanyResponseDto> getCompanyById(
            @PathVariable @Positive(message = "Company id should be positive") Long companyId
    ) {
        log.debug("Getting company by id {}", companyId);
        CompanyResponseDto companyResponseDto = companyService.getCompanyDtoById(companyId);

        return new ResponseEntity<>(companyResponseDto, HttpStatus.OK);
    }

    /**
     * Checks a company by id
     */
    @GetMapping("/{companyId}/exist")
    @Operation(summary = "Check company", description = "Check a company by id")
    public ResponseEntity<Boolean> checkCompanyIsExistById(
            @PathVariable @Positive(message = "Company id should be positive") Long companyId
    ) {
        log.debug("Checking company by id {}", companyId);
        boolean companyIsExist = companyService.checkCompanyIsExist(companyId);

        return new ResponseEntity<>(companyIsExist, HttpStatus.OK);
    }

    /**
     * Adds a new company
     */
    @PostMapping
    @Operation(summary = "Create company", description = "Allows adding a new company to the database")
    public ResponseEntity<CompanyResponseDto> createNewCompany(@Valid @RequestBody CompanyCreateDto companyCreateDto) {
        log.debug("Creating company: {}", companyCreateDto);
        CompanyResponseDto responseDto = companyService.createCompany(companyCreateDto);

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    /**
     * Updates an existing company
     */
    @PutMapping("/{companyId}")
    @Operation(summary = "Update company", description = "Allows updating company data in the database")
    public ResponseEntity<CompanyResponseDto> updateCompany(
            @PathVariable @Positive(message = "Company id should be positive") Long companyId,
            @Valid @RequestBody CompanyCreateDto companyCreateDto
    ) {
        log.debug("Updating company with id {}", companyId);
        CompanyResponseDto responseDto = companyService.updateCompany(companyId, companyCreateDto);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    /**
     * Deletes a company by id
     */
    @DeleteMapping("/{companyId}")
    @Operation(summary = "Delete company", description = "Allows deleting a company by their id")
    public ResponseEntity<String> deleteCompany(
            @PathVariable @Positive(message = "Company id should be positive") Long companyId
    ) {
        log.debug("Deleting company with id {}", companyId);
        companyService.deleteCompanyById(companyId);

        return new ResponseEntity<>("Company deleted", HttpStatus.OK);
    }
}