package ru.gigaden.companyservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gigaden.companyservice.entity.Company;

/**
 * A repository for working with companies
 */
public interface CompanyRepository extends JpaRepository<Company, Long> {
}
