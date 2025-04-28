package ru.gigaden.companyservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gigaden.companyservice.dto.CompanyCreateDto;
import ru.gigaden.companyservice.dto.CompanyResponseDto;
import ru.gigaden.companyservice.service.CompanyService;

import java.util.Collection;

/**
 * Controller for working with companies
 */
@RestController
@RequestMapping("/api/companies")
@AllArgsConstructor
@Tag(name = "Companies", description = "Company management controller")
public class CompanyController {

    private final CompanyService companyService;

    /**
     * Gets all companies
     */
    @GetMapping
    @Operation(summary = "Get companies", description = "Retrieves all companies from the database")
    public ResponseEntity<Collection<CompanyResponseDto>> getAllCompanies() {
        Collection<CompanyResponseDto> companies = companyService.getAllCompanys();

        return new ResponseEntity<>(companies, HttpStatus.OK);
    }

    /**
     * Gets a company by id
     */
    @GetMapping("/{companyId}")
    @Operation(summary = "Get company", description = "Retrieves a company by their id")
    public ResponseEntity<CompanyResponseDto> getCompanyById(@PathVariable Long companyId) {
        CompanyResponseDto companyResponseDto = companyService.getCompanyDtoById(companyId);

        return new ResponseEntity<>(companyResponseDto, HttpStatus.OK);
    }

    /**
     * Adds a new company
     */
    @PostMapping
    @Operation(summary = "Create company", description = "Allows adding a new company to the database")
    public ResponseEntity<CompanyResponseDto> createNewCompany(@Valid @RequestBody CompanyCreateDto companyCreateDto) {
        CompanyResponseDto responseDto = companyService.createCompany(companyCreateDto);

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    /**
     * Updates an existing company
     */
    @PutMapping("/{companyId}")
    @Operation(summary = "Update company", description = "Allows updating company data in the database")
    public ResponseEntity<CompanyResponseDto> updateCompany(@PathVariable Long companyId, @Valid @RequestBody CompanyCreateDto companyCreateDto) {
        CompanyResponseDto responseDto = companyService.updateCompany(companyId, companyCreateDto);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    /**
     * Deletes a company by id
     */
    @DeleteMapping("/{companyId}")
    @Operation(summary = "Delete company", description = "Allows deleting a company by their id")
    public ResponseEntity<String> deleteCompany(@PathVariable Long companyId) {
        companyService.deleteCompanyById(companyId);

        return new ResponseEntity<>("Company deleted", HttpStatus.OK);
    }
}