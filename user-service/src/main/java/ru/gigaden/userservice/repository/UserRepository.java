package ru.gigaden.userservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.gigaden.userservice.entity.User;

/**
 * A repository for working with users
 */
public interface UserRepository extends JpaRepository<User, Long> {

    Page<User> findAllByCompanyId(Long companyId, Pageable pageable);
}
